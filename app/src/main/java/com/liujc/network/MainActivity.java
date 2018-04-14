package com.liujc.network;

import android.databinding.ViewDataBinding;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.commonlibrary.base.BaseActivity;
import com.android.commonlibrary.utils.ToastUitl;
import com.android.httplib.basebean.ApiException;
import com.android.httplib.download.DownloadObserver;
import com.android.httplib.okhttp.callBack.StringRequestCallback;
import com.android.httplib.retrofit.RetrofitManager;
import com.liujc.network.net.common.IRequestManager;
import com.liujc.network.net.common.RequestFactory;
import com.liujc.network.net.okhttp.OkHttpRequestManager;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.show_msg)
    TextView showMsg;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        showMsg.setText("hello,liujc");
    }

    @Override
    protected boolean useDataBinding() {
        return false;
    }

    @OnClick({R.id.tv_okhttp, R.id.tv_volley, R.id.tv_retrofit, R.id.tv_download})
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
                RetrofitManager.downloadFile("https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/app-debug.apk")
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
