package com.ftoul.androidclient.fragments.invest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.BidAdapter;
import com.ftoul.androidclient.adapter.recycle.NewUserAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.AbstractBaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;
import com.ftoul.androidclient.bean.request.GetAllBidIn;
import com.ftoul.androidclient.bean.request.GetNewUserWelfareIn;
import com.ftoul.androidclient.bean.response.GetNewUserWelfareOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by ftoul106 on 2017/4/26
 * updated by pengtang on 2017/05/19
 */

public class NewUserFragment extends BaseFragment {
    @BindView(R.id.xrecycleview)
    XRecyclerView recyclerviewBid;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private int pageSize = 15;
    private int pageNo = 1;
    private static final String bidType = "bid_xszx";
    private NewUserAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invest_fragment_new_user, container, false);
        return view;
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerviewBid.setLayoutManager(manager);
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                pageNo = 1;
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
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                pageNo = 1;
                getData();
            }
        });
        adapter = new NewUserAdapter(getActivity());
        recyclerviewBid.setAdapter(adapter);
        getTiYanData();
    }

    @Override
    public void onStart() {
        if (MyApp.newUserInvestRefresh == false) {
            MyApp.newUserInvestRefresh = true;
            getData();
        }
        super.onStart();
    }

    /**
     * 获取新手标
     */
    private void getData() {
        GetAllBidIn bean = new GetAllBidIn(bidType, pageSize, pageNo);
        String datas = Utilities.toJsonString2(bean);
        Log.e("datas:" + datas);
        String url = MyUrl.SERVICE_ADDRESS + "/services/api/v2/bidList";
        Log.e("url:" + url);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<List<NewBidInfosBean>>(new TypeToken<DataBean<List<NewBidInfosBean>>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(List<NewBidInfosBean> bidInfosBeen) {
                        recyclerviewBid.refreshComplete();
                        if ((bidInfosBeen == null || bidInfosBeen.size() == 0) && pageNo == 1) {
                            //  myFramelayout.onEmpty("暂无记录");
                            recyclerviewBid.noMoreLoading();
                            return;
                        }
                        if (bidInfosBeen == null || bidInfosBeen.size() < pageSize) {
                            recyclerviewBid.noMoreLoading();
                        }
                        adapter.setList(bidInfosBeen);
                        if (pageNo == 1) {//下拉刷新
                            adapter.setList(bidInfosBeen);
                        } else {//加载更多
                            adapter.addList(bidInfosBeen);
                        }
                    }

                });
//        DataBean<GetNewUserWelfareIn> dataBean = new DataBean<>(TransCode.GET_NEW_USER_WELFARE);
//        dataBean.setBody(new GetNewUserWelfareIn(pageSize, pageNum));
//        String encryptData = Utilities.toJsonString(dataBean);
//        OkHttpUtils
//                .postString()
//                .tag(this)
//                .url(MyUrl.SERVICE_MAIN)
//                .content(encryptData)
//                .build()
//                .execute(new SmartBaseCallBack<GetNewUserWelfareOut>(new TypeToken<DataBean<GetNewUserWelfareOut>>() {
//                }, myFramelayout) {
//                    @Override
//                    public void onSuccess(GetNewUserWelfareOut body) {
//                        xrecycleview.refreshComplete();
//                        if(body.lctyBid==null&&(body.xszxBid==null||body.xszxBid.bidList==null||body.xszxBid.bidList.size()==0)){
//                            myFramelayout.onEmpty("暂无记录");
//                            return;
//                        }
//                        List<GetNewUserWelfareOut.LctyBidBean> list = new ArrayList();
//                        list.add(body.lctyBid);
//                        NewUserAdapter adapter = null;
//
//                        if (body.xszxBid == null || body.xszxBid.bidList.size() < pageSize) {
//                            xrecycleview.noMoreLoading();
//                        }
//                        if (adapter == null) {//第一次创建
//                            if (body.xszxBid == null) {
//                                adapter = new NewUserAdapter(getActivity(), list, null);
//                            } else {
//                                adapter = new NewUserAdapter(getActivity(), list, body.xszxBid.bidList);
//                            }
//                            xrecycleview.setAdapter(adapter);
//                        } else if (pageNum == 1) {//下拉刷新
//                            adapter.setList(body.xszxBid.bidList);
//                        } else {//加载更多
//                            adapter.addList(body.xszxBid.bidList);
//                        }
//                    }
//                });
//    }
    }

    /**
     * 获取体验标
     */
    private void getTiYanData() {
        String datas = Utilities.toJsonString2(new AbstractBaseParamsVO());
        Log.e("datas:" + datas);
        String url = MyUrl.SERVICE_ADDRESS + "/services/api/v2/lctyProduct";
        Log.e("url:" + url);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<GetNewUserWelfareOut>(new TypeToken<DataBean<GetNewUserWelfareOut>>() {
                }) {
                    @Override
                    public void onSuccess(GetNewUserWelfareOut body) {

                        if (body == null || body.lctyBid == null) {
                            //  myFramelayout.onEmpty("暂无记录");
                            return;
                        }
                        List<GetNewUserWelfareOut.LctyBidBean> list = new ArrayList<GetNewUserWelfareOut.LctyBidBean>();
                        list.add(body.lctyBid);
                       adapter.setTiyanList(list);
                    }

                });
    }
}
