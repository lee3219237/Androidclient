package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseUserServiceActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.ExperienceBidCodeIn;
import com.ftoul.androidclient.bean.request.GetTiYanMoneyIn;
import com.ftoul.androidclient.bean.response.GetNewUserWelfareOut;
import com.ftoul.androidclient.bean.response.GetTiYanMoneyOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 理财体验标
 */
public class ExperienceBidActivity extends BaseUserServiceActivity {
    /**
     * 标题
     */
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    /**
     * 还款方式
     */
    @BindView(R.id.txt_experience_repayment_methods)
    TextView txtExperienceRepaymentMethods;
    /**
     * 预期收益
     */
    @BindView(R.id.txt_experience_money)
    TextView txtExperienceMoney;

    /**
     * 当前体验券
     */
    @BindView(R.id.txt_experience_coupon)
    TextView txtExperienceCoupon;
    /**
     * 项目介绍
     */
    @BindView(R.id.txt_experience_introduce)
    TextView txtExperienceIntroduce;

    public static final String LCTY_BID_BEAN = "LctyBidBean";//产品数据
    @BindView(R.id.tv_range)
    TextView tvRange;//利率
    @BindView(R.id.tv_period)
    TextView tvPeriod;//周期
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private GetNewUserWelfareOut.LctyBidBean bidBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_bid);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        bidBean = (GetNewUserWelfareOut.LctyBidBean) intent.getSerializableExtra(LCTY_BID_BEAN);
        headerTitle.setText("理财体验标");
        if(bidBean==null){
            return;
        }
        String range = Utilities.df.format(bidBean.rate) + "%";
        tvRange.setText(Utilities.getDividerStr(range, ".", 65, 30));
        tvPeriod.setText("投资当天开始计息，" + bidBean.period + "天后收益到账，零成本体验投资乐趣");
        getData();
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        commit(bidBean.id + "");
    }

    private void commit(String bidId) {
        showDialog("正在提交中");
        DataBean<ExperienceBidCodeIn> dataBean = new DataBean<>(TransCode.GET_EXPERIENCE_BID);
        dataBean.setBody(new ExperienceBidCodeIn(bidId));
        String datas = Utilities.toJsonString(dataBean);
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
                        MyToast.show(body.toString());
                    }
                });
    }

    public void getData() {
        DataBean<GetTiYanMoneyIn> dataBean = new DataBean<>(TransCode.GET_TI_YAN_JIN_INFOR);
        dataBean.setBody(new GetTiYanMoneyIn(1 + "", +30 + ""));
        Gson gson = new Gson();
        String datas = gson.toJson(dataBean);
        Log.e(datas);
        String encryptData = DataUtils.encry(datas);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(encryptData)
                .build()
                .execute(new SmartBaseCallBack<GetTiYanMoneyOut>(new TypeToken<DataBean<GetTiYanMoneyOut>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(GetTiYanMoneyOut body) {
                        int size = 0;
                        if (body.page.items != null) {
                            for (GetTiYanMoneyOut.PageBean.ItemsBean bean : body.page.items) {
                                if (bean.expired == 0 && bean.status == 0) {//体验卷未使用，未过期
                                    size++;
                                }
                            }
                        }
                        txtExperienceCoupon.setText(Utilities.df.format(body.money) + "/" + size + "张");
                        //利息收益   = 投资金额*年利率*天数/365
                        double income = body.money * (bidBean.rate / 100) * bidBean.period / 365;
                        txtExperienceMoney.setText(Utilities.df.format(income) + "元");
                        if (body.money == 0) {
                            btnCommit.setEnabled(false);
                        }
                    }
                });
    }
}
