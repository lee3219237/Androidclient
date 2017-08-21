package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ftoul.androidclient.base.BaseFragmentPagerAdapter;
import com.ftoul.androidclient.fragments.tresures.TresuresAllCommodityFragment;
import com.ftoul.androidclient.fragments.tresures.TresuresMainFragment;


/**
 * Created by 蜂投网-chenxiaoli on 2017/5/8.
 *  投资页面---定期子页面
 *
 */

public class TreasurePagerAdapter extends BaseFragmentPagerAdapter {
    public TreasurePagerAdapter(FragmentManager fm, Context context, String[] titles) {
        super(fm, context, titles);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TresuresMainFragment();
                break;
            case 1:
                fragment = new TresuresAllCommodityFragment();
                break;
        }
        return fragment;
    }
}
