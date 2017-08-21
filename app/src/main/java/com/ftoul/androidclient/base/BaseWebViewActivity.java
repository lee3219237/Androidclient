package com.ftoul.androidclient.base;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.webview.HuiFuChromeClient;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 有标题的和左上角退出的activity
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class BaseWebViewActivity extends BaseActivity {
    @BindView(R.id.webView)
    public WebView webView;
    @BindView(R.id.my_framelayout)
    public MyFramelayout myFramelayout;
    @BindView(R.id.headerTitle)
    public TextView headerTitle;
    private boolean tag;
    private HuiFuChromeClient chromeClient;
    private ArrayList<String> closeUrls;
    private ArrayList<String> homeUrls;
    private boolean isUseWebTitle = true;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        RelativeLayout headLeft = (RelativeLayout) findViewById(R.id.rl_headerLeft);
        headLeft.setVisibility(View.VISIBLE);
        ImageView ivHeadLeft = (ImageView) findViewById(R.id.iv_left);
        ivHeadLeft.setColorFilter(getResources().getColor(R.color.white));
    }

    @Override
    protected void initView() {
        webSetting();
    }

    private void webSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);//支持DOM API
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (closeUrls != null) {
                    for (String closeUrl : closeUrls) {
                        if (url.contains(closeUrl)) {
                            finish();
                            return true;
                        }
                    }
                }
                if (homeUrls != null) {
                    for (String homeUrl : homeUrls) {
                        if (url.contains(homeUrl)) {
                            startActivity(new Intent(BaseWebViewActivity.this, MainActivity.class));
                            return true;
                        }
                    }
                }
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                }
                // 处理自定义scheme
                if (!url.contains("http")) {
                    android.util.Log.e(TAG, "url:" + url);
                    try {
                        // 以下固定写法
                        final Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        // 防止没有安装的情况
                        e.printStackTrace();
                    }
                    return true;
                }
                myFramelayout.onLoading();
                Log.e("weburl:" + url);
                view.loadUrl(url);
                tag = false;
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            //    myFramelayout.onError();
             //   tag = true;
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isUseWebTitle == true) {
                    headerTitle.setText(view.getTitle());
                }
                Log.e("weburl:" + url);
            }
        });
        chromeClient = new HuiFuChromeClient(this) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 50 && tag == false) {
                    myFramelayout.onSuccess();
                } else if (newProgress <= 50&&tag==true) {
                    myFramelayout.onLoading();
                }
                super.onProgressChanged(view, newProgress);
            }
        };
        webView.setWebChromeClient(chromeClient);
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                webView.loadUrl(webView.getUrl());
                tag = false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        chromeClient.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            android.util.Log.e("拍照返回无数据", "拍照返回无数据");
        }
        switch (requestCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 1:
                if (resultCode == RESULT_OK) {

                }
                break;
        }
    }

    public ArrayList<String> getCloseUrls() {
        return closeUrls;
    }

    public void setCloseUrls(ArrayList<String> closeUrl) {
        this.closeUrls = closeUrl;
    }

    public ArrayList<String> getHomeUrls() {
        return homeUrls;
    }

    public void setHomeUrls(ArrayList<String> homeUrls) {
        this.homeUrls = homeUrls;
    }

    public void setUseWebTitle(boolean useWebTitle) {
        isUseWebTitle = useWebTitle;
    }
}
