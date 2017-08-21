package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by pengtang on 2017/5/26.
 * 合同与协议
 */
public class ContractProtrolActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_protrol);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("合同与协议");
    }
}
