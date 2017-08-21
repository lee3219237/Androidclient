package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetDueInMoneyIn;
import com.ftoul.androidclient.bean.response.GetDueInMoneyOut;
import com.ftoul.androidclient.fragments.xdt.XdtListViewFragment;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.PagerSlidingTabStrip;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pengtang on 2017/5/25.
 * 小贷通
 */
public class XdtDetailActivity extends BaseTitleActivity {
    public static final String PRODUCT_ID = "productId";
    public static final String PRODUCT_NAME = "productName";
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.txt_total_original)
    TextView txtTotalOriginal;
    @BindView(R.id.txt_total_income)
    TextView txtTotalIncome;
    @BindView(R.id.txt_all_total_income)
    TextView txtAllTotalIncome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xdt_detail);
    }

    @Override
    protected void initView() {
        getData();
        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void getData() {
        String productName = getIntent().getStringExtra(PRODUCT_NAME);
        headerTitle.setText(productName);
        String productId = getIntent().getStringExtra(PRODUCT_ID);
        DataBean<GetDueInMoneyIn> dataBean = new DataBean<>(TransCode.GET_PRODUCT_DUE_IN_MONEY);
        dataBean.setBody(new GetDueInMoneyIn(productId));
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<GetDueInMoneyOut>(new TypeToken<DataBean<GetDueInMoneyOut>>() {
                }) {
                    @Override
                    public void onSuccess(GetDueInMoneyOut body) {
                        txtTotalOriginal.setText(body.getTotal());// 总待收本金
                        txtTotalIncome.setText(body.getTotalInterest());//   总应计收益
                        txtAllTotalIncome.setText(body.getCumulateInterest());//  累计到账收益
                    }
                });

    }

    @OnClick(R.id.rl_headerLeft)
    public void onViewClicked() {
        XdtDetailActivity.this.finish();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private String[] tabTitle = {
                "投资中", "回款中", "已回款"
        };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return XdtListViewFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
}
