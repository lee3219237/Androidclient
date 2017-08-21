package com.ftoul.androidclient.adapter.viewpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ftoul.androidclient.fragments.MainFragmentFactory;

/**
 * Created by admin on 2016/9/21 0021.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragmentFactory.create(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position == 3) {
            super.destroyItem(container,position,object);
        }
    }
}
