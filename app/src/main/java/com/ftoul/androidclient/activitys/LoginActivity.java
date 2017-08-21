package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.WxBean;
import com.ftoul.androidclient.bean.request.LoginIn;
import com.ftoul.androidclient.bean.response.LoginOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.GlideCircleTransform;
import com.ftoul.androidclient.utils.Constants;
import com.ftoul.androidclient.utils.MD5;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.reflect.TypeToken;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;



public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.tv_change_account)
    TextView tvChangeAccount;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    // 微信登录
    private static IWXAPI WXapi;
    @BindView(R.id.iv_show_password)
    ImageView ivShowPassword;
    private String WX_APP_ID = Constants.APP_ID;
    private boolean userNameTag;
    private boolean pwdTag;
    private String userName;
    private String pwd;
    private boolean isShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }


    @Override
    public void initData() {
        initTextListener();//登录判断逻辑,决定按钮是否可以点击
    }


    public void initView() {
        String userName = userInfo.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            if (!(TextUtils.isEmpty(userInfo.getIcon()) || "default".equals(userInfo.getIcon()))) {
                Glide.with(this).load(userInfo.getIcon()).placeholder(R.mipmap.ftoul01_logo).transform(new GlideCircleTransform(this)).into(ivUserIcon);
            }
            tvPhoneNum.setText(userName);
            userNameTag = true;
            etPhoneNum.setVisibility(View.INVISIBLE);
        } else {

            tvChangeAccount.setVisibility(View.INVISIBLE);
            etPhoneNum.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 登录二次验证逻辑
     */
    private boolean verify() {

        userName = etPhoneNum.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            userName = userInfo.getUserName();
        }
//        if (!VerifyUtil.userName(userName)) {
//            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
//            return false;
//        }
        pwd = etPwd.getText().toString().trim();
        if (pwd.length() < 6 || pwd.length() > 20) {
            Toast.makeText(this, "密码过长", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * 登录是否可点击验证逻辑
     */
    private void initTextListener() {
        etPhoneNum.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userNameTag = s.length() > 3;
                setBtn();//按钮状态选择

            }
        });
        etPwd.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdTag = s.length() > 5;
                setBtn();
            }

        });
    }

    private void setBtn() {
        if (userNameTag && pwdTag) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }


    @OnClick({R.id.iv_close, R.id.tv_change_account, R.id.btn_login, R.id.tv_forget_pwd, R.id.tv_register, R.id.iv_weixin_login,R.id.iv_show_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
              //  startActivity(new Intent(LoginActivity.this, BindPhoneActivity.class));
                finish();
                break;
            case R.id.tv_change_account://切换账号
                tvChangeAccount.setVisibility(View.INVISIBLE);
                etPhoneNum.setVisibility(View.VISIBLE);
                tvPhoneNum.setVisibility(View.INVISIBLE);
                ivUserIcon.setImageResource(R.mipmap.ftoul01_logo);
                userInfo.setUserName("");
                break;
            case R.id.btn_login://登录
                if (verify()) {
                    showDialog("登录中");
                    DataBean<LoginIn> dataBean = new DataBean<>(TransCode.LOGIN);
                    String md5Pwd = MD5.md5low(pwd);
                    // String form, String userName, String pwd, String ipAddress, String phoneModel, String local
                    LoginIn loginBean = new LoginIn("0", userName, md5Pwd, "", Build.MODEL, "");
                    dataBean.setBody(loginBean);
//                    userInfo.setUserName(userName)
//                            .setUid("110")
//                            .setLogin(true);
                    String datas = Utilities.toJsonString(dataBean);
                    OkHttpUtils
                            .postString()
                            .tag(this)
                            .url(MyUrl.SERVICE_MAIN)
                            .content(datas)
                            .build()
                            .execute(new SmartBaseCallBack<LoginOut>(new TypeToken<DataBean<LoginOut>>() {
                            }, pDialog) {
                                @Override
                                public void onSuccess(LoginOut body) {
                                    MyApp.mainRefresh = false;
                                    userInfo.setUserName(userName)
                                            .setUid(body.getUid())
                                            .setIcon(body.getIcon())
                                            .setToken(body.getToken())
                                            .setPhone(body.getPhone())
                                            .setLogin(true);
                                    if (TextUtils.isEmpty(body.getNickName())) {

                                    } else {
                                        userInfo.setNickName(body.getNickName());
                                    }
                                    if (body.getHasBindCard() == 1) {
                                        userInfo.setHasBindCard(true);
                                    } else {
                                        userInfo.setHasBindCard(false);
                                    }
                                    if (body.getHasIpsAccount() == 1) {
                                        userInfo.setCreateAccount(true);
                                    } else {
                                        userInfo.setCreateAccount(false);
                                    }
                                    if (TextUtils.isEmpty(body.getRiskTest())) {
                                        userInfo.setRiskTest(0);
                                    } else {
                                        userInfo.setRiskTest(1);
                                    }
                                    if (userInfo.isFirstLogin() == false) {
                                        startActivity(new Intent(LoginActivity.this, LockActivity.class));
                                        userInfo.setFirstLogin(true);
                                    }
                                    //登录成功后，把uid设置为用户的别名
                                    JPushInterface.setAlias(LoginActivity.this, body.getUid(), new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                                        @Override
                                        public void gotResult(int i, String s, Set<String> set) {//极光推送
                                            Log.d("alias", "set alias result is" + i);
                                        }
                                    });
                                    MyApp.pesonalRefresh = false;
                                    finish();
                                }
                            });
                }
                break;
            case R.id.tv_forget_pwd://忘记密码
                startActivity(new Intent(this, GetBackPwdFirstActivity.class));
                break;
            case R.id.tv_register://注册
                startActivity(new Intent(this, RegisterFirstActivity.class));
                break;
            case R.id.iv_weixin_login://微信登录
                // 通过WXAPIFactory工厂，获取IWXAPI的实例
                WXapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
                WXapi.registerApp(WX_APP_ID);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";//获取个人用户信息的权限
                req.state = "wechat_sdk_ftou";//防止攻击
                WXapi.sendReq(req);//向微信发送请求
                break;
            case R.id.iv_show_password://密码可见不可见
                if(isShow == false){
                    isShow =true;
                    ivShowPassword.setImageResource(R.mipmap.bukejian_icon);
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                }else{
                    isShow =false;
                    ivShowPassword.setImageResource(R.mipmap.kejian_icon);
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
        }
    }

    /**
     * 获取微信登录的返回数据(成功)
     *
     * @param bean
     */
    @Subscriber(tag = "WXlogin")
    public void getWxData(final WxBean bean) {
        showDialog("登录中");
        DataBean<LoginIn> dataBean = new DataBean<>(TransCode.LOGIN);
        // String form, String ipAddress, String unionId, String accessToken, String openId, String phoneModel, String local
        LoginIn loginBean = new LoginIn("1", "", bean.getUnionid(), bean.getAccess_token(), bean.getOpenid(), Build.MODEL, "");
        dataBean.setBody(loginBean);
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<LoginOut>(new TypeToken<DataBean<LoginOut>>() {
                }, pDialog) {
                    @Override
                    public void onSuccess(LoginOut body) {
                        userInfo.setUserName(body.getPhone())
                                .setUid(body.getUid())
                                .setIcon(body.getIcon())
                                .setToken(body.getToken())
                                .setLogin(true);
                        if (TextUtils.isEmpty(body.getNickName())) {

                        } else {
                            userInfo.setNickName(body.getNickName());
                        }
                        if (body.getHasBindCard() == 1) {
                            userInfo.setHasBindCard(true);
                        } else {
                            userInfo.setHasBindCard(false);
                        }
                        if (body.getHasIpsAccount() == 1) {
                            userInfo.setCreateAccount(true);
                        } else {
                            userInfo.setCreateAccount(false);
                        }
                        if (TextUtils.isEmpty(body.getRiskTest())) {
                            userInfo.setRiskTest(0);
                        } else {
                            userInfo.setRiskTest(1);
                        }
                        if (userInfo.isFirstLogin() == false) {
                            startActivity(new Intent(LoginActivity.this, LockActivity.class));
                            userInfo.setFirstLogin(true);
                        }
                        //登录成功后，把uid设置为用户的别名
                        JPushInterface.setAlias(LoginActivity.this, body.getUid(), new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {//极光推送
                                Log.d("alias", "set alias result is" + i);
                            }
                        });
                        MyApp.pesonalRefresh = false;
                        finish();
                    }

                    @Override
                    protected void onOther(String errMsg, String errCode) {
                       super.onOther(errMsg, errCode);
                        startActivity(new Intent(LoginActivity.this, BindPhoneActivity.class).putExtra(BindPhoneActivity.WEI_XIN_BEAN, bean));
                    }
                });
    }

}
