package com.liujc.network.net.common;


import com.liujc.network.net.okhttp.OkHttpRequestManager;
import com.liujc.network.net.volley.VolleyRequestManager;

public class RequestFactory {

    public static IRequestManager getRequestManager(){
        return VolleyRequestManager.getInstance();
//        return OkHttpRequestManager.getInstance();
    }

}
