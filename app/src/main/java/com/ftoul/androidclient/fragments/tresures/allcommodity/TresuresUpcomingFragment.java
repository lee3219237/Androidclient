package com.ftoul.androidclient.fragments.tresures.allcommodity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseFragment;

/**
 *  一元夺宝--全部商品--即将揭晓
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class TresuresUpcomingFragment extends BaseFragment {
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.treasure_fragment_upcoming,container,false);
        return view;
    }

    @Override
    protected void initData() {

    }
}
