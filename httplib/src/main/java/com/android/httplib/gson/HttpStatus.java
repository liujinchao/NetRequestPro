package com.android.httplib.gson;

import com.android.httplib.basebean.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * 类名称：HttpStatus
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/11/10 23:38
 * 描述：TODO
 */

public class HttpStatus {
    @SerializedName("code")
    private int mCode;
    @SerializedName("message")
    private String mMessage;

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    /**
     * API是否请求失败
     *
     * @return 失败返回true, 成功返回false
     */
    public boolean isSuccess() {
        return mCode == BaseResponse.SUCCESS;
    }
}
