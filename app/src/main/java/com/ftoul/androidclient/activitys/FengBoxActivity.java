package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetReturnMoneyPlanIn;
import com.ftoul.androidclient.bean.response.GetFengBoxItemOut;
import com.ftoul.androidclient.bean.response.GetReturnMoneyPlanOut;
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

/**
 * Created by pengtang on 2017/5/31.
 * 蜂宝箱
 */
public class FengBoxActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_fm)
    TextView tvFm;//蜂蜜券
    @BindView(R.id.tv_jx)
    TextView tvJx;//加息券
    @BindView(R.id.tv_ty)
    TextView tvTy;//体验金
    @BindView(R.id.tv_fb)
    TextView tvFb;//蜂币

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feng_box);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("蜂宝箱");
        getData();
    }

    private void getData() {
        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_FENG_BOX_ITEM);
        dataBean.setBody(new BaseParamsVO());
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<GetFengBoxItemOut>(new TypeToken<DataBean<GetFengBoxItemOut>>() {
                }) {
                    @Override
                    public void onSuccess(GetFengBoxItemOut body) {
                        tvFm.setText(body.userRedpacket + " 元");
                        tvFb.setText(body.coins + " 枚");
                        tvTy.setText(body.fundSource + " 元");
                        tvJx.setText(body.userInterestRate + " 张");
                    }
                });
    }

    @OnClick({R.id.rl_fm, R.id.rl_jx, R.id.rl_hk, R.id.rl_xj, R.id.rl_ty, R.id.rl_fb, R.id.rl_db})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_fm://蜂蜜券
                Utilities.startNewActivity(FengBoxActivity.this,FengMiCouponsActivity.class);
                break;
            case R.id.rl_jx://加息券
                Utilities.startNewActivity(FengBoxActivity.this,JiaxiCouponsActivity.class);
                break;
            case R.id.rl_hk://回款赠券
                Utilities.startNewActivity(this, PaymentCouponActivity.class);
                break;
            case R.id.rl_xj://现金券
                Utilities.startNewActivity(FengBoxActivity.this,CashCouponsActivity.class);
                break;
            case R.id.rl_ty://体验金
                Utilities.startNewActivity(this, TiyanMoneyActivity.class);
                break;
            case R.id.rl_fb://蜂币
                Utilities.startNewActivity(FengBoxActivity.this,FengCoinActivity.class);
                break;
            case R.id.rl_db://夺宝物品
                Utilities.startNewActivity(FengBoxActivity.this,DbaoListActivity.class);
                break;
        }
    }
}
