package com.ftoul.androidclient.fragments.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.MessageActivity;
import com.ftoul.androidclient.adapter.recycle.NoticeAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.bean.ReceiverBean;
import com.ftoul.androidclient.bean.response.HomeCodeOut;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ftoul106 on 2016/11/8 0008.
 * //公告页面
 */

public class NoticeFragment extends BaseFragment {
    @BindView(R.id.recyclerview_notice)
    XRecyclerView recyclerviewNotice;

    private int counts = 15;//从服务器获取条目数

    private NoticeAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_notify, null);
        return view;
    }

    /**
     * 全部已读
     */
    public void hasAllRead() {
        if (adapter != null)
            adapter.hasAllRead();
    }

    /**
     * 加载数据
     */
    public void initData() {
        recyclerviewNotice.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewNotice.clearHeader();
        //发起网络请求，获取数据
        getData();
    }

    /**
     * 发起网络请求，获取数据
     */
    private void getData() {
//        final List<ReceiverBean> msgs = new ArrayList<>();//所有用于存放消息的集合
//        // String url, String title, String content, String sendTime, boolean isOpen, int id
//        for (int i = 1; i < 16; i++) {
//            msgs.add(new ReceiverBean("", "标题" + i, "内容" + i, "2017-6-" + i, false, i + ""));
//        }
        ArrayList<HomeCodeOut.StartPagesBean> startPages = getActivity().getIntent().getParcelableArrayListExtra(MessageActivity.NOTICE_ITEMS);
        if (startPages == null) {
            return;
        }
        adapter = new NoticeAdapter(getActivity(), startPages);
        recyclerviewNotice.setAdapter(adapter);
        recyclerviewNotice.noMoreLoading();
    }



}
