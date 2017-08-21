package com.ftoul.androidclient.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.BidDetailActivity;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.adapter.list.CoupusAdapter;
import com.ftoul.androidclient.base.BaseUserServiceActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.BidInfosBean;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;
import com.ftoul.androidclient.bean.request.InvestCommitIn;
import com.ftoul.androidclient.bean.response.HuaXinAccountOut;
import com.ftoul.androidclient.bean.response.PersonalAssetsCodeOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pengtang on 2017/5/19.
 * 立即投资
 */

public class InvestmentNowActivity extends BaseUserServiceActivity {
    @BindView(R.id.txt_invest_count_show)
    TextView txtInvestCountShow;
    private NewBidInfosBean bidInfosBean;
    /**
     * 标题
     */
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    /**
     * 可投金额
     */
    @BindView(R.id.txt_can_invest_money)
    TextView txtCanInvestMoney;
    /**
     * 可用金额
     */
    @BindView(R.id.txt_account_money)
    TextView txtAccountMoney;
    /**
     * 输入金额
     */
    @BindView(R.id.et_input_money)
    EditText etInputMoney;
    /**
     * 投资卷金额
     */
    @BindView(R.id.txt_invest_coupon)
    TextView txtInvestCoupon;
    /**
     * 加息劵比例
     */
    @BindView(R.id.txt_xi_coupon)
    TextView txtXiCoupon;
    /**
     * 冻结金额
     */
    @BindView(R.id.txt_not_use_money)
    TextView txtNotUseMoney;
    /**
     * 计息金额
     */
    @BindView(R.id.txt_xi_money)
    TextView txtXiMoney;
    /**
     * 预计收益
     */
    @BindView(R.id.txt_get_money)
    TextView txtGetMoney;

