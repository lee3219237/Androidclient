package com.ftoul.androidclient.fragments.billcalenday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.BillCalendarActivity;
import com.ftoul.androidclient.adapter.recycle.ReturnedMoneyAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.bean.ReturnedMoneyBean;
import com.ftoul.androidclient.ui.FullyLinearLayoutManager;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.MyRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 * 回款日历--当天回款本息
 */

public class ReturnedMoneyFragment extends BaseFragment {
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private int moveY;
    @BindView(R.id.recycler_view)
    MyRecyclerView recyclerView;
    private ReturnedMoneyAdapter adapter;
    private BillCalendarActivity activity;
    private FullyLinearLayoutManager manager;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment_returned_money, container, false);
        return view;
    }

    @Override
    protected void initData() {
        activity = (BillCalendarActivity) getActivity();
    }

    public void refresh(JsonArray array) {
        List<ReturnedMoneyBean> list = new ArrayList<>();
        if (array != null) {
            Gson gson = new Gson();
            for (int i = 0; i < array.size(); i++) {
                JsonElement jsonElement = array.get(i);
                ReturnedMoneyBean bean = gson.fromJson(jsonElement, ReturnedMoneyBean.class);
                list.add(bean);
            }
        }
        if (list.size() == 0) {
            myFramelayout.onEmpty("暂无记录");
        } else {
            myFramelayout.onSuccess();
        }
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                myFramelayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myFramelayout.onEmpty("暂无记录");
                    }
                }, 500);
            }
        });
        if (adapter == null) {
            manager = new FullyLinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);
            adapter = new ReturnedMoneyAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
           // recyclerView.setWithScrollView(activity.scrollView);
        } else {
            adapter.reFresh(list);
        }
    }



}
