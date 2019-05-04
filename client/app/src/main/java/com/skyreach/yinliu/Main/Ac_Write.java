package com.skyreach.yinliu.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skyreach.yinliu.Main.connect.tool.Post;
import com.skyreach.yinliu.Main.connect.work.PostIF;
import com.skyreach.yinliu.R;

import androidx.annotation.Nullable;

public class Ac_Write extends Activity {
    private ImageView back;
    private TextView post;
    private EditText title;
    private EditText content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_write);
        back = findViewById(R.id.back);
        post = findViewById(R.id.post);
        title = findViewById(R.id.e_title);
        content = findViewById(R.id.e_content);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ac_Write.this,platePage.class);
                startActivity(intent);
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
                    Post n_post = new Post(Main.root_user.getUserId(),Main.clickBoard.getBoardId(),e_title,e_content,0,0);
                    PostIF.getInstance().addPost(n_post);
                    Intent intent = new Intent(Ac_Write.this,platePage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
