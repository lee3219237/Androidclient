package com.ftoul.androidclient.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ftoul.androidclient.global.UserInfoInstance;
import com.ftoul.androidclient.utils.Utilities;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;


/**
 * Created by ftoul106 on 2017/1/20 0020.
 */


public abstract class BaseActivity extends AppCompatActivity {
    protected UserInfoInstance userInfo;
    protected SharedPreferences sp;
    protected int mode;
    protected String TAG = this.getClass().getSimpleName();
    protected ProgressDialog pDialog;
    public Context mContext;



    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.setContentView(layoutResID);
        if (TAG.length() > 23) {
            TAG = TAG.substring(0, 23);
        }
        mContext = this;
        userInfo = UserInfoInstance.getInstance(this);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        mode = getIntent().getIntExtra("mode", 0);
        initView();
        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //友盟统计
           MobclickAgent.onResume(this);
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra("mode", mode);
        super.startActivity(intent);
        // 实现淡入淡出的效果
//        overridePendingTransition(android.R.anim.fade_in,
//                android.R.anim.fade_out);
        // // 由左向右滑入的效果
        // overridePendingTransition(android.R.anim.slide_in_left,
        // android.R.anim.slide_out_right);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("mode", mode);
        super.startActivityForResult(intent, requestCode);
        // 实现淡入淡出的效果
//        overridePendingTransition(android.R.anim.fade_in,
//                android.R.anim.fade_out);
        // // 由左向右滑入的效果
        // overridePendingTransition(android.R.anim.slide_in_left,
        // android.R.anim.slide_out_right);
    }

    protected abstract void initView();

    protected void initData() {
    }


    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
        //友盟统计
            MobclickAgent.onPause(this);
    }

    protected void showDialog(String message) {
        if (null == pDialog) {
            pDialog = new ProgressDialog(this);
        }
        pDialog.setMessage(message);//设置对话框中的提示信息
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void setViewHigh(View view, Context context) {
        //设置顶部PaddingView高度
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = Utilities.getStatusBarHeight(context);
    }
}
