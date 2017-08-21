package com.ftoul.androidclient.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.webview.HuiFuChromeClient;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 * H5
 */

public class DiscoveryFragment extends BaseFragment {

    HashMap<String, String> map = new HashMap<>();
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    @BindView(R.id.headerTitle)
    TextView headerTitle;

    private MainActivity activity;
    private boolean tag;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_discovery, container, false);
        return view;
    }

    @Override
    protected void initData() {
        headerTitle.setText("发现");
        activity = (MainActivity) getActivity();
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);//支持DOM API
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webView.requestFocusFromTouch();
        webView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                myFramelayout.onLoading();
                Log.e("weburl:" + url);
                view.loadUrl(url);
                tag = false;
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                myFramelayout.onError();
                tag = true;
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("weburl:" + url);
            }
        });
        HuiFuChromeClient chromeClient = new HuiFuChromeClient(getActivity()) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 50 && tag == false) {
                    myFramelayout.onSuccess();
                } else if (newProgress <= 50) {
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
       String urlParameter = initUrlParamter();
        webView.loadUrl(MyUrl.WEB_VIEW_DISCOVERY+"?"+urlParameter);
      //  webView.postUrl(MyUrl.WEB_VIEW_DISCOVERY, urlParameter.getBytes());

    }

    /**
     * 初始化urlParamter的值
     */
    private String initUrlParamter() {
        String urlParameter = "";
       String userid = userInfo.getUid();
        if (!TextUtils.isEmpty(userid)) {
            map.put("userId", userid);
        }
        map.put("appToken", userInfo.getToken());
        for (String key : map.keySet()) {
            urlParameter = urlParameter + "&" + key + "=" + map.get(key);
        }
        urlParameter = urlParameter.substring(1, urlParameter.length());
        android.util.Log.e("urlParameter:", urlParameter);
        return urlParameter;
    }
}
