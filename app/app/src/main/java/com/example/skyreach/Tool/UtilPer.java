package com.example.skyreach.Tool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


/**
 * Created by Anizwel on 2019/3/21.
 */

public class UtilPer {
    private static final int perCode=9,setCode=11;

    // TODO: 2019/3/21  分享图片时，选择时

    public static void Per(final Activity activity,Handler handler){
        final boolean per=isPer(activity);
        if(per){
            handler.sendEmptyMessage(1);
        }else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, perCode);
        }
    }
    public static void onActivityResult(Activity activity,int requestCode, int resultCode, Intent data) {
        if (requestCode == setCode) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // 检查该权限是否已经获取
                boolean per =isPer(activity);
            }
        }
    }
    public static void onRequestPermissionsResult(final Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == perCode){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //用户同意，执行操作
            }else{
                boolean b = activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(!b){//没有同意授权，但是再次申请
                }
            }
        }
    }
    public static boolean isPer(Context context){
        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            return true;
            //拥有权限，执行操作
        }else{
            //没有权限，向用户请求权限
            return false;
            //ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 9);
        }
    }

    public static void toAppSetting(Activity activity){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, setCode);
    }

}
