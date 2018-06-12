package com.android.httplib.gson;

import com.android.httplib.basebean.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * HttpStatus Create on 2017/11/10 23:38
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
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
