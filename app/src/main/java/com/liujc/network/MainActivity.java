package com.liujc.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.liujc.network.net.common.IRequestManager;
import com.liujc.network.net.common.RequestFactory;
import com.liujc.network.net.okhttp.callBack.StringRequestCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHttpRequest();
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
            }

            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "onSuccess: " + response.toString());
            }
        });
    }
}
