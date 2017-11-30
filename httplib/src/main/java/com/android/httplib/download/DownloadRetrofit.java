package com.android.httplib.download;


import com.android.httplib.baserx.RxUtil;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类名称：DownloadRetrofit
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/30
 * 描述：TODO
 */
public class DownloadRetrofit {

    private static DownloadRetrofit instance;
    private Retrofit mRetrofit;

    public DownloadRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(ApiConstants.getHost(HostType.SERVER_URL_OFFICE))
                .build();
    }

    public static DownloadRetrofit getInstance() {
        if (instance == null) {
            synchronized (DownloadRetrofit.class) {
                if (instance == null) {
                    instance = new DownloadRetrofit();
                }
            }
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        return DownloadRetrofit
                .getInstance()
                .getRetrofit()
                .create(DownloadApi.class)
                .downloadFile(fileUrl)
                .compose(RxUtil.<ResponseBody>switchSchedulers());
    }
}
