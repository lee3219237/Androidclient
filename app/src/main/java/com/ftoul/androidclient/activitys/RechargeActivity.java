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
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pengtang on 2017/5/31.
 * 充值
 */
public class RechargeActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.et_money)
    EditText etMoney;//输入金额
    @BindView(R.id.btn_pay)
    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("充值");
        etMoney.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1 || s.length() > 11) {
                    btnPay.setEnabled(false);
                } else {
                    btnPay.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.btn_pay, R.id.tv_to_eaccount, R.id.tv_to_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pay://充值
                String money = etMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    MyToast.show("充值金额不能为0");
                } else {
                    double account = Double.parseDouble(money);
                    if (account < 1) {
                        MyToast.show("充值金额不能低于1元");
                    } else {
                        startActivity(new Intent(this, HuaXinWebViewActivity.class)
                                .putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.DOCHARGE)
                                .putExtra(HuaXinWebViewActivity.AMOUNT, Utilities.df.format(account)));
                    }
                }
                break;
            case R.id.tv_to_eaccount://E账户充值图文指引
                break;
            case R.id.tv_to_card://银行卡图文指引
                break;
        }
    }
}
