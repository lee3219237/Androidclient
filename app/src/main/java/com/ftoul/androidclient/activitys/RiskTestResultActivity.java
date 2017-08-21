package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.RiskTestResultCodeIn;
import com.ftoul.androidclient.bean.response.RiskTestResultCodeOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

public class RiskTestResultActivity extends BaseTitleActivity {
    public static final String SCORE = "score";
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_img_title)
    TextView tvImgTitle;
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_test_result);
    }

    @OnClick({R.id.btn_go_invest, R.id.btn_restart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_go_invest:
                if (MyApp.mainActivity != null) {
                    MyApp.mainActivity.setInvestRegular();
                }
                finish();
                break;
            case R.id.btn_restart:
                startActivity(new Intent(this, RiskTestActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        headerTitle.setText("风测结果");
        Parcelable[] scores = getIntent().getParcelableArrayExtra(SCORE);
        if (scores != null) {
            // Log.e("scores.length:"+scores.length);
            RiskTestResultCodeIn.AnwserBean[] scores2 = new RiskTestResultCodeIn.AnwserBean[scores.length];
            for (int i = 0; i < scores.length; i++) {
                scores2[i] = (RiskTestResultCodeIn.AnwserBean) scores[i];
            }
            commitScore(scores2);
        } else {
            Log.e("getResult");
            getResult();
        }
    }

    /**
     * 提交蜂测分数
     */
    private void commitScore(RiskTestResultCodeIn.AnwserBean[] scores) {
        List<RiskTestResultCodeIn.AnwserBean> list = Arrays.asList(scores);
//        DataBean<RiskTestResultCodeIn> dataBean = new DataBean<>(TransCode.COMMIT_TEST);
//        dataBean.setBody(new RiskTestResultCodeIn(score));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new RiskTestResultCodeIn(list));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/saveRiskInfo")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<RiskTestResultCodeOut>(new TypeToken<DataBean<RiskTestResultCodeOut>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(RiskTestResultCodeOut riskTestResultCodeOut) {
                        tvContent.setText(Html.fromHtml(riskTestResultCodeOut.getPreview().getRemark()));
                        tvContentTitle.setText(riskTestResultCodeOut.getPreview().getTestResult());
                        tvImgTitle.setText(riskTestResultCodeOut.getPreview().getTestResult());
                        userInfo.setRiskTest(1);
                    }
                });
    }

    /**
     * 获取风测结果
     */
    private void getResult() {
//        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_TEST_RESULT);
//        dataBean.setBody(new BaseParamsVO());
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new BaseParamsVO());
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/getRiskPreview")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<RiskTestResultCodeOut>(new TypeToken<DataBean<RiskTestResultCodeOut>>() {
                }, myFramelayout) {
                    @Override
                    public void onSuccess(RiskTestResultCodeOut riskTestResultCodeOut) {
                        tvContent.setText(Html.fromHtml(riskTestResultCodeOut.getPreview().getRemark()));
                        tvContentTitle.setText(riskTestResultCodeOut.getPreview().getTestResult());
                        tvImgTitle.setText(riskTestResultCodeOut.getPreview().getTestResult());
                    }
                });
    }
}
