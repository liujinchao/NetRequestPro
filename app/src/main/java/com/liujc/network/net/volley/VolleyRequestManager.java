package com.liujc.network.net.volley;


import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.liujc.network.NetApplication;
import com.liujc.network.net.common.IRequestManager;
import com.liujc.network.net.common.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;


public class VolleyRequestManager implements IRequestManager {

    public static VolleyRequestManager getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final VolleyRequestManager sInstance = new VolleyRequestManager();
    }

    @Override
    public void get(String url, final RequestCallback requestCallback) {
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestCallback.onSuccess(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        requestCallback.onFailure(volleyError);
                    }
                });
        NetApplication.mQueue.add(request);
    }

    @Override
    public void get(String url, Map<String, String> params, RequestCallback callback) {
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            if (!TextUtils.isEmpty(next.getValue())){
                url = url+ "&"+next.getKey() + "="+next.getValue();
            }
        }
        get(url,callback);
    }

    @Override
    public void post(String url, String requestBodyJson, final RequestCallback requestCallback) {
        requestWithBody(url, requestBodyJson, requestCallback, Request.Method.POST);
    }

    @Override
    public void post(String url, Map<String, String> params, RequestCallback callback) {
        requestWithBody(url, params.toString(), callback, Request.Method.POST);
    }

    @Override
    public void post(String url, Map<String, String> params, String bodyKey, RequestCallback callback) {

    }

    @Override
    public void put(String url, String requestBodyJson, final RequestCallback requestCallback) {
        requestWithBody(url, requestBodyJson, requestCallback, Request.Method.PUT);
    }

    @Override
    public void delete(String url, String requestBodyJson, final RequestCallback requestCallback) {
        requestWithBody(url, requestBodyJson, requestCallback, Request.Method.DELETE);
    }

    /**
     * 封装带请求体的请求方法
     *
     * @param url             url
     * @param requestBodyJson Json string请求体
     * @param requestCallback 回调接口
     * @param method          请求方法
     */
    private void requestWithBody(String url, String requestBodyJson, final RequestCallback requestCallback, int method) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(requestBodyJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest<JSONObject> request = new JsonObjectRequest(method, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        requestCallback.onSuccess(response != null ? response.toString() : null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestCallback.onFailure(error);
                    }
                });
        NetApplication.mQueue.add(request);
    }

}
