package com.liujc.network;

import android.databinding.ViewDataBinding;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.commonlibrary.base.BaseCompatActivity;
import com.android.commonlibrary.utils.ToastUitl;
import com.android.httplib.NetManager;
import com.android.httplib.basebean.ApiException;
import com.android.httplib.download.DownloadObserver;
import com.android.httplib.okhttp.callBack.StringRequestCallback;
import com.android.httplib.retrofit.RetrofitImpl;
import com.android.httplib.update.UpdateAppBean;
import com.android.httplib.update.UpdateAppManager;
import com.android.httplib.update.listener.ExceptionHandler;
import com.android.httplib.update.listener.IUpdateDialogFragmentListener;
import com.android.httplib.update.utils.DrawableUtil;
import com.liujc.network.net.common.IRequestManager;
import com.liujc.network.net.common.RequestFactory;
import com.liujc.network.net.okhttp.OkHttpRequestManager;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.show_msg)
    TextView showMsg;

    private String mUpdateUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        DrawableUtil.setTextStrokeTheme((TextView) findViewById(R.id.tv_download2));
        showMsg.setText("hello,liujc");
    }

    @Override
    protected boolean useDataBinding() {
        return false;
    }

    @OnClick({R.id.tv_okhttp, R.id.tv_volley, R.id.tv_retrofit, R.id.tv_download, R.id.tv_download2})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_okhttp:
                ToastUitl.showShort("okhttp");
                OkHttpRequestManager.getInstance().get("http://www.jianshu.com/u/0633b9f8256b", new StringRequestCallback() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        showMsg.setText(throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showMsg.setText(Html.fromHtml(response));
                    }
                });
                break;
            case R.id.tv_volley:
                ToastUitl.showShort("Volley");
                initHttpRequest();
                break;
            case R.id.tv_retrofit:
                ToastUitl.showShort("Retrofit");
                break;
            case R.id.tv_download:
                ToastUitl.showShort("开始下载");
                NetManager.downloadFile("https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/app-debug.apk")
                .subscribe(new DownloadObserver("test.apk") {
                    @Override
                    protected void getDisposable(Disposable d) {

                    }

                    @Override
                    protected void onError(ApiException errorMsg) {

                    }

                    @Override
                    protected void onSuccess(long bytesRead, long contentLength, float progress, boolean done, String filePath) {
                        showMsg.setText("已下载"+ progress +"%");
                    }
                });
                break;
            case  R.id.tv_download2:
                UpdateAppBean updateAppBean = new UpdateAppBean();
                updateAppBean.setUpdate(true)
                        .setNewVersion("2.7.6")
                        .setApkFileUrl("https://aihuishou-internal.oss-cn-hangzhou.aliyuncs.com/apps/official_app/Aihuishou_V2.7.6_%5Bofficial%5D_2_baidu01_sign.apk")
                        .setUpdateLog("1，全新首页新体验。\r\n2，询价换机更方便。\r\n3，品牌列表排序更智能 不用担心找不到机型。\r\n4，修复已知bug。")
                        .setApkSize("21M")
                        .setNewMd5("B5A7C226C5D10C3734D2090282DF3FBD")
                        .setForceUpdate(false);
                new UpdateAppManager.Builder()
                        //当前Activity
                        .setActivity(this)
                        //更新地址
                        .handleException(new ExceptionHandler() {
                            @Override
                            public void onException(Exception e) {
                             ToastUitl.showShort(e.getMessage());
                            }
                        })
                        .dismissNotificationProgress()
//                        // 监听更新提示框相关事件
                        .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                            @Override
                            public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                                if(updateApp.isForceUpdate()){
                                    // 处理强制更新，被用户cancel的情况
                                }
                                ToastUitl.showShort("取消更新");
                            }
                        })
                        .build()
                        .update(updateAppBean);
                break;
        }
    }
    @Override
    protected void initBinding(ViewDataBinding binding) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void initHttpRequest() {
        //测试请求
        String url = "http://blog.csdn.net/u012532559";
        //这里发起请求依赖的是IRequestManager接口
        IRequestManager requestManager = RequestFactory.getRequestManager();
        requestManager.get(url, new StringRequestCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showMsg.setText(throwable.getLocalizedMessage());
            }

            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "onSuccess: " + response.toString());
                showMsg.setText(Html.fromHtml(response));
            }
        });
    }
}
