package com.example.skyreach.IM.common;



import android.view.View;

import androidx.annotation.Nullable;

import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Brvah.BaseViewHolder;
import com.example.skyreach.Ob.ObComment;
import com.example.skyreach.Ob.ObMsg;
import com.example.skyreach.R;
import com.example.skyreach.ShareP;

import java.util.List;

import static com.example.skyreach.YL.getContext;

public class Ad_Chat extends BaseQuickAdapter<ObMsg, BaseViewHolder> {

    ShareP shareP;

    public Ad_Chat(List<ObMsg> obComments) {
        super(R.layout.item_chat, obComments);
        shareP=new ShareP(getContext());
    }
    @Override
    protected void convert(BaseViewHolder helper, ObMsg item) {
        String here = UserManager.getInstance().getAccount();

        if(item.getUid().equals(here)){//发送
            helper.getView(R.id.receive).setVisibility(View.GONE);
           // helper.setVisible(R.id.receive,false);
            helper.setVisible(R.id.send,true);

            helper.setText(R.id.tv_send,item.getContent());
            helper.setText(R.id.send_account,item.getTime()+"   "+shareP.getString("name"));

        }else{//接受
            helper.setVisible(R.id.receive,true);
//            helper.setVisible(R.id.send,false);
            helper.getView(R.id.send).setVisibility(View.GONE);

            helper.setText(R.id.tv_receive,item.getContent());
            helper.setText(R.id.receive_account,item.getUname()+"   "+item.getTime());
        }
    }
}
