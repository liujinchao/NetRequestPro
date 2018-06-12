package com.android.httplib.download;

/**
 * ProgressListener Create on 2017/9/30 21:33
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
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
