package com.example.skyreach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.skyreach.Ob.ObPlate;
import com.example.skyreach.Tool.TShow;
import com.example.skyreach.connect.tool.Post;
import com.example.skyreach.connect.work.PostIF;

public class Ac_Write_Plate extends Activity {
    private ImageView back;
    private TextView post;
    private EditText title;
    private EditText content;

    private ShareP shareP;

    private String bid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_write);
        shareP=new ShareP(this);
        back = findViewById(R.id.back);
        post = findViewById(R.id.post);
        title = findViewById(R.id.e_title);
        content = findViewById(R.id.e_content);

        bid=getIntent().getStringExtra("bid");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_title = title.getText().toString();
                String e_content = content.getText().toString();
                if(e_title.equals("")||e_content.equals("")){
                    Toast.makeText(getApplicationContext(), "所有输入不能为空！", Toast.LENGTH_LONG).show();
                }
                else{
                    if(shareP.getBoolean("login")){
                        final Post n_post = new Post(shareP.getString("uid"),bid,e_title,e_content,0,0);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PostIF.getInstance().addPost(n_post);
                            }
                        }).start();
                        ObPlate obPlate=new ObPlate(e_title,e_content,"点击量："+0,"点赞数："+0);
                        Intent intent=new Intent();
                        intent.putExtra("plate",obPlate);
                        Ac_Write_Plate.this.setResult(1,intent);
                        Ac_Write_Plate.this.finish();
                        TShow.s("帖子发布成功");
                    }else{
                        TShow.s("请先登录");
                    }

                }
            }
        });
    }
}
