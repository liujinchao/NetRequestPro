package com.android.httplib.update;

/**
 * UpdateCallback Create on 2018/4/15 16:37
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 版本更新管理器
 */

public class UpdateCallback {


    /**
     * 有新版本
     *
     * @param updateApp        新版本信息
     * @param updateAppManager app更新管理器
     */
    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
        updateAppManager.showDialogFragment();
    }

    /**
     * 网路请求之后
     */
    protected void onAfter() {
    }


    /**
     * 没有新版本
     * @param error HttpManager实现类请求出错返回的错误消息，交给使用者自己返回，有可能不同的应用错误内容需要提示给客户
     */
    protected void noNewApp(String error) {
    }

    /**
     * 网络请求之前
     */
    protected void onBefore() {
    }

}
