package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.bean.response.HomeCodeOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.utils.DataCleanManager;
import com.ftoul.androidclient.utils.MyDialog;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class SettingActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.checkbox_jpush)
    CheckBox checkboxPush;
    @BindView(R.id.tv_clear_cache)
    TextView tvClearCache;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.btn_exit_login)
    Button btnExitLogin;
    private AlertDialog mDialog;
    private String cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        checkboxPush.setChecked(!userInfo.isJpush());
        headerTitle.setText("设置");
        if (!userInfo.isLogin()) {
            btnExitLogin.setVisibility(View.INVISIBLE);
        }
        tvVersion.setText(Utilities.getVersionName(this));
        setCache();//获取缓存大小并设置
    }

    @OnClick({R.id.ll_jpush, R.id.ll_clear_cache, R.id.ll_about_us, R.id.ll_inspect_version, R.id.ll_company_tel, R.id.btn_exit_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_clear_cache:
                //   Utilities.showInstalledAppDetails(this);
                aleartClearCacheDialog();
                break;
            case R.id.ll_jpush:
                checkboxPush.setChecked(!checkboxPush.isChecked());
                if (checkboxPush.isChecked()) {
                    //登录成功后，把uid设置为用户的别名
                    JPushInterface.setAlias(SettingActivity.this, userInfo.getUid(), new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {//极光推送
                            android.util.Log.d("alias", "set alias result is" + i);
                        }
                    });
                    userInfo.setJpush(false);
                } else {
                    JPushInterface.setAlias(SettingActivity.this, "", new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {//极光推送
                            android.util.Log.d("alias", "set alias result is" + i);
                        }
                    });
                    userInfo.setJpush(true);
                }
                break;
            case R.id.ll_about_us:
                startActivity(new Intent(this, SimpleWebActivity.class)
                        .putExtra(SimpleWebActivity.WEB_URL, MyUrl.ABOUT_FTOUL));
                break;
            case R.id.ll_inspect_version:
                if( MyDialog.aleartAppUpdateDialog(this,userInfo.getAppInfo())==false){
                    MyToast.show("APP已经是最新版本");
                }
                break;
            case R.id.ll_company_tel:
                aleartDialog();
                break;
            case R.id.btn_exit_login:
                userInfo.setLogin(false)
                        .setRiskTest(0)
                        .setUid("")
                        .setHasBindCard(false)
                        .setCreateAccount(false)
                        .setCanUseMoney(0)
                        .setPhone("")
                        .setNickName("");
                MyApp.mainRefresh = false;
                startActivity(new Intent(this, LoginActivity.class));
                //退出登录后，把别名设置为空串，机关推送
                JPushInterface.setAlias(this, "", new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                    @Override
                    public void gotResult(int i, java.lang.String s, Set<String> set) {
                        Log.d("alias", "set alias result is" + i);
                    }
                });
                MyApp.pesonalRefresh = false;
                finish();
                break;
        }
    }

    /**
     * 客服电话Dialog
     */
    private void aleartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = View.inflate(this, R.layout.dialog_setting, null);
        View view = inflate.findViewById(R.id.tv_close);
        View okView = inflate.findViewById(R.id.tv_ok);
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.call(SettingActivity.this, getString(R.string.tel));
                mDialog.cancel();
            }
        });
        builder.setView(inflate);
        mDialog = builder.show();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        mDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 清除缓存Dialog
     */

    private void aleartClearCacheDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = View.inflate(this, R.layout.dialog_setting, null);
        View view = inflate.findViewById(R.id.tv_close);
        View okView = inflate.findViewById(R.id.tv_ok);
        TextView title = (TextView) inflate.findViewById(R.id.tv_title);
        TextView content = (TextView) inflate.findViewById(R.id.tv_content);
        title.setText("清除缓存");
        content.setText("您的缓存数据大小为" + cache + ",是否确定删\n除？");
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.cleanInternalCache(SettingActivity.this);
                setCache();
                MyToast.show("清除成功");
                mDialog.cancel();
            }
        });
        builder.setView(inflate);
        mDialog = builder.show();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        mDialog.setCanceledOnTouchOutside(false);
    }

    private void setCache() {
        try {
            cache = DataCleanManager.getCacheSize(getExternalCacheDir());
            tvClearCache.setText(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
