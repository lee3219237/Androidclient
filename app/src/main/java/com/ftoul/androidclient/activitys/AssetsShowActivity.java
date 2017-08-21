package com.ftoul.androidclient.activitys;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.ImageCodeIn;
import com.ftoul.androidclient.bean.response.GetMoneyInforOut;
import com.ftoul.androidclient.bean.response.ImageCodeOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by pengtang on 2017/5/24.
 * 资产总览
 */
public class AssetsShowActivity extends BaseTitleActivity {

    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;
    @BindView(R.id.headerTitle)
    TextView headerTitle;

    @BindView(R.id.txt_asset)
    TextView txtAsset;//资产总额
    /**
     * 待收本金
     */
    @BindView(R.id.txt_wait_original)
    TextView txtWaitOriginal;//待收本金

    @BindView(R.id.txt_wait_income)
    TextView txtWaitIncome;//待收收益

    @BindView(R.id.txt_use_balance)
    TextView txtUseBalance;//可用余额

    @BindView(R.id.txt_not_use_balance)
    TextView txtNotUseBalance;//冻结资金

    @BindView(R.id.txt_has_income)
    TextView txtHasIncome;//已收利息

    @BindView(R.id.txt_wait_income_lx)
    TextView getTxtWaitIncomeLx;//待收利息

    @BindView(R.id.txt_fm_income)
    TextView txtFmIncome;//蜂蜜券收益

    @BindView(R.id.txt_jx_income)
    TextView txtJxIncome;//加息收益

    @BindView(R.id.txt_ty_income)
    TextView txtTyIncome;//体验金收益

    @BindView(R.id.txt_yq_income)
    TextView txtYqIncome;//邀请收益

    @BindView(R.id.txt_register_info)
    TextView txtRegisterInfo;//时间注册

    @BindView(R.id.txt_register_time)
    TextView txtRegisterTime;//天数

    @BindView(R.id.txt_total_pay)
    TextView txtTotalPay;//总充值

    @BindView(R.id.txt_total_assets)
    TextView txtTotalAssets;//总投资

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_show);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("资产总览");
        rlHeaderLeft.setVisibility(View.VISIBLE);
        getData();
    }

    private void getData() {
//        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_MONEY_INFOR);
//        dataBean.setBody(new BaseParamsVO());
//        String encryptData = Utilities.toJsonString(dataBean);
       String encryptData = Utilities.toJsonString2(new BaseParamsVO());
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/account/userAssets")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
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
                        txtAsset.setText(bean.totalAssets);//资产总额
                        txtWaitOriginal.setText(bean.dueInCorpus);//待收本金
                        txtWaitIncome.setText(bean.dueInIncomes);//待收收益
                        txtUseBalance.setText(bean.availablebal);//可用余额
                        txtNotUseBalance.setText(bean.frozbl);//冻结资金
                        txtHasIncome.setText(bean.receivedInterest);//已收利息
                        getTxtWaitIncomeLx.setText(bean.dueInInterest);//待收利息
                        txtFmIncome.setText(bean.redpacketIncomes);//蜂蜜券收益
                        txtJxIncome.setText(bean.interestRateIncomes);//加息收益
                        txtTyIncome.setText(bean.fundIncomes);//体验金收益
                        txtYqIncome.setText(bean.inviteIncomes);//邀请收益
                        txtRegisterInfo.setText(bean.registerDate+"注册, 已加入蜂投网");//时间注册
                        txtRegisterTime.setText(bean.joinInDays);//天数
                        txtTotalPay.setText(bean.cumulativeRecharge);//总充值
                        txtTotalAssets.setText(bean.cumulativeInvest);//总投资
                    }
                });
    }

}
