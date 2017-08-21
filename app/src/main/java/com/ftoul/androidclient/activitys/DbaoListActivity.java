package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.DbaoRecycleAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.DbaoListCodeIn;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
import com.ftoul.androidclient.utils.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengtang on 2017/6/1.
 * 夺宝物品
 */
public class DbaoListActivity extends BaseTitleActivity {

    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;

    private DbaoRecycleAdapter dbaoRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbao_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("夺宝物品");
        rlHeaderLeft.setVisibility(View.VISIBLE);
        loadData();
        getData();
    }

    private void getData() {
        DataBean<DbaoListCodeIn> dataBean = new DataBean<>(TransCode.GET_DUO_BAO_PRODUCT_INFOR);
        dataBean.setBody(new DbaoListCodeIn(15));
        Gson gson = new Gson();
        String datas = gson.toJson(dataBean);
        Log.e(datas);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(new TypeToken<DataBean<Object>>() {
                }) {
                    @Override
                    public void onSuccess(Object body) {

                    }
                });
    }


    void loadData()
    {
        List<String> dataList = new ArrayList<>();
        for (int i = 0 ; i<9;i++)
        {
            dataList.add("");
        }
        dbaoRecycleAdapter = new DbaoRecycleAdapter(this);
        dbaoRecycleAdapter.setDataList(dataList);
        xrecycleview.setAdapter(dbaoRecycleAdapter);
        xrecycleview.setLoadingMoreEnabled(true);
        xrecycleview.setLayoutManager(new LinearLayoutManager(this));
        xrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
    }
}
