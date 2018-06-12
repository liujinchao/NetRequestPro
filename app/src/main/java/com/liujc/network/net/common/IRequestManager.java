package com.liujc.network.net.common;


import java.util.Map;

public interface IRequestManager {

    void get(String url, RequestCallback requestCallback);
    void get(String url, Map<String, String> params, RequestCallback callback);

    void post(String url, String requestBodyJson, RequestCallback requestCallback);
    void post(String url, Map<String, String> params, final RequestCallback callback);
    void post(String url, Map<String, String> params, String bodyKey, final RequestCallback callback);

    void put(String url, String requestBodyJson, RequestCallback requestCallback);

    void delete(String url, String requestBodyJson, RequestCallback requestCallback);

}
