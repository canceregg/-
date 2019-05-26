package com.example.skyreach.connect.work;

import com.example.skyreach.connect.tool.MyHttp;
import com.example.skyreach.connect.tool.MyJson;
import com.example.skyreach.connect.tool.User;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class LoginIF
{
    //获取本类对象
    public static LoginIF getInstance()
    {
        return single;
    }
    private String result= "a";

    //新增一个用户，需要输入该用户的个人信息（昵称、密码、电子邮件、权限等级）
    //返回值：创建成功返回true，否则返回false
    public Boolean newUser(String name, String password, String email, int type)
    {
        final String url = "http://111.230.183.219/myapp/SignUserServlet";
        List<User> userList = new ArrayList<User>();
        userList.add(new User(name, password, email, type));
        final String src = MyJson.getInstance().toJson(userList);
        result = MyHttp.getInstance().postSync(url,"userdata",src);
        System.out.println("输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }

    //验证用户登录，输入邮箱和密码
    public Boolean check(String email, String password)
    {
        final String url = "http://111.230.183.219/myapp/LoginUserServlet";
        List<User> userList = new ArrayList<User>();
        userList.add(new User("aa", password, email, -1));
        final String src = MyJson.getInstance().toJson(userList);
        result = MyHttp.getInstance().postSync(url,"userdata",src);
        System.out.println("输出---------" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }

    //---忘记密码
    /*
    * 有
    * 待
    * 实
    * 现
    * */

    //public User(String name, String pw, String email, int t)

    //修改密码
    //切记需要登陆后才可以修改密码
    public Boolean updatePassword(String email, String oldPW, String newPW)
    {
        final String url = "http://111.230.183.219/myapp/ResetPasswordServlet";
        List<User> userList = new ArrayList<User>();
        userList.add(new User(newPW, oldPW, email, -1));
        final String src = MyJson.getInstance().toJson(userList);
        result = MyHttp.getInstance().postSync(url,"userdata",src);
        System.out.println("LoginIF输出---------" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }

    //获取所有用户信息
    //返回User对象的list
    public List<User> getAllInf()
    {
        final String url = "http://111.230.183.219/myapp/ListUserServlet";
        result = MyHttp.getInstance().postSync(url," "," ");
        List<User> userList = (List<User>)MyJson.getInstance().fromJson(result, new TypeToken<List<User>>(){}.getType());
        return userList;
    }

    //单例模式
    private LoginIF() { }
    private static LoginIF single = new LoginIF();

}
