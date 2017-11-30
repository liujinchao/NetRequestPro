package com.android.commonlibrary.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 类名称：FontHelper
 * 创建者：Created by Liujc.
 * 创建时间：Created on 2016/12/11
 * 描述：iconfont工具类
 */

public class FontHelper {
    public static final String DEF_FONT = "iconfont/iconfont.ttf";

    public static final void injectFont(View rootView) {
        injectFont(rootView, Typeface.createFromAsset(rootView.getContext().getAssets(),
                DEF_FONT));
    }

    private static void injectFont(View rootView, Typeface typeface) {
        if (rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            int childViewCount = viewGroup.getChildCount();
            for (int i = 0; i < childViewCount; i++) {
                injectFont(viewGroup.getChildAt(i), typeface);
            }
        } else if (rootView instanceof TextView) {
            ((TextView) rootView).setTypeface(typeface);
        }
    }
}
