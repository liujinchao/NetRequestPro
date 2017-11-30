package com.android.httplib.retrofit;

import com.android.httplib.download.DownloadRetrofit;
import com.android.httplib.gson.ResponseConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 类名称：RetrofitManager
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/9/28
 * 描述：retrofit集成0kHttp3的抽象基类
 */
public class RetrofitManager {

    private static Retrofit mRetrofit = null;
    private static OkHttpClient mOkHttpClient = null;
    /**
     * 获取Retrofit对象
     *
     * @return
     */
    public static Retrofit getRetrofit(String baseUrl){
        if (null == mRetrofit) {
            mRetrofit = initRetrofitBuilder(baseUrl).build();
        }else if (!mRetrofit.baseUrl().url().toString().equals(baseUrl)){
            mRetrofit = initRetrofitBuilder(baseUrl).build();
        }
        return mRetrofit;
    }

    private static Retrofit.Builder  initRetrofitBuilder(String baseUrl){
        if (!baseUrl.endsWith("/")){
            baseUrl = baseUrl +"/";
        }
        if (null == mOkHttpClient) {
            mOkHttpClient = OkHttp3Util.getOkHttpClient();
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        Retrofit.Builder builder = new Retrofit.Builder()
                                //设置服务器路径
                                .baseUrl(baseUrl)
                                //添加转化库，默认是Gson
//                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .addConverterFactory(ResponseConverterFactory.create(gson))
                                //添加回调库，采用RxJava
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                //设置使用okhttp网络请求
                                .client(mOkHttpClient);
        return builder;
    }
    /*下载文件*/
    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        return DownloadRetrofit.downloadFile(fileUrl);
    }
}
