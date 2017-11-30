package com.android.commonlibrary.base;

/**
 * 类名称：BasePresenter
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/28
 * 描述：Presenter基类
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
