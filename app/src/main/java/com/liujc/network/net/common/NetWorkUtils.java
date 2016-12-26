package com.liujc.network.net.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 类名称：NetWorkUtils
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/12/22 12:39
 * 描述：TODO
 * 最近修改时间：2016/12/22 12:39
 * 修改人：Modify by liujc
 */
public class NetWorkUtils {
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }
}
