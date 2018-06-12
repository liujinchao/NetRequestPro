package com.android.httplib.baserx;



import com.android.httplib.basebean.ApiException;

import io.reactivex.disposables.Disposable;

/**
 * IFenormalSubscriber Create on 2017/9/29 10:24
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
 */

public interface IFenormalSubscriber<T> {

    void doOnSubscribe(Disposable d);

    void doOnError(ApiException errorMsg);

    void doOnNext(T t);

}
