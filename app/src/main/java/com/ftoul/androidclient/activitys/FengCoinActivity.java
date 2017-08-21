package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.adapter.recycle.FbRecycleAdapter;
import com.ftoul.androidclient.adapter.viewpager.TiyanPageAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetTiYanMoneyIn;
import com.ftoul.androidclient.bean.response.GetFengBiInforOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
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
 * Created by pengtang on 2017/6/2.
 * 蜂宝箱——蜂币
 */
public class FengCoinActivity extends BaseTitleActivity {
    private int num = 1;
    private int prePage = 15;
    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_fb)
    TextView tvFb;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private FbRecycleAdapter fbRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feng_coin);
    }

    @Override
    protected void initView() {
        headerTitle.setText("蜂币");
        getData();
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dataList.add("");
        }
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                getData();
            }
        });
        xrecycleview.clearHeader();
        xrecycleview.setLayoutManager(new LinearLayoutManager(this));
        xrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                num = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                num += 1;
                getData();
            }
        });
    }

    private void getData() {
        DataBean<GetTiYanMoneyIn> dataBean = new DataBean<>(TransCode.GET_FENG_BI_INFOR);
        dataBean.setBody(new GetTiYanMoneyIn(num + "", +prePage + ""));
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<GetFengBiInforOut>(new TypeToken<DataBean<GetFengBiInforOut>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(GetFengBiInforOut body) {
                        tvFb.setText(body.count + "");
                        tvCount.setText(body.alignOverdue + "");
                        if (body.page == null || body.page.items == null || body.page.items.size() == 0) {
                            myFramelayout.onEmpty("没有找到蜂币记录");
                        } else {
                            if (body.page.items.size() < prePage) {
                                xrecycleview.noMoreLoading();
                            }
                            if (fbRecycleAdapter == null) {
                                fbRecycleAdapter = new FbRecycleAdapter(FengCoinActivity.this, body.page.items);
                                xrecycleview.setAdapter(fbRecycleAdapter);
                                return;
                            }
                            if (num == 1) {//下拉刷新
                                fbRecycleAdapter.setDataList(body.page.items);
                            } else {//加载更多
                                fbRecycleAdapter.addDataList(body.page.items);
                            }
                        }
                    }
                });
    }

    @OnClick(R.id.rl_headerRight)
    public void onViewClicked() {//蜂币使用规则
        startActivity(new Intent(this, SimpleWebActivity.class)
                .putExtra(SimpleWebActivity.WEB_URL,MyUrl.FENG_COIN_RULE));
    }
}
