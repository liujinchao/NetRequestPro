package com.android.httplib.update;

import java.io.Serializable;

/**
 * 类名称：UpdateAppBean
 * 创建者：Create by liujc
 * 创建时间：Create on 2018/4/15 16:36
 * 描述：TODO版本信息
 */
public class UpdateAppBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * update : true
     * new_version : xxxxx
     * apk_url : http://xxxxx/xx.apk
     * update_desc : xxxx
     * new_md5 : xxxxxxxxxxxxxx
     * apk_size : 601132
     */
    //是否有新版本
    private boolean update;
    //新版本号
    private String new_version;
    //新app下载地址
    private String apk_file_url;
    //更新文案
    private String update_desc;
    //标题
    private String update_def_dialog_title;
    //新app大小
    private String apk_size;
    //是否强制更新
    private boolean force_update;
    //md5
    private String new_md5;

    private String targetPath;
    private boolean mHideDialog;
    private boolean mShowIgnoreVersion;
    private boolean mDismissNotificationProgress;
    private boolean mOnlyWifi;

    //是否隐藏对话框下载进度条,内部使用
    public boolean isHideDialog() {
        return mHideDialog;
    }

    public void setHideDialog(boolean hideDialog) {
        mHideDialog = hideDialog;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public UpdateAppBean setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }

    public boolean isForceUpdate() {
        return force_update;
    }

    public UpdateAppBean setForceUpdate(boolean force_update) {
        this.force_update = force_update;
        return this;
    }

    public boolean isUpdate() {
        return update;
    }

    public UpdateAppBean setUpdate(boolean update) {
        this.update = update;
        return this;
    }

    public String getNewVersion() {
        return new_version;
    }

    public UpdateAppBean setNewVersion(String new_version) {
        this.new_version = new_version;
        return this;
    }

    public String getApkFileUrl() {
        return apk_file_url;
    }


    public UpdateAppBean setApkFileUrl(String apk_file_url) {
        this.apk_file_url = apk_file_url;
        return this;
    }

    public String getUpdateDesc() {
        return update_desc;
    }

    public UpdateAppBean setUpdateLog(String update_desc) {
        this.update_desc = update_desc;
        return this;
    }

    public String getUpdateDefDialogTitle() {
        return update_def_dialog_title;
    }

    public UpdateAppBean setUpdateDefDialogTitle(String updateDefDialogTitle) {
        this.update_def_dialog_title = updateDefDialogTitle;
        return this;
    }

    public String getNewMd5() {
        return new_md5;
    }

    public UpdateAppBean setNewMd5(String new_md5) {
        this.new_md5 = new_md5;
        return this;
    }

    public String getApkSize() {
        return apk_size;
    }

    public UpdateAppBean setApkSize(String apk_size) {
        this.apk_size = apk_size;
        return this;
    }

    public boolean isShowIgnoreVersion() {
        return mShowIgnoreVersion;
    }

    public void showIgnoreVersion(boolean showIgnoreVersion) {
        mShowIgnoreVersion = showIgnoreVersion;
    }

    public void dismissNotificationProgress(boolean dismissNotificationProgress) {
        mDismissNotificationProgress = dismissNotificationProgress;
    }

    public boolean isDismissNotificationProgress() {
        return mDismissNotificationProgress;
    }

    public boolean isOnlyWifi() {
        return mOnlyWifi;
    }

    public void setOnlyWifi(boolean onlyWifi) {
        mOnlyWifi = onlyWifi;
    }

}
