package com.example.skyreach.Ad;



import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Main;
import com.example.skyreach.Ob.ObComment;
import com.example.skyreach.Ob.ObInterest;
import com.example.skyreach.R;
import com.example.skyreach.ShareP;
import com.example.skyreach.YL;
import com.example.skyreach.connect.tool.Board;

import java.util.ArrayList;
import java.util.List;

public class Ad_Interest extends BaseQuickAdapter<Board, BaseViewHolder> {
    ShareP shareP;
    public Ad_Interest(List<Board> obInterests) {
        super(R.layout.item_interest, obInterests);
        shareP=new ShareP(YL.getContext());
    }

    @Override
    protected void convert(final BaseViewHolder helper, Board item) {
        if(shareP.getBoolean("manager")){
            helper.setVisible(R.id.del,true);
        }else{
            helper.setVisible(R.id.del,false);
        }
        helper.addOnClickListener(R.id.item_interest).addOnClickListener(R.id.del);
        helper.setText(R.id.title,item.getBoardName());
        helper.setText(R.id.content,"发帖量："+ item.getBoardPostCnt()+"   点击量："+item.getBoardClickCnt());

    }
}
