package com.android.httplib.basebean;

import com.android.httplib.baserx.ISubscriber;
import com.android.httplib.utils.LogUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 类名称：BaseObserver
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/29 10:26
 * 描述：TODO
 */
public abstract class BaseObserver<T extends BaseResponse> implements Observer<T>, ISubscriber<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        doOnNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String error = e.getMessage();
        LogUtil.d(error);
        if (e instanceof ConnectException ||
                e instanceof SocketTimeoutException ||
                e instanceof TimeoutException) {
            doOnError(new ApiException(ApiException.TIME_OUT,error));
        } else if (e instanceof ApiException){
            doOnError((ApiException)e);
        }else {
            doOnError(new ApiException(ApiException.SHOWTOAST,error));
        }
    }


    @Override
    public void onComplete() {
        doOnCompleted();
    }

}
