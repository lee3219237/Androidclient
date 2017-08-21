package com.ftoul.androidclient.fragments.xdt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.InvestmentDetail2Activity;
import com.ftoul.androidclient.activitys.InvestmentDetailActivity;
import com.ftoul.androidclient.activitys.XdtDetailActivity;
import com.ftoul.androidclient.adapter.recycle.XdtRecycleAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetProductDueInforIn;
import com.ftoul.androidclient.bean.response.GetProductDueInforOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;

public class XdtListViewFragment extends BaseFragment {

    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    public int[] statusArr = {2, 4, 5};//收益类型：2：投资中4：回款中5：已回款

    private int num = 1;
    private int prePage = 15;
    protected static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
    protected int mFragmentIndex;
    private XdtRecycleAdapter xdtRecycleAdapter;

    public static XdtListViewFragment newInstance(int index) {
        XdtListViewFragment fragment = new XdtListViewFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public XdtListViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFragmentIndex = bundle.getInt(BUNDLE_FRAGMENT_INDEX, 0);
        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xdt_list_view, container, false);
        return view;
    }


    @Override
    protected void initData() {
        xrecycleview.clearHeader();
        xrecycleview.setLoadingMoreEnabled(true);
        xrecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                num += 1;
                getData();
            }
        });
        getData();
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                getData();
            }
        });
    }

    private void getData() {
        String productId = getActivity().getIntent().getStringExtra(XdtDetailActivity.PRODUCT_ID);
        int status = statusArr[mFragmentIndex];
//        DataBean<GetProductDueInforIn> dataBean = new DataBean<>(TransCode.GET_PRODUCT_DUE_IN_INFOR);
//        dataBean.setBody(new GetProductDueInforIn(productId, status, num + "", prePage + ""));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new GetProductDueInforIn(productId, status, num + "", prePage + ""));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/invest/investRecord")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<List<GetProductDueInforOut>>(new TypeToken<DataBean<List<GetProductDueInforOut>>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(List<GetProductDueInforOut> body) {
                        if (body == null ||body.size() == 0) {
                            myFramelayout.onEmpty("暂无记录");
                            xrecycleview.noMoreLoading();
                            return;
                        }
                        if (body.size() < prePage) {
                            xrecycleview.noMoreLoading();
                        }
                        setData(body);
                    }
                });
    }

    private void setData(final List<GetProductDueInforOut> list) {
        if (xdtRecycleAdapter == null) {
            xdtRecycleAdapter = new XdtRecycleAdapter(getActivity(), list);
            xrecycleview.setAdapter(xdtRecycleAdapter);
        } else if (num == 1) {
            xdtRecycleAdapter.setDataList(list);
        } else {
            xdtRecycleAdapter.addDataList(list);
        }
        xdtRecycleAdapter.setOnItemListener(new XdtRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {//跳转投资详情
                if (mFragmentIndex != 0) {
                    Intent intent = new Intent(getContext(), InvestmentDetailActivity.class);
                    intent.putExtra(InvestmentDetailActivity.BID_INFOR, list.get(position));
                    startActivity(intent);
                }
            }
        });
        xrecycleview.refreshComplete();
    }

}
