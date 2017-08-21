package com.ftoul.androidclient.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuiFuWebViewActivity;
import com.ftoul.androidclient.bean.JpushBean;
import com.ftoul.androidclient.global.UserInfoInstance;
import com.ftoul.androidclient.utils.Constants;
import com.ftoul.androidclient.utils.Log;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.Set;

/**
 * Created by ftoul106 on 2017/6/15 0015.
 */

public class HuiFuJsObject {
    private Activity activity;
    private WebView webView;
    private UserInfoInstance userInfo;

    private static AlertDialog mDialog;
    private static String TAG = "HostJsScope";
    // 微信登录
    private static IWXAPI WXapi;
    private static final int deviceType = 1;

    public HuiFuJsObject(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
        userInfo = UserInfoInstance.getInstance(activity);
    }
    /**
     * 设备型号标识
     *
     * @return
     */

    @JavascriptInterface
    public String getDeviceType(WebView webView) {
        Log.e("return 1");
        return "1";
    }


    /**
     * 短暂气泡提醒
     */
    @JavascriptInterface
    public void toast(String url) {
    }

    @JavascriptInterface
    public String getjsondata() {
        Log.e("return null");
        return null;
    }


    /**
     * 是否支持指纹
     *
     * @return
     */
    @JavascriptInterface
    public boolean getSupportPassword() {
        Log.e("return false");
        return false;
    }

