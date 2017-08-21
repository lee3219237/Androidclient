package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.RegularPagerAdapter;
import com.ftoul.androidclient.adapter.viewpager.TreasurePagerAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 一蜂币夺宝页
 */
public class CaptureTreasureActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_treasure);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("一蜂币夺宝");
    }

    @Override
    protected void initData() {
        String[] titles = new String[]{"首页", "全部商品"};
        TreasurePagerAdapter adapter = new TreasurePagerAdapter(getSupportFragmentManager(),this, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
