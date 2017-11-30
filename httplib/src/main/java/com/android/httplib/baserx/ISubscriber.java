package com.android.httplib.baserx;

import com.android.httplib.basebean.ApiException;
import com.android.httplib.basebean.BaseResponse;

import io.reactivex.disposables.Disposable;

/**
 * 类名称：ISubscriber
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/29 10:24
 * 描述：TODO
 */
public interface ISubscriber<T extends BaseResponse> {

    void doOnSubscribe(Disposable d);

    void doOnError(ApiException errorMsg);

    void doOnNext(T t);

    void doOnCompleted();
}
