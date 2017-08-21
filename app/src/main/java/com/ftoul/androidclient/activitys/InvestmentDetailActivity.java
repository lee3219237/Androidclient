package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.InvestmentDetailAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetProductDueInforIn;
import com.ftoul.androidclient.bean.request.GetReturnMoneyPlanIn;
import com.ftoul.androidclient.bean.response.GetProductDueInforOut;
import com.ftoul.androidclient.bean.response.GetReturnMoneyPlanOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;

public class InvestmentDetailActivity extends BaseTitleActivity {
    public static final String BID_INFOR = "bidInfor";
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private InvestmentDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_detail);
    }

    @Override
    protected void initView() {
        headerTitle.setText("投资详情");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GetProductDueInforOut bean = (GetProductDueInforOut) getIntent().getSerializableExtra(BID_INFOR);
        adapter = new InvestmentDetailAdapter(this, bean);
        recyclerView.setAdapter(adapter);
        getData(bean.getBidId());
    }

    private void getData(int bidId) {
//        DataBean<GetReturnMoneyPlanIn> dataBean = new DataBean<>(TransCode.GET_RETURNED_MONEY_PLAN);
//        dataBean.setBody(new GetReturnMoneyPlanIn(bidId+""));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new GetReturnMoneyPlanIn(bidId + ""));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/repayCalendar/bidRepayBills")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<GetReturnMoneyPlanOut>(new TypeToken<DataBean<GetReturnMoneyPlanOut>>() {
                }) {
                    @Override
                    public void onSuccess(GetReturnMoneyPlanOut body) {
                        adapter.setList(body.planInfos);
                    }
                });
    }
}
