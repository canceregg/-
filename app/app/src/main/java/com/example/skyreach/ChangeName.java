package com.example.skyreach;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import android.widget.TextView;

import com.example.skyreach.Tool.TShow;


/**
 * Created by Anizwel on 2018/9/9.
 */

public class ChangeName extends Activity {

    private EditText edit_name;
    private String str_name,result=null;
    private android.widget.TextView yes,youname;
    private ShareP shareP;
    private Context context;
    private Activity activity;

    private RelativeLayout top;

    private ImageView del,back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changename);
        shareP=new ShareP(this);
        activity=(Activity)this;
        this.context=this;
        View();

        edit_name=(EditText)findViewById(R.id.name);
        int len=shareP.getString("name").length();
        if(len<=20){
            edit_name.setText(shareP.getString("name"));
            edit_name.setSelection(len);

        }else{
            String ss=shareP.getString("name").substring(0,19);
            edit_name.setText(ss);
            edit_name.setSelection(ss.length());
        }

        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int n=edit_name.getText().toString().trim().length();
                if(n==0){
                    del.setVisibility(View.GONE);
                }else{
                    del.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name=edit_name.getText().toString().trim();
                int n=str_name.length();
                if(n==0){
                    TShow.s("←_←不能为空");
                }else{
                    ByeBye(str_name);
                }
                }
        });
    }
    private void View(){
        yes=(TextView) findViewById(R.id.yes);
        back=(ImageView)findViewById(R.id.back);
        del=(ImageView)findViewById(R.id.del);
        top=(RelativeLayout)findViewById(R.id.top);
        youname=(TextView)findViewById(R.id.youname);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_name.setText("");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }
    private void ByeBye(String name){
        Intent intent=new Intent();
        intent.putExtra("name", name);
        shareP.putString("name",name);
        activity.setResult(2,intent);
        activity.finish();
    }
}
