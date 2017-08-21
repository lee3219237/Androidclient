package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tp on 2017/5/20.
 * 服务协议
 */
public class ServiceProtrolActivity extends BaseActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_protrol);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        rlHeaderLeft.setVisibility(View.VISIBLE);
        headerTitle.setText("授权投资服务协议");
    }

    @OnClick(R.id.rl_headerLeft)
    public void onViewClicked() {
        ServiceProtrolActivity.this.finish();
    }
}
