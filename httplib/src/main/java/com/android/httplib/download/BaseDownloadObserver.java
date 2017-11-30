package com.android.httplib.download;

import com.android.httplib.basebean.ApiException;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;

/**
 * 类名称：BaseDownloadObserver
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/30
 * 描述：TODO
 */
public abstract class BaseDownloadObserver implements Observer<ResponseBody> {

    /**
     * 失败回调
     * @param errorMsg
     */
    protected abstract void doOnError(ApiException errorMsg);


    @Override
    public void onError(@NonNull Throwable e) {
        String error = e.getMessage();
        if (e instanceof SocketTimeoutException) {
            doOnError(new ApiException(ApiException.TIME_OUT,error));
        } else {
            doOnError(new ApiException(ApiException.SHOWTOAST,error));
        }
    }
}
