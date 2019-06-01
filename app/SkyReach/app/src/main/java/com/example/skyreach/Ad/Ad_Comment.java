package com.example.skyreach.Ad;



import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Ob.ObComment;
import com.example.skyreach.R;

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
