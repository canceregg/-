package com.skyreach.yinliu.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.skyreach.yinliu.Main.Ad.Ad_Comment;
import com.skyreach.yinliu.Main.Ob.ObComment;
import com.skyreach.yinliu.Main.connect.tool.User;
import com.skyreach.yinliu.Main.connect.work.LoginIF;
import com.skyreach.yinliu.R;

import java.util.List;


public class articlePage extends AppCompatActivity {

    private TextView a_title;
    private TextView a_content;
    private TextView a_date;
    private TextView a_writer;

    RecyclerView art;
    Ad_Comment ad_comment;
    List<ObComment> obComments;

    View artHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_artical);
        View();
        a_title = artHead.findViewById(R.id.message5);
        a_content = artHead.findViewById(R.id.mainText);
        a_date = artHead.findViewById(R.id.writeDate);
        a_writer = artHead.findViewById(R.id.writerName);

        a_title.setText(Main.clickPost.getPostTitle());
        a_content.setText(Main.clickPost.getPostContent());
        a_date.setText(Main.clickPost.getPostId());
        for(User user : LoginIF.getInstance().getAllInf()){
            if(user.getUserId().equals(Main.clickPost.getUserId())){
                a_writer.setText(user.getUserName());
            }
        }
    }
    private void View(){
        art=(RecyclerView)findViewById(R.id.art);
        artHead=(View) LayoutInflater.from(this).inflate(R.layout.art_head,null,true);

        obComments=TData.getComments();

        ad_comment=new Ad_Comment(obComments);

        ad_comment.setHeaderView(artHead);
        art.setLayoutManager(new LinearLayoutManager(this));
        art.setAdapter(ad_comment);
    }
}
