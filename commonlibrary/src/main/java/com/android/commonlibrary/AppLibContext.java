package com.android.commonlibrary;

import android.content.Context;

import com.android.httplib.NetManager;

/**
 * 类名称：AppContext
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/3/30 9:48
 * 描述：TODO
 */
public class AppLibContext {
  public static Context mAppContext;

  public static void init(Context context) {
    if (mAppContext == null) {
      mAppContext = context.getApplicationContext();
      NetManager.init(mAppContext);
    } else {
      throw new IllegalStateException("set context duplicate");
    }
  }

  public static Context getAppContext() {
    if (mAppContext == null) {
      throw new IllegalStateException("forget init?");
    } else {
      return mAppContext;
    }
  }
}
