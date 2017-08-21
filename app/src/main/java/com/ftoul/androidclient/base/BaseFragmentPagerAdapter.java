package com.ftoul.androidclient.base;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ftoul106 on 2017/5/8 0008.
 */

public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;

    private Context context;

    public BaseFragmentPagerAdapter(FragmentManager fm, Context context, String[] titles) {
        super(fm);
        this.context = context;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
