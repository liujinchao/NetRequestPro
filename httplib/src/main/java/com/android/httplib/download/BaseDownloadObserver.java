package com.android.httplib.download;

import com.android.httplib.basebean.ApiException;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;

/**
 * BaseDownloadObserver Create on 2017/9/30 21:30
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
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
