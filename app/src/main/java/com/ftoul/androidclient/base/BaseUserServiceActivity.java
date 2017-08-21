package com.ftoul.androidclient.base;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HelpActivity;

/**
 * 顶部标题栏包含帮助中心的BaseActivity
 * Created by ftoul106 on 2017/4/26 0026.
 */

public abstract class BaseUserServiceActivity extends BaseTitleActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        try {
            RelativeLayout headRight = (RelativeLayout) findViewById(R.id.rl_headerRight);
            ImageView imageView = (ImageView) findViewById(R.id.iv_customer_service);
            imageView.setVisibility(View.VISIBLE);
            imageView.setColorFilter(getResources().getColor(R.color.white));
            headRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BaseUserServiceActivity.this, HelpActivity.class));
                }
            });
        }catch (Exception e){

        }
    }
}
