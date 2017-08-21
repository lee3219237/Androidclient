package com.ftoul.androidclient.activitys;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.FundHistoryAdapter;
import com.ftoul.androidclient.adapter.recycle.PopuWindowScreenAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.FundHistoryCodeIn;
import com.ftoul.androidclient.bean.response.FundHistoryCodeOut;
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
import butterknife.OnClick;

public class FundHistoryActivity extends BaseTitleActivity {

    private int pageSize = 15;
    private int pageNo = 1;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recycler_view)
    XRecyclerView xRecyclerView;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private PopupWindow mPopupWindow;
    private int TYP = 0;
    private FundHistoryAdapter adapter;
    private boolean tag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_history);
    }

    @Override
    protected void initView() {
        headerTitle.setText("资金记录");
        tvRight.setText("筛选");
    }

    @Override
    protected void initData() {
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.clearHeader();
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                pageNo += 1;
                getData(TYP);
            }
        });
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                getData(TYP);
            }
        });
        getData(TYP);
    }

    private void getData(int typ) {
//        DataBean<FundHistoryCodeIn> dataBean = new DataBean<>(TransCode.MONEY_HISTORY);
//        dataBean.setBody(new FundHistoryCodeIn(typ + "", pageSize, pageNo));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new FundHistoryCodeIn(typ + "", pageSize, pageNo));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/dealRecord/list")
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<List<FundHistoryCodeOut.FundHistoryBean>>(new TypeToken<DataBean<List<FundHistoryCodeOut.FundHistoryBean>>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(List<FundHistoryCodeOut.FundHistoryBean> body) {
                        xRecyclerView.refreshComplete();
                        if (body == null ||  body.size() == 0) {
                            myFramelayout.onEmpty("未找到资金记录");
                        } else {
                            if (body.size() < pageSize) {
                                xRecyclerView.noMoreLoading();
                            } else {
                                if (xRecyclerView.isnomore) {
                                    xRecyclerView.isnomore = false;
                                }
                            }
                            setData(body);
                        }
                    }
                });
    }

    private void setData(List<FundHistoryCodeOut.FundHistoryBean> fundHistory) {
        if (adapter == null) {
            adapter = new FundHistoryAdapter(FundHistoryActivity.this, fundHistory);
            xRecyclerView.setAdapter(adapter);
        } else {
            if (tag) {
                tag = false;
                adapter.setList(fundHistory);
            } else {
                adapter.addList(fundHistory);
            }
        }

    }


    @OnClick(R.id.rl_headerRight)
    public void onViewClicked() {//筛选
        if (mPopupWindow == null) {
            View popupView = getLayoutInflater().inflate(R.layout.popupwindow_fund_history, null);
            RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            List list = new ArrayList();
            list.add("全部");
            list.add("a");
            list.add("充值");
            list.add("提现");
            list.add("还款");
            list.add("收入");
            list.add("投资");
            list.add("其他");
            PopuWindowScreenAdapter adapter = new PopuWindowScreenAdapter(this, list);
            adapter.setOnItemClickListener(new PopuWindowScreenAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (position == 0) {
                        if (TYP != position) {//改变选择
                            pageNo = 1;
                            TYP = position;
                            getData(TYP);
                            tag = true;

                        }
                    } else {
                        if (TYP != position - 1) {//改变选择
                            pageNo = 1;
                            TYP = position - 1;
                            myFramelayout.onLoading();
                            getData(TYP);
                            tag = true;
                        }
                    }
                    mPopupWindow.dismiss();
                }
            });
            recyclerView.setAdapter(adapter);
            mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        }

        //popupWindow消失屏幕变为不透明
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        mPopupWindow.showAsDropDown(headerTitle);
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
    }


}
