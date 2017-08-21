package com.ftoul.androidclient.fragments.fmcoupons;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.FMCouponsRecycleAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetFengMiInfor;
import com.ftoul.androidclient.bean.response.FmCouponsCodeOut;
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
 * 未使用蜂蜜券
 */
public class NotUseFmCouponsFragment extends BaseFragment {

    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;
    protected static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
    protected int mFragmentIndex;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private int num = 1;
    private int prePage = 15;
    private FMCouponsRecycleAdapter fmCouponsRecycleAdapter;

    public static NotUseFmCouponsFragment newInstance(int index) {
        NotUseFmCouponsFragment fragment = new NotUseFmCouponsFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public NotUseFmCouponsFragment() {
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
        DataBean<GetFengMiInfor> dataBean = new DataBean<>(TransCode.GET_FENG_MI_INFOR);
        final int index= mFragmentIndex;
        dataBean.setBody(new GetFengMiInfor(index, num + "", +prePage + ""));
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<FmCouponsCodeOut>(new TypeToken<DataBean<FmCouponsCodeOut>>() {
                },myFramelayout) {
                    @Override
                    public void onSuccess(FmCouponsCodeOut body) {
                            if (body == null || body.page == null || body.page.items == null || body.page.items.size() == 0) {
                                myFramelayout.onEmpty("暂无记录");
                                return;
                            }
                            setData(body.page.items);
                        }

                });
    }
    private void setData(List<FmCouponsCodeOut.PageBean.ItemsBean> items) {
        fmCouponsRecycleAdapter = new FMCouponsRecycleAdapter(getActivity());
        fmCouponsRecycleAdapter.setType(mFragmentIndex);
        fmCouponsRecycleAdapter.setDataList(items);
        xrecycleview.setAdapter(fmCouponsRecycleAdapter);
    }


}
