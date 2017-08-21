package com.ftoul.androidclient.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.CaptureTreasureActivity;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.adapter.recycle.PanicAdapter;
import com.ftoul.androidclient.adapter.recycle.TreasureHorizontalAdapter;
import com.ftoul.androidclient.adapter.viewpager.BannerAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.bean.BannerBean;
import com.ftoul.androidclient.bean.response.PanicBean;
import com.ftoul.androidclient.bean.response.TreasureBean;
import com.ftoul.androidclient.ui.SmartScrollView;
import com.ftoul.androidclient.ui.ViewPagerPoint;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 * H5
 */

public class SecondDiscoveryFragment extends BaseFragment {
//    @BindView(R.id.headerTitle)
//    TextView headerTitle;
//    @BindView(R.id.tv_bid_rate)
//    TextView tvBidRate;
//    @BindView(R.id.tv_bid_time)
//    TextView tvBidTime;
//    @BindView(R.id.vp_banner)
//    ViewPager vpBanner;
//    @BindView(R.id.vp_point)
//    ViewPagerPoint vpPoint;
//    @BindView(R.id.recyclerview_panic)
//    RecyclerView recyclerviewPanic;
//    @BindView(R.id.recyclerview_treasures)
//    RecyclerView recyclerviewTreasures;
//    @BindView(R.id.scrollView)
//    SmartScrollView scrollView;
//
//    private MainActivity activity;
//
//
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_discovery, container, false);
        return view;
    }

    @Override
    protected void initData() {

    }
//
//    @Override
//    protected void initData() {
//        activity = (MainActivity) getActivity();
//        scrollView.addSmartScrollChangedListener(new SmartScrollView.SmartScrollChangedListener() {
//            @Override
//            public void onScrolledToBottom() {
//                activity.bottomBarVisible(true);
//            }
//
//            @Override
//            public void onScrolledToTop() {
//                activity.bottomBarVisible(false);
//            }
//        });
//        headerTitle.setText("发现");
//
//        recyclerviewPanic.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        List<PanicBean> panicBeanList = new ArrayList<>();
//        panicBeanList.add(new PanicBean("抢理财基金", "2017-06-14 8:00:00"));
//        panicBeanList.add(new PanicBean("抢加息劵", "2017-06-14 12:00:00"));
//        panicBeanList.add(new PanicBean("抢红包", "2017-06-14 18:00:00"));
//        panicBeanList.add(new PanicBean("抢蜂币", "2017-06-14 20:00:00"));
//        PanicAdapter adapter = new PanicAdapter(getActivity(), panicBeanList);
//        recyclerviewPanic.setAdapter(adapter);
//
//        recyclerviewTreasures.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        List<TreasureBean> treasureBeanList = new ArrayList<>();
//        treasureBeanList.add(new TreasureBean("http://192.168.imgurl", "电饭煲", 60));
//        treasureBeanList.add(new TreasureBean("http://192.168.imgurl", "充电宝", 10));
//        treasureBeanList.add(new TreasureBean("http://192.168.imgurl", "手机", 90));
//        treasureBeanList.add(new TreasureBean("http://192.168.imgurl", "电视机", 70));
//        treasureBeanList.add(new TreasureBean("http://192.168.imgurl", "汽车", 80));
//        TreasureHorizontalAdapter adapter2 = new TreasureHorizontalAdapter(getActivity(), treasureBeanList);
//        recyclerviewTreasures.setAdapter(adapter2);
//        List<BannerBean> bannerBeanList = new ArrayList<>();
//        BannerAdapter adapter3 = new BannerAdapter(bannerBeanList, getContext());
//        vpBanner.setAdapter(adapter3);
//
//        tvBidRate.setText(Utilities.getDividerStr("13.00%",".",26,16));
//        tvBidTime.setText(Utilities.getDividerStr("30天","天",26,20));
//    }
//
//
//    @OnClick({R.id.tv_capture_treasure, R.id.btn_bid, R.id.tv_know_ftoul,R.id.tv_freshman_welfare,R.id.tv_ftoul_datas})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_capture_treasure:
//                startActivity(new Intent(getContext(), CaptureTreasureActivity.class));
//                break;
//            case R.id.btn_bid:
//                MyToast.show("发现页面的抢购");
//                break;
//            case R.id.tv_know_ftoul:
//                MyToast.show("蜂社区--H5");
//                break;
//            case R.id.tv_freshman_welfare:
//                MyToast.show("蜂商城--H5");
//                break;
//            case R.id.tv_ftoul_datas:
//                MyToast.show("蜂阅读--H5");
//                break;
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, rootView);
//        return rootView;
//    }
}
