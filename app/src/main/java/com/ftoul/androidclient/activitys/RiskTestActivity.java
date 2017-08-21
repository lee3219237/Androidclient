package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.RiskTestAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.RiskTestResultCodeIn;
import com.ftoul.androidclient.bean.response.RiskTestCodeOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

public class RiskTestActivity extends BaseTitleActivity {


    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_process)
    TextView tvProcess;
    private int length = 13;
    public RiskTestResultCodeIn.AnwserBean[] scores;//用戶打分值容器
    private List<RiskTestCodeOut.QuestionInfosBean> questionInfos;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_test);

    }

    private void getData() {
//        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_TEST_QUESTION);
//        dataBean.setBody(new BaseParamsVO());
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new BaseParamsVO());
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/getRiskInfo")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<RiskTestCodeOut>(new TypeToken<DataBean<RiskTestCodeOut>>() {
                }) {

                    @Override
                    public void onSuccess(RiskTestCodeOut riskTestCodeOut) {
                        length = riskTestCodeOut.riskList.size();
                        progressBar.setMax(riskTestCodeOut.riskList.size());
                        tvProcess.setText(1 + "/" + length);
                        setQuestionInfos(riskTestCodeOut.riskList);
                        RiskTestAdapter adapter = new RiskTestAdapter(getSupportFragmentManager(), length);
                        scores = new RiskTestResultCodeIn.AnwserBean[length];
                        viewPager.setAdapter(adapter);
                    }
                });
    }

    @Override
    protected void initView() {
        headerTitle.setText("蜂眸风测");
        progressBar.setProgress(1);
        tvProcess.setText(1 + "/" + length);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progressBar.setProgress(position + 1);
                tvProcess.setText(position + 1 + "/" + length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getData();
    }

    public List<RiskTestCodeOut.QuestionInfosBean> getQuestionInfos() {
        return questionInfos;
    }

    public void setQuestionInfos(List<RiskTestCodeOut.QuestionInfosBean> questionInfos) {
        this.questionInfos = questionInfos;
    }

    public void next(RiskTestResultCodeIn.AnwserBean score) {
        scores[viewPager.getCurrentItem()] = score;
        viewPager.setCurrentItem(1 + viewPager.getCurrentItem());
    }

}
