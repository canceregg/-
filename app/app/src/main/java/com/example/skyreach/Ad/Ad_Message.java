package com.example.skyreach.Ad;


import android.content.Context;

import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Ob.ObMessage;
import com.example.skyreach.R;
import com.example.skyreach.TData;

public class Ad_Message extends BaseQuickAdapter<ObMessage, BaseViewHolder> {
    public Ad_Message(Context context) {
        super(R.layout.item_message, TData.getMessage(context));
    }
    @Override
    protected void convert(BaseViewHolder helper, ObMessage item) {
        helper.setText(R.id.m_title,item.getTitle());
        helper.setText(R.id.m_content,item.getContent());
        helper.addOnClickListener(R.id.m_title).addOnClickListener(R.id.delete).addOnClickListener(R.id.m_content);
    }
}