    /**
     * 是否开启手势密码了
     *
     * @param uid 用户id
     * @return
     */
    @JavascriptInterface
    public boolean getPwdStatusByID(String uid, String phone) {
        Log.e("uid:"+uid+",phone:"+phone);
        userInfo.setUid(uid);
        if (TextUtils.isEmpty(userInfo.getLock())) {
            if (!TextUtils.isEmpty(uid)
                    && !uid.equals("undefined")
                    && webView.getUrl().contains("front/account/accountInformationNew")
                    && !userInfo.sp.getBoolean(uid + "firstLogin", false)
                    ) {
                if (!TextUtils.isEmpty(phone)) {
                    userInfo.setUserName(phone);
                }
                userInfo.sp.edit().putBoolean(uid + "firstLogin", true).commit();
                aleartClearCacheDialog(webView.getContext(), uid, webView);
            }

            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否开启手势密码了
     *
     * @param
     * @return
     */
    @JavascriptInterface
    public boolean getPwdStatusByID(String uid) {
        Log.e("uid:"+uid);
        if (TextUtils.isEmpty(userInfo.getLock())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 返回上一页
     */
    @JavascriptInterface
    public void back() {
        webView.goBack();
    }

    /**
     * 是否开启手势密码了
     *
     * @return
     */
    @JavascriptInterface
    public boolean getPasswordStatus() {
        if (TextUtils.isEmpty(userInfo.getLock())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 微信登录
     */
    @JavascriptInterface
    public void WXLogin() {
        WXapi = WXAPIFactory.createWXAPI(webView.getContext(), Constants.APP_ID, true);
        WXapi.registerApp(Constants.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_ftou";
        WXapi.sendReq(req);
    }


//    /**
//     * 分享
//     *
//     * @param url
//     * @param imgUrl
//     * @param title
//     * @param contect
//     */
//    public static void shareWithUrl(WebView webView, String url, String imgUrl, String title, String contect) {
//        new ShareDialog(webView.getContext(), url, imgUrl, title, contect);
//    }

    /**
     * 设置手势密码
     *
     * @param isOpen
     * @return
     */
    @JavascriptInterface
    public boolean setPwd(boolean isOpen, String phone, String uid, String token) {
        Log.e("uid:"+uid+",phone:"+phone+",isOpen:"+isOpen+",token:"+token);
        if (!TextUtils.isEmpty(phone)) {
            userInfo.setUserName(phone);
        }
        if ((!TextUtils.isEmpty(uid + "")) && !TextUtils.isEmpty(token)) {
            userInfo.sp.edit().putString(uid + "token", token).commit();
        }

        Message ms = new Message();
        if (isOpen) {
            ms.what = 1;
            ms.obj = uid;
            getHandler(webView).sendMessage(ms);
        } else {
            ms.what = 2;
            ms.obj = uid;
            getHandler(webView).sendMessage(ms);
        }
        return true;
    }

    /**
     * 设置手势密码
     *
     * @param isOpen
     * @return
     */
    @JavascriptInterface
    public boolean setPassword(boolean isOpen, String phone, String uid, String token) {
        Log.e("uid:"+uid+",phone:"+phone+",isOpen:"+isOpen+",token:"+token);
        if (!TextUtils.isEmpty(phone)) {
            userInfo.setUserName(phone);
        }
        if ((!TextUtils.isEmpty(uid + "")) && !TextUtils.isEmpty(token)) {
            userInfo.sp.edit().putString(uid + "token", token).commit();
        }

        Message ms = new Message();
        if (isOpen) {
            ms.what = 1;
            ms.obj = uid;
            getHandler(webView).sendMessage(ms);
        } else {
            ms.what = 2;
            ms.obj = uid;
            getHandler(webView).sendMessage(ms);
        }
        return true;
    }


    /**
     * 清除缓存
     */
    @JavascriptInterface
    public void clearCache() {
        Message ms = new Message();
        ms.what = 4;
        ms.obj = "4";
        getHandler(webView).sendMessage(ms);
        Log.e("clearCache");
    }


    /**
     * 获取联系人信息
     */
    @JavascriptInterface
    public void getContacts() {
        Log.e("getContacts");
        Message ms = new Message();
        ms.what = 5;
        ms.obj = "5";
        getHandler(webView).sendMessage(ms);
    }

    /**
     * 设置别名标签
     */
    @JavascriptInterface
    public void setAliens(String alias, Set<String> tags) {
        Message ms = new Message();
        ms.what = 6;
        JpushBean jpushBean = new JpushBean(alias, tags);
        ms.obj = jpushBean;
        getHandler(webView).sendMessage(ms);
    }

//    public static void setDataInfo(WebView webView, String url, String param, final JsCallback jsCallback) {
//
//        MyLog.e(TAG, "setDataInfo");
////        httpPost(TAG,url,new HashMap<String, String>(),getHandler(webView),jsCallback);
//    }

    @JavascriptInterface
    public int getPhoneType() {
        Log.e("return 1");
        return 1;
    }

    @JavascriptInterface
    public void jumpUrl(WebView webView, int id, String title) {

    }

    @JavascriptInterface
    public void alert(int id, String content) {
        Intent intent = new Intent(webView.getContext(), HuiFuWebViewActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("content", content);
        webView.getContext().startActivity(intent);
    }

    @JavascriptInterface
    public String getDetail() {
        String content = "";
//        content=((WebViewActivity)webView.getContext()).getContent();
        return content;
    }

    //    public static void jumpPage(WebView webView, int id, String param, String url, String detail) {
//        MyLog.e(TAG, "jumpPage ==" + param);
//    }
//
//    public static void jalert(WebView webView, String str) {
//        MyLog.e(TAG, "str ==" + str);
//    }
    @JavascriptInterface
    public void getDate(String string) {
        Message ms = new Message();
        ms.what = 1;
        ms.obj = string;
        getHandler(webView).sendMessage(ms);
    }

    private Handler getHandler(WebView view) {
        HuiFuWebViewActivity hui = (HuiFuWebViewActivity) activity;
        return hui.getHander();
    }


    /**
     * 临时打开关闭手势
     * <p>
     * 退出登录时，手势密码出错时，使用账号密码登录时，登录成功时都会调用此方法
     */
    @JavascriptInterface
    public void setFingerTempStatus(boolean status, String uid) {
        Log.e("status:"+status+",uid:"+uid);
        userInfo.sp.edit().putBoolean("FingerTempStatus", status)
                .putString("TempUserId", uid).commit();
    }

    /**
     * 获取版本号
     *
     * @param
     * @return
     */
    @JavascriptInterface
    public String getVersionName() {
        int versionCode = 0;
        // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
        try {
            versionCode = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //versionName = MyApp.getInstance().getPackageManager().getPackageInfo(pkg, 1).versionName;
        Log.e("versionCode:"+versionCode);
        return versionCode + "";
    }

    /**
     * 清除缓存Dialog
     */

    private static void aleartClearCacheDialog(final Context context, final String uid, final WebView webView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = View.inflate(context, R.layout.dialog_setting, null);
        View view = inflate.findViewById(R.id.tv_close);
        View okView = inflate.findViewById(R.id.tv_ok);
        TextView title = (TextView) inflate.findViewById(R.id.tv_title);
        TextView content = (TextView) inflate.findViewById(R.id.tv_content);
        title.setText("温馨提示");
        content.setText("系统检测到您可以设置手势密码，是否去设置。\n也可以在我的-设置-手势密码设置");
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(HuiFuWebViewActivity.HTTPS_WWW_FTOUL_COM + "/front/account/settings");
                mDialog.cancel();
            }
        });
        builder.setView(inflate);
        mDialog = builder.show();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        mDialog.setCanceledOnTouchOutside(false);
    }
}
