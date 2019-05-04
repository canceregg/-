package com.skyreach.yinliu.Main.Ad;

import com.skyreach.yinliu.Main.Brvah.BaseQuickAdapter;
import com.skyreach.yinliu.Main.Brvah.BaseViewHolder;
import com.skyreach.yinliu.Main.Ob.ObPlate;
import com.skyreach.yinliu.R;

import java.util.List;

public class Ad_Plate extends BaseQuickAdapter<ObPlate, BaseViewHolder> {
    public Ad_Plate(List<ObPlate> obPlates) {
        super(R.layout.item_plate, obPlates);
    }

    @Override
    protected void convert(BaseViewHolder helper, ObPlate item) {
        helper.setText(R.id.name,item.getTitle());
        helper.setText(R.id.content,item.getContent());
        helper.setText(R.id.see,item.getSee()+"人已看");
        helper.setText(R.id.spport,item.getSupport()+"人点赞");
    }
}
