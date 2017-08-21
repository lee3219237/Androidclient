package com.ftoul.androidclient.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.AssetsShowActivity;
import com.ftoul.androidclient.activitys.BillCalendarActivity;
import com.ftoul.androidclient.activitys.FengBoxActivity;
import com.ftoul.androidclient.activitys.FundHistoryActivity;
import com.ftoul.androidclient.activitys.LoginActivity;
import com.ftoul.androidclient.activitys.OrderManagerActivity;
import com.ftoul.androidclient.activitys.RechargeActivity;
import com.ftoul.androidclient.activitys.RiskTestActivity;
import com.ftoul.androidclient.activitys.RiskTestResultActivity;
import com.ftoul.androidclient.activitys.SettingActivity;
import com.ftoul.androidclient.activitys.UserInfoActivity;
import com.ftoul.androidclient.activitys.WithdrawCashActivity;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.adapter.recycle.PersonalAdapter;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.response.PersonalAssetsCodeOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.GlideCircleTransform;
import com.ftoul.androidclient.ui.MyLinearLayout;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class PersonalFragment extends BaseFragment {

    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;//用户手机号码
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.ll_body)
    MyLinearLayout llBody;
    @BindView(R.id.tv_name)
    TextView tvNickname;//昵称
    @BindView(R.id.tv_total)
    TextView tvAllAsset;//所有资产
    @BindView(R.id.tv_canuse)
    TextView tvCanUseAsset;//可用余额
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private Intent intent;
    private PersonalAdapter adapter;
    //    private LinearLayout.LayoutParams layoutParams;
