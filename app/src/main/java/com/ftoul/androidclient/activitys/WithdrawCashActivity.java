package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.response.GetMoneyInforOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pengtang on 2017/5/31.
 * 提现
 */
public class WithdrawCashActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_canuse)
    TextView tvCanuse;
    @BindView(R.id.tv_notuse)
    TextView tvNotuse;
    @BindView(R.id.et_money)
    EditText etMoney;//提现金额
    @BindView(R.id.btn_out)
    Button btnOut;
    private int canUseMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash);
        ButterKnife.bind(this);

    }

    @Override
    protected void initView() {
        headerTitle.setText("提现");
        etMoney.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1 || s.length() > 11) {
                    btnOut.setEnabled(false);
                } else {
                    btnOut.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.btn_out, R.id.tv_withdraw, R.id.tv_to_eaccount,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_out://提现
                String money = etMoney.getText().toString().trim();
                    double account = Double.parseDouble(money);
                    if (account < 1) {
                        MyToast.show("提现金额不得低于1元");
                        return;
                    }
                    if (account > canUseMoney) {
                        MyToast.show("账户余额不足");
                        return;
                    }
                    startActivity(new Intent(this, HuaXinWebViewActivity.class)
                            .putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.WITH_DRAW)
                            .putExtra(HuaXinWebViewActivity.AMOUNT, Utilities.df.format(account)));
                break;
            case R.id.tv_withdraw://操作指引2
                break;
            case R.id.tv_to_eaccount://操作指引6
                break;
        }
    }

    @Override
    protected void onStart() {
        getData();
        super.onStart();
    }

    private void getData() {
        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_MONEY_INFOR);
        dataBean.setBody(new BaseParamsVO());
        String encryptData = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(encryptData)
                .build()
                .execute(new SmartBaseCallBack<GetMoneyInforOut>(new TypeToken<DataBean<GetMoneyInforOut>>() {
                }) {
                    @Override
                    public void onSuccess(GetMoneyInforOut body) {
                        if (body == null) {
                            return;
                        }
                        GetMoneyInforOut.AssetsDetailBean bean = body.assetsDetail;
                        if (bean == null) {
                            return;
                        }

                        tvNotuse.setText(bean.frozbl);//冻结资金
                        if (TextUtils.isEmpty(bean.availablebal)) {
                            tvCanuse.setText("0.00");//可用余额
                            return;
                        }
                        tvCanuse.setText(bean.availablebal);//可用余额
                        canUseMoney = (int) Float.parseFloat(bean.availablebal);
                    }
                });
    }
}
