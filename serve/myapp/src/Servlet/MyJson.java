package Servlet;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class MyJson
{
    private MyJson() { }
    private static MyJson single = new MyJson();
    public static MyJson getInstance()
    {
        return single;
    }

    private static Gson gson = new Gson();

    public String toJson(Object o)
    {
        return gson.toJson(o);
    }

    //type的参考格式：new TypeToken<数据结构形式>(){}.getType()
    public Object fromJson(String src, Type type)
    {
        return gson.fromJson(src, type);
    }
}
