package com.ftoul.androidclient.fragments.fengbox;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.list.PaymentListAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetFengMiInfor;
import com.ftoul.androidclient.bean.response.PaymentCouponCodeOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 蜂投网-HzJ on 2017/6/3.
 */

public class PaymentCountFragment extends BaseFragment {

    public static final String ARGS_PAGE = "args_page";
    @BindView(R.id.payment_listview)
    ListView paymentListview;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private int num = 1;
    private int prePage = 15;

    private int mPage;
    private PaymentListAdapter paymentListAdapter;

    public static PaymentCountFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        PaymentCountFragment fragment = new PaymentCountFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paymentcoupon, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mPage = getArguments().getInt(ARGS_PAGE);
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
    }


    /**
     * 唤起popupwindow
     */
    private void showPopueWindow() {
        View popView = View.inflate(getActivity(), R.layout.popupwindow_hkzj, null);
        //设置pop宽高
        int weight = getActivity().getResources().getDisplayMetrics().widthPixels;
        int height = getActivity().getResources().getDisplayMetrics().heightPixels * 4 / 5;
        PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);

        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.CENTER, 0, 0);
    }

    private void getData() {
        final int index = getArguments().getInt(ARGS_PAGE);
        DataBean<GetFengMiInfor> dataBean = new DataBean<>(TransCode.GET_HUI_KUAN_ZENG_JUAN_INFOR);
        dataBean.setBody(new GetFengMiInfor(index, num + "", +prePage + ""));
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<PaymentCouponCodeOut>(new TypeToken<DataBean<PaymentCouponCodeOut>>() {
                },myFramelayout) {
                    @Override
                    public void onSuccess(PaymentCouponCodeOut body) {
                        if (body == null || body.page == null || body.page.items == null || body.page.items.size() == 0) {
                            myFramelayout.onEmpty("暂无记录");
                            return;
                        }
                        setData(body.page.items);
                    }
                });
    }
    private void setData(List<PaymentCouponCodeOut.PageBean.ItemsBean> items) {
        if (paymentListAdapter==null){
            if (mPage == 0) {            //未领取
                paymentListAdapter = new PaymentListAdapter(getActivity(), 1);
                paymentListAdapter.setDataList(items);
                paymentListAdapter.setmOnclickListener(new PaymentListAdapter.OnBtnClickListener() {
                    @Override
                    public void onclick(View view, int pst) {
                        MyToast.showCenterToast("点击了");
                        showPopueWindow();
                    }
                });
                paymentListview.setAdapter(paymentListAdapter);
            } else if (mPage == 1) {      //已领取
                paymentListAdapter = new PaymentListAdapter(getActivity(), 2);
                paymentListAdapter.setDataList(items);
                paymentListview.setAdapter(paymentListAdapter);
            } else {                     //已过期
                paymentListAdapter = new PaymentListAdapter(getActivity(), 3);
                paymentListAdapter.setDataList(items);
                paymentListview.setAdapter(paymentListAdapter);
            }

        }


    }

}
