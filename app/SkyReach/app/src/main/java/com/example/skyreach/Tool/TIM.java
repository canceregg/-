package com.example.skyreach.Tool;

import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.example.skyreach.IM.bean.Msg;
import com.example.skyreach.IM.common.Constant;
import com.example.skyreach.IM.common.TimeUtils;
import com.example.skyreach.IM.common.UserManager;
import com.example.skyreach.Ob.ObMsg;
import com.example.skyreach.R;
import com.xiaomi.mimc.MIMCUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TIM {


    public static void login(String account){
        MIMCUser user = UserManager.getInstance().newUser(account);
        user.login();
    }
    public static void logout(String account){
        MIMCUser user = UserManager.getInstance().newUser(account);
        user.logout();
    }
    public static void sendMsg(String to,String msg){
        UserManager userManager = UserManager.getInstance();
        MIMCUser user = userManager.getUser();
        if (user != null)
            userManager.sendMsg(to, msg.getBytes(), Constant.TEXT);
    }
    public static void getMsg(String here,String there){
        int beginYear, beginMonth, beginDay, beginHour, beginMinute;
        int endYear, endMonth, endDay, endHour, endMinute;
        Calendar cal=Calendar.getInstance();
        String beginDateTime,endDateTime;

        endYear = beginYear=cal.get(Calendar.YEAR);
        endMonth = beginMonth=cal.get(Calendar.MONTH);
        endDay = beginDay=cal.get(Calendar.DAY_OF_MONTH);

        endHour = beginHour = cal.get(Calendar.HOUR_OF_DAY);
        endMinute = beginMinute = cal.get(Calendar.MINUTE);

        beginDateTime = Long.toString(TimeUtils.local2UTC(beginYear, beginMonth, beginDay, beginHour-3, beginMinute));
        endDateTime = Long.toString(TimeUtils.local2UTC(endYear, endMonth, endDay, endHour, endMinute));

        UserManager.getInstance().pullP2PHistory(here, there, beginDateTime, endDateTime);
    }
    public static List<ObMsg> getM(String info,String name){
        List<ObMsg> obMsgs=null;
        try{
            JSONObject object = new JSONObject(info);
            object = object.getJSONObject("data");
            JSONArray members = object.getJSONArray("messages");
            obMsgs=new ArrayList<>();
            for (int i = 0; i < members.length(); i++) {
                JSONObject member = members.getJSONObject(i);

                String payload = new String(Base64.decode(member.getString("payload"), Base64.DEFAULT));
                Msg msg = null;
                try {
                    msg = JSON.parseObject(payload, Msg.class);
                } catch (Exception e) {
                }
                payload = (msg == null ? payload : new String(msg.getPayload()));

                ObMsg obMsg=new ObMsg(payload,TimeUtils.utc2Local(member.getLong("ts")),member.getString("fromAccount"));
                obMsg.setUname(name);
                obMsgs.add(obMsg);
            }
        }catch (Exception e){

        }
        return obMsgs;
    }
}
