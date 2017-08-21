package com.ftoul.androidclient.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.text.TextUtils;

import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.global.ErrCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;


/**
 * Created by ftoul106 on 2017/3/16 0016.
 */

public abstract class SmartBaseCallBack<T> extends BaseCallBack<T> {
    private Dialog pDialog;
    private MyFramelayout myFramelayout;
    //  private RefreshLayout refreshLayout;

    public SmartBaseCallBack(TypeToken typeToken) {
        super(typeToken);
    }

    public SmartBaseCallBack() {
        super(new TypeToken<DataBean<T>>() {
        });
    }

    public SmartBaseCallBack(MyFramelayout myFramelayout) {
        super(new TypeToken<DataBean<T>>() {
        });
        this.myFramelayout = myFramelayout;
    }

    //    public SmartBaseCallBack(MyFramelayout myFramelayout, RefreshLayout refreshLayout) {
//        super(new TypeToken<DataBean<T>>(){});
//        this.myFramelayout = myFramelayout;
//        this.refreshLayout = refreshLayout;
//    }
    public SmartBaseCallBack(Dialog pDialog) {
        super(new TypeToken<DataBean<T>>() {
        });
        this.pDialog = pDialog;
    }

    public SmartBaseCallBack(TypeToken typeToken, MyFramelayout myFramelayout) {
        super(typeToken);
        this.myFramelayout = myFramelayout;
    }

    //    public SmartBaseCallBack(TypeToken typeToken, MyFramelayout myFramelayout,RefreshLayout refreshLayout) {
//        super(typeToken);
//        this.myFramelayout = myFramelayout;
//        this.refreshLayout = refreshLayout;
//    }
    public SmartBaseCallBack(TypeToken typeToken, Dialog pDialog) {
        super(typeToken);
        this.pDialog = pDialog;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
//        if(refreshLayout!=null){
//            refreshLayout.setLoading(false);
//            refreshLayout.setRefreshing(false);
//        }
        MyToast.show("网络错误");
        if (myFramelayout != null) {
            myFramelayout.onError();
        }
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
        }
        Log.e(e.getMessage());
    }

    @Override
    public void onResponse(DataBean<T> response, int id) {
//        if(refreshLayout!=null){
//            refreshLayout.setLoading(false);
//            refreshLayout.setRefreshing(false);
//        }
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
        }
        if (response == null) {
            if (myFramelayout != null) {
                myFramelayout.onError();
            }
            MyToast.show("网络错误");
            return;
        }
        String errCode = response.getErrCode();
        if (response == null || TextUtils.isEmpty(errCode)) {
            MyToast.show("ErrorCode为空");
            return;
        }
        Log.e(errCode);
        if (ErrCode.SUCCESS.equals(errCode)) {
            if (myFramelayout != null) {
                myFramelayout.onSuccess();
            }
            if (response.getBody() != null) {
                Log.e("body数据：" + response.getBody().toString());
            }
            try {
                onSuccess(response.getBody());
            }catch (Exception e){
                MyToast.show("网络数据错误");
            }
        } else {
            if (myFramelayout != null) {
                myFramelayout.onEmpty(response.getErrMsg());
            }
            onOther(response.getErrMsg(),response.getErrCode());

        }
    }

    protected void onOther(String errMsg,String errCode) {
        MyToast.show(errMsg);
    }

    public abstract void onSuccess(T t);
}

