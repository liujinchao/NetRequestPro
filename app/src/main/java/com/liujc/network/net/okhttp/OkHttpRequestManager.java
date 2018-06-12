package com.liujc.network.net.okhttp;

import com.liujc.network.net.common.IRequestManager;
import com.liujc.network.net.common.RequestCallback;

import java.util.Map;


public class OkHttpRequestManager implements IRequestManager {

    public static OkHttpRequestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void get(String url, RequestCallback requestCallback) {

    }

    @Override
    public void get(String url, Map<String, String> params, RequestCallback callback) {

    }

    @Override
    public void post(String url, String requestBodyJson, RequestCallback requestCallback) {

    }

    @Override
    public void post(String url, Map<String, String> params, RequestCallback callback) {

    }

    @Override
    public void post(String url, Map<String, String> params, String bodyKey, RequestCallback callback) {

    }

    @Override
    public void put(String url, String requestBodyJson, RequestCallback requestCallback) {

    }

    @Override
    public void delete(String url, String requestBodyJson, RequestCallback requestCallback) {

    }

    private static class SingletonHolder {
        private static final OkHttpRequestManager INSTANCE = new OkHttpRequestManager();
    }


    public OkHttpRequestManager() {

    }

}
