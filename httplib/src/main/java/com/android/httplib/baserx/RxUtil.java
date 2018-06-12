package com.android.httplib.baserx;

import com.android.httplib.basebean.ApiException;
import com.android.httplib.basebean.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * RxUtil Create on 2017/9/29 10:04
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
 */

public class RxUtil {
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    /**
     * 处理服务器返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    if (result.isSuccess()) {
                        return Observable.just(result.data);
                    } else {
                        return Observable.error(new ApiException(result.code,result.message));
                    }
                }

        );
    }

    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {

                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
