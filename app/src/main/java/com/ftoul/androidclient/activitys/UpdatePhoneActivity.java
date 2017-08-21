package com.ftoul.androidclient.activitys;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.ImageCodeIn;
import com.ftoul.androidclient.bean.request.MessageCodeIn;
import com.ftoul.androidclient.bean.request.UpdatePhoneIn;
import com.ftoul.androidclient.bean.request.VerifyMobileIn;
import com.ftoul.androidclient.bean.response.ImageCodeOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.TimeButton;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.utils.VerifyUtil;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 更换手机号码
 */
public class UpdatePhoneActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_old_phone)
    TextView tvOldPhone;
    @BindView(R.id.et_image_code)
    EditText etImageCode;
    @BindView(R.id.iv_image_code)
    ImageView ivImageCode;
    @BindView(R.id.et_message_code_old)
    EditText etMessageCodeOld;
    @BindView(R.id.btn_get_code_old)
    TimeButton btnGetCodeOld;
    @BindView(R.id.et_new_phone)
    EditText etNewPhone;
    @BindView(R.id.et_message_code_new)
    EditText etMessageCodeNew;
    @BindView(R.id.btn_get_code_new)
    TimeButton btnGetCodeNew;
    private String newPhoneNum;
    private String imgCode;
    private String serviceImgCode;
    private String oldMsgCode;
    private String newMsgCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
    }

    @Override
    protected void initView() {
        headerTitle.setText("更换手机号码");
        String phone = userInfo.getPhone();
        phone = phone.substring(0,5)+"****"+phone.substring(9);
        tvOldPhone.setText(phone);
        getImgCode();
    }

    @OnClick({R.id.iv_image_code, R.id.btn_get_code_old, R.id.btn_get_code_new, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_image_code:
                getImgCode();
                break;
            case R.id.btn_get_code_old://获取原手机短信验证码
                btnGetCodeOld.startTiming();
                getMessageCode(userInfo.getUserName(), null, 3);
                break;
            case R.id.btn_get_code_new://获取新手机短信验证码
                newPhoneNum = etNewPhone.getText().toString().trim();
                if (!VerifyUtil.phone(newPhoneNum)) {
                    MyToast.show("请输入正确的手机号码");
                    return;
                }
                if (!veriftyImgCode()) {
                    MyToast.show("请输入正确的图片验证码");
                    return;
                }
                btnGetCodeNew.startTiming();
                getMessageCode(newPhoneNum, imgCode, 4);
                break;
            case R.id.btn_commit://确定
                verifty();
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getMessageCode(String phoneNum, String imgCode, int codeType) {
        showDialog("获取短信中");
//        DataBean<MessageCodeIn> dataBean = new DataBean<>(TransCode.GET_MESSAGE_CODE);
//        dataBean.setBody(new MessageCodeIn(phoneNum, imgCode, codeType));
//        String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new MessageCodeIn(phoneNum, imgCode, codeType));
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
                    }
                });
    }

    /**
     * 获取图片验证码
     */
    private void getImgCode() {
        ivImageCode.setImageResource(R.mipmap.zhengzaihuoqu);
        ivImageCode.setEnabled(false);
//        DataBean<ImageCodeIn> dataBean = new DataBean<>(TransCode.GET_IMG_CODE);
//        dataBean.setBody(new ImageCodeIn(0));
        String datas = Utilities.toJsonString2(new ImageCodeIn(4));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/imageCode")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
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
     * 修改手机号码
     */
    private void updatePhone(final UpdatePhoneIn body) {
        showDialog("提交中");
        String datas2 = Utilities.toJsonString2(new VerifyMobileIn(body.getNewPhone(), body.getNewCode(), imgCode));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/verifyMobile")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas2)
                .build()
                .execute(new SmartBaseCallBack<Object>(pDialog) {
                    @Override
                    public void onSuccess(Object imageCodeOut) {
                        userInfo.setPhone(body.getNewPhone());
                        setResult(RESULT_OK);
                        finish();
                        MyToast.show("修改成功");
                    }
                });
    }

    /**
     * 二次验证手机号
     */
    private void verifyMobile(final UpdatePhoneIn body) {
        showDialog("提交中");
//        DataBean<MessageCodeIn> dataBean = new DataBean<>(TransCode.GET_MESSAGE_CODE);
//        dataBean.setBody(new MessageCodeIn(phoneNum, imgCode, codeType));
        //       String datas = Utilities.toJsonString(dataBean);
        String datas = Utilities.toJsonString2(new VerifyMobileIn(body.getOldPhone(), body.getOldCode()));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/verifyMobile")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent", "Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(pDialog) {
                    @Override
                    public void onSuccess(Object imageCodeOut) {

                        updatePhone(body);

                    }
                });
    }


    /**
     * 验证方法
     *
     * @return
     */
    private boolean veriftyImgCode() {
        imgCode = etImageCode.getText().toString().trim();
        if (TextUtils.isEmpty(imgCode)) {
            MyToast.show("验证码不能为空");
            return false;
        }

        if (!imgCode.toLowerCase().equals(serviceImgCode.toLowerCase())) {
            MyToast.show("验证码错误");
            return false;
        }
        return true;
    }

    private boolean verifty() {
        if (veriftyImgCode() == false) {
            return false;
        }
        oldMsgCode = etMessageCodeOld.getText().toString().trim();
        newMsgCode = etMessageCodeNew.getText().toString().trim();
        if (TextUtils.isEmpty(oldMsgCode) || TextUtils.isEmpty(newMsgCode)) {
            MyToast.show("短信验证码不能为空");
            return false;
        }
        newPhoneNum = etNewPhone.getText().toString().trim();
        if (!VerifyUtil.phone(newPhoneNum)) {
            MyToast.show("请输入正确的手机号码");
            return false;
        }
        UpdatePhoneIn body = new UpdatePhoneIn();
        body.setOldPhone(userInfo.getPhone());
        body.setImageCode(imgCode);
        body.setOldCode(oldMsgCode);
        body.setNewPhone(newPhoneNum);
        body.setNewCode(newMsgCode);
        verifyMobile(body);
        return true;
    }
}
