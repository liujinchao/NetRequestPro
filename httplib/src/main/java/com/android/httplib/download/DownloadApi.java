package com.android.httplib.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * DownloadApi Create on 2017/9/30 21:30
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 默认下载文件api
 */

public interface DownloadApi {

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
