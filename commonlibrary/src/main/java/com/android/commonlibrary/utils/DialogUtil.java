package com.android.commonlibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.commonlibrary.AppLibContext;
import com.android.commonlibrary.R;

/**
 * 类名称：DialogUtil
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/10/20
 * 描述：TODO
 */
public class DialogUtil {
    public static void setDialogToFullScreenWidth(Dialog dialog ) {
        Rect frame = new Rect();
        Window win = dialog.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(frame);
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
    }


    public static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = null;
        if(context != null){
            inflater = LayoutInflater.from(context);
        }else{
            inflater = LayoutInflater.from( AppLibContext.getAppContext());
        }

        View v = inflater.inflate(R.layout.dialog_loading_layout, null);
        FrameLayout layout = (FrameLayout) v.findViewById(R.id.dialog_view);
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,R.anim.loading_animation);
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        return loadingDialog;
    }

}
