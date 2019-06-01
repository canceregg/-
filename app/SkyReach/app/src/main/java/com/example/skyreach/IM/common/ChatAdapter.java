package com.example.skyreach.IM.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.skyreach.IM.bean.ChatMsg;
import com.example.skyreach.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<ChatMsg> mDatas;
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    public ChatAdapter(Context context, List<ChatMsg> datas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_SEND) {
            view = mLayoutInflater.inflate(R.layout.item_chat_send, parent, false);
            return new ChatSendViewHolder(view);
        } else {
            view = mLayoutInflater.inflate(R.layout.item_chat_receive, parent, false);
            return new ChatReceiveViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMsg chatMsg = mDatas.get(position);
        String content = new String(chatMsg.getMsg().getPayload());
        String fromAccount = chatMsg.getFromAccount();
        String timestamp = TimeUtils.utc2Local(chatMsg.getMsg().getTimestamp());
        String mimcAccount = UserManager.getInstance().getAccount();
        if (holder instanceof ChatSendViewHolder) {
            ((ChatSendViewHolder) holder).tv_send.setText(content);
            ((ChatSendViewHolder) holder).send_account.setText(timestamp.toString() + "    " + mimcAccount);
        }
        if (holder instanceof ChatReceiveViewHolder) {
            ((ChatReceiveViewHolder) holder).tv_receive.setText(content);
            ((ChatReceiveViewHolder) holder).receive_account.setText(fromAccount.toString() + "    " + timestamp);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getFromAccount().equals(UserManager.getInstance().getAccount())) {
            return TYPE_SEND;
        }
        return TYPE_RECEIVED;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ChatSendViewHolder extends RecyclerView.ViewHolder {
        TextView tv_send;
        TextView send_account;

        ChatSendViewHolder(View view) {
            super(view);
            tv_send = (TextView) view.findViewById(R.id.tv_send);
            send_account = (TextView) view.findViewById(R.id.send_account);
        }
    }

    static class ChatReceiveViewHolder extends RecyclerView.ViewHolder {
        TextView tv_receive;
        TextView receive_account;

        ChatReceiveViewHolder(View view) {
            super(view);
            tv_receive = (TextView) view.findViewById(R.id.tv_receive);
            receive_account = (TextView) view.findViewById(R.id.receive_account);
        }
    }
}


