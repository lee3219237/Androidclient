package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuiFuWebViewActivity;
import com.ftoul.androidclient.base.BaseActivity;

import butterknife.OnClick;

public class ChangeVersionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_version);

    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.ll_hua_xin, R.id.ll_hui_fu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_hua_xin:
                startActivity(new Intent(this, MainActivity.class));
                userInfo.setAppType(0);
                finish();
                break;
            case R.id.ll_hui_fu:
                startActivity(new Intent(this, HuiFuWebViewActivity.class));
                userInfo.setAppType(1);
                userInfo.setLogin(false)
                        .setRiskTest(0)
                        .setUid("")
                        .setHasBindCard(false)
                        .setCreateAccount(false)
                        .setCanUseMoney(0)
                        .setNickName("")
                        .setUserName("")
                        .setIcon("");
                finish();
                break;
        }
    }
}
