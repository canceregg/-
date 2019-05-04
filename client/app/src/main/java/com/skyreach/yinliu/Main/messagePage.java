package com.skyreach.yinliu.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.skyreach.yinliu.Main.Ad.Ad_Message;
import com.skyreach.yinliu.Main.Brvah.BaseQuickAdapter;
import com.skyreach.yinliu.R;


public class messagePage extends AppCompatActivity {

    RecyclerView ins;
    Ad_Message ad_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_message);
        View();
    }
    private void View(){
        ins=(RecyclerView)findViewById(R.id.megs);
        ad_message=new Ad_Message();
        ad_message.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Main.clickPost = Main.root_my_postlist.get(position);
                Intent intent = new Intent(messagePage.this,articlePage.class);
                startActivity(intent);
            }
        });
        ins.setLayoutManager(new LinearLayoutManager(this));
        ins.setAdapter(ad_message);
    }
}
