package com.example.skyreach.Ad;



import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Ob.ObPlate;
import com.example.skyreach.R;
import com.example.skyreach.ShareP;
import com.example.skyreach.YL;
import com.example.skyreach.connect.tool.Post;

import java.util.List;

public class Ad_Plate extends BaseQuickAdapter<Post, BaseViewHolder> {
    ShareP shareP;
    public Ad_Plate(List<Post> obPlates) {
        super(R.layout.item_plate, obPlates);
        shareP=new ShareP(YL.getContext());
    }

    @Override
    protected void convert(BaseViewHolder helper, Post item) {


        if(shareP.getBoolean("manager")){
            helper.setVisible(R.id.del,true);
        }else{
            helper.setVisible(R.id.del,false);
        }
        helper.addOnClickListener(R.id.del,R.id.item_plate);
        helper.setText(R.id.name,item.getPostTitle());
        helper.setText(R.id.content,item.getPostContent());
        helper.setText(R.id.see,"点击量："+item.getPostClickCnt());
        helper.setText(R.id.spport,"点赞数："+item.getPostLikeCnt());
    }
}
