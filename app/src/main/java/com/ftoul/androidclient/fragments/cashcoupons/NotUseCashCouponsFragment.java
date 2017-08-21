package com.ftoul.androidclient.fragments.cashcoupons;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.recycle.CashCouponsAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetFengMiInfor;
import com.ftoul.androidclient.bean.response.NotUseCashCouponsCodeOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.PerfectClickListener;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by pengtang on 2017/6/2.
 * 未使用现金券
 */
public class NotUseCashCouponsFragment extends BaseFragment {

    @BindView(R.id.xrecycleview)
    XRecyclerView xrecycleview;
    protected static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
    protected int mFragmentIndex;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private int num = 1;
    private int prePage = 15;
    private CashCouponsAdapter cashCouponsAdapter;

    public static NotUseCashCouponsFragment newInstance(int index) {
        NotUseCashCouponsFragment fragment = new NotUseCashCouponsFragment();
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
        getData();
        xrecycleview.clearHeader();
        xrecycleview.setLoadingMoreEnabled(true);
        xrecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        // xrecycleview.setAdapter(cashCouponsAdapter);
        xrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
        myFramelayout.setOnReloadingListener(new MyFramelayout.OnReloadingListener() {
            @Override
            public void onReloading() {
                getData();
            }
        });
    }

    /**
     * 弹出对话框
     *
     * @param pst
     */
    private void aleartDialog(int pst) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = View.inflate(getActivity(), R.layout.dialog_cash_coupons, null);
        builder.setView(inflate);
        final AlertDialog mDialog = builder.show();
        mDialog.setCanceledOnTouchOutside(false);
        ImageView ivExit = (ImageView) inflate.findViewById(R.id.iv_close);
        ivExit.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                mDialog.dismiss();
            }
        });
        Button exitBtn = (Button) inflate.findViewById(R.id.btn_go);
        exitBtn.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    private void getData() {
        final int status = mFragmentIndex;
        DataBean<GetFengMiInfor> dataBean = new DataBean<>(TransCode.GET_XIAN_JIN_JUAN_INFOR);
        dataBean.setBody(new GetFengMiInfor(status, num + "", +prePage + ""));
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<NotUseCashCouponsCodeOut>(new TypeToken<DataBean<NotUseCashCouponsCodeOut>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(NotUseCashCouponsCodeOut body) {
                        if (body == null || body.page == null || body.page.items == null || body.page.items.size() == 0) {
                            myFramelayout.onEmpty("没有找到此项目");
                            return;
                        }
                        setData(body.page.items);
                    }
                });
    }

    private void setData(List<NotUseCashCouponsCodeOut.PageBean.ItemsBean> items) {
        if(items.size()<prePage){
            xrecycleview.noMoreLoading();
        }
        if (cashCouponsAdapter == null) {
            cashCouponsAdapter = new CashCouponsAdapter(getActivity(), mFragmentIndex);
            cashCouponsAdapter.setDataList(items);
            if (mFragmentIndex == 0) {
                cashCouponsAdapter.setOnItemListener(new CashCouponsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        aleartDialog(position);
                    }
                });
            }
            xrecycleview.setAdapter(cashCouponsAdapter);
        }


    }

}
