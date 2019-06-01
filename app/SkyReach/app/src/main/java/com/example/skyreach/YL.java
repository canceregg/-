package com.example.skyreach;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.xiaomi.msg.logger.Logger;
import com.xiaomi.msg.logger.MIMCLog;

public class YL extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        MIMCLog.setLogger(new Logger() {
            @Override
            public void d(String tag, String msg) {
                Log.d(tag, msg);
            }

            @Override
            public void d(String tag, String msg, Throwable th) {
                Log.d(tag, msg, th);
            }

            @Override
            public void i(String tag, String msg) {
                Log.i(tag, msg);
            }

            @Override
            public void i(String tag, String msg, Throwable th) {
                Log.i(tag, msg, th);
            }

            @Override
            public void w(String tag, String msg) {
                Log.w(tag, msg);
            }

            @Override
            public void w(String tag, String msg, Throwable th) {
                Log.w(tag, msg, th);
            }

            @Override
            public void e(String tag, String msg) {
                Log.e(tag, msg);
            }

            @Override
            public void e(String tag, String msg, Throwable th) {
                Log.e(tag, msg, th);
            }
        });
        MIMCLog.setLogSaveLevel(MIMCLog.INFO);
        MIMCLog.setLogPrintLevel(MIMCLog.INFO);
    }
    private static Context context;

    public static Context getContext() {
        return context;
    }
}
