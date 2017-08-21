package com.ftoul.androidclient.utils;

import android.util.Log;

import com.ftoul.androidclient.bean.HttpBean;
import com.ftoul.androidclient.global.MyUrl;
import com.google.gson.Gson;

/**
 * Created by ftoul106 on 2016/12/10 0010.
 */

public class DataUtils {


    /**
     * 加密
     * @return
     */
    public static String encry(String src) {
        if (MyUrl.tag == false) {
            return src;
        }
        String MD5data = "";
        String key = MD5.md5(src);
        //  Log.e("MD5dataIn", gson.toJson(data));
        try {
            MD5data = AppDES3EncryptAndDecrypt.DES3EncryptMode(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String datas = key + MD5data;
        HttpBean httpBean = new HttpBean();
        httpBean.setData(datas);
        Gson gson = new Gson();
        String result = gson.toJson(httpBean);
        Log.e("MD5dataIn", result);
        return result;
    }

    /**
     * 解密
     *
     * @return
     */
    public static String deEncry(String src) {
        if (MyUrl.tag == false) {
            return src;
        }
        Gson gson =new Gson();
        HttpBean bean = gson.fromJson(src,HttpBean.class);
        String datas = bean.getData();
        String key = datas.substring(32);
        String data = datas.substring(32, datas.length());
        String s = "";
        try {
            s = AppDES3EncryptAndDecrypt.DES3DecryptMode(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("onResponse:deEncry", s);
        return s;
    }
}
