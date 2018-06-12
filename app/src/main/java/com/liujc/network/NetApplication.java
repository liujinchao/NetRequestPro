package com.liujc.network;

import android.app.Application;
import android.content.Context;

import com.android.commonlibrary.AppLibContext;

/**
 * 类名称：NetApplication
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/22 11:14
 * 描述：TODO
 */
public class NetApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppLibContext.init(this);
//        initOkhttp();
    }

    private void initOkhttp() {

    }

    public static Context getContext(){
        return context;
    }
}
