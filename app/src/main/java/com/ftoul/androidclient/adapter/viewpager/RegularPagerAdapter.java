package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ftoul.androidclient.base.BaseFragmentPagerAdapter;
import com.ftoul.androidclient.bean.response.GetAllProductOut;
import com.ftoul.androidclient.fragments.invest.regular.FengJiHuaFragment;

import java.util.List;


/**
 * Created by 蜂投网-chenxiaoli on 2017/5/8.
 *  投资页面---定期子页面
 *
 */

public class RegularPagerAdapter extends FragmentPagerAdapter {
    private List<GetAllProductOut.RegularBidsBean> list;
    public RegularPagerAdapter(FragmentManager fm, List<GetAllProductOut.RegularBidsBean> list) {
       super(fm);
        this.list = list;
    }
    @Override
    public Fragment getItem(int position) {
        return FengJiHuaFragment.newInstance(list.get(position).getId());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}
