package com.android.httplib.baserx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * RetryWithDelay Create on 2017/9/28 13:18
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 重试设置
 */

public class RetryWithDelay  implements Function<Observable<? extends Throwable>, ObservableSource<?>> {

    private static final int MAX_RETRIES = 3;
    private static final int DELAY_MILLIS = 2000;

    private int maxRetries;
    private int delayMillis;
    private int retryCount;

    public RetryWithDelay() {
        this(MAX_RETRIES, DELAY_MILLIS);
    }
    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.delayMillis = retryDelayMillis;
        this.retryCount = 0;
    }

    @Override
    public ObservableSource<?> apply(Observable<? extends Throwable> attempts) throws Exception {
        return  attempts
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        if (++retryCount <= maxRetries) {
                            return Observable.timer(delayMillis,
                                    TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(throwable);
                    }
                });
    }
}