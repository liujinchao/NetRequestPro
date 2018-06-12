package com.android.httplib.basebean;

import java.io.Serializable;

/**
 * BaseResponse Create on 2017/8/9 21:24
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : 通过泛型的形式封装服务器返回数据
 */

public class BaseResponse<T> implements Serializable {
    public int code;
    public String message;
    public String resultMessage;
    public T data;
    public static final int SUCCESS = 0;

    public boolean isSuccess() {
        return SUCCESS == code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
