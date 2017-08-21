package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pengtang on 2017/5/26.
 * 账户升级
 */
public class EaccountUpdateActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pid)
    EditText etPid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eaccount_update);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("账号升级");
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
    }
}
