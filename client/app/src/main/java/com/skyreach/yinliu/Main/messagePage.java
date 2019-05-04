package com.skyreach.yinliu.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.skyreach.yinliu.Main.Ad.Ad_Message;
import com.skyreach.yinliu.Main.Brvah.BaseQuickAdapter;
import com.skyreach.yinliu.Main.connect.work.PostIF;
import com.skyreach.yinliu.R;


public class messagePage extends AppCompatActivity {

    RecyclerView ins;
    Ad_Message ad_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_message);
        View();
        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostIF.getInstance().delPost(Main.clickPost.getPostId());
                Intent intent = new Intent(messagePage.this,messagePage.class);
                startActivity(intent);
            }
        });*/
    }
    private void View(){
        ins=(RecyclerView)findViewById(R.id.megs);
        ad_message=new Ad_Message();
        /*ad_message.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Main.clickPost = Main.root_my_postlist.get(position);
                Intent intent = new Intent(messagePage.this,articlePage.class);
                startActivity(intent);
            }
        });*/
        ad_message.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch(view.getId()){
                    case R.id.m_title:
                        Main.clickPost = Main.root_my_postlist.get(position);
                        Intent intent = new Intent(messagePage.this,articlePage.class);
                        startActivity(intent);
                        break;
                    case R.id.m_content:
                        Main.clickPost = Main.root_my_postlist.get(position);
                        Intent intents = new Intent(messagePage.this,articlePage.class);
                        startActivity(intents);
                        break;
                    case R.id.delete:
                        Main.clickPost = Main.root_my_postlist.get(position);
                        PostIF.getInstance().delPost(Main.clickPost.getPostId());
                        Intent intentss = new Intent(messagePage.this,messagePage.class);
                        startActivity(intentss);
                        break;
                }
            }
        });
        ins.setLayoutManager(new LinearLayoutManager(this));
        ins.setAdapter(ad_message);
    }
}
