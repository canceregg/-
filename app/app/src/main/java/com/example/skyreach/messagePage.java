package com.example.skyreach;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.skyreach.Ad.Ad_Message;
import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Tool.TShow;
import com.example.skyreach.connect.work.PostIF;


public class messagePage extends AppCompatActivity {

    RecyclerView ins;
    Ad_Message ad_message;
    RelativeLayout zero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_message);
        View();
    }
    private void ZERO(){
        if(Main.root_my_postlist.size()==0){
            zero.setVisibility(View.VISIBLE);
            ins.setVisibility(View.GONE);
        }else{
            zero.setVisibility(View.GONE);
            ins.setVisibility(View.VISIBLE);
        }
    }
    private void View(){
        ins=(RecyclerView)findViewById(R.id.megs);
        zero=findViewById(R.id.zero);
        ad_message=new Ad_Message(this);
        ad_message.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
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
                        new AlertDialog.Builder(messagePage.this)
                                .setMessage("确定清除这个帖子嘛?")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Main.clickPost = Main.root_my_postlist.get(position);
                                        PostIF.getInstance().delPost(Main.clickPost.getPostId(),Main.clickPost.getBoardId());
                                        Main.root_my_postlist.remove(position);
                                        ad_message.remove(position);
                                        ZERO();
                                        TShow.s("已清除");
                                    }
                                })
                                .create().show();

                        break;
                }
            }
        });
        ins.setLayoutManager(new LinearLayoutManager(this));
        ins.setAdapter(ad_message);
        ZERO();
    }
}
