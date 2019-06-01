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

    public static List<ObComment> getComments(String pid){
        List<ObComment> comments=new ArrayList<>();
        ObComment obcomment;
        Main.clickComment = CommentIF.getInstance().getAllInfByPostId(pid);

        for(int i = 0; i < Main.clickComment.size(); i++){
            for(User user : LoginIF.getInstance().getAllInf()){
                if(user.getUserId().equals(Main.clickComment.get(i).getObserverId())){
                    obcomment = new ObComment(user.getUserName()+"   "+Main.clickComment.get(i).getCommentId(),Main.clickComment.get(i).getCommentContent());
                    comments.add(obcomment);
                }
            }
        }
        return comments;
    }
}
