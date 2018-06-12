package com.liujc.network.net.volley;


import com.liujc.network.net.common.IRequestManager;
import com.liujc.network.net.common.RequestCallback;

import java.util.Map;


public class VolleyRequestManager implements IRequestManager {

    public static VolleyRequestManager getInstance() {
        return SingletonHolder.sInstance;
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
        private static final VolleyRequestManager sInstance = new VolleyRequestManager();
    }


}
