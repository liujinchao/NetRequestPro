package com.android.httplib.okhttp.callBack;

import android.util.Log;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 类名称：RequestCallback
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/26 11:15
 * 描述：请求返回成功/失败，成功时，把服务器返回的结果回调出去，失败时回调异常信息
 */
public abstract class RequestCallback<T>
{
    public void onBefore(Request request)
    {
        Log.d("TAG","onBefore:"+request.url());
    }

    public void onAfter()
    {
        Log.d("TAG","onAfter");
    }
    public void inProgress(float progress)
    {

    }

    /**
     * if you parse reponse code in parseNetworkResponse, you should make this method return true.
     * @param response
     * @return
     */
    public boolean validateReponse(Response response)
    {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onFailure(Throwable throwable);

    public abstract void onSuccess(T response);


    public static RequestCallback CALLBACK_DEFAULT = new RequestCallback()
    {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception
        {
            return null;
        }

        @Override
        public void onFailure(Throwable throwable)
        {

        }

        @Override
        public void onSuccess(Object response)
        {

        }
    };

}