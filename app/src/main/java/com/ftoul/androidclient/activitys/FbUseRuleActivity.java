package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pengtang on 2017/6/2.
 * 蜂宝箱——蜂币使用规则
 */
public class FbUseRuleActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_use_rule);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("蜂币使用规则");
    }
}
