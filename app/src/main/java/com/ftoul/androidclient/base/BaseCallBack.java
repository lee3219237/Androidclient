package com.ftoul.androidclient.base;


import android.text.TextUtils;

import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.MyData;
import com.ftoul.androidclient.utils.AppDES3EncryptAndDecrypt;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by chenxiaoli on 2017/3/16 0016.
 * 解析基类
 */


public abstract class BaseCallBack<T> extends Callback<DataBean<T>> {
    protected TypeToken typeToken;

    public BaseCallBack(TypeToken typeToken) {//运行时泛型会被擦除，需要指定TypeToken
        super();
        this.typeToken = typeToken;
    }

    @Override
    public DataBean<T> parseNetworkResponse(Response response, int id) throws Exception {
        String body = response.body().string();
        Log.e("返回的总数据为："+body);
        if(TextUtils.isEmpty(body)){
            return null;
        }
        String deEncryData = DataUtils.deEncry(body);

        // String result = DataUtils.deEncry(httpBean.data);
        // TypeToken typeToken =new TypeToken<MyData<T>>(){};//运行时泛型会被擦除，需要指定TypeToken
        Type type = typeToken.getType();
        DataBean<T> datas =null;
        try {
             datas = new Gson().fromJson(deEncryData, type);
        } catch (Exception e) {
            datas = new Gson().fromJson(deEncryData,new TypeToken<DataBean<T>>(){}.getType());
        }
        return datas;
    }

}
