package com.ftoul.androidclient.global;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

import com.ftoul.androidclient.activitys.MainActivity;
import com.zhy.http.okhttp.OkHttpUtils;
//import com.squareup.leakcanary.LeakCanary;
//import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ftoul106 on 2017/4/25 0025.
 */

public class MyApp extends Application {
    public static Context appContext;
    public static String machineCode;
    public static long deviationTime;//本地时间与网络时间的差值，单位为毫秒值
    public static int width;
    public static int height;
    public static MainActivity mainActivity;

    public static boolean newUserInvestRefresh = false;//新手福利页是否重新请求刷新数据，false 刷新，true不刷新
    public static boolean regularInvestRefresh = false;//定期投资页是否重新请求刷新数据，false 刷新，true不刷新
    public static boolean mainRefresh = false ; //主页是否重新请求刷新数据，false 刷新，true 不刷新
    public static boolean pesonalRefresh = false;//个人中心是否重新请求刷新数据，false 刷新，true不刷新
    public static boolean huaXinAccountRefresh = true ; //是否查询用户开户绑卡信息，false 刷新，true 不刷新
    public static boolean appUpdate = false ; //app是否升级弹窗 false 弹出，true不弹出
    public static HashMap registerMap;
    public static HashMap getBackPwdMap;
    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
         width = wm.getDefaultDisplay().getWidth();//获取屏幕宽度
        height = wm.getDefaultDisplay().getHeight();

        machineCode = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        /**
         * 极光推送初始化
         */
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        ShareSDK.initSDK(this);
        /**
         * 为Okhttp添加统一头部
         */
//        OkHttpClient.Builder client = OkHttpUtils.getInstance().getOkHttpClient().newBuilder();
//
//        client.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request original = chain.request();
//
//                // Request customization: add request headers
//                Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("user-agent","Android")
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        });
//        OkHttpUtils.getInstance().
        //        if (LeakCanary.isInAnalyzerProcess(this)) {//内存泄露检测工具
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }
}
