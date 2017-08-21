/**
 * Summary: js脚本所能执行的函数空间
 * Version 1.0
 * Date: 13-11-20
 * Time: 下午4:40
 * Copyright: Copyright (c) 2013
 */

package com.ftoul.androidclient.webview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;


import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuiFuWebViewActivity;
import com.ftoul.androidclient.bean.JpushBean;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.UserInfoInstance;
import com.ftoul.androidclient.ui.ShareDialog;
import com.ftoul.androidclient.utils.Constants;
import com.ftoul.androidclient.utils.Log;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.Set;


//HostJsScope中需要被JS调用的函数，必须定义成public static，且必须包含WebView这个参数
public class HostJsScope {
    private static AlertDialog mDialog;
    private static String TAG = "HostJsScope";
    // 微信登录
    private static IWXAPI WXapi;


    private static final int deviceType = 1;
    private static UserInfoInstance userInfo = UserInfoInstance.getInstance(MyApp.appContext);

    /**
     * 设备型号标识
     *
     * @return
     */


    public static String getDeviceType(WebView webView) {
        return "1";
    }


    /**
     * 短暂气泡提醒
     *
     * @param webView 浏览器
     */
    public static void toast(WebView webView, String url) {
    }


    public static String getjsondata(WebView webView) {
        return null;
    }


    /**
     * 是否支持指纹
     *
     * @return
     */
    public static boolean getSupportPassword(WebView webView) {
        return false;
    }

