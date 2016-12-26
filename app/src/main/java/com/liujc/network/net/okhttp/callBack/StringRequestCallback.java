package com.liujc.network.net.okhttp.callBack;

import com.liujc.network.net.common.RequestCallback;

import java.io.IOException;

import okhttp3.Response;

public abstract class StringRequestCallback extends RequestCallback<String>
{
    @Override
    public String parseNetworkResponse(Response response) throws IOException
    {
        return response.body().string();
    }
}
