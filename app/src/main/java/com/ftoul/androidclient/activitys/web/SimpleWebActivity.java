package com.ftoul.androidclient.activitys.web;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseWebViewActivity;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import butterknife.OnClick;

/**
 *
 */
public class SimpleWebActivity extends BaseWebViewActivity {
    public static final String WEB_URL = "webUrl";
    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web);
    }

    @Override
    protected void initView() {
        super.initView();
        webUrl = getIntent().getStringExtra(WEB_URL);
        if (!TextUtils.isEmpty(webUrl)) {
            webView.loadUrl(webUrl);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }


    @OnClick({R.id.rl_headerLeft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_headerLeft:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                break;
        }
    }
}
