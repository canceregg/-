package com.example.skyreach.Ad;



import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Ob.ObPlate;
import com.example.skyreach.R;
import com.example.skyreach.ShareP;
import com.example.skyreach.YL;

import java.util.List;

public class Ad_Plate extends BaseQuickAdapter<ObPlate, BaseViewHolder> {
    ShareP shareP;
    public Ad_Plate(List<ObPlate> obPlates) {
        super(R.layout.item_plate, obPlates);
        shareP=new ShareP(YL.getContext());
    }

    @Override
    protected void convert(BaseViewHolder helper, ObPlate item) {


        if(shareP.getBoolean("manager")){
            helper.setVisible(R.id.del,true);
        }else{
            helper.setVisible(R.id.del,false);
        }
        helper.addOnClickListener(R.id.del,R.id.item_plate);
        helper.setText(R.id.name,item.getTitle());
        helper.setText(R.id.content,item.getContent());
        helper.setText(R.id.see,item.getSee());
        helper.setText(R.id.spport,item.getSupport());
    }
}
