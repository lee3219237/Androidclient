package com.ftoul.androidclient.fragments.jiaxicoupons;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.JiaxiRecycleAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetFengMiInfor;
import com.ftoul.androidclient.bean.response.JXcouponsFragmentCodeOut;
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

/**
 * Created by pengtang on 2017/6/1.
 * 未使用加息券
 */
public class NotUseJXCouponsFragment extends BaseFragment {

    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;
    protected static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
    protected int mFragmentIndex;
    private JiaxiRecycleAdapter jiaxiRecycleAdapter;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private int num = 1;
    private int prePage = 15;

    public static NotUseJXCouponsFragment newInstance(int index) {
        NotUseJXCouponsFragment fragment = new NotUseJXCouponsFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xdt_list_view, container, false);
        return view;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFragmentIndex = bundle.getInt(BUNDLE_FRAGMENT_INDEX, 0);
        }
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
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
        xrecycleview.clearHeader();
        xrecycleview.setLoadingMoreEnabled(true);
        xrecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void getData() {
        final int status = mFragmentIndex;
        DataBean<GetFengMiInfor> dataBean = new DataBean<>(TransCode.GET_JIA_XI_INFOR);
        dataBean.setBody(new GetFengMiInfor(status, num + "", +prePage + ""));
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<JXcouponsFragmentCodeOut>(new TypeToken<DataBean<JXcouponsFragmentCodeOut>>() {
                },myFramelayout) {
                    @Override
                    public void onSuccess(JXcouponsFragmentCodeOut body) {
                        if (body == null || body.page == null || body.page.items == null || body.page.items.size() == 0) {
                            myFramelayout.onEmpty("暂无记录");
                            return;
                        }
                        setData(body.page.items);
                    }
                });
    }

    private void setData(List<JXcouponsFragmentCodeOut.PageBean.ItemsBean> items) {
        if(items.size()<prePage){
            xrecycleview.noMoreLoading();
        }
        if (jiaxiRecycleAdapter==null){
            jiaxiRecycleAdapter = new JiaxiRecycleAdapter(getActivity());
            jiaxiRecycleAdapter.setType(mFragmentIndex);
            jiaxiRecycleAdapter.setDataList(items);

        }
        xrecycleview.setAdapter(jiaxiRecycleAdapter);

    }
}
