package com.ftoul.androidclient.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.adapter.viewpager.MainPagerAdapter;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.RiskTestResultCodeIn;
import com.ftoul.androidclient.bean.response.HuaXinAccountOut;
import com.ftoul.androidclient.bean.response.RiskTestResultCodeOut;
import com.ftoul.androidclient.fragments.InvestFragment;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.SpType;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.NoScrollViewPager;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;


public class MainActivity extends BaseActivity {

    private static Boolean isExit = false;//双击退出状态
    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    @BindView(R.id.rb_investment)
    RadioButton rbInvestment;
    @BindView(R.id.rb_main)
    RadioButton rbMain;
    @BindView(R.id.rb_discover)
    RadioButton rbDiscover;
    @BindView(R.id.rb_personal)
    RadioButton rbPersonal;
    private boolean tag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.mainActivity = this;
    }

    public interface OnMainFragmentListener {
        void onStart();

        void onStop();
    }


    private OnMainFragmentListener onMainFragmentListener;

    public void setOnMainFragmentListener(OnMainFragmentListener onMainFragmentListener) {
        this.onMainFragmentListener = onMainFragmentListener;
    }

    @OnClick({R.id.rb_main, R.id.rb_investment, R.id.rb_discover, R.id.rb_personal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_main:
                if (onMainFragmentListener != null && tag == false) {
                    onMainFragmentListener.onStart();
                    tag = true;
                }
                vpMain.setCurrentItem(0, false);
                break;
            case R.id.rb_investment:
                if (onMainFragmentListener != null) {
                    onMainFragmentListener.onStop();
                    tag = false;
                }

                vpMain.setCurrentItem(1, false);
                break;
            case R.id.rb_discover:
                if (onMainFragmentListener != null) {
                    onMainFragmentListener.onStop();
                    tag = false;
                }
                vpMain.setCurrentItem(2, false);
                break;
            case R.id.rb_personal:
                if (onMainFragmentListener != null) {
                    onMainFragmentListener.onStop();
                    tag = false;
                }
                if (!userInfo.isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    MyToast.show("请先登录");
                }
                vpMain.setCurrentItem(3, false);
                break;
        }
    }


    @Override
    protected void initView() {
        //setViewPagerHeight();
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        vpMain.setAdapter(adapter);
        vpMain.setCurrentItem(0);
        if (userInfo.isLogin() && userInfo.getRiskTest() == 0) {
            aleartDialog();//弹出风险测评对话框
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (userInfo.getAppType() == 1) {
            finish();
            return;
        }
        if (userInfo.isLogin() && userInfo.getRiskTest() == 0) {
            aleartDialog();//弹出风险测评对话框
        }
    }

    /**
     * 风险测评dialog
     */
    private void aleartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = View.inflate(this, R.layout.dialog_item_risk_test, null);
        View startView = inflate.findViewById(R.id.btn_start_test);
        View notTestView = inflate.findViewById(R.id.tv_not_test);
        builder.setView(inflate);
        final AlertDialog mDialog = builder.show();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        startView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                startActivity(new Intent(MainActivity.this, RiskTestActivity.class));
            }
        });
        notTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo.setRiskTest(1);
                commitScore();//提交分数
                mDialog.dismiss();
            }
        });
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }



    /**
     * 跳转投资页面并且切换到新手标
     */
    public void setInvestment() {
        vpMain.setCurrentItem(1, false);
        rbInvestment.setChecked(true);
//        bottomBarVisible(true);
//        mBottomBar.selectTabAtPosition(1, true);
        List list = getSupportFragmentManager().getFragments();
        InvestFragment fragment = (InvestFragment) list.get(1);
        fragment.setRegular(0);
    }

    /**
     * 跳转投资页面并且切换到定期页面
     */
    public void setInvestRegular() {
        vpMain.setCurrentItem(1, false);
        rbInvestment.setChecked(true);
//        bottomBarVisible(true);
//        mBottomBar.selectTabAtPosition(1, true);
        List list = getSupportFragmentManager().getFragments();
        InvestFragment fragment = (InvestFragment) list.get(1);
        fragment.setRegular(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // mBottomBar.onSaveInstanceState(outState);
    }

    public void bottomBarVisible(boolean visible) {
//        if (visible) {
//            mBottomBar.show();
//        } else {
//            mBottomBar.hide();
//        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 30;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }




    private void commitScore() {
        String datas = Utilities.toJsonString2(new RiskTestResultCodeIn(new ArrayList<RiskTestResultCodeIn.AnwserBean>()));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/saveRiskInfo")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<RiskTestResultCodeOut>(new TypeToken<DataBean<RiskTestResultCodeOut>>() {
                }) {
                    @Override
                    public void onSuccess(RiskTestResultCodeOut riskTestResultCodeOut) {
                        userInfo.setRiskTest(1);
                    }
                });
    }


    @Override
    protected void onStart() {
        if (userInfo.isLogin()) {
            if (MyApp.huaXinAccountRefresh == false) {//获取用户开户绑卡信息
                MyApp.huaXinAccountRefresh = true;
                DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_HUA_XIN_ACCOUNT_INFO);
                dataBean.setBody(new BaseParamsVO());
                String datas = Utilities.toJsonString(dataBean);
                OkHttpUtils
                        .postString()
                        .tag(this)
                        .url(MyUrl.SERVICE_MAIN)
                        .content(datas)
                        .build()
                        .execute(new SmartBaseCallBack<HuaXinAccountOut>(new TypeToken<DataBean<HuaXinAccountOut>>() {
                        }) {
                            @Override
                            public void onSuccess(HuaXinAccountOut body) {
                                if (body == null || body.getStatus() == 0) {//是否开户
                                    userInfo.setCreateAccount(false);
                                } else {
                                    userInfo.setCreateAccount(true);
                                    if (body.getHxAccount() == null || body.getHxAccount().getIsOncard() == 0) {//是否绑卡
                                        userInfo.setHasBindCard(false);
                                    } else {
                                        userInfo.setHasBindCard(true);
                                    }
                                }
                            }

                            @Override
                            protected void onOther(String errMsg, String errCode) {

                            }
                        });
            }
        }
        if (onMainFragmentListener != null) {
            onMainFragmentListener.onStart();
            tag = true;
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (onMainFragmentListener != null) {
            onMainFragmentListener.onStop();
            tag = false;
        }
        super.onStop();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }
}
