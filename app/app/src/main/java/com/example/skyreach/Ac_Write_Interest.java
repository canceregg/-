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

import com.example.skyreach.Ob.ObInterest;
import com.example.skyreach.Ob.ObPlate;
import com.example.skyreach.Tool.TShow;
import com.example.skyreach.connect.tool.Board;
import com.example.skyreach.connect.tool.Post;
import com.example.skyreach.connect.work.BoardIF;
import com.example.skyreach.connect.work.PostIF;

public class Ac_Write_Interest extends Activity {
    private ImageView back;
    private TextView post;
    private EditText title;
    private EditText content;

    private ShareP shareP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_write);
        shareP=new ShareP(this);
        back = findViewById(R.id.back);
        post = findViewById(R.id.post);
        title = findViewById(R.id.e_title);
        content = findViewById(R.id.e_content);

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
                        final Board n_post = new Board(e_title,e_content,0,0);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BoardIF.getInstance().addBoard(n_post);
                            }
                        }).start();
                        ObInterest obPlate=new ObInterest(e_title,"点击量："+0+"点赞数："+0);
                        Intent intent=new Intent();
                        intent.putExtra("interest",obPlate);
                        Ac_Write_Interest.this.setResult(1,intent);
                        Ac_Write_Interest.this.finish();
                        TShow.s("板块发布成功");
                    }else{
                        TShow.s("请先登录");
                    }

                }
            }
        });
    }
}
