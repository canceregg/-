package com.example.skyreach.Tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.skyreach.Main;
import com.example.skyreach.Ob.ObComment;
import com.example.skyreach.R;
import com.example.skyreach.ShareP;
import com.example.skyreach.articlePage;
import com.example.skyreach.connect.tool.Comment;
import com.example.skyreach.connect.tool.User;
import com.example.skyreach.connect.work.CommentIF;
import com.example.skyreach.connect.work.LoginIF;

import org.greenrobot.eventbus.EventBus;


public class TDialog {

    public static void commentDialog(final Context context,String pid,FragmentManager fragmentManager){
        final BottomDialog bottomDialog=new BottomDialog();
        bottomDialog.setFragmentManager(fragmentManager);
        bottomDialog.setLayoutRes(R.layout.comment_dialog);
        bottomDialog.setViewListener(new BottomDialog.ViewListener() {
            @Override
            public void bindView(View v) {
                commentView(context,pid,v,bottomDialog);
            }
        });
        bottomDialog.show();
    }
    private static void commentView(final Context context,String pid,View view, final BottomDialog bottomDialog){
        final EditText content=(EditText)view.findViewById(R.id.content);
        content.post(new Runnable() {
            @Override
            public void run() {
                UtilSoft.show((Activity) context,content);
            }
        });
        TextView yes=(TextView)view.findViewById(R.id.yes);
        final ShareP shareP=new ShareP(context);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment=content.getText().toString().trim();

                if(comment.equals("")){
                    TShow.s("请输入评论");
                }
                else{
                    if(shareP.getBoolean("login")){
                        for(User user : LoginIF.getInstance().getAllInf()) {
                            if (user.getUserId().equals(shareP.getString("uid"))) {
                                if (user.getUserType() == 2) {

                                    TShow.s("你已被封禁，不能发评论");
                                    return;
                                } else {
                                    Comment n_comment = new Comment(pid,shareP.getString("uid"),comment);
                                    ObComment obComment=new ObComment(shareP.getString("name"),comment);
                                    EventBus.getDefault().post(obComment);
                                    CommentIF.getInstance().addComment(n_comment);
                                    bottomDialog.dismiss();
                                    TShow.s("评论成功");
                                }
                            }
                        }

                    }else{
                        TShow.s("请先登录");
                    }
                }
            }
        });
        view.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
    }
}
