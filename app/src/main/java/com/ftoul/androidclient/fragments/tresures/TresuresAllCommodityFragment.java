package com.ftoul.androidclient.fragments.tresures;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.RegularPagerAdapter;
import com.ftoul.androidclient.adapter.viewpager.TreasureAllCommodityPagerAdapter;
import com.ftoul.androidclient.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 一蜂币夺宝 -- 全部商品
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class TresuresAllCommodityFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_all_commodity)
    ViewPager vpAllCommodity;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.treasure_fragment_all_commodity, container, false);
        return view;
    }

    @Override
    protected void initData() {
        String[] titles = new String[]{"夺宝商品", "即将揭晓", "往期中奖"};
        TreasureAllCommodityPagerAdapter adapter = new TreasureAllCommodityPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), titles);
        vpAllCommodity.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpAllCommodity);
    }

}