    @BindView(R.id.btn_next)
    Button btnNext;
    /**
     * 劵相关
     */
    private List<String> data;
    private List<Integer> checkValues;
    private CoupusAdapter coupusAdapter;
    public static final int MAX_COUNT = 2;//最多选2张
    public static final int CONPUS_TYPE_INVEST = 100;//投资劵
    public static final int CONPUS_TYPE_XI = 101;//加息劵
    private double canInvestMoney;//剩余可投金额
    private double apr;//利率
    private int bidID;
    private int period;//周期，天数
    private double money;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_now);
    }

    @Override
    protected void initView() {
        headerTitle.setText("立即投资");
        Intent intent = getIntent();
        bidInfosBean = (NewBidInfosBean) intent.getSerializableExtra(BidDetailActivity.BID_INFOR_BEAN);
        if (bidInfosBean == null) {
            return;
        }
        headerTitle.setText(bidInfosBean.getTitle());
        if (bidInfosBean.getPeriodType() == 0) {
            period = bidInfosBean.getPeriod();
        } else {
            period = bidInfosBean.getPeriod() * 30;
        }
        if (bidInfosBean.getMinInvestAmount() != 0) {
            txtInvestCountShow.setText(" 投资金额(" + (int) bidInfosBean.getMinInvestAmount() + "元起投)");
        }
        apr = bidInfosBean.getApr();
        bidID = bidInfosBean.getBidId();
        canInvestMoney = bidInfosBean.getOverPlus();
        txtCanInvestMoney.setText(Utilities.df.format(canInvestMoney));
        txtAccountMoney.setText(Utilities.df.format(userInfo.getCanUseMoney()));
        etInputMoney.addTextChangedListener(new MyTextWatcher() {//输入金额
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tempStr = s.toString();
                if (!TextUtils.isEmpty(tempStr) && Double.parseDouble(tempStr) >= 1) {
                    money = Double.parseDouble(tempStr);//输入金额
                    if (money > userInfo.getCanUseMoney()) {
                        money = (long) userInfo.getCanUseMoney();
                        etInputMoney.setText(String.valueOf(money));
                        MyToast.show("账户余额不足，请先充值");
                    }
                    if (money > canInvestMoney) {
                        money = (long) canInvestMoney;
                        etInputMoney.setText(String.valueOf(money));
                        MyToast.show("投资金额超过剩余可投金额");
                    }
                    if (bidInfosBean.maxInvestAmount != 0 && money > bidInfosBean.maxInvestAmount) {
                        money = (long) bidInfosBean.maxInvestAmount;
                        etInputMoney.setText(String.valueOf(money));
                        MyToast.show("项目最大可投金额为" + money + "元");
                    }

                    txtNotUseMoney.setText(money + " 元");
                    txtXiMoney.setText(money + " 元");
                    //利息收益   = 投资金额*年利率*天数/365
                    double income = money * (apr / 100) * period / 365;
                    txtGetMoney.setText(Utilities.df.format(income) + " 元");
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setEnabled(false);
                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();//获取账号可用余额接口
    }

    private void getData() {
        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_MAIN_MONEY);
        dataBean.setBody(new BaseParamsVO());
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<PersonalAssetsCodeOut>(new TypeToken<DataBean<PersonalAssetsCodeOut>>() {
                }) {
                    @Override
                    public void onSuccess(PersonalAssetsCodeOut personalAssetsCodeOut) {
                        userInfo.setCanUseMoney(personalAssetsCodeOut.getAvailablebal());
                        txtAccountMoney.setText(Utilities.df.format(userInfo.getCanUseMoney()));
                    }
                });
    }

    private void loadData() {
        coupusAdapter = new CoupusAdapter(mContext);
        data = new ArrayList<>();
        checkValues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            data.add("test");
            checkValues.add(0);
        }
        coupusAdapter.setCheckValuesList(checkValues);
        coupusAdapter.setData(data);
        coupusAdapter.setmOnSelectListener(new CoupusAdapter.onSelectListener() {
            @Override
            public void onSelectd(View v, int pst) {
                if (0 == checkValues.get(pst)) {//未被选中
                    if (getSelectedItemCount() < MAX_COUNT) {
                        coupusAdapter.getCheckValuesList().set(pst, 1);
                    } else {
                        MyToast.show("最多只能使用" + MAX_COUNT + "张");
                    }
                } else//已选中
                {
                    coupusAdapter.getCheckValuesList().set(pst, 0);
                }
                coupusAdapter.refreshUI();
            }
        });
    }

    /**
     * 获取选中条目数
     *
     * @return
     */
    private int getSelectedItemCount() {
        int temp = 0;
        for (int i = 0; i < coupusAdapter.getCheckValuesList().size(); i++) {
            if (1 == coupusAdapter.getCheckValuesList().get(i)) {
                temp++;
            }
        }
        return temp;
    }

    /**
     * 底部弹出劵选择框
     *
     * @param context
     */
    private void popupChooseWindow(final Context context, int Type) {
        final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        View view = LayoutInflater.from(context).inflate(
                R.layout.pop_bottom_choose_coupus, null);
        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
        TextView txtLimit = (TextView) view.findViewById(R.id.txt_limit);
        LinearLayout llContain = (LinearLayout) view.findViewById(R.id.llcontainer);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llContain.getLayoutParams();
        layoutParams.height = (display.getHeight() / 5) * 2;
        view.setLayoutParams(layoutParams);
        view.setMinimumWidth(display.getWidth());
        /*
        * 填充数据
        * */
        if (CONPUS_TYPE_INVEST == Type) {
            txtTitle.setText("蜂蜜劵");
            txtLimit.setText("可用投资劵 (" + MAX_COUNT + ")");
        } else {
            txtTitle.setText("加息劵");
            txtLimit.setText("可用加息劵 (" + MAX_COUNT + ")");
        }
        loadData();
        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(coupusAdapter);
        view.findViewById(R.id.ivDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    @OnClick({R.id.rl_headerRight, R.id.btn_pay, R.id.rl_invest_coupon, R.id.rl_xi_coupon, R.id.rl_row_protol, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pay://充值
                if (!userInfo.isCreateAccount()) {//是否开户
                    startActivity(new Intent(this, HuaXinWebViewActivity.class)
                            .putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.ACCOUNT_CREATE));
                } else if (!userInfo.isHasBindCard()) {//是否绑卡
                    startActivity(new Intent(this, HuaXinWebViewActivity.class)
                            .putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.BIND_CARD));
                } else {
                    Utilities.startNewActivity(InvestmentNowActivity.this, RechargeActivity.class);
                }
                break;
            case R.id.rl_invest_coupon://投资劵
                popupChooseWindow(mContext, CONPUS_TYPE_INVEST);
                break;
            case R.id.rl_xi_coupon://加息劵
                popupChooseWindow(mContext, CONPUS_TYPE_XI);
                break;
            case R.id.rl_row_protol://协议
                startActivity(new Intent(this, SimpleWebActivity.class)
                        .putExtra(SimpleWebActivity.WEB_URL, MyUrl.BID_AGREEMENT));
                break;
            case R.id.btn_next://投资
                //   if (verify()) {
                if (money < bidInfosBean.getMinInvestAmount()) {
                    MyToast.show("项目最小投资额为" + (int) bidInfosBean.getMinInvestAmount() + "元");
                    return;
                }
                startActivity(new Intent(this, HuaXinWebViewActivity.class)
                        .putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.INVEST)
                        .putExtra(HuaXinWebViewActivity.BID_ID, bidID)
                        .putExtra(HuaXinWebViewActivity.AMOUNT, Utilities.df.format(money)));

                // invest();//开始投资
                //    }
                MyToast.show("投资");
                break;
        }
    }

    private boolean verify() {
        if (money > userInfo.getCanUseMoney()) {
            MyToast.show("账户金额不足，请先充值");
            return false;
        }
        if (money > canInvestMoney) {
            MyToast.show("投资金额超过剩余可投金额");
            return false;
        }
        return true;
    }

    private void invest() {
        showDialog("正在投资中");
        DataBean<InvestCommitIn> dataBean = new DataBean<>(TransCode.COMMIT_INVEST);
        dataBean.setBody(new InvestCommitIn(money, bidID + ""));
        Gson gson = new Gson();
        String datas = gson.toJson(dataBean);
        Log.e(datas);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(new TypeToken<DataBean<Object>>() {
                }, pDialog) {
                    @Override
                    public void onSuccess(Object body) {
                        MyApp.pesonalRefresh = false;//个人中心刷新
                        MyApp.newUserInvestRefresh = false;//新手福利页刷新
                        MyApp.regularInvestRefresh = false;//定期投资页刷新
                        startActivity(new Intent(InvestmentNowActivity.this, MainActivity.class));
                    }
                });
    }

    @Override
    protected void onRestart() {
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
        super.onRestart();
    }
}
