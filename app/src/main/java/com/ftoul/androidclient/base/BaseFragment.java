package com.ftoul.androidclient.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.global.UserInfoInstance;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxiaoli on 2017/4/25 0025.
 */

public abstract class BaseFragment extends Fragment {
    protected UserInfoInstance userInfo;
    protected Activity parent;
    protected SharedPreferences sp;
    protected ProgressDialog pDialog;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = getActivity();
        userInfo = UserInfoInstance.getInstance(parent);
        sp = parent.getSharedPreferences("config", parent.MODE_PRIVATE);
        View view = initView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }


    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initData();

    protected void showDialog(String message) {
        if (null == pDialog) {
            pDialog = new ProgressDialog(parent);
        }
        pDialog.setMessage(message);//设置对话框中的提示信息
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroyView();
        unbinder.unbind();
    }
}
