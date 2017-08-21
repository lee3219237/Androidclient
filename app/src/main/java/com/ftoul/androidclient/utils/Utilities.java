package com.ftoul.androidclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by tp on 2017/5/20.
 * 公共工具类
 */

public class Utilities {
    public static String toJsonString(Object dataBean){
        Gson gson = new Gson();
        String datas = gson.toJson(dataBean);
        Log.e(datas);
        String encryptData = DataUtils.encry(datas);
        return encryptData;
    }

    public static String toJsonString2(Object dataBean){
        Gson gson = new Gson();
        String datas = gson.toJson(dataBean);
        Log.e(datas);
       String encryptData = DataUtils.encry(datas);
        return encryptData;
    }

   public static DecimalFormat df = new DecimalFormat("0.00");
    /**
     * 指定特殊符号以后字体颜色
     * @param str
     * @param specialChar
     * @param Color
     * @return
     */
    public static SpannableStringBuilder getColorfulWord(String str, String specialChar, int Color)
    {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        ForegroundColorSpan span = new ForegroundColorSpan(Color);
        builder.setSpan(span, str.indexOf(specialChar), str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }


    /**
     * 获取特殊字符分割的文本用于分段显示不同字体大小文本
     *
     * @param str
     * @param specialChar
     * @return
     */
    public static Spannable getDividerStr(String str, String specialChar, int firstSize, int secondSize) {
        Spannable sp = new SpannableString(str);
        int dotIndex = str.indexOf(specialChar);
        sp.setSpan(new AbsoluteSizeSpan(firstSize, true), 0, dotIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(secondSize, true), dotIndex, str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return sp;
    }


    /**
     * 开启新的activity
     *
     * @param context 上下文
     * @param cls     activity类
     */
    public static void startNewActivity(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    public static void startNewActivity(Context context, Class<?> cls,
                                        int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 打开新的activity，并带有String数据
     *
     * @param context
     * @param cls     类
     * @param keyName 获取string的key值
     * @param value   string值
     */
    public static void startNewActivity(Context context, Class<?> cls,
                                        String keyName, String value) {
        Intent intent = new Intent();
        intent.putExtra(keyName, value);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 打开新的activity，并带有int数据
     *
     * @param context
     * @param cls     类
     * @param keyName 获取string的key值
     * @param value   int值
     */
    public static void startNewActivity(Context context, Class<?> cls,
                                        String keyName, int value) {
        Intent intent = new Intent();
        intent.putExtra(keyName, value);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param cls
     * @param keyName1
     * @param value1
     * @param keyName
     * @param value
     */
    public static void startNewActivity(Context context, Class<?> cls,
                                        String keyName1, String value1, String keyName, int value) {
        Intent intent = new Intent();
        intent.putExtra(keyName, value);
        intent.putExtra(keyName1, value1);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 打开新的activity，并带有Serializable对象数据
     *
     * @param context
     * @param cls
     * @param keyName
     * @param value
     */
    public static void startNewActivity(Context context, Class<?> cls,
                                        String keyName, Serializable value) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(keyName, value);
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 打开新的activity，并带有Serializable对象数据
     *
     * @param context
     * @param cls
     * @param keyName
     * @param value
     * @param keyName1
     * @param value1
     */
    public static void startNewActivity(Context context, Class<?> cls,
                                        String keyName, Serializable value, String keyName1, String value1) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(keyName, value);
        bundle.putString(keyName1, value1);
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 打开新的activity，并带有Serializable对象数据
     *
     * @param context
     * @param cls
     * @param keyName
     * @param value
     * @param keyName1
     * @param value1
     */
    public static void startNewActivity(Context context, Class<?> cls,
                                        String keyName, Serializable value, String keyName1, int value1) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(keyName, value);
        bundle.putInt(keyName1, value1);
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    /**
     * 调用系统InstalledAppDetails界面显示已安装应用程序的详细信息。 对于Android 2.3（Api Level
     * 9）以上，使用SDK提供的接口； 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）。
     *
     * @param context
     */
    public static void showInstalledAppDetails(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", "com.ftoul.androidclient", null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void call(Context context,String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取当前设备状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
