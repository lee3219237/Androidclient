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
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.MyTextWatcher;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.WxBean;
import com.ftoul.androidclient.bean.request.MessageCodeIn;
import com.ftoul.androidclient.bean.request.WeiXinBindIn;
import com.ftoul.androidclient.bean.response.LoginOut;
import com.ftoul.androidclient.global.ErrCode;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.TimeButton;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.utils.VerifyUtil;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.MediaType;

public class BindPhoneActivity extends BaseTitleActivity {
    public static final String WEI_XIN_BEAN= "WxBean";
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_message_code)
    EditText etMessageCode;
    @BindView(R.id.btn_get_code)
    TimeButton btnGetCode;
    private String phoneNum;
    private String messageCode;
    private boolean messageCodeTag;
    private boolean phoneNumTag;
    private int codeType = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("绑定手机");
        tvRight.setText("注册");
        initTextListener();//注册文本监听
    }


    @OnClick({R.id.btn_next, R.id.rl_headerRight, R.id.btn_get_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                if (verifty()) {
                    btnGetCode.startTiming();
                    getMessageCode();
                }
                break;
            case R.id.btn_next:
                if (verifty()) {
                    bindPhone();
                }
                break;
            case R.id.rl_headerRight:
                startActivity(new Intent(this, RegisterFirstActivity.class));
                finish();
                break;
        }
    }

    /**
     * 绑定二次验证
     *
     * @return
     */
    private boolean verifty() {
        phoneNum = etPhoneNum.getText().toString().trim();
        if (!VerifyUtil.phone(phoneNum)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
            return false;
        }
        messageCode = etMessageCode.getText().toString().trim();
        return true;
    }

    /**
     * 绑定判断逻辑
     */
    private void initTextListener() {
        etMessageCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                messageCodeTag = s.length() > 2;
                setBtn();//btn状态选择
            }
        });
        etPhoneNum.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumTag = s.length() > 9;
                setBtn();//btn状态选择
            }
        });
    }

    /**
     * btn状态选择
     */
    private void setBtn() {
        if (messageCodeTag && phoneNumTag) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
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
         String datas = Utilities.toJsonString2(new MessageCodeIn(phoneNum,codeType));
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

                    }
                });
    }

    /**
     * 绑定手机
     */
    private void bindPhone() {
        showDialog("正在绑定中");
        //"accessToken":"ivW_U1VkSQXyv_3v4Ilkvx1OCYJPY-iT3YG0n3delXtrocMMbrN3i21OBbt--zmzaj-L4KGRcq_D6blECD-hAc2FM8ZsQGoo4kAhUcca9-Q",
        // "from":"1","ipAddress":"","local":"",
        // "openId":"o8PS0s4JhUr3U0D5mXGfqZM-OF5w"
        // ,"phoneModel":"Redmi Note 4X",
        // "unionId":"oYBlKs0EaafDno_aEArsQzp6Lt38","device":2,"machineCode":"568e9863d01d9226","uid":""}
   //   WxBean bean = (WxBean) getIntent().getSerializableExtra(WEI_XIN_BEAN);
//        DataBean<WeiXinBindIn> dataBean = new DataBean<>(TransCode.WEI_XIN_BIND);
//       // String phone, String code, String unionId, String accessToken, String openId, String ipAddress
//        dataBean.setBody(new WeiXinBindIn(phoneNum, messageCode,bean.getUnionid(),bean.getAccess_token(),bean.getOpenid(),""));
  //      String datas = Utilities.toJsonString2(new WeiXinBindIn(phoneNum, messageCode,bean.getUnionid(),bean.getAccess_token(),bean.getOpenid(),""));
      String datas = Utilities.toJsonString2(new WeiXinBindIn(phoneNum, messageCode,"oYBlKs0EaafDno_aEArsQzp6Lt38","ivW_U1VkSQXyv_3v4Ilkvx1OCYJPY-iT3YG0n3delXtrocMMbrN3i21OBbt--zmzaj-L4KGRcq_D6blECD-hAc2FM8ZsQGoo4kAhUcca9-Q","o8PS0s4JhUr3U0D5mXGfqZM-OF5w",""));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS+"/services/api/v2/userInfo/wxBind")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<LoginOut>(new TypeToken<DataBean<LoginOut>>() {
                },pDialog) {
                    @Override
                    public void onSuccess(LoginOut body) {
                        MyToast.show("绑定成功");
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
                        //登录成功后，把uid设置为用户的别名
                        JPushInterface.setAlias(BindPhoneActivity.this, body.getUid(),new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {//极光推送
                                android.util.Log.d("alias", "set alias result is" + i);
                            }
                        });
                        MyApp.pesonalRefresh = false;
                        if(userInfo.isFirstLogin()==false){
                            userInfo.setFirstLogin(true);
                            startActivity(new Intent(BindPhoneActivity.this,MainActivity.class));
                            startActivity(new Intent(BindPhoneActivity.this,LockActivity.class));
                        }else{
                            startActivity(new Intent(BindPhoneActivity.this,MainActivity.class));
                        }
                    }

                });
    }
}
