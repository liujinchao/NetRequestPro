package com.android.httplib.basebean;

import com.android.httplib.baserx.ISubscriber;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * BaseObserver Create on 2017/9/29 10:26
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 正常restful api返回结果
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
