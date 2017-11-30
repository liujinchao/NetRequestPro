package com.android.commonlibrary.base;

/**
 * 类名称：BaseView
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/28
 * 描述：View基类
 */
public interface BaseView {
    void showError(String msg);
    void showLoading(String msg);
    void hideLoading();
}
