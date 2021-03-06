package com.android.httplib.retrofit;

import com.android.httplib.NetManager;
import com.android.httplib.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * OkHttp3Util Create on 2017/9/28 21:38
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : okHttp的配置
 */

public class OkHttp3Util {
    //读超时长，单位：秒
    public static final int READ_TIME_OUT = 30;
    public static final int WRITE_TIME_OUT = 30;
    //连接时长，单位：秒
    public static final int CONNECT_TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (OkHttp3Util.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = initOkHttpClient();
                }
            }
        }
        return mOkHttpClient;

    }

    private static OkHttpClient initOkHttpClient() {
        LogInterceptor logInterceptor = new LogInterceptor();

        File cacheFile = new File(NetManager.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor =  new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long timestamp = System.currentTimeMillis() / 1000;  // unit: second
                Request.Builder requestBuilder = chain.request().newBuilder();
                Request request =   requestBuilder
                                        .addHeader("timestamp", timestamp + "")
                                        .build();
                return chain.proceed(request);
            }
        };
        SSLUtil.SSLParams sslParams = SSLUtil.getSslSocketFactory();
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cache(cache)
                .build();
        return mOkHttpClient;
    }

    public static class LogInterceptor implements Interceptor {
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
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long t1 = System.nanoTime();
                Response response = chain.proceed(chain.request());
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
                        LogUtil.d("retrofit--> "+
                                String.format("GET " + F_REQUEST_WITHOUT_BODY  + F_RESPONSE_WITH_BODY,
                                        request.url(),
                                        time,
                                        request.headers(),
                                        response.code(),
                                        response.headers(),
                                        stringifyResponseBody(bodyString)));
                        break;
                    case "POST":
                        LogUtil.d("retrofit--> " +
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
                        LogUtil.d("retrofit--> "+
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
                        LogUtil.d("retrofit--> "+
                                String.format("DELETE " + F_REQUEST_WITHOUT_BODY  + F_RESPONSE_WITHOUT_BODY,
                                        request.url(),
                                        time,
                                        request.headers(),
                                        response.code(),
                                        response.headers()));
                        break;
                }
                if (response.body() != null) {
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
