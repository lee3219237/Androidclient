package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ftoul.androidclient.fragments.fengbox.PaymentCountFragment;

/**
 * Created by 蜂投网-HzJ on 2017/6/3.
 */

public class PaymentCouponAdapter extends FragmentPagerAdapter {
    public final int COUNT = 3;
    private String[] titles = new String[]{"未领取", "已领取", "已过期"};
    private Context context;

    public PaymentCouponAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PaymentCountFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
