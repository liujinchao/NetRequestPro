package com.liujc.network.net.okhttp;

import com.android.httplib.okhttp.OkHttpUtils;
import com.android.httplib.okhttp.callBack.RequestCallback;
import com.liujc.network.net.common.IRequestManager;

import java.util.Map;


public class OkHttpRequestManager implements IRequestManager {

    public static OkHttpRequestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OkHttpRequestManager INSTANCE = new OkHttpRequestManager();
    }


    public OkHttpRequestManager() {
        OkHttpUtils.getInstance();
    }
    @Override
    public void get(String url, RequestCallback requestCallback) {
        OkHttpUtils.getAsyn(url, requestCallback);
    }

    @Override
    public void get(String url, Map<String, String> params, RequestCallback callback) {
        OkHttpUtils.getAsyn(url, params, callback);
    }

    @Override
    public void post(String url, String requestBodyJson, RequestCallback requestCallback) {
        OkHttpUtils.post(url, requestBodyJson, requestCallback);
    }

    @Override
    public void post(String url, Map<String, String> params, RequestCallback callback) {
        OkHttpUtils.postAsyn(url, params, callback);
    }

    @Override
    public void post(String url, Map<String, String> params, String bodyKey, RequestCallback callback) {
        OkHttpUtils._postBodyAsyn(url, params, bodyKey, callback);
    }

    @Override
    public void put(String url, String requestBodyJson, RequestCallback requestCallback) {
        OkHttpUtils.put(url, requestBodyJson, requestCallback);
    }

    @Override
    public void delete(String url, String requestBodyJson, RequestCallback requestCallback) {
        OkHttpUtils.delete(url, requestBodyJson, requestCallback);
    }

}
