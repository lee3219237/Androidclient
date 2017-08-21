package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.MessageCodeIn;
import com.ftoul.androidclient.global.ErrCode;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.TimeButton;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.utils.VerifyUtil;
import com.zhy.http.okhttp.OkHttpUtils;


import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class GetBackPwdFirstActivity extends BaseTitleActivity {
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_msg_code)
    EditText etMsgCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_get_code)
    TimeButton btnGetCode;

    private boolean phoneNumTag;
    private boolean msgCodeTag;
    private String phoneNum;
    private String msgCode;
    private int codeType = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_back_pwd);

    }

    public void initView() {
        btnGetCode.onCreate(MyApp.getBackPwdMap);
        headerTitle.setText("找回密码");
        initTextListener();//找回密码文本监听
    }

    @OnClick({R.id.btn_get_code, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code://获取验证码
                phoneNum = etPhoneNum.getText().toString().trim();
                if (!VerifyUtil.phone(phoneNum)) {
                    MyToast.show("请输入正确的手机号码");
                } else {
                    btnGetCode.startTiming();
                    getMessageCode();
                }
                break;
            case R.id.btn_register://找回密码
                if (verifty()) {
                    startActivity(new Intent(this, GetBackPwdSecondActivity.class)
                            .putExtra(GetBackPwdSecondActivity.PHONE_NUM, phoneNum)
                            .putExtra(GetBackPwdSecondActivity.MESSAGE_CODE, msgCode));
                }
                break;
        }
    }

    /**
     * 找回密码二次验证
     *
     * @return
     */
    private boolean verifty() {
        phoneNum = etPhoneNum.getText().toString().trim();
        if (!VerifyUtil.phone(phoneNum)) {
            MyToast.show("请输入正确的手机号码");
            return false;
        }
        msgCode = etMsgCode.getText().toString().trim();
        if (!VerifyUtil.captcha(msgCode)) {
            MyToast.show("短信验证码有误");
            return false;
        }
        return true;
    }

    /**
     * 找回密码判断逻辑
     */
    private void initTextListener() {
        etPhoneNum.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumTag = s.length() > 8;
                setBtn();//btn状态选择
            }
        });
        etMsgCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                msgCodeTag = s.length() > 2;
                setBtn();//btn状态选择
            }
        });
    }

    /**
     * btn状态选择
     */
    private void setBtn() {
        if (phoneNumTag && msgCodeTag) {
            btnRegister.setEnabled(true);
        } else {
            btnRegister.setEnabled(false);
        }
    }


    /**
     * 获取短信验证码
     */
    private void getMessageCode() {
        showDialog("正在发送短信");
//        DataBean<MessageCodeIn> dataBean = new DataBean<>(TransCode.GET_MESSAGE_CODE);
//        dataBean.setBody(new MessageCodeIn(phoneNum, codeType));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new MessageCodeIn(phoneNum, codeType));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/smsCode")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(pDialog) {
                    @Override
                    public void onSuccess(Object imageCodeOut) {
                        MyToast.show("短信发送成功");
//                        etMsgCode.setText((String) imageCodeOut);
//                        msgCodeTag = true;
                        setBtn();
                    }
                });
    }
}
