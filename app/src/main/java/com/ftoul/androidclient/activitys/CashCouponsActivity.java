package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.fragments.cashcoupons.NotUseCashCouponsFragment;
import com.ftoul.androidclient.ui.PagerSlidingTabStrip;

import butterknife.BindView;

/**
 * Created by pengtang on 2017/6/2.
 * 现金券
 */
public class CashCouponsActivity extends BaseTitleActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_coupons);
    }

    @Override
    protected void initView() {
        headerTitle.setText("现金券");
        rlHeaderLeft.setVisibility(View.VISIBLE);

        viewpager.setAdapter(new CashCouponsActivity.ViewPagerAdapter(getSupportFragmentManager()));
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        viewpager.setPageMargin(pageMargin);
        tabs.setViewPager(viewpager);
        tabs.setIndicatorColor(mContext.getResources().getColor(R.color.red_fd7d6a));
        tabs.setIndicatorHeight(1);
        tabs.setShouldExpand(true);
        tabs.setTabPaddingLeftRight(100);
        tabs.setUnderlineColor(mContext.getResources().getColor(R.color.white));
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources()
                .getDisplayMetrics()));
        tabs.setSelectedTextColor(mContext.getResources().getColor(R.color.red_fd7d6a));
        tabs.setTextColor(mContext.getResources().getColor(R.color.black_666666));
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private String[] tabTitle = {
                "未使用", "已使用", "已过期"
        };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            switch (position)
//            {
//                case 0:
//                    return NotUseCashCouponsFragment.newInstance(position);
//                case 1:
//                    return UseCashCouponsFragment.newInstance(position);
//                case 2:
//                    return DelayCashCouponsFragment.newInstance(position);
//            }
            return NotUseCashCouponsFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }
}
