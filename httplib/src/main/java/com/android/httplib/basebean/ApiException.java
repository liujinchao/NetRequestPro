package com.android.httplib.basebean;

import java.io.Serializable;

/**
 * ApiException Create on 2017/9/28 12:36
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
 */

public class ApiException extends RuntimeException implements Serializable{
    private int code;
    private String msg;

    public static final int SIGN_OUT = 401;//需要登陆
    public static final int SHOWTOAST = 102;//显示Toast
    public static final int TIME_OUT = 103;//请求超时

    public boolean isTokenInvalid() {
        return SIGN_OUT == code;
    }

    public boolean isShowToast() {
        return SHOWTOAST == code;
    }
    public boolean isTimeOut() {
        return TIME_OUT == code;
    }
    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
