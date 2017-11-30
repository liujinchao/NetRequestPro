package com.android.commonlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 类名称：CommonUtil
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/10/20
 * 描述：公共方法工具类
 */
public class CommonUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isListEmpty( List list ) {
        if( list != null && list.size() > 0 ) {
            return false;
        }

        return true;
    }

    public static boolean isInList( Object object, List objectList ) {
        boolean ret = false;

        if( !isListEmpty(objectList) ) {
            for( Object o : objectList ) {
                if( o.equals( object ) ) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }
    public static void installApk(Context context, String filePath) {
        Log.i(TAG, "开始执行安装: " + filePath);
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.w(TAG, "正常进行安装");
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static String getVersionName(Context context){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }
    public static String getSDCardPath() {
        File path = Environment.getExternalStorageDirectory();
        if (path != null) {
            return path.getAbsolutePath();
        } else {
            return "/storage/sdcard0";
        }
    }
}
