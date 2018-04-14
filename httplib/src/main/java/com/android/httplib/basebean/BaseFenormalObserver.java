package com.liujc.httplib.basebean;


import com.android.httplib.basebean.ApiException;
import com.android.httplib.utils.LogUtil;
import com.liujc.httplib.baserx.IFenormalSubscriber;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 类名称：BaseFenormalObserver
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/29 10:26
 * 描述：服务器返回格式不统一的处理(即服务器返回结果没有按照BaseResponse定义的格式返回，需单独处理)
 */
public abstract class BaseFenormalObserver<T> implements Observer<T>, IFenormalSubscriber<T> {

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

    }

}
