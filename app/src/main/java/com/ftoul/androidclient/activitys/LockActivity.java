package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;


import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;

import com.ftoul.androidclient.bean.BaseModel;
import com.ftoul.androidclient.locking.GestureLockView;
import com.ftoul.androidclient.utils.MyToast;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


public class LockActivity extends BaseActivity {
    @BindView(R.id.gesturelock)
    GestureLockView lockView;
    @BindView(R.id.tv_tips)
    TextView tvTips;//设置密码提示
    public static int setPassword = 1;
    private String passWord;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
    }

    @Override
    protected void initView() {
        uid =userInfo.getUid();
        Log.i("lockactivity设置id页面:", uid);
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
            }

            @Override
            public void onGestureLockFail(String password) {
                Log.e("onGestureLockFail", password);
            }

            @Override
            public void onSetPassword(String password) {
                if (password.length() < 5) {
                    setPassword = 1;
                    MyToast.show("密码长度不得小于五位！");
                    tvTips.setText("两次密码输入不一样，重新输入！");
                    tvTips.setTextColor(getResources().getColor(R.color.red_f74c4c));
                    return;
                }
                setPassword = 2;
                LockActivity.this.passWord = password;
                MyToast.show("请再次确认手势密码！");
                tvTips.setTextColor(getResources().getColor(R.color.white));
                tvTips.setText("请再次确认手势密码");
            }

            @Override
            public void onSetPasswordAgain(String password) {
                setPassword = 1;
                if (LockActivity.this.passWord.equals(password)) {
                    MyToast.show("设置成功！");
                    userInfo.setLock(password);
                    BaseModel<String> baseModel = new BaseModel<String>();
                    baseModel.setId("11");
                    baseModel.setCode("1");
                    baseModel.setMessage("设置成功");
                    baseModel.setData("");
                    EventBus.getDefault().post(baseModel, "SETPASSWORD");
                    finish();
                } else {
                    MyToast.show("两次密码输入不一样，重新输入！");
                    tvTips.setText("两次密码输入不一样，重新输入！");
                    tvTips.setTextColor(getResources().getColor(R.color.red_f74c4c));
                }
            }
        });
    }
    @OnClick(R.id.tv_skip)
    public void onClick() {
        finish();
    }

}