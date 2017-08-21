package com.ftoul.androidclient.fragments.invest.regular;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.BidAdapter;
import com.ftoul.androidclient.adapter.viewpager.RegularPagerAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.AbstractBaseParamsVO;
import com.ftoul.androidclient.bean.BidInfosBean;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;
import com.ftoul.androidclient.bean.request.GetAllBidIn;
import com.ftoul.androidclient.bean.response.GetAllBidOut;
import com.ftoul.androidclient.bean.response.GetAllProductOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
import com.ftoul.androidclient.utils.AppDES3EncryptAndDecrypt;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.StringUtil;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class FengJiHuaFragment extends BaseFragment {
    @BindView(R.id.recyclerview_bid)
    XRecyclerView recyclerviewBid;
    public static final String ARGS_PAGE = "bidType";
    @BindView(R.id.myFramelayout)
    MyFramelayout myFramelayout;
    private String bidType;
    private Runnable runnable;
    private BidAdapter bidAdapter;
    private int pageSize = 15;
    private int pageNo = 1;
    private boolean tag = false;//是否请求数据的标记

    public static FengJiHuaFragment newInstance(String bidType) {
        Bundle args = new Bundle();
        args.putString(ARGS_PAGE,bidType);
        FengJiHuaFragment fragment = new FengJiHuaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invest_fragment_feng_ji_hua, container, false);
        return view;
    }


    @Override
    protected void initData() {
        pageSize = 4;
        pageNo = 1;
        tag = false;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerviewBid.setLayoutManager(manager);
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                getData();
            }
        });
        recyclerviewBid.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                pageNo += 1;
                getData();
            }
        });
    }



    private void getData() {
        bidType = getArguments().getString(ARGS_PAGE);
        GetAllBidIn bean = new GetAllBidIn(bidType, pageSize, pageNo);
        String datas = Utilities.toJsonString2(bean);
        Log.e("datas:"+datas);
        String url = MyUrl.SERVICE_ADDRESS+"/services/api/v2/bidList";
        Log.e("url:"+url);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<List<NewBidInfosBean>>(new TypeToken<DataBean<List<NewBidInfosBean>>>() {}, myFramelayout) {
                    @Override
                    public void onSuccess(List<NewBidInfosBean> bidInfosBeen) {
                        MyApp.regularInvestRefresh = true;
                        recyclerviewBid.refreshComplete();
                        if ((bidInfosBeen == null || bidInfosBeen.size() == 0) && pageNo==1) {
                            myFramelayout.onEmpty("暂无记录");
                            return;
                        }
                        if (bidInfosBeen == null || bidInfosBeen.size() < pageSize) {
                            recyclerviewBid.noMoreLoading();
                        }

                        if (bidAdapter == null||recyclerviewBid.getAdapter() == null) {//第一次初始化
                            bidAdapter = new BidAdapter(getActivity(), bidInfosBeen, bidType);
                            recyclerviewBid.setAdapter(bidAdapter);
                            return;
                        }
                        if (pageNo == 1) {//刷新数据
                            bidAdapter.setList(bidInfosBeen);
                        } else {//加载更多
                            bidAdapter.addList(bidInfosBeen);
                        }
                    }

                    @Override
                    protected void onOther(String errMsg, String errCode) {
                        MyApp.regularInvestRefresh = true;
                        super.onOther(errMsg, errCode);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyApp.regularInvestRefresh = true;
                        super.onError(call, e, id);
                    }
                });
 //       dataBean.setBody(new GetAllBidIn(bidType, pageSize, pageNo));
//        bidType = getArguments().getString(ARGS_PAGE);
//        DataBean<GetAllBidIn> dataBean = new DataBean<>(TransCode.GET_ALL_BID);
//        dataBean.setBody(new GetAllBidIn(bidType, pageSize, pageNo));
//
//        String encryptData = Utilities.toJsonString(dataBean);
//        OkHttpUtils
//                .postString()
//                .tag(this)
//                .url(MyUrl.SERVICE_MAIN)
//                .content(encryptData)
//                .build()
//                .execute(new SmartBaseCallBack<GetAllBidOut>(new TypeToken<DataBean<GetAllBidOut>>() {
//                }, myFramelayout) {
//                    @Override
//                    public void onSuccess(GetAllBidOut body) {
//                        MyApp.regularInvestRefresh = true;
//                        recyclerviewBid.refreshComplete();
//                        if ((body.bidInfos == null || body.bidInfos.size() == 0) && pageNo==1) {
//                            myFramelayout.onEmpty("暂无记录");
//                            return;
//                        }
//                        if (body.bidInfos == null || body.bidInfos.size() < pageSize) {
//                            recyclerviewBid.noMoreLoading();
//                        }
//
//                        if (bidAdapter == null||recyclerviewBid.getAdapter() == null) {//第一次初始化
//                            bidAdapter = new BidAdapter(getActivity(), body.bidInfos, bidType);
//                            recyclerviewBid.setAdapter(bidAdapter);
//                            return;
//                        }
//                        if (pageNo == 1) {//刷新数据
//                            bidAdapter.setList(body.bidInfos);
//                        } else {//加载更多
//                            bidAdapter.addList(body.bidInfos);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        MyApp.regularInvestRefresh = true;
//                        super.onError(call, e, id);
//                    }
//                });

    }

    @Override
    public void onStart() {
        if (tag == false || MyApp.regularInvestRefresh == false) {
            tag = true;
            getData();
        }
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        myFramelayout.removeCallbacks(runnable);
        runnable = null;
        super.onDestroyView();
    }
}
