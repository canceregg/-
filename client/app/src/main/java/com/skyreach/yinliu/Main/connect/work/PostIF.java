package com.skyreach.yinliu.Main.connect.work;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import com.skyreach.yinliu.Main.connect.tool.*;

public class PostIF
{
    String result = null;
    //获取本类对象
    public static PostIF getInstance()
    {
        return single;
    }

    //获取某个板块的所有帖子信息
    //参数：板块ID
    //返回帖子对象的List对象
    public List<Post> getAllInfByBoardId(String boardId)
    {
        final String url = "http://111.230.183.219/myapp/ListPostServlet";
        result = MyHttp.getInstance().postSync(url,"boardId",boardId);
        List<Post> list = (List<Post>) MyJson.getInstance().fromJson(result, new TypeToken<List<Post>>(){}.getType());
        return list;
    }


    //根据用户id获取帖子信息
    //参数：用户ID
    //返回帖子的lis对象
    public List<Post> getAllInfByUserId(String userId)
    {
        final String url = "http://111.230.183.219/myapp/ListPostByUidServlet";
        result = MyHttp.getInstance().postSync(url,"userId",userId);
        List<Post> list = (List<Post>) MyJson.getInstance().fromJson(result, new TypeToken<List<Post>>(){}.getType());
        return list;
    }



    //新增一个帖子
    //传入一个帖子对象， 该对象的postId不需要赋值，系统自动生成
    //返回：执行情况
    public Boolean addPost(Post post)
    {
        final String url = "http://111.230.183.219/myapp/NewPostServlet";
        List<Post> postList = new ArrayList<Post>();
        postList.add(post);
        final String src = MyJson.getInstance().toJson(postList);
        result = MyHttp.getInstance().postSync(url,"postdata",src);
        System.out.println("PostJF输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }


    //修改帖子
    //参数：一个已经修改的帖子对象，其中会修改帖子的标题、内容，其他不改
    //返回；修改情况
    public Boolean updatePost(String postId, String title, String content)
    {
        final String url = "http://111.230.183.219/myapp/UpdatePostServlet";
        List<Post> postList = new ArrayList<Post>();
        Post p = new Post();
        p.setPostId(postId);
        p.setPostTitle(title);
        p.setPostContent(content);
        postList.add(p);
        final String src = MyJson.getInstance().toJson(postList);
        result = MyHttp.getInstance().postSync(url,"postdata",src);
        System.out.println("PostJF输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }


    //删除帖子
    //参数：被删除帖子的ID
    //返回：修改情况
    public Boolean delPost(String postId)
    {
        final String url = "http://111.230.183.219/myapp/DeletePostServlet";
        Post p = new Post();
        p.setPostId(postId);
        List<Post> postList = new ArrayList<Post>();
        postList.add(p);
        final String src = MyJson.getInstance().toJson(postList);
        result = MyHttp.getInstance().postSync(url,"postdata",src);
        System.out.println("PostJF输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }

    //增加帖子的点击数加一
    //参数：postId->板块ID
    public Boolean increaseClickCnt(String postId)
    {
        final String url = "http://111.230.183.219/myapp/PostClickPlusServlet";
        result = MyHttp.getInstance().postSync(url,"postId", postId);
        System.out.println("输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }

    //增加帖子的点赞数加一
    //参数：postId->板块ID
    public Boolean increaseLikeCnt(String postId)
    {
        final String url = "http://111.230.183.219/myapp/PostLikePlusServlet";
        result = MyHttp.getInstance().postSync(url,"postId", postId);
        System.out.println("输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }



    //单例模式
    private PostIF() { }
    private static PostIF single = new PostIF();
}
