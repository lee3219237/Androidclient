package com.ftoul.androidclient.fragments.risktest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.RiskTestActivity;
import com.ftoul.androidclient.activitys.RiskTestResultActivity;
import com.ftoul.androidclient.base.BaseFragment;
import com.ftoul.androidclient.bean.request.RiskTestResultCodeIn;
import com.ftoul.androidclient.bean.response.RiskTestCodeOut;
import com.ftoul.androidclient.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ftoul106 on 2017/4/26 0026.
 */

public class RiskTestFragment extends BaseFragment {
    public static final String ARGS_PAGE = "args_page";
    @BindView(R.id.tv_question_title)
    TextView tvQuestionTitle;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private int mPage;//代表题目的索引
    private String[] questionScores;//存放分值的容器
    private RiskTestActivity activity;
    private List<String> questions;//存放题目的容器
    private RiskTestCodeOut.QuestionInfosBean bean;


    public static RiskTestFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        RiskTestFragment fragment = new RiskTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_risk_test, container, false);
        mPage = getArguments().getInt(ARGS_PAGE);
        return view;
    }

    @Override
    protected void initData() {
        questions=new ArrayList<>();//存放题目的容器
        questionScores = new String[5];//存放分值的容器
        activity = (RiskTestActivity) getActivity();
        bean = activity.getQuestionInfos().get(mPage);
        tvQuestionTitle.setText((mPage+1)+"、"+ bean.getTitle());
        if(!TextUtils.isEmpty(bean.getResulta())){
            questions.add(bean.getResulta());
            questionScores[0] = bean.scopea+"";
        }
        if(!TextUtils.isEmpty(bean.getResultb())){
            questions.add(bean.getResultb());
            questionScores[1] = bean.scopeb+"";
        }
        if(!TextUtils.isEmpty(bean.getResultc())){
            questions.add(bean.getResultc());
            questionScores[2] = bean.scopec+"";
        }
        if(!TextUtils.isEmpty(bean.getResultd())){
            questions.add(bean.getResultd());
            questionScores[3] = bean.scoped+"";
        }
        if(!TextUtils.isEmpty(bean.getResulte())){
            questions.add(bean.getResulte());
            questionScores[4] = bean.scopee+"";
        }
        for (int i = 0; i < questions.size(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setText(questions.get(i));
            radioButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4, R.id.rb_5})
    public void onViewClicked(View view) {
        RiskTestResultCodeIn.AnwserBean score = new RiskTestResultCodeIn.AnwserBean();
        score.setSubjectId(bean.getId()+"");
        switch (view.getId()) {
            case R.id.rb_1:
                score.setResult("A");
                score.setScope(questionScores[0]);
                next(score);
                break;
            case R.id.rb_2:
                score.setResult("B");
                score.setScope(questionScores[1]);
                next(score);
                break;
            case R.id.rb_3:
                score.setResult("C");
                score.setScope(questionScores[2]);
                next(score);
                break;
            case R.id.rb_4:
                score.setResult("D");
                score.setScope(questionScores[3]);
                next(score);
                break;
            case R.id.rb_5:
                score.setResult("E");
                score.setScope(questionScores[4]);
                next(score);
                break;
        }
    }

    private void next(RiskTestResultCodeIn.AnwserBean score) {
        if (mPage == activity.scores.length-1) {//当到最后一题目时
            activity.scores[activity.scores.length-1] = score;
            for (int i = 0; i < activity.scores.length; i++) {//检查是否有题目未选择
                if(activity.scores[i]==null){//存在题目未选择
                    MyToast.show("第"+(i+1)+"题未选择");
                    return;
                }
            }
            activity.startActivity(new Intent(activity, RiskTestResultActivity.class)
                .putExtra(RiskTestResultActivity.SCORE,activity.scores));
            activity.finish();
        } else {
            activity.next(score);
        }
    }


}
