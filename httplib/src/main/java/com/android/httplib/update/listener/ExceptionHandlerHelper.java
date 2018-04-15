package com.android.httplib.update.listener;

/**
 * 类名称：ExceptionHandlerHelper
 * 创建者：Create by liujc
 * 创建时间：Create on 2018/4/15 16:15
 * 描述：TODO
 */
public class ExceptionHandlerHelper {
    private static  ExceptionHandler instance;
    public static void init(ExceptionHandler exceptionHandler) {
        ExceptionHandler temp = instance;
        if (temp == null) {
            synchronized (ExceptionHandlerHelper.class) {
                temp = instance;
                if (temp == null) {
                    temp = exceptionHandler;
                    instance = temp;
                }
            }
        }
    }
    public static ExceptionHandler getInstance() {
        return instance;
    }
}
