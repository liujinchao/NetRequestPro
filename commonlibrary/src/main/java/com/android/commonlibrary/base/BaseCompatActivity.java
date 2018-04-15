package com.android.commonlibrary.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.android.commonlibrary.R;
import com.android.commonlibrary.utils.ActivityUtils;
import com.android.commonlibrary.utils.DialogUtil;
import com.android.commonlibrary.utils.ToastUitl;
import com.android.commonlibrary.widget.TitleBar;
import com.android.httplib.utils.LogUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static android.content.ContentValues.TAG;

/**
 * 类名称：BaseActivity
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/9
 * 描述：activity基类
 */
public abstract class BaseCompatActivity extends RxAppCompatActivity implements BaseView{
    private TitleBar titleBar = null;
    protected Context mContext = null;
    private ViewDataBinding binding;
    private Dialog dialog;
    protected List<Disposable> disposables = new ArrayList<>();
    protected RxPermissions rxPermission = null;

    /**
     * 进入进出动画模式
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doTransitionAnimations();
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityUtils.getActivityManager(mContext).addActivity(this);
        rxPermission = new RxPermissions(this);
        if (getContentViewLayoutID() != 0) {
            if (useDataBinding()){
                binding = DataBindingUtil.setContentView(this,getContentViewLayoutID());
                initBinding(binding);
            }else {
               setContentView(getContentViewLayoutID());
            }
        }
        ButterKnife.bind(this);
        initViewsAndEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposables != null) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
            disposables.clear();
        }
        hideLoading();
        dismissDialog();
        ActivityUtils.getActivityManager(mContext).finishActivity(this);
        doTransitionAnimations();
    }

    private void doTransitionAnimations() {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    public void showError(String msg) {
        ToastUitl.showShort(msg);
        LogUtil.d(msg);
    }

    @Override
    public void showLoading(String msg) {
        dialog = DialogUtil.createLoadingDialog(this);
        dialog.show();
        LogUtil.d(msg);
    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        LogUtil.d("hideLoading");
    }

    /**
     * 销毁对话框
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public TitleBar getTitleBar() {
        if (titleBar == null) {
            titleBar = new TitleBar(this,
                    ((ViewGroup) findViewById(android.R.id.content))
                            .getChildAt(0));
        }
        return titleBar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取布局文件
     */
    protected abstract int getContentViewLayoutID();
    /**
     * 初始化views和events
     */
    protected abstract void initViewsAndEvents();
    /**
     * 是否使用databinding
     */
    protected abstract boolean useDataBinding();
    /**
     * 初始化binding
     */
    protected abstract void initBinding(ViewDataBinding binding);

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void doRequestEachPermissions(String... permissions){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            rxPermission.requestEach(permissions)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(@NonNull Permission permission) throws Exception {
                            if (permission.granted) {
                                // 用户已经同意该权限
                                Log.d(TAG, permission.name + " is granted.");
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                Log.d(TAG, permission.name + " is denied. More info should be provided.");
                            } else {
                                // 用户拒绝了该权限，并且选中『不再询问』
                                Log.d(TAG, permission.name + " is denied.");
                            }
                        }
                    });
        }
    }

    protected boolean doRequestPermissions(String... permissions){
        final boolean[] isGrant = {false};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermission.request(permissions)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(@NonNull Boolean aBoolean) throws Exception {
                            isGrant[0] = aBoolean;
                        }
                    });
        }else {
            isGrant[0] = true;
        }
        LogUtil.d(isGrant[0]+"");
        return isGrant[0];
    }

}
