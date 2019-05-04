package com.skyreach.yinliu.Main.Ad;

import com.skyreach.yinliu.Main.Brvah.BaseQuickAdapter;
import com.skyreach.yinliu.Main.Brvah.BaseViewHolder;
import com.skyreach.yinliu.Main.Ob.ObComment;
import com.skyreach.yinliu.R;

import java.util.List;

public class Ad_Comment extends BaseQuickAdapter<ObComment, BaseViewHolder> {
    public Ad_Comment(List<ObComment> obComments) {
        super(R.layout.item_comment, obComments);
    }

    @Override
    protected void convert(BaseViewHolder helper, ObComment item) {
        helper.setText(R.id.username,item.getUserName());
        helper.setText(R.id.content,item.getContent());
    }
}
