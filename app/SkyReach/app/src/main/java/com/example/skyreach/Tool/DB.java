package com.example.skyreach.Tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.skyreach.Ob.OBUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Won on 2017/1/13.
 */

public class DB extends SQLiteOpenHelper {

    public static final String DB_NAME = "GoodDb_DBNAME";

    public static final String TABLE_NAME = "GoodDb_TABLE";

    private static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " ("
            + "id integer primary key autoincrement, "
            + "uid text,  "+"name text "+")";

    public DB(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);//创建表
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<OBUser> get(){
        List<OBUser> obUsers=new ArrayList<>();
        //获取数据库对象
        SQLiteDatabase db = getReadableDatabase();
        //查询表中的数据
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, "id desc");
        //获取name列的索引
        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
            String u = cursor.getString(1);
            String n = cursor.getString(2);
            obUsers.add(new OBUser(u,n));
        }
        cursor.close();//关闭结果集
        //db.close();//关闭数据库对象

        return obUsers;
    }

    public void add(String uid,String name){
        SQLiteDatabase db = getWritableDatabase();
        //生成ContentValues对象
        ContentValues cv = new ContentValues();
        //往ContentValues对象存放数据，键-值对模式
        for(OBUser obUser:get()){
            if(obUser.getUid().equals(uid)) return;
        }
        cv.put("uid", uid);
        cv.put("name", name);
        //调用insert方法，将数据插入数据库
        db.insert(TABLE_NAME, null, cv);
        //关闭数据库
    }

    public void delOne(String uid){
        SQLiteDatabase db = getWritableDatabase();
        //生成ContentValues对象
        db.delete(TABLE_NAME, "uid=?", new String[]{uid});
    }

    public void delAll() {
        SQLiteDatabase db = getWritableDatabase();
        //删除全部数据
        db.execSQL("delete from " + TABLE_NAME);
        //关闭数据库
    }

}
