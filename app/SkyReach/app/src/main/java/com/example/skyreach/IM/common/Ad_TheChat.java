package com.example.skyreach.IM.common;



import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Ob.OBUser;
import com.example.skyreach.Ob.ObComment;
import com.example.skyreach.R;

import java.util.List;

public class Ad_TheChat extends BaseQuickAdapter<OBUser, BaseViewHolder> {
    public Ad_TheChat(List<OBUser> obComments) {
        super(R.layout.item_thechat, obComments);
    }

    @Override
    protected void convert(BaseViewHolder helper, OBUser item) {
        helper.setText(R.id.name,item.getName());
    }
}