//    private int margns;
//    private float oldY;
//    private int maxMargns;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_personal, container, false);
        return view;
    }

    @Override
    protected void initData() {
        ivSetting.setColorFilter(getResources().getColor(R.color.white));
        intent = new Intent();
        // initListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getData() {
        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_MAIN_MONEY);
        dataBean.setBody(new BaseParamsVO());
        String encryptData = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(encryptData)
                .build()
                .execute(new SmartBaseCallBack<PersonalAssetsCodeOut>(new TypeToken<DataBean<PersonalAssetsCodeOut>>() {
                }) {
                    @Override
                    public void onSuccess(PersonalAssetsCodeOut personalAssetsCodeOut) {
                        if (adapter == null) {
                            adapter = new PersonalAdapter(getActivity(), personalAssetsCodeOut.getAssetList());
                            recyclerView.setAdapter(adapter);
                        } else {
                            adapter.reFresh(personalAssetsCodeOut.getAssetList());
                        }
                        tvAllAsset.setText(Utilities.df.format(personalAssetsCodeOut.getHxbalance()));
                        tvCanUseAsset.setText(Utilities.df.format(personalAssetsCodeOut.getAvailablebal()));
                        userInfo.setCanUseMoney(personalAssetsCodeOut.getAvailablebal());
                    }


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getData();
//                            }
//                        }, 4000);
                    }
                });

    }

    @OnClick({R.id.ll_order_form, R.id.ll_bill_calendar,
            R.id.ll_bill, R.id.rl_user_infor,
            R.id.ll_discount_coupon, R.id.ll_invite_friend,
            R.id.ll_risk_test, R.id.iv_setting,
            R.id.tv_total, R.id.btn_in, R.id.btn_out})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_setting) {
            intent.setClass(getContext(), SettingActivity.class);
            startActivity(intent);
            return;
        }
        if (!userInfo.isLogin()) {
            intent.setClass(getContext(), LoginActivity.class);
            startActivity(intent);
            MyToast.show("请先登录");
            return;
        }
        switch (view.getId()) {
            case R.id.rl_user_infor:
                Utilities.startNewActivity(getContext(), UserInfoActivity.class);
                break;
            case R.id.ll_order_form://蜂商城订单
                intent.setClass(getContext(), OrderManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_bill_calendar://账单日历
                intent.setClass(getContext(), BillCalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_bill://资金记录
                intent.setClass(getContext(), FundHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_discount_coupon://蜂宝箱
                Utilities.startNewActivity(getActivity(), FengBoxActivity.class);
                break;
            case R.id.ll_invite_friend://邀请好友
//                String url = "https://www.ftoul.com/appShare";
//                String imgUrl = "https://www.ftoul.com/public/app/images/Guide-1.png";
//                String title = "我在用的理财神器，资金安全，收益稳健，推荐给你！";
//                String contect = "我在用的理财神器，资金安全，收益稳健，推荐给你！来蜂投网，注册即送15888元理财基金10%加息券1张+2枚蜂币，更有新手专享标！第三方资金托管，保障资金安全！";
//                new ShareDialog(getContext(), url, imgUrl, title, contect);
//                intent.setClass(getContext(), WebViewActivity.class);
//                intent.putExtra(WebViewActivity.HTTP_URL,MyUrl.ACCOUNT_CREATE);
//                startActivity(intent);
                break;
            case R.id.ll_risk_test://蜂眸风测
                if (userInfo.getRiskTest() == 0) {
                    Utilities.startNewActivity(getActivity(), RiskTestActivity.class);
                } else {
                    Utilities.startNewActivity(getActivity(), RiskTestResultActivity.class);
                }
                break;
            case R.id.tv_total://所有资产明细
                Utilities.startNewActivity(getActivity(), AssetsShowActivity.class);
                break;
            case R.id.btn_in://充值
                if (!userInfo.isCreateAccount()) {//是否开户
                    intent.setClass(getContext(), HuaXinWebViewActivity.class);
                    intent.putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.ACCOUNT_CREATE);
                    startActivity(intent);
                } else if (!userInfo.isHasBindCard()) {//是否绑卡
                    intent.setClass(getContext(), HuaXinWebViewActivity.class);
                    intent.putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.BIND_CARD);
                    startActivity(intent);
                } else {
                    Utilities.startNewActivity(getActivity(), RechargeActivity.class);
                }
                break;
            case R.id.btn_out://提现
                if (!userInfo.isCreateAccount()) {//是否开户
                    intent.setClass(getContext(), HuaXinWebViewActivity.class);
                    intent.putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.ACCOUNT_CREATE);
                    startActivity(intent);
                } else if (!userInfo.isHasBindCard()) {//是否绑卡
                    intent.setClass(getContext(), HuaXinWebViewActivity.class);
                    intent.putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.BIND_CARD);
                    startActivity(intent);
                } else {
                    Utilities.startNewActivity(getActivity(), WithdrawCashActivity.class);
                }
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (userInfo.isLogin()) {
            if (!(TextUtils.isEmpty(userInfo.getIcon()) || "default".equals(userInfo.getIcon()))) {//设置头像
                Glide.with(this).load(userInfo.getIcon()).transform(new GlideCircleTransform(getContext())).into(ivUserIcon);
            } else {
                ivUserIcon.setImageResource(R.mipmap.toux);
            }
            if (MyApp.pesonalRefresh == false) {//是否重新请求个人中心金额信息
                MyApp.pesonalRefresh = true;
                getData();
            }

            if (TextUtils.isEmpty(userInfo.getNickName()) || "default".equals(userInfo.getNickName())) {//设置昵称
                tvUserName.setText(userInfo.getUserName());
            } else {
                tvUserName.setText(userInfo.getNickName());
            }

        } else {
            tvUserName.setText("登录/注册");
            ivUserIcon.setImageResource(R.mipmap.toux);
            if (adapter != null) {
                adapter.reFresh(null);
            }
            tvAllAsset.setText("0.00");
            tvCanUseAsset.setText("0.00");
        }
    }

    @Override
    public void onDestroyView() {
        MyApp.pesonalRefresh = false;
        adapter =null;
        super.onDestroyView();
    }
}

//    private void initListener() {
//        ViewTreeObserver vto = llBody.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            public boolean onPreDraw() {
//                layoutParams = (LinearLayout.LayoutParams) rlHead.getLayoutParams();
//                int llBodyHeight = llBody.getMeasuredHeight();
//                maxMargns = layoutParams.height + llBodyHeight + DensityUtil.dip2px(getContext(), 50) - MyApp.height;
//                Log.e("onTouch", "llBodyHeight:" + llBodyHeight);
//                Log.e("onTouch", "layoutParams.height:" + layoutParams.height);
//                Log.e("onTouch", "MyApp.height:" + MyApp.height);
//                Log.e("onTouch", "maxMargns:" + maxMargns);
//                return true;
//            }
//        });
//
//        llBody.setMyTouchListener(new MyLinearLayout.MyTouchListener() {
//            @Override
//            public void onTouch(MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        oldY = event.getY();
//                        Log.e("onTouch", "oldY:" + oldY);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        float newY = event.getY();
//                        margns = (int) (margns + oldY - newY);
//                        Log.e("onTouch", "oldY:" + oldY + "newY:" + newY + "margns:" + margns);
//                        if (margns <= maxMargns && margns >= 0) {
//                            layoutParams.setMargins(0, 0, 0, -margns);
//                            rlHead.setLayoutParams(layoutParams);
//                        } else if (margns < 0) {
//                            margns = 0;
//                        } else {
//                            margns = maxMargns;
//                        }
//                        break;
//                }
//            }
//        });
//    }


