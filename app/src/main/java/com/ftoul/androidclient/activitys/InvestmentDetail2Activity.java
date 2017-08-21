package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.bean.response.GetProductDueInforOut;
import com.ftoul.androidclient.fragments.investdetail.InvestInfoFragment;
import com.ftoul.androidclient.fragments.investdetail.InvestPlanFragment;
import com.ftoul.androidclient.fragments.xdt.XdtListViewFragment;
import com.ftoul.androidclient.ui.PagerSlidingTabStrip;
import com.ftoul.androidclient.ui.ScrollviewViewPager;
import com.ftoul.androidclient.utils.Utilities;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by pengtang on 2017/5/26.
 * 投资详情
 */
public class InvestmentDetail2Activity extends BaseTitleActivity {
    public static final String BID_INFOR = "bidInfor";
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_money)
    TextView txtMoney;
    @BindView(R.id.txt_range)
    TextView txtRange;
    @BindView(R.id.txt_type)
    TextView txtType;
    @BindView(R.id.txt_day)
    TextView txtDay;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.viewpager)
    ScrollviewViewPager viewpager;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;
    @BindView(R.id.scroll)
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_detail2);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
//        GetProductDueInforOut bidInfor = (GetProductDueInforOut.PageBean.ItemsBean) intent.getSerializableExtra(BID_INFOR);
//        txtName.setText(bidInfor.name);
//        txtMoney.setText(Utilities.df.format(bidInfor.investMoney));
//        txtRange.setText(Utilities.df.format(bidInfor.rate)+"%");
//        txtDay.setText(bidInfor.startTime);
//        txtTime.setText(bidInfor.investTime);
        scrollView.smoothScrollTo(0,20);
        headerTitle.setText("投资详情");
        viewpager.setAdapter(new InvestmentDetail2Activity.ViewPagerAdapter(getSupportFragmentManager()));
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        viewpager.setPageMargin(pageMargin);
        tabs.setViewPager(viewpager);
        tabs.setIndicatorColor(mContext.getResources().getColor(R.color.red_fd7d6a));
        tabs.setIndicatorHeight(1);
        tabs.setShouldExpand(true);
        tabs.setTabPaddingLeftRight(100);
        tabs.setUnderlineColor(mContext.getResources().getColor(R.color.gray_ddd));
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources()
                .getDisplayMetrics()));
        tabs.setSelectedTextColor(mContext.getResources().getColor(R.color.red_fd7d6a));
        tabs.setTextColor(mContext.getResources().getColor(R.color.black_666666));
    }
    @OnClick({R.id.txt_go})
    public void onViewClicked(View view) {
        switch (view.getId())
        {
            case R.id.txt_go://合同协议
                Utilities.startNewActivity(InvestmentDetail2Activity.this,ContractProtrolActivity.class);
                break;
        }
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private String[] tabTitle = {
                "回款计划", "借款人信息", "风控保障","投资记录"
        };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return InvestPlanFragment.newInstance(position);
                case 1:
                    return InvestInfoFragment.newInstance(position);
                case 2:
                    return XdtListViewFragment.newInstance(position);
                case 3:
                    return XdtListViewFragment.newInstance(position);
            }
            return XdtListViewFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }
}
