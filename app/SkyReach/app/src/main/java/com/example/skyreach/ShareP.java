package com.example.skyreach;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Beyond on 2017/3/31.
 */

public class ShareP {
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ShareP(Context context){
        this.sharedPreferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
        this.editor=sharedPreferences.edit();
    }

    public ShareP(Context context,String key){
        this.sharedPreferences=context.getSharedPreferences(key,Context.MODE_PRIVATE);
        this.editor=sharedPreferences.edit();
    }

    public void putInt(String key,int value){
        editor.putInt(key,value);
        editor.commit();
    }
    public void clear(){
        editor.clear();
        editor.commit();
    }

    public int getInt(String key){
        return   this.sharedPreferences.getInt(key,0);
    }

    public void putString(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }
    public void putLong(String key,long value){
        editor.putLong(key,value);
        editor.commit();
    }

    public long getLong(String key){
        return sharedPreferences.getLong(key,0);
    }
    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }

    public void putBoolean(String key,boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean getBoolean(String key){
        return   sharedPreferences.getBoolean(key,false);
    }

    public void setFloat(String key,float value){
        editor.putFloat(key,value);
        editor.commit();
    }

    public float getFloat(String key){
        return   sharedPreferences.getFloat(key,1);
    }

    public void putObject(String key,String [] object) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(key,temp);
            editor.commit();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public String [] getObject(String key) {
        String temp = sharedPreferences.getString(key,null);
        ByteArrayInputStream bais =  new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        String strings[]=null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            strings = (String[]) ois.readObject();
        } catch (IOException e) {

        }catch(ClassNotFoundException e1) {
        }
        return strings;
    }


    public void putPicture(String key,Drawable drawable){
        String s=drawableToByte(drawable);
        editor.putString(key,s);
        editor.commit();
    }

    public Drawable getPicture(String key){
        String s=sharedPreferences.getString(key,null);
        return byteToDrawable(s);
    }

    private Drawable byteToDrawable(String icon) {
        if (icon != null) {
            byte[] img = Base64.decode(icon.getBytes(), Base64.DEFAULT);
            Bitmap bitmap;
            if (img != null) {
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                @SuppressWarnings("deprecation")
                Drawable drawable = new BitmapDrawable(bitmap);


                return drawable;
            }
            return null;

        }else {
            return null;
        }
    }
    private  String drawableToByte(Drawable drawable) {

        if (drawable != null) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;

            // 创建一个字节数组输出流,流的大小为size
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            // 将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();

            String icon= Base64.encodeToString(imagedata, Base64.DEFAULT);

            return icon;
        }
        return null;
    }

}
