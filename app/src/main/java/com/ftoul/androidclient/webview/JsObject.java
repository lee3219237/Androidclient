package com.ftoul.androidclient.webview;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.ftoul.androidclient.activitys.ChangeVersionActivity;

/**
 * Created by ftoul106 on 2017/2/18 0018.
 */

public class JsObject {
    private static final String TAG = "JsObject";
    private WebView JsObjectwebView;
    private Activity activity;

    public JsObject(WebView webView, Activity activity) {
        JsObjectwebView = webView;
        this.activity = activity;
    }

    @JavascriptInterface
    public void back() {
        JsObjectwebView.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "goBack:");
                JsObjectwebView.goBack();

            }
        });
    }

    @JavascriptInterface
    public void forward() {
        Log.i(TAG, "forward:");
        JsObjectwebView.post(new Runnable() {
            @Override
            public void run() {
                JsObjectwebView.goForward();
            }
        });
    }

    @JavascriptInterface
    public void go(final String i) {
        JsObjectwebView.post(new Runnable() {
            @Override
            public void run() {
                JsObjectwebView.goBackOrForward(Integer.parseInt(i));
            }
        });
    }

    @JavascriptInterface
    public void finish() {
        activity.finish();
    }

    @JavascriptInterface
    public void changeVersion() {
        activity.startActivity(new Intent(activity, ChangeVersionActivity.class));
    }
}
