package com.ftoul.androidclient.fragments.billcalenday;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.BillCalendarActivity;
import com.ftoul.androidclient.adapter.recycle.InvestHistoryAdapter;
import com.ftoul.androidclient.adapter.recycle.ReturnedMoneyAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.bean.InvestHistoryBean;
import com.ftoul.androidclient.bean.ReturnedMoneyBean;
import com.ftoul.androidclient.ui.FullyLinearLayoutManager;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.MyRecyclerView;
import com.ftoul.androidclient.utils.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ftoul106 on 2017/6/1 0026.
 * 账单日历 --- 当天投资记录
 */

public class InvestHistoryFragment extends BaseFragment {
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    @BindView(R.id.recycler_view)
    MyRecyclerView recyclerView;
    private InvestHistoryAdapter adapter;
    private BillCalendarActivity activity;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment_invest_history, container, false);
        return view;
    }

    @Override
    protected void initData() {
        activity = (BillCalendarActivity) getActivity();
    }

    public void refresh(JsonArray array) {
        List<InvestHistoryBean> list = new ArrayList<>();
        if (array != null) {
            Gson gson = new Gson();
            for(int i =0;i<array.size();i++){
                JsonElement jsonElement = array.get(i);
                InvestHistoryBean bean = gson.fromJson(jsonElement, InvestHistoryBean.class);
                list.add(bean);
            }
        }
        if (list.size() == 0) {
            myFramelayout.onEmpty("未找到投资记录");
        }else{
            myFramelayout.onSuccess();
        }
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                myFramelayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myFramelayout.onEmpty("未找到投资记录");
                    }
                },400);

            }
        });
        if (adapter == null) {
            recyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext()));
            adapter = new InvestHistoryAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
         //   recyclerView.setWithScrollView(activity.scrollView);
        } else {
            adapter.reFresh(list);
        }
    }

}
