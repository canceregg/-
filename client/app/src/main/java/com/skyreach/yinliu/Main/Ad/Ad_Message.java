package com.skyreach.yinliu.Main.Ad;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.skyreach.yinliu.Main.Brvah.BaseQuickAdapter;
import com.skyreach.yinliu.Main.Brvah.BaseViewHolder;
import com.skyreach.yinliu.Main.Ob.ObMessage;
import com.skyreach.yinliu.Main.TData;
import com.skyreach.yinliu.R;


public class Ad_Message extends BaseQuickAdapter<ObMessage, BaseViewHolder> {
    public Ad_Message() {
        super(R.layout.item_message, TData.getMessage());
    }
    @Override
    protected void convert(BaseViewHolder helper, ObMessage item) {
        helper.setText(R.id.m_title,item.getTitle());
        helper.setText(R.id.m_content,item.getContent());
        helper.addOnClickListener(R.id.m_title).addOnClickListener(R.id.delete).addOnClickListener(R.id.m_content);
    }
}
