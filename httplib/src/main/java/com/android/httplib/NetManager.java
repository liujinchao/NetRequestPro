package com.android.httplib;

import com.android.httplib.download.DownloadRetrofit;
import com.android.httplib.retrofit.RetrofitManager;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 类名称：NetManager
 * 创建者：Create by liujc
 * 创建时间：Create on 2018/6/9 19:04
 * 描述：TODO
 */
public class NetManager {
    /**
     * @param baseUrl  api的baseUrl
     * @param apiClass 对应service类型
     * @return  所创建的对应service
     */
    public static <T> T createApiService(String baseUrl, Class<T> apiClass) {
        return RetrofitManager.createIApiService(baseUrl).getApiService(apiClass);
    }

    public static <T> T createDownloadApiService(Class<T> apiClass) {
        DownloadRetrofit downloadRetrofit = DownloadRetrofit.getInstance();
        return downloadRetrofit.getApiService(apiClass);
    }

    /**
     *
     * @param fileUrl  文件路径
     * @return 文件下载
     */
    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        return DownloadRetrofit.downloadFile(fileUrl);
    }
}
