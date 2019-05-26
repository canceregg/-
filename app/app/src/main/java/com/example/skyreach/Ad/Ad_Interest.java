package com.example.skyreach.Ad;



import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Ob.ObComment;
import com.example.skyreach.Ob.ObInterest;
import com.example.skyreach.R;
import com.example.skyreach.ShareP;
import com.example.skyreach.YL;

import java.util.ArrayList;
import java.util.List;

public class Ad_Interest extends BaseQuickAdapter<ObInterest, BaseViewHolder> {
    ShareP shareP;
    public Ad_Interest(List<ObInterest> obInterests) {
        super(R.layout.item_interest, obInterests);
        shareP=new ShareP(YL.getContext());
    }

    @Override
    protected void convert(final BaseViewHolder helper, ObInterest item) {
        if(shareP.getBoolean("manager")){
            helper.setVisible(R.id.del,true);
        }else{
            helper.setVisible(R.id.del,false);
        }
        helper.addOnClickListener(R.id.item_interest).addOnClickListener(R.id.del);
        helper.setText(R.id.title,item.getTitle());
        helper.setText(R.id.content,item.getContent());

    }
}
