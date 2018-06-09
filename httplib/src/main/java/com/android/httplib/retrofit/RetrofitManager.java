package com.android.httplib.retrofit;

/**
 * 类名称：RetrofitManager
 * 创建者：Create by liujc
 * 创建时间：Create on 2018/6/9 19:09
 * 描述：TODO
 */
public class RetrofitManager {
    private volatile static IApiService iApiService;

    public static IApiService createIApiService(String baseUrl) {
        if (iApiService == null) {
            synchronized (RetrofitManager.class) {
                if (iApiService == null) {
                    iApiService = new RetrofitImpl(baseUrl);
                }
            }
        }
        return iApiService;
    }
}
