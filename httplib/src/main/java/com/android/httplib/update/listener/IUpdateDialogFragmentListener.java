package com.android.httplib.update.listener;


import com.android.httplib.update.UpdateAppBean;

/**
 * 类名称：IUpdateDialogFragmentListener
 * 创建者：Create by liujc
 * 创建时间：Create on 2018/4/15 16:10
 * 描述：监听更新框被取消的回调
 */
public interface IUpdateDialogFragmentListener {
    /**
     * 当默认的更新提示框被用户点击取消的时候调用
     * @param updateApp
     */
    void onUpdateNotifyDialogCancel(UpdateAppBean updateApp);
}
