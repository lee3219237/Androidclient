package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.TiyanPageAdapter;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetTiYanMoneyIn;
import com.ftoul.androidclient.bean.response.GetTiYanMoneyOut;
import com.ftoul.androidclient.fragments.fengbox.TiyanMoneyFragment;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 蜂投网-HzJ on 2017/6/2.
 * <p>
 * 体验金
 */

public class TiyanMoneyActivity extends BaseActivity {
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    @BindView(R.id.tv_tiyan_money)
    TextView tvTiyanMoney;
    @BindView(R.id.tv_tiyan_2)
    TextView tvTiyan2;
    private int num = 1;
    private int prePage = 15;
    @BindView(R.id.paddingView)
    View paddingView;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tiyan_viewpage)
    ViewPager tiyanViewpage;
    @BindView(R.id.tiyan_tablayout)
    TabLayout tiyanTablayout;
    private TiyanPageAdapter tiyanPageAdapter;
    private List<GetTiYanMoneyOut.PageBean.ItemsBean> items;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiyanmoney);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        getData(num, prePage);
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                getData(num, prePage);
            }
        });
        ivLeft.setColorFilter(getResources().getColor(R.color.white));
    }

    @OnClick({R.id.tiyan_return, R.id.tiyan_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tiyan_return:
                finish();
                break;
            case R.id.tiyan_btn:
                startActivity(new Intent(this, ExperienceBidActivity.class)
                        .putExtra(ExperienceBidActivity.LCTY_BID_BEAN, userInfo.getLctyBidBean()));
                break;
        }
    }

    public void getData(int num, int prePage) {
        DataBean<GetTiYanMoneyIn> dataBean = new DataBean<>(TransCode.GET_TI_YAN_JIN_INFOR);
        dataBean.setBody(new GetTiYanMoneyIn(num + "", +prePage + ""));
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<GetTiYanMoneyOut>(new TypeToken<DataBean<GetTiYanMoneyOut>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(GetTiYanMoneyOut body) {
                        tvTiyanMoney.setText(Utilities.df.format(body.money));
                        tvTiyan2.setText(Utilities.df.format(body.income));
                        if (body.page == null || body.page.items == null || body.page.items.size() == 0) {
                            myFramelayout.onEmpty("暂无记录");
                        } else {
                            setItems(body.page.items);
                        }
                        if (tiyanPageAdapter == null) {
                            tiyanPageAdapter = new TiyanPageAdapter(getSupportFragmentManager(), TiyanMoneyActivity.this);
                            tiyanViewpage.setAdapter(tiyanPageAdapter);
                            tiyanTablayout.setupWithViewPager(tiyanViewpage);
                        } else {
                            List<Fragment> fragments = getSupportFragmentManager().getFragments();
                            ((TiyanMoneyFragment) fragments.get(0)).reFresh();
                        }
                    }
                });
    }


    public List<GetTiYanMoneyOut.PageBean.ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<GetTiYanMoneyOut.PageBean.ItemsBean> items) {
        this.items = items;
    }
}
