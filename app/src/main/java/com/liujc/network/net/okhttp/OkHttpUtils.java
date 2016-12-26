package com.liujc.network.net.okhttp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.liujc.network.NetApplication;
import com.liujc.network.net.common.RequestCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils
{
    private static final String TAG = "OkHttpUtils";
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private Handler mDelivery;
    private static Context context;

    public static OkHttpUtils getInstance()
    {
        return initClient(null);
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient)
    {
        context = NetApplication.getContext();
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }
    public OkHttpUtils(OkHttpClient okHttpClient)
    {
        mDelivery = new Handler(Looper.getMainLooper());
        if (okHttpClient == null)
        {
            mOkHttpClient = new HttpClient().getOkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mPlatform = Platform.get();
    }

    public Executor getDelivery()
    {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }

    public void sendFailResultCallback(final Call call, final Exception e, final RequestCallback callback)
    {
        if (callback == null)
            return;
        mPlatform.execute(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onFailure(e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final RequestCallback callback)
    {
        if (callback == null)
            return;
        mPlatform.execute(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onSuccess(object);
                callback.onAfter();
            }
        });
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response _getAsyn(String url) throws IOException
    {
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response execute = mOkHttpClient.newCall(request).execute();
        return execute;
    }

    /**
     * 同步的Get请求
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url) throws IOException
    {
        Response execute = _getAsyn(url);
        return execute.body().string();
    }


    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final RequestCallback callback)
    {
        Log.d(TAG,"url:"+url);
        final Request request;
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-type", String.valueOf(TYPE_JSON))
                    .build();
        deliveryResult(callback, request);
    }

    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, Map<String, String> params , final RequestCallback callback)
    {
        if (reqMap == null){
            reqMap = new HashMap<String,String>();
        }else {
            reqMap.clear();
        }

        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            if (!TextUtils.isEmpty(next.getValue())){
                url = url+ "&"+next.getKey() + "="+next.getValue();
                reqMap.put(next.getKey(), next.getValue());
            }
        }
        Log.d(TAG,url);
        final Request request = new Request.Builder()
                                .url(url)
                                .get()
                                .build();
        deliveryResult(callback, request);
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException
    {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }
    private Response _post(String url, Map<String, String> params) throws IOException
    {
        Param[] paramsArr = map2Params(params);
        Response response = _post(url, paramsArr);
        return response;
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException
    {
        Response response = _post(url, params);
        return response.body().string();
    }
    private String _postAsString(String url, Map<String, String> params) throws IOException
    {
        Param[] paramsArr = map2Params(params);
        Response response = _post(url, paramsArr);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final RequestCallback callback, Param... params)
    {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }



    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final RequestCallback callback, Map<String, String> params)
    {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postBodyAsyn(String url, final RequestCallback callback, Map<String, String> params, String bodyKey)
    {
        Request request = buildPostBodyRequest(url, params, bodyKey);
        deliveryResult(callback, request);
    }
    /**
     * 同步基于post的文件上传
     *
     * @param params
     * @return
     */
    private Response _post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, RequestCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, RequestCallback callback, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, RequestCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param callback
     */
    private void _downloadAsyn(final String url, RequestCallback callback)
    {
        if (callback == null)
            callback = RequestCallback.CALLBACK_DEFAULT;
        final RequestCallback finalCallback = callback;
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call, e, finalCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (call.isCanceled()) {
                    sendFailResultCallback(call, new IOException("Canceled!"), finalCallback);
                    return;
                }
                if (!finalCallback.validateReponse(response)) {
                    sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback);
                    return;
                }

                try {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback);

                }
            }
        });
    }

    private String getFileName(String path)
    {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 加载图片
     *
     * @param callback
     * @param url
     * @throws IOException
     */
    private void _displayImage(RequestCallback callback, final String url)
    {
        if (callback == null)
            callback = RequestCallback.CALLBACK_DEFAULT;
        final RequestCallback finalCallback = callback;
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call, e, finalCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (call.isCanceled()) {
                    sendFailResultCallback(call, new IOException("Canceled!"), finalCallback);
                    return;
                }
                if (!finalCallback.validateReponse(response)) {
                    sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback);
                    return;
                }

                try {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback);

                }
            }
        });
    }

    /**
     * 加载图片
     *
     * @param view
     * @param url
     * @throws IOException
     */
    private void _displayImage(final ImageView view, final String url, final int errorResId)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setErrorResId(view, errorResId);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                try {
                    is = response.body().byteStream();
                    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
                    ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
                    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                    try {
                        is.reset();
                    } catch (IOException e) {
                        response = _getAsyn(url);
                        is = response.body().byteStream();
                    }

                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
                    ops.inSampleSize = inSampleSize;
                    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e) {
                    setErrorResId(view, errorResId);

                } finally {
                    if (is != null) try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void setErrorResId(final ImageView view, final int errorResId)
    {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(errorResId);
            }
        });
    }

    private void _post(String url, String requestBodyJson, RequestCallback requestCallback){
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        deliveryResult(requestCallback, request);
    }
    private void _put(String url, String requestBodyJson, RequestCallback requestCallback){
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        deliveryResult(requestCallback, request);
    }
    private void _delete(String url, String requestBodyJson, RequestCallback requestCallback){
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        deliveryResult(requestCallback, request);
    }

    //*************供外部调用的方法************
    public static String getAsString(String url) throws IOException
    {
        return getInstance()._getAsString(url);
    }

    public static void getAsyn(String url, RequestCallback callback)
    {
        getInstance()._getAsyn(url,new HashMap<String, String>(), callback);
    }
    public static void getAsyn(String url, Map<String, String> params, RequestCallback callback)
    {
        getInstance()._getAsyn(url,params,callback);
    }

    public static String postAsString(String url, Map<String, String> params) throws IOException
    {
        return getInstance()._postAsString(url, params);
    }

    public static void postAsyn(String url, Map<String, String> params, final RequestCallback callback)
    {
        getInstance()._postAsyn(url, callback, params);
    }

    public static void _postBodyAsyn(String url, Map<String, String> params, String bodyKey, final RequestCallback callback)
    {
        getInstance()._postBodyAsyn(url, callback, params,bodyKey);
    }


    public static Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        return getInstance()._post(url, files, fileKeys, params);
    }

    public static Response post(String url, File file, String fileKey) throws IOException
    {
        return getInstance()._post(url, file, fileKey);
    }

    public static Response post(String url, File file, String fileKey, Param... params) throws IOException
    {
        return getInstance()._post(url, file, fileKey, params);
    }

    public static void postAsyn(String url, RequestCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        getInstance()._postAsyn(url, callback, files, fileKeys, params);
    }


    public static void postAsyn(String url, RequestCallback callback, File file, String fileKey) throws IOException
    {
        getInstance()._postAsyn(url, callback, file, fileKey);
    }


    public static void postAsyn(String url, RequestCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }

    public static void displayImage(RequestCallback callback, String url) throws IOException
    {
        getInstance()._displayImage(callback, url);
    }

    public static void displayImage(final ImageView view, String url, int errorResId) throws IOException
    {
        getInstance()._displayImage(view, url, errorResId);
    }


    public static void post(String url, String requestBodyJson, RequestCallback requestCallback){
        getInstance()._post(url, requestBodyJson, requestCallback);
    }
    public static void put(String url, String requestBodyJson, RequestCallback requestCallback){
        getInstance()._put(url, requestBodyJson, requestCallback);
    }
    public static void delete(String url, String requestBodyJson, RequestCallback requestCallback){
        getInstance()._delete(url, requestBodyJson, requestCallback);
    }
    public static void displayImage(final ImageView view, String url)
    {
        getInstance()._displayImage(view, url, -1);
    }

    public static void downloadAsyn(String url, RequestCallback callback)
    {
        getInstance()._downloadAsyn(url, callback);
    }

    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params)
    {
        params = validateParam(params);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (Param param : params)
        {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null)
        {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                                "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params)
    {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params)
    {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries)
        {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private void deliveryResult(final RequestCallback callback, Request request)
    {
        callback.onBefore(request);
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (call.isCanceled()) {
                    sendFailResultCallback(call, new IOException("Canceled!"), callback);
                    return;
                }

                if (!callback.validateReponse(response)) {
                    sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), callback);
                    return;
                }

                try {
                    Object o = callback.parseNetworkResponse(response);
                        sendSuccessResultCallback(o, callback);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, callback);
                }

            }
        });
    }

    Map<String,String> reqMap;
    private Request buildPostRequest(String url, Param[] params)
    {
        if (reqMap == null){
            reqMap = new HashMap<String,String>();
        }else {
            reqMap.clear();
        }
        //添加其他参数
        for (int i=0;i<params.length;i++){
            reqMap.put(params[i].key,params[i].value);
        }
        if (params == null)
        {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params)
        {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-type",String.valueOf(TYPE_JSON))
                .build();
    }


    private Request buildPostBodyRequest(String url, Map<String, String> params, String bodyKey)
    {
        String object = params.get(bodyKey);
        params.remove(bodyKey);
        if (reqMap == null){
            reqMap = new HashMap<String,String>();
        }else {
            reqMap.clear();
        }
        String bodyStr = JSON.toJSONString(params).substring(0,JSON.toJSONString(params).length()-1).concat(",\""+bodyKey+"\":"+object+"}");
        Log.d(TAG,bodyStr);
        RequestBody requestBody = RequestBody.create(TYPE_JSON,bodyStr);
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-type", String.valueOf(TYPE_JSON))
                .build();
    }

    public static class Param
    {
        public Param()
        {
        }

        public Param(String key, String value)
        {
            this.key = key;
            this.value = value;
        }
        public void put(String key, String value)
        {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }

}

