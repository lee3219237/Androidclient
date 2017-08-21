package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ftoul.androidclient.base.BaseFragmentPagerAdapter;
import com.ftoul.androidclient.fragments.billcalenday.InvestHistoryFragment;
import com.ftoul.androidclient.fragments.billcalenday.ReturnedMoneyFragment;

import java.util.List;

/**
 * Created by ftoul106 on 2017/5/31 0031.
 */

public class BillCalendayAdapter extends BaseFragmentPagerAdapter {
    private List list;

    public BillCalendayAdapter(FragmentManager fm, Context context, String[] titles,List list) {
        super(fm, context, titles);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new ReturnedMoneyFragment();
        }else{
            return new InvestHistoryFragment();
        }

    }
}
