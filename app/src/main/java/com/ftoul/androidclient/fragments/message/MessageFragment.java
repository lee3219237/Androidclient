package com.ftoul.androidclient.fragments.message;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.SecondMessageAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;
import com.ftoul.androidclient.bean.ReceiverBean;
import com.ftoul.androidclient.bean.request.GetAddressIn;
import com.ftoul.androidclient.bean.request.GetMessageIn;
import com.ftoul.androidclient.bean.response.GetAddressCodeOut;
import com.ftoul.androidclient.bean.response.GetMessageOut;
import com.ftoul.androidclient.bean.response.MessageBean;
import com.ftoul.androidclient.dao.MySQLiteHelper;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;


/**
 * Created by ftoul106 on 2016/11/8 0008.
 * 消息页面
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.recyclerview_message)
    XRecyclerView recyclerviewMessage;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private int num = 1;
    private int prePage = 15;
    // private MessageAdapter adapter;//极光推送Adapter
    private SecondMessageAdapter adapter;//站内信Adapter
    private Map<Long, Boolean> map;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_message, null);
        return view;
    }


    /**
     * 全部已读
     */
    public void hasAllRead() {
        if (adapter != null) {
            showDialog("正在标记中");
            new Thread() {
                public void run() {
                    adapter.hasAllRead();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pDialog.dismiss();
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }.start();

        }
    }

    public void initData() {
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                num = 1;
                getData();
            }
        });
        recyclerviewMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewMessage.clearHeader();
        recyclerviewMessage.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                num += 1;
                getData();
            }
        });
        MySQLiteHelper helper = new MySQLiteHelper(getActivity());
        final SQLiteDatabase db = helper.getWritableDatabase();
        //所有用于存放消息的集合
        map = new HashMap();
        new Thread() {
            public void run() {
                Cursor cursor = db.query("msgs",
                        new String[]{"isOpen", "id"}, "uid=?",
                        new String[]{userInfo.getUid()}, null, null, "id desc");
                while (cursor.moveToNext()) {//将所有数据库数据存入本地集合中
                    map.put((long) cursor.getInt(1), 0 != cursor.getInt(0));
                }
                cursor.close();
                db.close();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getData();

                    }
                });
            }

        }.start();

    }


    @Override
    public void onDestroyView() {
        if (adapter != null) {
            adapter.onDestroy();
        }
        super.onDestroyView();
    }

    private void getData() {
//        DataBean<GetMessageIn> dataBean = new DataBean<>(TransCode.GET_MESSAGE);
//        dataBean.setBody(new GetMessageIn(1, num + "", prePage + ""));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new GetMessageIn(1, num + "", prePage + ""));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/message/messageList")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<List<MessageBean>>(new TypeToken<DataBean<List<MessageBean>>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(List<MessageBean> body) {
                        recyclerviewMessage.refreshComplete();
                        if (body == null) {
                            recyclerviewMessage.noMoreLoading();
                            return;
                        }
                        if (num == 1) {
                            if (body.size() == 0) {
                                myFramelayout.onEmpty("暂无消息");
                            }
                            adapter = new SecondMessageAdapter(getActivity(), body, map);
                            recyclerviewMessage.setAdapter(adapter);
                        } else {
                            adapter.addList(body);
                        }
                        if (body.size() < prePage) {
                            recyclerviewMessage.noMoreLoading();
                        }
                    }
                });
    }

    //   public void initData() {
//        recyclerviewMessage.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        MySQLiteHelper helper = new MySQLiteHelper(getActivity());
//        final SQLiteDatabase db = helper.getWritableDatabase();
//        final List<ReceiverBean> msgs = new ArrayList<>();//所有用于存放消息的集合
//        new Thread() {
//            public void run() {
//                Cursor cursor = db.query("msgs",
//                        new String[]{"type", "url", "title", "content", "sendTime", "isOpen", "id"}, "uid=?",
//                        new String[]{userInfo.getUid()}, null, null, "id desc");
//                while (cursor.moveToNext()) {//将所有数据库数据存入本地集合中
//                    ReceiverBean bean = new ReceiverBean(cursor.getInt(0),
//                            cursor.getString(1), cursor.getString(2),
//                            cursor.getString(3), cursor.getString(4),
//                            0 != cursor.getInt(5), cursor.getInt(6));
//                    msgs.add(bean);
//                }
//                cursor.close();
//                db.close();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        getData();
//                        adapter = new SecondMessageAdapter(getActivity(), msgs);
//                        recyclerviewMessage.setAdapter(adapter);
//
//                    }
//                });
//            }
//
//        }.start();
//
//    }
}
