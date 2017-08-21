package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.ImageCodeIn;
import com.ftoul.androidclient.bean.request.MessageCodeIn;
import com.ftoul.androidclient.bean.response.ImageCodeOut;
import com.ftoul.androidclient.global.ErrCode;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.utils.VerifyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class RegisterFirstActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.et_image_code)
    EditText etImageCode;
    @BindView(R.id.iv_image_code)
    ImageView ivImageCode;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_invite_code)
    EditText etInviteCode;
    private boolean phoneNumTag;
    private boolean imgCodeTag;
    private String phoneNum;
    private String imgCode;
    private String serviceImgCode;
    private String inviteCode;
    private int codeType = 0;//0-注册 1-修改密码 2-找回密码 3-更换手机号码（原号码）     4-更换手机号码（新号码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initView() {
        headerTitle.setText("注册");
        initTextListener();//注册文本监听
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        getImgCode();
        super.onStart();
    }

    @OnClick({R.id.iv_image_code, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_image_code://图片验证码
                getImgCode();
                ivImageCode.setEnabled(false);
                ivImageCode.setImageResource(R.mipmap.zhengzaihuoqu);
                break;
            case R.id.btn_next://下一步
                if (verifty()) {
                    getMessageCode();
                }
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getMessageCode() {
        showDialog("正在发送短信");
//        DataBean<MessageCodeIn> dataBean = new DataBean<>(TransCode.GET_MESSAGE_CODE);
//        dataBean.setBody(new MessageCodeIn(phoneNum, imgCode, codeType));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new MessageCodeIn(phoneNum, imgCode, codeType));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS+"/services/api/v2/smsCode")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(pDialog) {
                    @Override
                    public void onSuccess(Object imageCodeOut) {
                        startActivity(new Intent(RegisterFirstActivity.this, RegisterSecondActivity.class)
                                .putExtra(RegisterSecondActivity.PHONE_NUM, phoneNum)
                                .putExtra(RegisterSecondActivity.IMG_CODE, imgCode)
                                .putExtra(RegisterSecondActivity.INVITE_CODE, inviteCode)
                                .putExtra(RegisterSecondActivity.MESSAGE_CODE,(String) imageCodeOut));
                        MyToast.show("短信发送成功");
                    }

                    @Override
                    public void onResponse(DataBean<Object> response, int id) {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.cancel();
                        }
                        String errCode = response.getErrCode();
                        if (response == null || TextUtils.isEmpty(errCode)) {
                            MyToast.show("ErrorCode为空");
                            return;
                        }
                        Log.e(errCode);
                        if (ErrCode.SUCCESS.equals(errCode)) {
                            if (response.getBody() != null) {
                                Log.e("body数据：" + response.getBody().toString());
                            }
                            onSuccess(response.getErrMsg());
                        } else {
                            ivImageCode.performClick();
                            onOther(response.getErrMsg(),response.getErrCode());
                            MyToast.show(response.getErrMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        ivImageCode.performClick();
                    }
                });
    }

    /**
     * 获取图片验证码
     */
    private void getImgCode() {
//        DataBean<ImageCodeIn> dataBean = new DataBean<>(TransCode.GET_IMG_CODE);
//        dataBean.setBody(new ImageCodeIn(0));
        String datas = Utilities.toJsonString2(new ImageCodeIn(0));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS+"/services/api/v2/imageCode")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<ImageCodeOut>(new TypeToken<DataBean<ImageCodeOut>>() {
                }) {

                    @Override
                    public void onSuccess(ImageCodeOut imageCodeOut) {
                        byte[] datas = Base64.decode(imageCodeOut.getImageBase64(), Base64.DEFAULT);
                        ivImageCode.setImageBitmap(BitmapFactory.decodeByteArray(datas, 0, datas.length));
                        serviceImgCode = imageCodeOut.getImageCode();
                        Log.e(imageCodeOut.getImageCode());
                        ivImageCode.setEnabled(true);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        ivImageCode.setEnabled(true);
                    }
                });
    }

    /**
     * 注册二次验证
     *
     * @return
     */
    private boolean verifty() {
        phoneNum = etPhoneNum.getText().toString().trim();
        if (!VerifyUtil.phone(phoneNum)) {
            MyToast.show("请输入正确的手机号码");
            return false;
        }
        imgCode = etImageCode.getText().toString().trim();
        if (TextUtils.isEmpty(imgCode)) {
            MyToast.show("验证码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(serviceImgCode) || !imgCode.toLowerCase().equals(serviceImgCode.toLowerCase())) {
            MyToast.show("验证码错误");
            return false;
        }
        inviteCode = etInviteCode.getText().toString();
        return true;
    }

    /**
     * 注册判断逻辑
     */
    private void initTextListener() {
        etPhoneNum.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumTag = s.length() > 8;
                setBtn();//btn状态选择
            }
        });
        etImageCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imgCodeTag = s.length() > 2;
                setBtn();//btn状态选择
            }
        });
    }

    /**
     * btn状态选择
     */
    private void setBtn() {
        if (phoneNumTag && imgCodeTag) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
    }
}
