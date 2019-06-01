package com.example.skyreach.connect.tool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyHttp
{
    //单例模式
    private final OkHttpClient client = new OkHttpClient();
    private MyHttp() { }
    private static MyHttp single = new MyHttp();
    public static MyHttp getInstance()
    {
        return single;
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //数据：
    Request request = null;
    Response response = null;


    public String get(final String url)
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            request = new Request.Builder().url(url).build();
            response = client.newCall(request).execute();

            return response.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    //Post 键值对
    public void post(final String url,final String key,final String value)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                try
                {
                    Request request = new Request.Builder().url(url).build();
                    FormBody formBody = new FormBody.Builder().add(key, value).build();
                    request = new Request.Builder().url(url).post(formBody).build();
                    response = client.newCall(request).execute();
                    //  response.body().string();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //线程同步
    public  String postSync(final String url,final String key,final String value)
    {

        try
        {
            FutureTask<String> task = new FutureTask<String>(new Callable<String>()
            {

                @Override
                public String call() throws Exception
                {
                    Request request = new Request.Builder().url(url).build();
                    FormBody formBody = new FormBody.Builder().add(key, value).build();
                    request = new Request.Builder().url(url).post(formBody).build();
                    response = client.newCall(request).execute();
                    return response.body().string();

                }
            });

            new Thread(task).start();
            return task.get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("网络访问错误");
        }

    }



}
