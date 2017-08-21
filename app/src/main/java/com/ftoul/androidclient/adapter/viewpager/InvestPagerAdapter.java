package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.base.BaseFragmentPagerAdapter;
import com.ftoul.androidclient.fragments.invest.InvestFragmentFactory;


/**
 * Created by 蜂投网-chenxiaoli on 2017/5/8.
 *  投资页面
 */

public class InvestPagerAdapter extends BaseFragmentPagerAdapter {
    public InvestPagerAdapter(FragmentManager fm, Context context, String[] titles) {
        super(fm,context,titles);

    }

    @Override
    public Fragment getItem(int position) {
        return InvestFragmentFactory.create(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
