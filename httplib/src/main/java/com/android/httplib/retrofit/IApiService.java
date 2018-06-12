package com.android.httplib.retrofit;

/**
 * IApiService Create on 2018/6/9 18:58
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
 */
public interface IApiService {
    <T> T getApiService(final Class<T> service);
}
