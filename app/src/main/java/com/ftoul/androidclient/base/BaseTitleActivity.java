package com.ftoul.androidclient.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ftoul.androidclient.R;

/**
 * 有标题的和左上角退出的activity
 * Created by ftoul106 on 2017/4/26 0026.
 */

public abstract class BaseTitleActivity extends BaseActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        try {
            RelativeLayout headLeft = (RelativeLayout) findViewById(R.id.rl_headerLeft);
            headLeft.setVisibility(View.VISIBLE);
            headLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            ImageView ivHeadLeft = (ImageView) findViewById(R.id.iv_left);
            ivHeadLeft.setColorFilter(getResources().getColor(R.color.white));
        }catch (Exception e){

        }
    }
}
