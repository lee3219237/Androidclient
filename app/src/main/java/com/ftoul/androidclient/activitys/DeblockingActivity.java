package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuiFuWebViewActivity;
import com.ftoul.androidclient.base.BaseActivity;

import com.ftoul.androidclient.locking.GestureLockView;
import com.ftoul.androidclient.utils.MyToast;

import butterknife.BindView;
import butterknife.OnClick;


public class DeblockingActivity extends BaseActivity {
    @BindView(R.id.gesturelock)
    GestureLockView lockView;
    @BindView(R.id.tv_tips)
    TextView tvTips;//设置密码提示
    private String passWord;
    private int times = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
    }

    @Override
    public void initView() {
        tvTips.setText("请输入手势密码");
        lockView.setmGestureLockListener(new GestureLockView.OnGestureLockListener() {
            @Override
            public void onBlockHitted(int index) {
                Log.e("onBlockHitted", index + "");
            }

            @Override
            public void onGetPassword(String password) {
                Log.e("onGetPassword", password);
            }

            @Override
            public void onGestureLockSuccess(String password) {
                Log.e("onGestureLockSuccess", password);
                MyToast.show("解锁成功！");
                if (userInfo.getAppType() == 0) {//华兴版
                    startActivity(new Intent(DeblockingActivity.this, MainActivity.class));
                }else{//汇付版
                    startActivity(new Intent(DeblockingActivity.this, HuiFuWebViewActivity.class));
                }
                userInfo.setLogin(true);
                finish();
            }

            @Override
            public void onGestureLockFail(String password) {
                Log.e("onGestureLockFail", password);
                if (times < 3) {
                    MyToast.show("密码错误，请重试！还有" + (3 - times) + "次机会！");
                    times++;
                } else {
                    if (userInfo.getAppType() == 0) {//华兴版
                        MyToast.show("解锁失败，请用账号密码登录！");
                        userInfo.setLogin(false).setUid("");
                        startActivity(new Intent(DeblockingActivity.this, MainActivity.class));
                        startActivity(new Intent(DeblockingActivity.this, LoginActivity.class));
                    }else{//汇付版
                        CookieManager cookieManager = CookieManager.getInstance();
                        cookieManager.removeAllCookie();
                        userInfo.setLogin(false).setUid("");
                        startActivity(new Intent(DeblockingActivity.this, HuiFuWebViewActivity.class));
                    }
                    finish();
                }

            }

            @Override
            public void onSetPassword(String password) {

            }

            @Override
            public void onSetPasswordAgain(String password) {

            }
        });
    }


    @OnClick(R.id.tv_skip)
    public void onClick() {
        if (userInfo.getAppType() == 0) {//华兴版
            startActivity(new Intent(DeblockingActivity.this, MainActivity.class));
            startActivity(new Intent(DeblockingActivity.this, LoginActivity.class));
            userInfo.setLogin(false).setUid("");
        }else{//汇付版
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            userInfo.setLogin(false).setUid("");
            startActivity(new Intent(DeblockingActivity.this, HuiFuWebViewActivity.class));
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}