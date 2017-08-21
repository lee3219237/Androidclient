package com.ftoul.androidclient.webview;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ftoul106 on 2017/6/15 0015.
 */

public class HuiFuWebViewClient extends WebViewClient {
    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        resend.sendToTarget();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }
        // 处理自定义scheme
        if (!url.contains("http")) {
            try {
                // 以下固定写法
                final Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                view.getContext().startActivity(intent);
            } catch (Exception e) {
                // 防止没有安装的情况
                e.printStackTrace();
            }
            return true;
        }
        view.loadUrl(url);
        Log.e("webViewurlture", url);
        return true;
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        String url  = view.getUrl();
        if(url.contains("ftoul.com")||url.contains("ftoul.cn")){
            handler.proceed();// 接受所有网站的证书
        }else{
            handler.cancel();
        }
//       // final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//        handler.proceed();// 接受所有网站的证书
    }


}
