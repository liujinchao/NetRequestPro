package com.android.httplib.retrofit;

/**
 * 类名称：IApiService
 * 创建者：Create by liujc
 * 创建时间：Create on 2018/6/9 18:58
 * 描述：TODO
 */
public interface IApiService {
    <T> T getApiService(final Class<T> service);
}
