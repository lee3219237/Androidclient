package com.ftoul.androidclient.fragments.investdetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.InvestmentDetail2Activity;
import com.ftoul.androidclient.adapter.recycle.XdtRecycleAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import butterknife.BindView;
/**
 * Created by pengtang on 2017/5/26.
 * 回款计划
 */
public class InvestPlanFragment extends BaseFragment {

    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;
    protected static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
    protected int mFragmentIndex;
    private XdtRecycleAdapter xdtRecycleAdapter;

    public static InvestPlanFragment newInstance(int index) {
        InvestPlanFragment fragment = new InvestPlanFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public InvestPlanFragment() {
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
        View view = inflater.inflate(R.layout.fragment_invest_detail_plan, container, false);
        return view;
    }

    @Override
    protected void initData() {
//        xdtRecycleAdapter = new XdtRecycleAdapter(getActivity());
//        xdtRecycleAdapter.setOnItemListener(new XdtRecycleAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {//跳转投资详情
//                Utilities.startNewActivity(getActivity(), InvestmentDetail2Activity.class);
//                MyToast.show("详细信息");
//                getActivity().finish();
//            }
//        });
//        xrecycleview.clearHeader();
//        xrecycleview.setLoadingMoreEnabled(true);
//        xrecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
//        xrecycleview.setAdapter(xdtRecycleAdapter);
//        xrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
    }
}
