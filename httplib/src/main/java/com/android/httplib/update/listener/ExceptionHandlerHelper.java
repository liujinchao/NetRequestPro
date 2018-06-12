package com.android.httplib.update.listener;

/**
 * ExceptionHandlerHelper Create on 2018/4/15 16:15
 * @author :<a href="liujc_love@163.com">liujc</a>
 * @version :1.0
 * @Description : TODO
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
