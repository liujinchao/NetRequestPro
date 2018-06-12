package com.android.httplib.update.listener;


import com.android.httplib.update.UpdateAppBean;

/**
 * IUpdateDialogFragmentListener Create on 2018/4/15 16:10
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 监听更新框被取消的回调
 */

public interface IUpdateDialogFragmentListener {
    /**
     * 当默认的更新提示框被用户点击取消的时候调用
     * @param updateApp
     */
    void onUpdateNotifyDialogCancel(UpdateAppBean updateApp);
}
