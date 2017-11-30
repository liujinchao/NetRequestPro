package com.android.httplib.okhttp.callBack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

public abstract class BitmapRequestCallback extends RequestCallback<Bitmap>
{
    @Override
    public Bitmap parseNetworkResponse(Response response) throws Exception
    {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
