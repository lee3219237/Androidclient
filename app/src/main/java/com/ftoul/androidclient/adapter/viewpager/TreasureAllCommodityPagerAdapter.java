package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ftoul.androidclient.base.BaseFragmentPagerAdapter;
import com.ftoul.androidclient.fragments.tresures.TresuresAllCommodityFragment;
import com.ftoul.androidclient.fragments.tresures.TresuresMainFragment;
import com.ftoul.androidclient.fragments.tresures.allcommodity.TresuresCommodityFragment;
import com.ftoul.androidclient.fragments.tresures.allcommodity.TresuresHistoryFragment;
import com.ftoul.androidclient.fragments.tresures.allcommodity.TresuresUpcomingFragment;


/**
 * Created by 蜂投网-chenxiaoli on 2017/5/8.
 *  投资页面---定期子页面
 *
 */

public class TreasureAllCommodityPagerAdapter extends BaseFragmentPagerAdapter {
    public TreasureAllCommodityPagerAdapter(FragmentManager fm, Context context, String[] titles) {
        super(fm, context, titles);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TresuresCommodityFragment();
                break;
            case 1:
                fragment = new TresuresUpcomingFragment();
                break;
            case 2:
                fragment = new TresuresHistoryFragment();
                break;
        }
        return fragment;
    }
}
