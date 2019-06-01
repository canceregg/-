package com.example.skyreach.IM;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.skyreach.R;


public class GroupInfoDialog extends Dialog {
    private TextView tvContent;

    public GroupInfoDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info_dialog);
        setCancelable(true);
        setTitle(R.string.tips);
        tvContent = (TextView)findViewById(R.id.tv_content);
    }

    public void setContent(String info) {
        tvContent.setText(info);
    }
}
