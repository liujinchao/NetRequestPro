package com.android.commonlibrary.base.binding;

import android.databinding.ViewDataBinding;

/**
 * 类名称：BindingItemListen
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/10/11 21:54
 * 描述：TODO
 */
@FunctionalInterface
public interface BindingItemListen<T, B extends ViewDataBinding> {
    void onItemClick(B binding, T t, int i);
}