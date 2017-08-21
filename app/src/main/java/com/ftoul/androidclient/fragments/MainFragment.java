package com.ftoul.androidclient.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.ChangeVersionActivity;
import com.ftoul.androidclient.activitys.LoginActivity;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.activitys.MessageActivity;
import com.ftoul.androidclient.adapter.recycle.MainFragmentAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.AbstractBaseParamsVO;
import com.ftoul.androidclient.bean.BannerBean;
import com.ftoul.androidclient.bean.BidInfosBean;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.response.HomeCodeOut;
import com.ftoul.androidclient.bean.response.MainFragmentBean;
import com.ftoul.androidclient.bean.response.RecommendProductsBeanOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyDialog;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class MainFragment extends BaseFragment {
    private int imageHeight;
    private int y = 0;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.recyclerview_main)
    RecyclerView recyclerviewMain;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    private MainFragmentAdapter adapter;
    private HomeCodeOut homeCodeOut;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_main_2, container, false);
        return view;
    }

    @Override
    protected void initData() {
        recyclerviewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MainFragmentAdapter((MainActivity) getActivity());
        recyclerviewMain.setAdapter(adapter);
        imageHeight = (int) (MyApp.width / 2 - getResources().getDimension(R.dimen.title_height2));
        recyclerviewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Log.i("TAG", "y--->" + y + "    height-->" + height);
                y = y + dy;
                if (y <= 0) {
                    llHead.setAlpha(0);
                    headerTitle.setTextColor(Color.argb((int) 0, 0xff, 0xff, 0xff));
                } else if (y > 0 && y <= imageHeight) {
                    float scale = (float) y / imageHeight;
                    float alpha = (255 * scale);
                    llHead.setAlpha(scale);
                    // 只是layout背景透明
                    headerTitle.setTextColor(Color.argb((int) alpha, 0xff, 0xff, 0xff));
                } else {
                    llHead.setAlpha(1);
                    headerTitle.setTextColor(Color.argb((int) 255, 0xff, 0xff, 0xff));
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.canScrollVertically(1) == false) {//滑动到底部
                    headerTitle.setTextColor(Color.argb((int) 255, 0xff, 0xff, 0xff));
                    llHead.setAlpha(1);
                }
            }
        });
        getData();
    }

    /**
     *
     */
    private void getData() {
//        DataBean<AbstractBaseParamsVO> dataBean = new DataBean<>(TransCode.GET_MAIN_INFOR);
//        AbstractBaseParamsVO abstractBaseParamsVO = new AbstractBaseParamsVO();
//        if (userInfo.isLogin()) {
//            abstractBaseParamsVO.setUid(userInfo.getUid());
//        }
//        dataBean.setBody(abstractBaseParamsVO);
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new AbstractBaseParamsVO());
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/home/init")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<HomeCodeOut>(new TypeToken<DataBean<HomeCodeOut>>() {
                }) {
                    @Override
                    public void onSuccess(HomeCodeOut homeCodeOut) {
                        MainFragment.this.homeCodeOut = homeCodeOut;
                        userInfo.setAppInfo(homeCodeOut.getAppInfo());
                        if (false == MyApp.appUpdate) {
                            MyApp.appUpdate = true;
                            MyDialog.aleartAppUpdateDialog(getContext(),userInfo.getAppInfo());//app升级对话框
                        }
                        MainFragmentBean bean = new MainFragmentBean(homeCodeOut.banner, homeCodeOut.getStartPages());
                        adapter.reFresh(bean);
                    }
                });
    }

    /**
     * 获取推荐标
     */
    private void getRecommendBid() {
//        DataBean<AbstractBaseParamsVO> dataBean = new DataBean<>(TransCode.GET_MAIN_INFOR);
//        AbstractBaseParamsVO abstractBaseParamsVO = new AbstractBaseParamsVO();
//        if (userInfo.isLogin()) {
//            abstractBaseParamsVO.setUid(userInfo.getUid());
//        }
//        dataBean.setBody(abstractBaseParamsVO);
        AbstractBaseParamsVO abstractBaseParamsVO = new AbstractBaseParamsVO();
        if (userInfo.isLogin()) {
            abstractBaseParamsVO.setUid(userInfo.getUid());
        }
        String datas = Utilities.toJsonString2(abstractBaseParamsVO);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/recommendProducts")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<RecommendProductsBeanOut>(new TypeToken<DataBean<RecommendProductsBeanOut>>() {
                }) {
                    @Override
                    public void onSuccess(RecommendProductsBeanOut body) {
                        adapter.recommendBidreFresh(body.getRecommendBidInfo());
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (MyApp.mainRefresh == false) {
            MyApp.mainRefresh = true;
            getRecommendBid();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.rl_headerLeft, R.id.rl_headerRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_headerLeft:
                startActivity(new Intent(getContext(), ChangeVersionActivity.class));
                break;
            case R.id.rl_headerRight:
                if (userInfo.isLogin()) {
                    if (homeCodeOut == null) {
                        startActivity(new Intent(getContext(), MessageActivity.class));
                    } else {
                        startActivity(new Intent(getContext(), MessageActivity.class)
                                .putParcelableArrayListExtra(MessageActivity.NOTICE_ITEMS, homeCodeOut.getStartPages()));
                    }
                } else {
                    MyToast.show("请先登录");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
        }
    }



}