    /**
     * 是否开启手势密码了
     *
     * @param webView 浏览器
     * @return
     */
    public static boolean getPwdStatusByID(WebView webView, String uid, String phone) {
        Log.e("uid:" + uid + ",phone:" + phone+",webView.getUrl():"+webView.getUrl());

        if (webView.getUrl().contains("https://www.ftoul.com/applogin")||webView.getUrl().contains("https://www.ftoul.com/front/account")) {
            userInfo.setUid(uid);
        }
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
     * @param webView 浏览器
     * @param
     * @return
     */
    public static boolean getPwdStatusByID(WebView webView, String uid) {
        Log.e("uid:" + uid);
        if (TextUtils.isEmpty(userInfo.getLock())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 返回上一页
     */
    public static void back(WebView webView) {
        webView.goBack();
    }

//    /**
//     * 是否开启手势密码了
//     *
//     * @return
//     */
//    public static boolean getPasswordStatus(WebView webView) {
//        MyLog.e(TAG, "getPasswordStatus11 ==");
//        if (TextUtils.isEmpty(MyApp.getInstance().getSpUtils().getString(""))) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    /**
     * 微信登录
     *
     * @param webView
     */
    public static void WXLogin(WebView webView) {
        WXapi = WXAPIFactory.createWXAPI(webView.getContext(), Constants.APP_ID, true);
        WXapi.registerApp(Constants.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_ftou";
        WXapi.sendReq(req);
    }


    /**
     * 分享
     *
     * @param url
     * @param imgUrl
     * @param title
     * @param contect
     */
    public static void shareWithUrl(WebView webView, String url, String imgUrl, String title, String contect) {
        new ShareDialog(webView.getContext(), url, imgUrl, title, contect);
    }

    /**
     * 设置手势密码
     *
     * @param webView
     * @param isOpen
     * @return
     */
    public static boolean setPwd(WebView webView, boolean isOpen, String phone, String uid, String token) {
        Log.e("uid:" + uid + ",phone:" + phone + ",isOpen:" + isOpen + ",token:" + token);
        UserInfoInstance userInfo = UserInfoInstance.getInstance(webView.getContext());
        userInfo.setUid(uid);
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
     * @param webView
     * @param isOpen
     * @return
     */
    public static boolean setPassword(WebView webView, boolean isOpen, String phone) {

        if (!TextUtils.isEmpty(phone)) {
            userInfo.setUserName(phone);
        }

        Message ms = new Message();
        if (isOpen) {
            ms.what = 1;
            ms.obj = 1;
            getHandler(webView).sendMessage(ms);
        } else {
            ms.what = 2;
            ms.obj = 2;
            getHandler(webView).sendMessage(ms);
        }
        return true;
    }

    /**
     * 手势登录
     *
     * @param webView
     */
    public static void loginPassword(WebView webView) {
//        Message ms = new Message();
//        ms.what = 3;
//        ms.obj = "3";
//        getHandler(webView).sendMessage(ms);
    }

    /**
     * 清除缓存
     *
     * @param webView
     */
    public static void clearCache(WebView webView) {
        Message ms = new Message();
        ms.what = 4;
        ms.obj = "4";
        getHandler(webView).sendMessage(ms);
    }


    /**
     * 获取联系人信息
     *
     * @param webView
     */
    public static void getContacts(WebView webView) {
        Message ms = new Message();
        ms.what = 5;
        ms.obj = "5";
        getHandler(webView).sendMessage(ms);
    }

    /**
     * 设置别名标签
     *
     * @param webView
     */
    public static void setAliens(WebView webView, String alias, Set<String> tags) {
        Message ms = new Message();
        ms.what = 6;
        JpushBean jpushBean = new JpushBean(alias, tags);
        ms.obj = jpushBean;
        getHandler(webView).sendMessage(ms);
    }

    public static void setDataInfo(WebView webView, String url, String param, final JsCallback jsCallback) {

//        httpPost(TAG,url,new HashMap<String, String>(),getHandler(webView),jsCallback);
    }

    public static int getPhoneType(WebView webView) {
        return 1;
    }

    public static void jumpUrl(WebView webView, int id, String title) {

    }

    public static void alert(WebView webView, int id, String content) {
        Intent intent = new Intent(webView.getContext(), HuiFuWebViewActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("content", content);
        webView.getContext().startActivity(intent);
    }

    public static String getDetail(WebView webView) {
        String content = "";
//        content=((WebViewActivity)webView.getContext()).getContent();
        return content;
    }

    public static void jumpPage(WebView webView, int id, String param, String url, String detail) {

    }

    public static void jalert(WebView webView, String str) {

    }

    public static void getDate(WebView webView, String string) {
        Message ms = new Message();
        ms.what = 1;
        ms.obj = string;
        getHandler(webView).sendMessage(ms);
    }

    private static Handler getHandler(WebView view) {
        return ((HuiFuWebViewActivity) view.getContext()).getHander();
    }

    /**
     * JSON字符串特殊字符处理，比如：“\A1;1300”
     *
     * @param s
     * @return String
     */
    public static String string2Json(String s) {
        if (s == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\'':
                    sb.append("\\'");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
//             case '\b':
//                 sb.append("\\b");
//                 break;
//             case '\f':
//                 sb.append("\\f");
//                 break;
//             case '':
//                 sb.append("\");
//                 break;
//             case '\r':
//                 sb.append("\\r");
//                 break;
//             case '\t':
//                 sb.append("\\t");
//                 break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 临时打开关闭手势
     * <p>
     * 退出登录时，手势密码出错时，使用账号密码登录时，登录成功时都会调用此方法
     */
    public static void setFingerTempStatus(WebView webView, boolean status, String uid) {
        Log.e("status:" + status + ",uid:" + uid);
        userInfo.sp.edit().putBoolean("FingerTempStatus", status)
                .putString("TempUserId", uid).commit();
    }

    /**
     * 获取版本号
     *
     * @param
     * @return
     */
    public static String getVersionName(WebView webView) {
        int versionCode = 0;
        // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
        try {
            versionCode = webView.getContext().getPackageManager().getPackageInfo(
                    webView.getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //versionName = MyApp.getInstance().getPackageManager().getPackageInfo(pkg, 1).versionName;
        Log.e("versionCode:" + versionCode);
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