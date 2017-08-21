package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.PaymentCouponAdapter;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 蜂投网-HzJ on 2017/6/3.
 */

public class PaymentCouponActivity extends BaseTitleActivity {
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.payment_tablayout)
    TabLayout paymentTablayout;
    @BindView(R.id.payment_viewpage)
    ViewPager paymentViewpage;

    private PaymentCouponAdapter paymentCouponAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentcoupon);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("回款赠劵");
        paymentCouponAdapter = new PaymentCouponAdapter(getSupportFragmentManager(), this);
        paymentViewpage.setAdapter(paymentCouponAdapter);
        paymentTablayout.setupWithViewPager(paymentViewpage);
    }

}
