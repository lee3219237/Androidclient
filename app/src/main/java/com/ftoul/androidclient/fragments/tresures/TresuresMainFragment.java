package com.ftoul.androidclient.fragments.tresures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseFragment;

/**
 * 一蜂币夺宝首页
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class TresuresMainFragment extends BaseFragment {
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.treasure_fragment_main,container,false);
        return view;
    }

    @Override
    protected void initData() {

    }
}
