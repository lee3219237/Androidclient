package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SafeManagerActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_update_lock)
    TextView tvUpdateLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_manager);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (TextUtils.isEmpty(userInfo.getLock())) {
            tvUpdateLock.setText("开启手势登录");
        }else{
            tvUpdateLock.setText("关闭手势登录");
        }
    }

    @Override
    protected void initView() {
        headerTitle.setText("密码管理");

    }

    @OnClick({R.id.ll_update_pwd, R.id.ll_update_lock})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_update_pwd:
                startActivity(new Intent(this, UpdatePwdActivity.class));
                break;
            case R.id.ll_update_lock:
                if (userInfo.isLogin()) {
                    if (TextUtils.isEmpty(userInfo.getLock())) {
                        startActivity(new Intent(this, LockActivity.class));
                    } else {
                        tvUpdateLock.setText("开启手势登录");
                        userInfo.setLock("");
                    }
                }
                break;
        }
    }
}
