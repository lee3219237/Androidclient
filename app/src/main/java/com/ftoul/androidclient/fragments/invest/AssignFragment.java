package com.ftoul.androidclient.fragments.invest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseFragment;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 * 投资 --- 转让子页面
 */

public class AssignFragment extends BaseFragment {
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invest_fragment_assign,container,false);
        return view;
    }

    @Override
    protected void initData() {

    }
}
