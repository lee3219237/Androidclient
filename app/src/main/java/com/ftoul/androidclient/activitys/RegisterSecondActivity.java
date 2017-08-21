package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.MessageCodeIn;
import com.ftoul.androidclient.bean.request.RegisterIn;
import com.ftoul.androidclient.bean.response.LoginOut;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.TimeButton;
import com.ftoul.androidclient.utils.MD5;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.utils.VerifyUtil;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;

public class RegisterSecondActivity extends BaseTitleActivity {
    public static final String PHONE_NUM = "phoneNum";
    public static final String IMG_CODE = "imgCode";
    public static final String INVITE_CODE = "inviteCode";
    public static final String MESSAGE_CODE = "messageCode";
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_register)
    Button btnNext;
    @BindView(R.id.btn_get_code)
    TimeButton btnGetCode;
    @BindView(R.id.et_message_code)
    EditText etMessageCode;
    private String phoneNum;
    private String messageCode;
    private boolean messageCodeTag;
    private boolean pwdTag;
    private String pwd;
    private AlertDialog mDialog;
    private int codeType = 0;
    private String un = "ftoul";//String	否		客户互相推荐邀请码	邀请码
    private String scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
    }

    @Override
    protected void initView() {
        messageCode = getIntent().getStringExtra(MESSAGE_CODE);
        etMessageCode.setText(messageCode);
        messageCodeTag = true;
        try {
            ApplicationInfo appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            un = appInfo.metaData.getString("UMENG_CHANNEL");//获取渠道号
        } catch (PackageManager.NameNotFoundException e) {

        }
        btnGetCode.onCreate(MyApp.registerMap);
        phoneNum = getIntent().getStringExtra(PHONE_NUM);
        scene = getIntent().getStringExtra(INVITE_CODE);
        tvPhoneNum.setText(phoneNum);
        headerTitle.setText("注册");
        initTextListener();
        btnGetCode.startTiming();
    }

    @OnClick({R.id.btn_get_code, R.id.btn_register, R.id.tv_register_agreement, R.id.rl_headerLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_agreement://注册协议
                startActivity(new Intent(this, SimpleWebActivity.class)
                        .putExtra(SimpleWebActivity.WEB_URL,MyUrl.REGISTER_AGREEMENT));
                break;
            case R.id.btn_get_code:
                btnGetCode.startTiming();
                getMessageCode();
                break;
            case R.id.btn_register:
                if (verifty()) {
                    showDialog("注册中");
                  //  DataBean<RegisterIn> dataBean = new DataBean<>(TransCode.REGISTER);
                    // String phone, String pwd, String un, String code, String phoneModel, String scene
                    //String mobile, String password, String invitationCode, String smsCode, String phoneModel, String scene
                    String md5Pwd = MD5.md5low(pwd);
                    String imgCode = getIntent().getStringExtra(IMG_CODE);
                    RegisterIn loginBean = new RegisterIn(phoneNum, md5Pwd, un, messageCode, android.os.Build.MODEL,scene,imgCode);
                  //  dataBean.setBody(loginBean);
                  //  String datas = Utilities.toJsonString(dataBean);
                  String datas = Utilities.toJsonString2(loginBean);
                    OkHttpUtils
                            .postString()
                            .tag(this)
                            .url(MyUrl.SERVICE_ADDRESS+"/services/api/v2/userRegister")
                            .mediaType(MediaType.parse("application/json; charset=utf-8"))
                            .addHeader("user-agent", "Android")
                            .content(datas)
                            .build()
                            .execute(new SmartBaseCallBack<LoginOut>(new TypeToken<DataBean<LoginOut>>() {
                            },pDialog) {
                                @Override
                                public void onSuccess(LoginOut body) {
                                    userInfo.setLogin(true)
                                            .setUid(body.getUid())
                                            .setUserName(phoneNum)
                                            .setToken(body.getToken())
                                            .setIcon("")
                                            .setNickName(body.getNickName());
                                    MyApp.pesonalRefresh = false;
                                    aleartDialog();
                                }
                            });
                }
                break;
            case R.id.rl_headerLeft:
                if (userInfo.isLogin()) {
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                }
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getMessageCode() {
        String imgCode = "ftoul";
//        DataBean<MessageCodeIn> dataBean = new DataBean<>(TransCode.GET_MESSAGE_CODE);
//        dataBean.setBody(new MessageCodeIn(phoneNum,imgCode,codeType));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new MessageCodeIn(phoneNum, imgCode, codeType));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS+"/services/api/v2/smsCode")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>() {
                    @Override
                    public void onSuccess(Object body) {
                        MyToast.show("短信发送成功");
                    }
                });
    }

    /**
     * 注册二次验证
     *
     * @return
     */
    private boolean verifty() {
        messageCode = etMessageCode.getText().toString().toString();
        pwd = etPwd.getText().toString().trim();
        if(pwd.length()>20){
            MyToast.show("密码长度过长");
        }
        if (!VerifyUtil.pwd(pwd)) {
            Toast.makeText(this, "密码中包含非法字符，只可以填写大小英文字母和数字", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * 注册判断逻辑
     */
    private void initTextListener() {
        etMessageCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                messageCodeTag = s.length() > 2;
                setBtn();//btn状态选择
            }
        });
        etPwd.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdTag = s.length() > 5;
                setBtn();//btn状态选择
            }
        });
    }

    /**
     * btn状态选择
     */
    private void setBtn() {
        if (messageCodeTag && pwdTag) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
    }

    private void aleartDialog() {
//        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//        alertDialog.show();
//        alertDialog.setContentView(R.layout.dialog_item_open_account);
//        alertDialog.setCanceledOnTouchOutside(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = View.inflate(this, R.layout.dialog_item_open_account, null);
        View view = inflate.findViewById(R.id.iv_close);
        View createAccount = inflate.findViewById(R.id.btn_create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterSecondActivity.this, MainActivity.class));
                startActivity(new Intent(RegisterSecondActivity.this, HuaXinWebViewActivity.class)
                        .putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.ACCOUNT_CREATE));
            }
        });
        builder.setView(inflate);
        mDialog = builder.show();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterSecondActivity.this, MainActivity.class));
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           if (userInfo.isLogin()) {
                startActivity(new Intent(this, MainActivity.class));
              //  startActivity(new Intent(this, LockActivity.class));
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
