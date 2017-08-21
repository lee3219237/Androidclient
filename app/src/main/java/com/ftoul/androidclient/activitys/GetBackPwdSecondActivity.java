package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetBackPwdIn;
import com.ftoul.androidclient.bean.request.MessageCodeIn;
import com.ftoul.androidclient.global.ErrCode;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.TimeButton;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MD5;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.utils.VerifyUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;

public class GetBackPwdSecondActivity extends BaseTitleActivity {
    public static final String PHONE_NUM = "phoneNum";
    public static final String MESSAGE_CODE = "messageCode";

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_agn)
    EditText etPwdAgn;
    @BindView(R.id.btn_finish)
    Button btnFinish;

    private boolean pwdTag;
    private boolean pwdAgnTag;
    private String phoneNum;
    private String msgCode;
    private String pwd;
    private String pwdAgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_back_pwd_second);
    }

    public void initView() {
        headerTitle.setText("找回密码");
        phoneNum = getIntent().getStringExtra(PHONE_NUM);
        msgCode = getIntent().getStringExtra(MESSAGE_CODE);
        initTextListener();//找回密码文本监听
    }

    @OnClick({R.id.btn_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_finish://完成
                if (verifty()) {
                    getBackPwd();

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
        pwd = etPwd.getText().toString().trim();
        if(pwd.length()>20){
            MyToast.show("密码长度过长");
            return false;
        }
        if (!VerifyUtil.pwd(pwd)) {
            Toast.makeText(this, "密码中包含非法字符，只可以填写大小英文字母和数字", Toast.LENGTH_LONG).show();
            return false;
        }
        pwdAgn = etPwdAgn.getText().toString().trim();
        if(!pwd.equals(pwdAgn)){
           MyToast.show("两次密码输入不一致");
            return false;
        }
        return true;
    }

    /**
     * 找回密码判断逻辑
     */
    private void initTextListener() {
        etPwd.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdTag = s.length() > 5;
                setBtn();//btn状态选择
            }
        });
        etPwdAgn.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdAgnTag = s.length() > 5;
                setBtn();//btn状态选择
            }
        });
    }


    /**
     * btn状态选择
     */
    private void setBtn() {
        if (pwdTag && pwdAgnTag) {
            btnFinish.setEnabled(true);
        } else {
            btnFinish.setEnabled(false);
        }
    }


    /**
     * 获取短信验证码
     */
    private void getBackPwd() {
        showDialog("重置密码中");
        String md5Pwd = MD5.md5low(pwd);
//        DataBean<GetBackPwdIn> dataBean = new DataBean<>(TransCode.GET_BACK_PWD);
//        dataBean.setBody(new GetBackPwdIn(phoneNum, md5Pwd,msgCode));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new GetBackPwdIn(phoneNum, md5Pwd,msgCode));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/retrievePwd")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(pDialog) {
                    @Override
                    public void onSuccess(Object imageCodeOut) {
                        startActivity(new Intent(GetBackPwdSecondActivity.this,LoginActivity.class));
                        MyToast.show("密码重置成功");
                    }
                });
    }
}
