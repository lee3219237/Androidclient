package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageDetailsActivity extends BaseTitleActivity {
    public static final String MSG_DETAIL_TITLE="1001";
    public static final String MSG_DETAIL_SEND_TIME="1002";
    public static final String MSG_DETAIL_CONTENT="1003";
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        headerTitle.setText("消息详情");
        String title = getIntent().getStringExtra(MSG_DETAIL_TITLE);
        String time = getIntent().getStringExtra(MSG_DETAIL_SEND_TIME);
        String content = getIntent().getStringExtra(MSG_DETAIL_CONTENT);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(time)) {
            tvTime.setText(time);
        }
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(Html.fromHtml(content));
        }
    }


    @Override
    protected void initView() {

    }


}
