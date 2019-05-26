package com.example.skyreach;




import android.content.Context;

import com.example.skyreach.Ob.ObComment;
import com.example.skyreach.Ob.ObInterest;
import com.example.skyreach.Ob.ObMessage;
import com.example.skyreach.Ob.ObPlate;
import com.example.skyreach.connect.tool.User;
import com.example.skyreach.connect.work.BoardIF;
import com.example.skyreach.connect.work.CommentIF;
import com.example.skyreach.connect.work.LoginIF;
import com.example.skyreach.connect.work.PostIF;

import java.util.ArrayList;
import java.util.List;

import static com.example.skyreach.YL.getContext;

public class TData {


    public static List<ObInterest> getInterest(){
        List<ObInterest> interests=new ArrayList<>();
        ObInterest obInterest;
        Main.root_boardlist = BoardIF.getInstance().getAllInf();
        for(int i = 0; i < Main.root_boardlist.size(); i++){
            obInterest = new ObInterest(Main.root_boardlist.get(i).getBoardName(), "发帖量："+Main.root_boardlist.get(i).getBoardPostCnt()+"   点击量："+Main.root_boardlist.get(i).getBoardClickCnt());
            interests.add(obInterest);
        }
        return interests;
    }
    public static List<ObMessage> getMessage(Context context){
        List<ObMessage> messages=new ArrayList<>();
        ObMessage obmessage;
        Main.root_my_postlist = PostIF.getInstance().getAllInfByUserId(new ShareP(context).getString("uid"));
        for(int i = 0; i < Main.root_my_postlist.size(); i++){
            obmessage = new ObMessage(Main.root_my_postlist.get(i).getPostTitle(),Main.root_my_postlist.get(i).getPostContent());
            messages.add(obmessage);
        }
        return messages;
    }
    public static List<ObComment> getComments(){
        List<ObComment> comments=new ArrayList<>();
        ObComment obcomment;
        Main.clickComment = CommentIF.getInstance().getAllInfByPostId(Main.clickPost.getPostId());

        for(int i = 0; i < Main.clickComment.size(); i++){
            for(User user : LoginIF.getInstance().getAllInf()){
                if(user.getUserId().equals(Main.clickComment.get(i).getObserverId())){
                    obcomment = new ObComment(user.getUserName()+Main.clickComment.get(i).getCommentId(),Main.clickComment.get(i).getCommentContent());
                    comments.add(obcomment);
                }
            }
        }
        return comments;
    }
    public static List<ObPlate> getPlate(){
        List<ObPlate> plates=new ArrayList<>();
        ObPlate obPlate;
        for(int i = 0; i < Main.root_board_postlist.size(); i++){
            obPlate = new ObPlate(Main.root_board_postlist.get(i).getPostTitle(),Main.root_board_postlist.get(i).getPostContent(),"点击量："+Main.root_board_postlist.get(i).getPostClickCnt(),"点赞数："+Main.root_board_postlist.get(i).getPostLikeCnt());
            plates.add(obPlate);
        }
        return plates;
    }
}
