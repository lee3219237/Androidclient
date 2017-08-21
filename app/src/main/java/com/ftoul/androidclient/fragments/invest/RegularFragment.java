package com.ftoul.androidclient.fragments.invest;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.InvestPagerAdapter;
import com.ftoul.androidclient.adapter.viewpager.RegularPagerAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.AbstractBaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.response.GetAllProductOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.AppDES3EncryptAndDecrypt;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 定期投资页面
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class RegularFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_regular_invest)
    ViewPager vpRegularInvest;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invest_fragment_regular, container, false);
        return view;
    }

    @Override
    protected void initData() {
        DataBean<AbstractBaseParamsVO> dataBean = new DataBean<>(TransCode.GET_ALL_PRODUCT);
        dataBean.setBody(new AbstractBaseParamsVO());
        String encryptData = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(encryptData)
                .build()
                .execute(new SmartBaseCallBack<GetAllProductOut>(new TypeToken<DataBean<GetAllProductOut>>(){}){
                    @Override
                    public void onSuccess(GetAllProductOut body) {
                        List<GetAllProductOut.RegularBidsBean> list = body.getRegularBids();
                        RegularPagerAdapter adapter = new RegularPagerAdapter(getActivity().getSupportFragmentManager(),list);
                        vpRegularInvest.setAdapter(adapter);
                        tabLayout.setupWithViewPager(vpRegularInvest);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                initData();
//                            }
//                        },4000);
                    }
                });

    }
}
