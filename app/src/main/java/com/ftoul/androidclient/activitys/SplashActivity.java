package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuiFuWebViewActivity;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.AbstractBaseParamsVO;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.response.SplashImgOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.SpType;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.ic_back_ground)
    ImageView icBackGround;
    @BindView(R.id.ic_skip)
    TextView icSkip;
    private Handler handler;
    private Runnable runnable;
    private Runnable runnable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        MyApp.mainRefresh = false;
        MyApp.pesonalRefresh = false;//个人中心是否重新请求刷新数据，false 刷新，true不刷新
        MyApp.newUserInvestRefresh = false;//新手福利页是否重新请求刷新数据，false 刷新，true不刷新
        MyApp.regularInvestRefresh = false;//定期投资页是否重新请求刷新数据，false 刷新，true不刷新
        MyApp.registerMap=new HashMap();
        MyApp.getBackPwdMap=new HashMap();
        //登录成功后，把uid设置为用户的别名
        JPushInterface.setAlias(SplashActivity.this, "110", new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
            @Override
            public void gotResult(int i, String s, Set<String> set) {//极光推送
                Log.d("alias", "set alias result is" + i);
            }
        });
        //  userInfo.setLogin(true);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                next();
            }
        };
        runnable2 = new Runnable() {
            @Override
            public void run() {
                icBackGround.setVisibility(View.VISIBLE);

                getData();
            }
        };
        handler.postDelayed(runnable2,2000);
        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        handler.removeCallbacks(runnable2);
        handler = null;
    }

    private void getData() {
        DataBean<AbstractBaseParamsVO> dataBean = new DataBean<>(TransCode.GET_SPLASH_IMG);
        dataBean.setBody(new AbstractBaseParamsVO());
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<SplashImgOut>(new TypeToken<DataBean<SplashImgOut>>() {
                }) {
                    @Override
                    public void onSuccess(final SplashImgOut body) {
                        if (body == null) {
                            next();
                            return;
                        }
                        if(body.getStartPage()==null){
                            next();
                            return;
                        }
                        icSkip.setVisibility(View.VISIBLE);
                        Glide.with(SplashActivity.this)
                                .load(body.getStartPage().getImgUrl())
                                .centerCrop()
                                .into(icBackGround);
                        icBackGround.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                next();
                                startActivity(new Intent(SplashActivity.this,SimpleWebActivity.class)
                                        .putExtra(SimpleWebActivity.WEB_URL,body.getStartPage().getLinkUrl()));
                            }
                        });
                    }
                });
    }

    @OnClick({ R.id.ic_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_skip:
                next();
                break;
        }
    }

    private void next(){
        if (sp.getBoolean(SpType.NOT_FIRST_OPEN, false)) {
            if (TextUtils.isEmpty(userInfo.getLock())) {
                if (userInfo.getAppType() == 0) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, HuiFuWebViewActivity.class));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, DeblockingActivity.class));
            }
        } else {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        }
        finish();
    }
}
