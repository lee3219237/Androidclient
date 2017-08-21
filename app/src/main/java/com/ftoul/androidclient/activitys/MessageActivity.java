package com.ftoul.androidclient.activitys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.MessagePagerAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.bean.ReceiverBean;
import com.ftoul.androidclient.dao.MySQLiteHelper;
import com.ftoul.androidclient.fragments.message.MessageFragment;
import com.ftoul.androidclient.fragments.message.NoticeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MessageActivity extends BaseTitleActivity {
    public static final String NOTICE_ITEMS ="noticeItems";
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.vp_message)
    ViewPager vpMessage;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tv_right)
    TextView tvRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
    }

    public void initData() {
        String[] titles = {"平台公告", "站内信"};
        vpMessage.setAdapter(new MessagePagerAdapter(getSupportFragmentManager(), this, titles));
        tabLayout.setupWithViewPager(vpMessage);
    }

    public void initView() {
        headerTitle.setText("消息中心");
        tvRight.setText("全部标记已读");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(this, MainActivity.class));

    }

    @OnClick(R.id.rl_headerRight)
    public void onViewClicked() {
        NoticeFragment fragment = (NoticeFragment) getSupportFragmentManager().getFragments().get(0);
        fragment.hasAllRead();
        MessageFragment fragment1 = (MessageFragment) getSupportFragmentManager().getFragments().get(1);
        fragment1.hasAllRead();
    }
}
