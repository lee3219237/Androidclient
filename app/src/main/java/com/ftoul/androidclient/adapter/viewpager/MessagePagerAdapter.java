package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ftoul.androidclient.base.BaseFragmentPagerAdapter;
import com.ftoul.androidclient.fragments.message.MessageFragment;
import com.ftoul.androidclient.fragments.message.NoticeFragment;


/**
 * Created by ftoul106 on 2017/6/3 0008.
 */

public class MessagePagerAdapter extends BaseFragmentPagerAdapter {


    public MessagePagerAdapter(FragmentManager fm, Context context, String[] titles) {
        super(fm, context, titles);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NoticeFragment();
        } else {
            return new MessageFragment();
        }
    }

}
