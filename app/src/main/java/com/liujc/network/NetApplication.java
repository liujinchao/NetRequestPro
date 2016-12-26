package com.liujc.network;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.liujc.network.net.okhttp.HttpsUtils;
import com.liujc.network.net.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 类名称：NetApplication
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/22 11:14
 * 描述：TODO
 * 最近修改时间：2016/12/22 11:14
 * 修改人：Modify by liujc
 */
public class NetApplication extends Application {
    public static RequestQueue mQueue;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mQueue = Volley.newRequestQueue(this);
        OkHttpUtils.getInstance();
//        initOkhttp();
    }

    private void initOkhttp() {

    }

    public static Context getContext(){
        return context;
    }
}
