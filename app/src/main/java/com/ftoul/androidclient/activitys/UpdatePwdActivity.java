package com.ftoul.androidclient.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.UpdatePwdIn;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.MD5;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.utils.VerifyUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

/**
 * 更换密码
 */
public class UpdatePwdActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_agn)
    EditText etNewPwdAgn;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private String oldpwd, newPwd, pwdAgn;
    private boolean oldPwdTag;
    private boolean pwdTag;
    private boolean pwdAgnTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("登录密码修改");
        initTextListener();//找回密码文本监听
    }

    @OnClick(R.id.btn_commit)
    public void onClick() {
        if (verifty()) {
            getBackPwd();
        }
    }

    /**
     * 获取短信验证码
     */
    private void getBackPwd() {
        showDialog("重置密码中");
        String md5OldPwd = MD5.md5low(oldpwd);
        String md5NewPwd = MD5.md5low(newPwd);
//        DataBean<UpdatePwdIn> dataBean = new DataBean<>(TransCode.UPDATE_PASSWORD);
//        dataBean.setBody(new UpdatePwdIn(userInfo.getUserName(), md5OldPwd, md5NewPwd));
//        String datas = Utilities.toJsonString(dataBean);
       String datas = Utilities.toJsonString2(new UpdatePwdIn(userInfo.getUserName(), md5OldPwd, md5NewPwd));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/modifyPassword")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(pDialog) {
                    @Override
                    public void onSuccess(Object imageCodeOut) {
                        MyToast.show("密码重置成功");
                        finish();
                    }
                });
    }

    /**
     * 找回密码二次验证
     *
     * @return
     */
    private boolean verifty() {
        oldpwd = etOldPwd.getText().toString().trim();
        newPwd = etNewPwd.getText().toString().trim();
        if(newPwd.length()>20){
            MyToast.show("新密码长度过长");
            return false;
        }
        if (!VerifyUtil.pwd(newPwd)) {
            Toast.makeText(this, "密码中包含非法字符，只可以填写大小英文字母和数字", Toast.LENGTH_LONG).show();
            return false;
        }
        pwdAgn = etNewPwdAgn.getText().toString().trim();
        if (!newPwd.equals(pwdAgn)) {
            MyToast.show("两次密码输入不一致");
            return false;
        }
        return true;
    }

    /**
     * 找回密码判断逻辑
     */
    private void initTextListener() {
        etNewPwd.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdTag = s.length() > 5;
                setBtn();//btn状态选择
            }
        });
        etNewPwdAgn.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdAgnTag = s.length() > 5;
                setBtn();//btn状态选择
            }
        });
        etOldPwd.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldPwdTag = s.length() > 5;
                setBtn();//btn状态选择
            }
        });
    }


    /**
     * btn状态选择
     */
    private void setBtn() {
        if (pwdTag && pwdAgnTag&&oldPwdTag) {
            btnCommit.setEnabled(true);
        } else {
            btnCommit.setEnabled(false);
        }
    }

}
