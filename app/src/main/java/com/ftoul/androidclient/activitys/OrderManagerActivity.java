package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.OrderManagerAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 蜂商城订单
 */
public class OrderManagerActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
    }

    @Override
    protected void initView() {
        headerTitle.setText("蜂商城订单");
    }

    @Override
    protected void initData() {
        //getData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        OrderManagerAdapter adapter = new OrderManagerAdapter(this,list);
        recyclerView.setAdapter(adapter);
        myFramelayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myFramelayout.onSuccess();
                }
        },1500);

    }
    private void getData() {
        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode. GET_FENG_ORDER);
        dataBean.setBody(new BaseParamsVO());
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
}
