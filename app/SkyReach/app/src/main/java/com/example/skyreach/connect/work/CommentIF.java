package com.example.skyreach.connect.work;

import com.example.skyreach.connect.tool.Comment;
import com.example.skyreach.connect.tool.MyHttp;
import com.example.skyreach.connect.tool.MyJson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;



public class CommentIF
{
    String result = null;
    //获取本类对象
    public static CommentIF getInstance()
    {
        return single;
    }
    //根据帖子ID获取该帖子的所有评论
    //参数：需要查询的帖子
    //返回：一个评论对象的List对象
    public List<Comment> getAllInfByPostId(String postId)
    {
        final String url = "http://111.230.183.219/myapp/ListCommentServlet";
        result = MyHttp.getInstance().postSync(url,"postId",postId);

        List<Comment> list = (List<Comment>) MyJson.getInstance().fromJson(result, new TypeToken<List<Comment>>(){}.getType());
        return list;
    }
    //新增一条评论
    //参数： 一个评论对象
    //返回值：创建情况
    public Boolean addComment(Comment comment)
    {
        final String url = "http://111.230.183.219/myapp/NewCommentServlet";
        List<Comment> list = new ArrayList<Comment>();
        list.add(comment);
        final String src = MyJson.getInstance().toJson(list);
        result = MyHttp.getInstance().postSync(url,"commentdata",src);
        System.out.println("PostJF输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }
    //删除一条评论
    //参数：需要删除评论的id
    //返回值：删除情况
    public Boolean delComment(String commentId)
    {
        final String url = "http://111.230.183.219/myapp/DeleteCommentServlet";
        Comment c = new Comment();
        c.setCommentId(commentId);
        List<Comment> list = new ArrayList<Comment>();
        list.add(c);
        final String src = MyJson.getInstance().toJson(list);
        result = MyHttp.getInstance().postSync(url, "commentdata", src);
        System.out.println("PostJF输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }


    private CommentIF() { }
    private static CommentIF single = new CommentIF();
}
