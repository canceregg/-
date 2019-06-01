package com.example.skyreach.IM;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyreach.IM.bean.ChatMsg;
import com.example.skyreach.IM.common.Ad_Chat;
import com.example.skyreach.IM.common.ChatAdapter;
import com.example.skyreach.IM.common.ParseJson;
import com.example.skyreach.IM.common.TimeUtils;
import com.example.skyreach.IM.common.UserManager;
import com.example.skyreach.IM.dialog.LoginDialog;
import com.example.skyreach.IM.dialog.PullP2PHistoryMsgDialog;
import com.example.skyreach.IM.dialog.SendMsgDialog;
import com.example.skyreach.Ob.ObMessage;
import com.example.skyreach.Ob.ObMsg;
import com.example.skyreach.R;
import com.example.skyreach.Tool.TShow;
import com.xiaomi.mimc.MIMCGroupMessage;
import com.xiaomi.mimc.MIMCMessage;
import com.xiaomi.mimc.MIMCServerAck;
import com.xiaomi.mimc.MIMCUser;
import com.xiaomi.mimc.common.MIMCConstant;

import java.util.ArrayList;
import java.util.List;

public class IMain extends Activity implements UserManager.OnHandleMIMCMsgListener {
    private Ad_Chat mAdapter;
    private RecyclerView mRecyclerView;
    private List<ObMsg> mDatas = new ArrayList<>();

    GroupInfoDialog groupInfoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_imain);
        groupInfoDialog = new GroupInfoDialog(this);


        // 设置处理MIMC消息监听器
        UserManager.getInstance().setHandleMIMCMsgListener(this);

        // 登录
        findViewById(R.id.mimc_login).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog loginDialog = new LoginDialog(IMain.this);
                        loginDialog.show();
                    }
                });

        // 注销
        findViewById(R.id.mimc_logout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MIMCUser user = UserManager.getInstance().getUser();
                        if (user != null) {
                            user.logout();
                        }
                    }
                });

        // 发送消息
        findViewById(R.id.mimc_sendMsg).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog sendMsgDialog = new SendMsgDialog(IMain.this);
                        sendMsgDialog.show();
                    }
                });




        // 拉取单聊休息记录
        findViewById(R.id.btn_p2p_history).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dlgP2PHistory = new PullP2PHistoryMsgDialog(IMain.this);
                        dlgP2PHistory.show();
                    }
                });



        mRecyclerView = findViewById(R.id.rv_chat);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Ad_Chat( mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updateOnlineStatus(MIMCConstant.OnlineStatus status) {
        TextView textView = findViewById(R.id.mimc_status);
        Drawable drawable;
        if (status == MIMCConstant.OnlineStatus.ONLINE) {
            drawable = getResources().getDrawable(R.drawable.point_h);
        } else {
            drawable = getResources().getDrawable(R.drawable.point);
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null,
                null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (UserManager.getInstance().getUser() != null) {
            updateOnlineStatus(UserManager.getInstance().getUser().getOnlineStatus());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 处理单聊消息
    @Override
    public void onHandleMessage(final ChatMsg chatMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TShow.s("from:"+chatMsg.getFromAccount()+"\n"+new String(chatMsg.getMsg().getPayload()));
                ObMsg msg=new ObMsg(new String(chatMsg.getMsg().getPayload()),TimeUtils.utc2Local(chatMsg.getMsg().getTimestamp()),chatMsg.getFromAccount());
                mDatas.add(msg);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        });
    }


    public List<ObMsg> getDatas() {
        return mDatas;
    }

    // 处理登录状态
    @Override
    public void onHandleStatusChanged(final MIMCConstant.OnlineStatus status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                TShow.s(status.toString());
                // TODO: 2019/5/31
                updateOnlineStatus(status);
            }
        });
    }

    // 处理服务端消息确认
    @Override
    public void onHandleServerAck(final MIMCServerAck serverAck) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(IMain.this, "Server has received packetId: "
                        + serverAck.getPacketId()
                        + "\n" + TimeUtils.utc2Local(serverAck.getTimestamp()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // 处理拉取单聊消息
    @Override
    public void onHandlePullP2PHistory(String json, boolean isSuccess) {
        if (isSuccess) {
            json = ParseJson.parseP2PHistoryJson(this, json);
        }
        final String info = json;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                groupInfoDialog.show();
                groupInfoDialog.setContent(info);
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
                Toast.makeText(IMain.this, "Send message timeout: " +
                        info, Toast.LENGTH_SHORT).show();
            }
        });
    }
}