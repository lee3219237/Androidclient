package com.ftoul.androidclient.fragments.fengbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.TiyanMoneyActivity;
import com.ftoul.androidclient.adapter.list.TiyanListAdapter;
import com.ftoul.androidclient.adapter.recycle.TiYanAdapter;
import com.ftoul.androidclient.adapter.viewpager.TiyanPageAdapter;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetTiYanMoneyIn;
import com.ftoul.androidclient.bean.response.GetTiYanInvestOut;
import com.ftoul.androidclient.bean.response.GetTiYanMoneyOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 蜂投网-HzJ on 2017/6/2.
 */

public class TiyanMoneyFragment extends Fragment {
    private int num = 1;
    private int prePage = 15;
    public static final String ARGS_PAGE = "args_page";
    @BindView(R.id.tiyan_fg_tv1)
    TextView tiyanFgTv1;
    @BindView(R.id.tiyan_fg_tv2)
    TextView tiyanFgTv2;
    @BindView(R.id.tiyan_fg_tv3)
    TextView tiyanFgTv3;
    @BindView(R.id.tiyan_fg_tv4)
    TextView tiyanFgTv4;
    Unbinder unbinder;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    private int mPage;
    private TiyanMoneyActivity activity;
    private TiYanAdapter adapter;

    public static TiyanMoneyFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        TiyanMoneyFragment fragment = new TiyanMoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tiyanmoney, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        activity = (TiyanMoneyActivity) getActivity();
        List<GetTiYanMoneyOut.PageBean.ItemsBean> itemsBeen = activity.getItems();
        recyclerView.clearHeader();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (mPage == 1) {       //获取记录
            tiyanFgTv1.setText("获取时间");
            tiyanFgTv2.setText("面额");
            tiyanFgTv3.setText("来源");
            tiyanFgTv4.setText("到期时间");
            if (itemsBeen == null || itemsBeen.size() == 0) {
                return;
            }
            adapter = new TiYanAdapter(activity, itemsBeen, mPage);
            recyclerView.setAdapter(adapter);
            if (itemsBeen.size() < prePage) {
                recyclerView.noMoreLoading();
            }
            recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {

                }

                @Override
                public void onLoadMore() {
                    num += 1;
                    prePage += 15;
                    activity.getData(num, prePage);
                }
            });
        } else {                //投资记录
            getData();
            tiyanFgTv1.setText("投资时间");
            tiyanFgTv2.setText("投资金额");
            tiyanFgTv3.setText("预期收益");
            tiyanFgTv4.setText("状态");
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 刷新的方法
     */
    public void reFresh() {
        List<GetTiYanMoneyOut.PageBean.ItemsBean> itemsBeen = activity.getItems();
        if (itemsBeen == null||itemsBeen.size() < prePage) {
            recyclerView.noMoreLoading();
        }
    }

    public void getData() {
        DataBean<GetTiYanMoneyIn> dataBean = new DataBean<>(TransCode.GET_TI_YAN_JIN_INVEST);
        dataBean.setBody(new GetTiYanMoneyIn(num + "", +prePage + ""));
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<GetTiYanInvestOut>(new TypeToken<DataBean<GetTiYanInvestOut>>() {
                }) {
                    @Override
                    public void onSuccess(GetTiYanInvestOut body) {
                        adapter = new TiYanAdapter(activity, body.page.items, mPage);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                            @Override
                            public void onRefresh() {

                            }

                            @Override
                            public void onLoadMore() {
                                num += 1;
                                prePage += 15;
                                getData();
                            }
                        });
                    }
                });
    }
}
