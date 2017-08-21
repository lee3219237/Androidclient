package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ftoul.androidclient.fragments.risktest.RiskTestFragment;

/**
 * Created by ftoul106 on 2016/12/12 0012.
 */

public class RiskTestAdapter extends FragmentPagerAdapter {
    private int length;
    private Context context;

    public RiskTestAdapter(FragmentManager fm, int length) {
        super(fm);
        this.length = length;
    }


    @Override
    public int getCount() {
        return length;
    }

    @Override
    public Fragment getItem(int position) {
        return RiskTestFragment.newInstance(position);
    }

}
