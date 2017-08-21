package com.ftoul.androidclient.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ftoul.androidclient.activitys.BindPhoneActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseModel;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.WxBean;
import com.ftoul.androidclient.bean.request.LoginCodeIn;
import com.ftoul.androidclient.bean.request.LoginIn;
import com.ftoul.androidclient.bean.response.LoginCodeOut;
import com.ftoul.androidclient.bean.response.LoginOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.global.UserInfoInstance;
import com.ftoul.androidclient.utils.Constants;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.MediaType;


/**
 * Created by M on 2016/10/10.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private BaseResp resp = null;
    private String WX_APP_ID = "wxf56babe4ed01dc73";
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    private String WX_APP_SECRET = "8e4130bdf4ff39c50f391baff2edcbf0";
    private String unionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        Log.e("111", "111");
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        com.ftoul.androidclient.utils.Log.e(resp.errStr+"------"+resp.openId+"======="+resp.errCode+"++++++++++"+resp.transaction);
        //登录
        String result = "";
        if (resp != null) {
            resp = resp;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                try {
                    String code = ((SendAuth.Resp) resp).code;
            /*
             * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
             */
                    String get_access_token = getCodeRequest(code);
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post(get_access_token, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response) {
                            // TODO Auto-generated method stub
                            super.onSuccess(statusCode, response);
                            try {
                                if (!response.equals("")) {
                                    String access_token = response
                                            .getString("access_token");
                                    String openid = response.getString("openid");
                                    unionid = response.getString("unionid");
                                    getUserInfo(access_token, openid);
                                }
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    finish();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "操作取消";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                result = "发送返回";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }


    private void loginSuccess(String openId, String nickName, String headImgUrl, String accessToken) {
       UserInfoInstance userInfo =  UserInfoInstance.getInstance(this);
        if(userInfo.getAppType()==0){//华兴版
            WxBean wxBean =new WxBean(accessToken,openId,unionid);
            EventBus.getDefault().post(wxBean,"WXlogin");
        }else{//汇付版
            MyToast.show("登录成功");
            BaseModel<WxBean> beanBaseModelEvent =new BaseModel<WxBean>();
            beanBaseModelEvent.setId("1");
            beanBaseModelEvent.setCode("1");
            beanBaseModelEvent.setMessage("登录成功");
            beanBaseModelEvent.setData(new WxBean(accessToken,openId,unionid));
            EventBus.getDefault().post(beanBaseModelEvent,"beanBaseModel");
        }
        finish();
    }

//    private void getData(String nickName ,String openId) {
//        final Gson gson = new Gson();
//        OkHttpUtils
//                .postString()
//                .url(MyUrl.LOGIN)
//                .content(new Gson().toJson(new LoginCodeIn(nickName,openId,"1")))
//                .mediaType(MediaType.parse("application/json; charset=UTF-8"))
//                .build()
//                .execute(new StringCallback() {
//                             @Override
//                             public void onError(Call call, Exception e, int id) {
//                                MyToast.show("网络错误");
//                             }
//
//                             @Override
//                             public void onResponse(String response, int id) {
//                                 com.ftoul.androidclient.utils.Log.e(response+"aaaaaaaaaa");
//                                 LoginCodeOut loginCodeOut=  gson.fromJson(response, LoginCodeOut.class);
//                                 com.ftoul.androidclient.utils.Log.e("aaaaa"+loginCodeOut.getId()+"");
//
//                             }
//                         }
//
//                );
//    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        Log.e("111", "555");
        finish();
    }

    /**
     * 获取access_token的URL（微信）
     *
     * @param code 授权时，微信回调给的
     * @return URL
     */
    private String getCodeRequest(String code) {
        String result = null;
        GetCodeRequest = GetCodeRequest.replace("APPID",
                urlEnodeUTF8(Constants.APP_ID));
        GetCodeRequest = GetCodeRequest.replace("SECRET",
                urlEnodeUTF8(Constants.APP_Secret));
        GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
        result = GetCodeRequest;
        return result;
    }

    /**
     * 获取用户个人信息
     *
     * @param access_token 获取access_token时给的
     * @param openid       获取access_token时给的
     *
     */
    private void getUserInfo(final String access_token, String openid) {
        String result = null;
        GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
                urlEnodeUTF8(access_token));
        GetUserInfo = GetUserInfo.replace("OPENID", urlEnodeUTF8(openid));
        result = GetUserInfo;//通过拼接的用户信息url获取用户信息
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(result, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                // TODO Auto-generated method stub
                super.onSuccess(statusCode, response);
                try {
                    Log.e("获取用户信息:", response.toString());
                    if (!response.equals("")) {
                        String openId = response.getString("openid");
                        String nickName = response.getString("nickname");
                        String headImgUrl = response.getString("headimgurl");
                        loginSuccess(openId, nickName, headImgUrl, access_token);
                    }

                } catch (Exception e) {
                    finish();
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    private String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
