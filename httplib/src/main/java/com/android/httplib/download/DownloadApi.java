package com.android.httplib.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 类名称：DownloadApi
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/30
 * 描述：下载文件
 */
public interface DownloadApi {

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
