package com.android.httplib.download;

/**
 * 类名称：ProgressListener
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/30
 * 描述：TODO
 */
public interface ProgressListener {

    /**
     * 载进度监听
     *
     * @param bytesRead     已经下载文件的大小
     * @param contentLength 文件的大小
     * @param done          是否下载完成
     */
    void onResponseProgress(long bytesRead, long contentLength, int progress, boolean done, String filePath);


}
