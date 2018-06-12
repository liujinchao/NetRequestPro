package com.android.httplib.basebean;


import android.app.Dialog;

import io.reactivex.disposables.Disposable;

/**
 * CommonObserver Create on 22017/9/29 21:25
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 通用的Observer
 */

public abstract class CommonObserver<T extends BaseResponse> extends BaseObserver<T> {

    private Dialog mProgressDialog;

    public CommonObserver() {
    }

    public CommonObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    /**
     * 获取disposable 在onDestroy方法中取消订阅disposable.dispose()
     */
    protected abstract void getDisposable(Disposable d);

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onFail(ApiException errorMsg);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);


    @Override
    public void doOnSubscribe(Disposable d) {
        getDisposable(d);
    }

    @Override
    public void doOnError(ApiException errorMsg) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        onFail(errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnCompleted() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
