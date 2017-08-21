package com.ftoul.androidclient.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.InvestPagerAdapter;
import com.ftoul.androidclient.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 * 投资页
 */

public class InvestFragment extends BaseFragment {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_invest)
    ViewPager vpInvest;
    @BindView(R.id.iv_customer_service)
    ImageView ivCustomerService;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_invest, container, false);
        return view;
    }

    @Override
    protected void initData() {
        //, "转让"
        String[] titles = new String[]{"新手", "定期"};
        ivCustomerService.setColorFilter(getResources().getColor(R.color.white));
        InvestPagerAdapter adapter = new InvestPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), titles);
        vpInvest.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpInvest);
    }

    @OnClick(R.id.iv_customer_service)
    public void onClick() {

    }

    /**
     * 跳转投资页面并且切换到定期页面
     */
    public void setRegular(int position) {
        vpInvest.setCurrentItem(position);
    }

}
