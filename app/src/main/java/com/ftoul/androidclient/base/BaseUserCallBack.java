package com.ftoul.androidclient.base;


import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;

import com.ftoul.androidclient.activitys.LoginActivity;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.response.LoginCodeOut;
import com.ftoul.androidclient.bean.response.LoginErrorCodeOut;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by chenxiaoli on 2017/3/16 0016.
 * 解析基类
 */


public abstract class BaseUserCallBack extends StringCallback {
    private Dialog pDialog;

    public BaseUserCallBack(Dialog pDialog) {
        super();
        this.pDialog = pDialog;
    }

    public BaseUserCallBack() {
        super();
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        String msg = e.getMessage();
        Log.e(msg);
        if (!("400".equals(msg) || "401".equals(msg))) {
            MyToast.show("网络错误");
        }
    }

    @Override
    public boolean validateReponse(Response response, int id) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
        }
        if (response != null) {
            int code = response.code();
            if (code == 400 || code == 401) {
                try {
                    String body = response.body().string();
                    Gson gson = new Gson();
                    LoginErrorCodeOut errorCodeOut = gson.fromJson(body, LoginErrorCodeOut.class);
                    //  MyToast.show("用户不存在或密码错误");
                    MyToast.show(errorCodeOut.errMsg);
                } catch (IOException e) {
                    return super.validateReponse(response, id);
                }
            }
        }
        return super.validateReponse(response, id);

    }
}
