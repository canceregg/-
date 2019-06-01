package com.example.skyreach.IM;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyreach.IM.bean.ChatMsg;
import com.example.skyreach.IM.common.Ad_Chat;
import com.example.skyreach.IM.common.ParseJson;
import com.example.skyreach.IM.common.TimeUtils;
import com.example.skyreach.IM.common.UserManager;
import com.example.skyreach.Ob.ObMsg;
import com.example.skyreach.R;
import com.example.skyreach.ShareP;
import com.example.skyreach.Tool.TIM;
import com.example.skyreach.Tool.TShow;
import com.xiaomi.mimc.MIMCMessage;
import com.xiaomi.mimc.MIMCServerAck;
import com.xiaomi.mimc.MIMCUser;
import com.xiaomi.mimc.common.MIMCConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Ac_Chat extends Activity implements UserManager.OnHandleMIMCMsgListener{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.refresh)
    RecyclerView refresh;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.send)
    TextView send;


    private Ad_Chat mAdapter;
    private List<ObMsg> mDatas = new ArrayList<>();

    String here,there,nickName;

    ShareP shareP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_chat);
        ButterKnife.bind(this);
        shareP=new ShareP(this);
        // 设置处理MIMC消息监听器
        here=getIntent().getStringExtra("here");
        there=getIntent().getStringExtra("there");
        nickName=getIntent().getStringExtra("name");

        name.setText(nickName);

        UserManager.getInstance().setHandleMIMCMsgListener(this);

        refresh.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Ad_Chat( mDatas);
        refresh.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserManager userManager = UserManager.getInstance();
        MIMCUser user = userManager.getUser();
        if(!userManager.getAccount().equals(shareP.getString("uid"))){
            if(user!=null)  user.logout();
            TIM.login(shareP.getString("uid"));
        }else{
            if(user!=null)
            TIM.getMsg(here,there);
        }
    }
    public void updateOnlineStatus(MIMCConstant.OnlineStatus status) {

        if (status == MIMCConstant.OnlineStatus.ONLINE) {
            TIM.getMsg(here,there);
        } else {

        }
    }
    // 处理单聊消息
    @Override
    public void onHandleMessage(final ChatMsg chatMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                TShow.s("from:"+chatMsg.getFromAccount()+"\n"+new String(chatMsg.getMsg().getPayload()));


                ObMsg msg=new ObMsg(new String(chatMsg.getMsg().getPayload()), TimeUtils.utc2Local(chatMsg.getMsg().getTimestamp()),chatMsg.getFromAccount());
                String here = UserManager.getInstance().getAccount();

                if(msg.getUid().equals(here)) {//发送
                    msg.setUname(shareP.getString("name"));
                }else{
                    msg.setUname(nickName);
                }
                mDatas.add(msg);
                mAdapter.notifyDataSetChanged();
                refresh.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        });
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
                List<ObMsg> obMsgs=TIM.getM(json,nickName);
                if(obMsgs!=null&&obMsgs.size()!=0){
                    mAdapter.addData(obMsgs);
                    refresh.scrollToPosition(mAdapter.getItemCount() - 1);
                }

//                TShow.s(json);
            }
        });
    }
    @OnClick({R.id.back, R.id.send,R.id.content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send:
                String c=content.getText().toString().trim();
                if(!c.equals("")){
                    TIM.sendMsg(there,c);
                }else{
                    TShow.s("请说话");
                }
                content.setText("");
                break;
            case R.id.content:
                content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mDatas.size()>3) refresh.scrollToPosition(mAdapter.getItemCount() - 1);
                    }
                },333);
                break;
        }
    }
    // 处理发送消息超时
    @Override
    public void onHandleSendMessageTimeout(MIMCMessage message) {
        final String info = new String(message.getPayload());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
