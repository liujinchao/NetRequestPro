package com.android.httplib.retrofit;

/**
 * RetrofitManager Create on 2018/6/9 19:09
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
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
