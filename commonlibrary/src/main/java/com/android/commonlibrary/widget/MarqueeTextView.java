package com.android.commonlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 类名称：MarqueeTextView
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/10/20
 * 描述：TODO
 */
public class MarqueeTextView extends TextView {

	public MarqueeTextView(Context context) {
		super(context);
	}
	public MarqueeTextView(Context context, AttributeSet attrs){
		super(context,attrs);
	}
	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	public boolean isFocused(){
		return true;// 返回true，任何时候都处于focused状态，就能跑马
	}

}
