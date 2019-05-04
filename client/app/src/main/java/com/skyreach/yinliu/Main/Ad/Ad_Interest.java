package com.skyreach.yinliu.Main.Ad;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;


import com.skyreach.yinliu.Main.Brvah.BaseQuickAdapter;
import com.skyreach.yinliu.Main.Brvah.BaseViewHolder;
import com.skyreach.yinliu.Main.Main;
import com.skyreach.yinliu.Main.Ob.ObInterest;
import com.skyreach.yinliu.Main.TData;
import com.skyreach.yinliu.Main.connect.work.PostIF;
import com.skyreach.yinliu.Main.platePage;
import com.skyreach.yinliu.R;

import java.util.ArrayList;
import java.util.List;

public class Ad_Interest extends BaseQuickAdapter<ObInterest, BaseViewHolder> {
    public Ad_Interest(List<ObInterest> obInterests) {
        super(R.layout.item_interest, obInterests);
    }

    @Override
    protected void convert(final BaseViewHolder helper, ObInterest item) {
        helper.setText(R.id.title,item.getTitle());
        helper.setText(R.id.content,item.getContent());
    }
}
