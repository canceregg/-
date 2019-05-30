package com.example.skyreach.Ad;


import android.content.Context;

import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Ob.ObMessage;
import com.example.skyreach.R;
import com.example.skyreach.TData;
import com.example.skyreach.connect.tool.Post;

import java.util.List;

public class Ad_Message extends BaseQuickAdapter<Post, BaseViewHolder> {
    public Ad_Message(List<Post> obPlates) {
        super(R.layout.item_message, obPlates);
    }
    @Override
    protected void convert(BaseViewHolder helper, Post item) {
        helper.setText(R.id.m_title,item.getPostTitle());
        helper.setText(R.id.m_content,item.getPostContent());
        helper.addOnClickListener(R.id.m_title).addOnClickListener(R.id.delete).addOnClickListener(R.id.m_content);
    }
}
