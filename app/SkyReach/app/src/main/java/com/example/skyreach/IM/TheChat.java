package com.example.skyreach.IM;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.IM.bean.ChatMsg;
import com.example.skyreach.IM.common.Ad_TheChat;
import com.example.skyreach.IM.common.TimeUtils;
import com.example.skyreach.IM.common.UserManager;
import com.example.skyreach.Ob.OBUser;
import com.example.skyreach.Ob.ObMsg;
import com.example.skyreach.R;
import com.example.skyreach.ShareP;
import com.example.skyreach.Tool.DB;
import com.example.skyreach.Tool.TIM;
import com.example.skyreach.Tool.TShow;
import com.example.skyreach.connect.tool.User;
import com.example.skyreach.connect.work.LoginIF;
import com.example.skyreach.tkrefreshlayout.RefreshListenerAdapter;
import com.example.skyreach.tkrefreshlayout.TwinklingRefreshLayout;
import com.xiaomi.mimc.MIMCMessage;
import com.xiaomi.mimc.MIMCServerAck;
import com.xiaomi.mimc.MIMCUser;
import com.xiaomi.mimc.common.MIMCConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TheChat extends Activity implements UserManager.OnHandleMIMCMsgListener{

    Ad_TheChat ad_theChat;
    List<OBUser> obUserList;
    @BindView(R.id.rec)
    RecyclerView rec;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;

    DB db;
    ShareP shareP;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thechat);
        ButterKnife.bind(this);
        UserManager.getInstance().setHandleMIMCMsgListener(this);
        View();
        shareP=new ShareP(this);
    }
    private void View(){
        db=new DB(this,DB.DB_NAME);
        obUserList=db.get();
        ad_theChat=new Ad_TheChat(obUserList);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setAdapter(ad_theChat);

        ad_theChat.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(TheChat.this,Ac_Chat.class);
                intent.putExtra("name",obUserList.get(position).getName());
                intent.putExtra("here",shareP.getString("uid"));
                intent.putExtra("there",obUserList.get(position).getUid());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //notice();
        UserManager userManager = UserManager.getInstance();
        MIMCUser user = userManager.getUser();
        if(!userManager.getAccount().equals(shareP.getString("uid"))){
            if(user!=null){
                user.logout();
            }
        }
        TIM.login(shareP.getString("uid"));
    }
    public void updateOnlineStatus(MIMCConstant.OnlineStatus status) {

    }
    // 处理单聊消息
    @Override
    public void onHandleMessage(final ChatMsg chatMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String here = UserManager.getInstance().getAccount();

                if(!chatMsg.getFromAccount().equals(here)) {//发送
                    for(User user : LoginIF.getInstance().getAllInf()){
                        if(user.getUserId().equals(chatMsg.getFromAccount())){
                            db.add(chatMsg.getFromAccount(),user.getUserName());
                            notice();
                        }
                    }
                }
            }
        });
    }
    private void notice(){
        //obUserList.clear();
        db=new DB(this,DB.DB_NAME);
        obUserList=db.get();
        ad_theChat.notifyDataSetChanged();
    }
    // 处理服务端消息确认
    @Override
    public void onHandleServerAck(final MIMCServerAck serverAck) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(Ac_Chat.this, "Server has received packetId: "
//                        + serverAck.getPacketId()
//                        + "\n" + TimeUtils.utc2Local(serverAck.getTimestamp()), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // 处理登录状态
    @Override
    public void onHandleStatusChanged(final MIMCConstant.OnlineStatus status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TShow.s(status.toString());
                // TODO: 2019/5/31
                updateOnlineStatus(status);
            }
        });
    }
    // 处理拉取单聊消息
    @Override
    public void onHandlePullP2PHistory(final String json, boolean isSuccess) {
//        if (isSuccess) {
//            json = ParseJson.parseP2PHistoryJson(this, json);
//        }
//        final String info = json;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                TShow.s(json);


//                TShow.s(json);
            }
        });
    }
    // 处理发送消息超时
    @Override
    public void onHandleSendMessageTimeout(MIMCMessage message) {
        final String info = new String(message.getPayload());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(Ac_Chat.this, "Send message timeout: " +
//                        info, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
