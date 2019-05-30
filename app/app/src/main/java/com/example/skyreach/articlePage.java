package com.example.skyreach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.skyreach.Ad.Ad_Comment;
import com.example.skyreach.Ob.ObComment;
import com.example.skyreach.Tool.TDialog;
import com.example.skyreach.connect.tool.Post;
import com.example.skyreach.connect.tool.User;
import com.example.skyreach.connect.work.LoginIF;
import com.example.skyreach.connect.work.PostIF;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class articlePage extends AppCompatActivity {

    private TextView a_title;
    private TextView a_content;
    private TextView a_date;
    private TextView a_writer;
    private ImageView like;
    private TextView like_n;

    RecyclerView art;
    Ad_Comment ad_comment;
    List<ObComment> obComments;

    View artHead;

    private Post post;

    boolean liked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_artical);
        ButterKnife.bind(this);
        post=(Post)getIntent().getSerializableExtra("post");
        View();
        a_title = artHead.findViewById(R.id.message5);
        a_content = artHead.findViewById(R.id.mainText);
        a_date = artHead.findViewById(R.id.writeDate);
        a_writer = artHead.findViewById(R.id.writerName);
        like = artHead.findViewById(R.id.like);
        like_n = artHead.findViewById(R.id.like_n);

        a_title.setText(post.getPostTitle());
        a_content.setText(post.getPostContent());
        a_date.setText(post.getPostId());
        like_n.setText(post.getPostLikeCnt()+"");
        for(User user : LoginIF.getInstance().getAllInf()){
            if(user.getUserId().equals(post.getUserId())){
                a_writer.setText(user.getUserName());
            }
        }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostIF.getInstance().increaseLikeCnt(post.getPostId());
                int n=Integer.valueOf(like_n.getText().toString().trim());
                if(liked){
                    liked=false;
                    like_n.setText(--n+"");
                    like.setImageDrawable(getResources().getDrawable(R.drawable.love_no));
                }else{
                    liked=true;
                    like_n.setText(++n+"");
                    like.setImageDrawable(getResources().getDrawable(R.drawable.love_yes));
                }
                //Toast.makeText(getApplicationContext(), "点赞成功！", Toast.LENGTH_LONG).show();
            }
        });
    }
    @OnClick(R.id.comment)
    public void comment(){
        TDialog.commentDialog(this,post.getPostId(),getSupportFragmentManager());
    }
    private void View(){
        art=(RecyclerView)findViewById(R.id.art);
        artHead=(View) LayoutInflater.from(this).inflate(R.layout.art_head,null,true);

        obComments=TData.getComments(post.getPostId());

        ad_comment=new Ad_Comment(obComments);

        ad_comment.setHeaderView(artHead);
        art.setLayoutManager(new LinearLayoutManager(this));
        art.setAdapter(ad_comment);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eOne(ObComment comment) {
            ad_comment.add(0,comment);
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
