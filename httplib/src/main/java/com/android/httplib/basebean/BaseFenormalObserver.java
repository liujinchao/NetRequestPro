package com.android.httplib.basebean;

import com.android.httplib.baserx.IFenormalSubscriber;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * BaseFenormalObserver Create on 2017/9/29 10:26
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 服务器返回格式不统一的处理(即服务器返回结果没有按照BaseResponse定义的格式返回，需单独处理)
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
