package com.example.skyreach.Tool;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

public class UtilImage {

    public static String getPath(String dir,String name){
        return getPath()+"/"+dir+"/"+name;
    }
    public static String getPath(){
        String path=Environment.getExternalStorageDirectory().getPath()+"/skyreach";
        File pathF=new File(path);
        if(!pathF.exists()){
            pathF.mkdir();
        }
        return path;
    }
    public static String getPath(String dir){
        return getPath()+"/"+dir;
    }
    public static void compress(Context context, File file, final String dir, final String name, final Handler handler){
        Luban.with(context)
                .load(file)
                .ignoreBy(100)
                .setTargetDir(UtilImage.getPath())
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return name;
                    }
                })
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {

                        FileUtil.saveFile(file, UtilImage.getPath(dir),name);
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }
                }).launch();
    }

}
