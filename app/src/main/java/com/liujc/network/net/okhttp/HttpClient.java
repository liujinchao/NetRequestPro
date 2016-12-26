package com.liujc.network.net.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.liujc.network.NetApplication;
import com.liujc.network.net.common.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


/**
 * 类名称：OkHttpClient
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/11/22 15:59
 * 描述：OkHttpClient的配置
 * 可根据自己的需要进行相关的修改
 */
public class HttpClient {
    private static final String TAG = "OkHttpClient";
    //读超时长，单位：秒
    public static final int READ_TIME_OUT = 30;
    public static final int WRITE_TIME_OUT = 30;
    //连接时长，单位：秒
    public static final int CONNECT_TIME_OUT = 30;
    private static okhttp3.OkHttpClient mOkHttpClient;

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 获取OkHttpClient对象
     */
    public static okhttp3.OkHttpClient getOkHttpClient() {
        //开启Log
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        LogInterceptor logInterceptor = new LogInterceptor();

        File cacheFile = new File(NetApplication.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor =new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept", "*/*")
                        .build();
                return chain.proceed(build);
            }
        };
        /**
         * 云端响应头拦截器，用来配置缓存策略
         * Dangerous interceptor that rewrites the server's cache-control header.
         */
        Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.isNetworkConnected(NetApplication.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetWorkUtils.isNetworkConnected(NetApplication.getContext())) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
        if (null == mOkHttpClient) {

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new okhttp3.OkHttpClient.Builder()
                    .connectTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(mRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                    .addInterceptor(headerInterceptor)
                    .addInterceptor(logInterceptor)
                    .sslSocketFactory(HttpsUtils.getSslSocketFactory(null, null, null))
                    .cache(cache)
                    .build();
        }

        return mOkHttpClient;
    }

    private static class LogInterceptor implements Interceptor {
        private static final String F_BREAK = " %n";
        private static final String F_URL = " %s";
        private static final String F_TIME = " in %.1fms";
        private static final String F_HEADERS = "%s";
        private static final String F_RESPONSE = F_BREAK + "Response: %d";
        private static final String F_BODY = "body: %s";

        private static final String F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK;
        private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS;
        private static final String F_RESPONSE_WITHOUT_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BREAKER;
        private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
        private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAKER;

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            MediaType contentType = null;
            String bodyString = null;
            if (response.body() != null) {
                contentType = response.body().contentType();
                bodyString = response.body().string();
            }
            // 请求响应时间
            double time = (t2 - t1) / 1e6d;
            switch (request.method()) {
                case "GET":
                    Log.d(TAG,"OkHttpClient--> "+
                            String.format("GET " + F_REQUEST_WITHOUT_BODY  + F_RESPONSE_WITH_BODY,
                                    request.url(),
                                    time,
                                    request.headers(),
                                    response.code(),
                                    response.headers(),
                                    stringifyResponseBody(bodyString)));
                    break;
                case "POST":
                    Log.d(TAG,"OkHttpClient--> " +
                            String.format("POST " + F_REQUEST_WITH_BODY  + F_RESPONSE_WITH_BODY,
                                    request.url(),
                                    time,
                                    request.headers(),
                                    stringifyRequestBody(request),
                                    response.code(),
                                    response.headers(),
                                    stringifyResponseBody(bodyString)));
                    break;
                case "PUT":
                    Log.d(TAG,"OkHttpClient--> "+
                            String.format("PUT " + F_REQUEST_WITH_BODY  + F_RESPONSE_WITH_BODY,
                                    request.url(),
                                    time,
                                    request.headers(),
                                    request.body().toString(),
                                    response.code(),
                                    response.headers(),
                                    stringifyResponseBody(bodyString)));
                    break;
                case "DELETE":
                    Log.d(TAG,"OkHttpClient--> "+
                            String.format("DELETE " + F_REQUEST_WITHOUT_BODY  + F_RESPONSE_WITHOUT_BODY,
                                    request.url(),
                                    time,
                                    request.headers(),
                                    response.code(),
                                    response.headers()));
                    break;
            }
            if (response.body() != null) {
                // 深坑！
                // 打印body后原ResponseBody会被清空，需要重新设置body
                ResponseBody body = ResponseBody.create(contentType, bodyString);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
        private static String stringifyRequestBody(Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }

        public String stringifyResponseBody(String responseBody) {
            return responseBody;
        }
    }


}
