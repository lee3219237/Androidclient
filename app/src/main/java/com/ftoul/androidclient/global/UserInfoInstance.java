package com.ftoul.androidclient.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ftoul.androidclient.bean.response.GetNewUserWelfareOut;
import com.ftoul.androidclient.bean.response.HomeCodeOut;

import java.util.List;


/**
 * Created by chenxiaoli on 2017/3/2 0002.
 * 用户信息单例类
 * 包含SharedPreferences本地缓存
 */

public class UserInfoInstance {
    public static SharedPreferences sp;
    private String icon;
    private String msgCode;
    private String uid;
    private String userName;
    private String phone;
    private String token;
    private String p2pUid;
    private String nickName;//昵称
    private String location;//用户地址
    private boolean login;//是否登录
    private String lock;//手势登录密码
    private boolean hasBindCard;//是否绑卡
    private boolean createAccount;//是否开户
    private String cityCode;//百度定位地址
    private int riskTest;//风险测评状态，0未完成，1已完成
    private int appType;//0，华兴版 1，汇付版
    private float canUseMoney;//剩余可用金额
    private GetNewUserWelfareOut.LctyBidBean lctyBidBean;//理财体验标
    private HomeCodeOut.AppInfoBean appInfo;
    private boolean firstLogin;//用户第一次登录 false 第一次登录，true 不是
    private boolean Jpush;//机关推送 false 开，true 关闭；

    private UserInfoInstance() {
    }

    private static UserInfoInstance mInstance;

    // 单实例,仅初始化一次
    public static UserInfoInstance getInstance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        if (mInstance == null) {
            synchronized (UserInfoInstance.class) {
                if (mInstance == null)
                    mInstance = new UserInfoInstance();
            }
        }
        return mInstance;
    }

    public HomeCodeOut.AppInfoBean getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(HomeCodeOut.AppInfoBean appInfo) {
        this.appInfo = appInfo;
    }

    public GetNewUserWelfareOut.LctyBidBean getLctyBidBean() {
        return lctyBidBean;
    }

    public void setLctyBidBean(GetNewUserWelfareOut.LctyBidBean lctyBidBean) {
        this.lctyBidBean = lctyBidBean;
    }

    public int getRiskTest() {
        if (riskTest == 0) {
            riskTest = sp.getInt(SpType.RISK_TEST, 0);
        }
        return riskTest;
    }

    public UserInfoInstance setRiskTest(int riskTest) {
        this.riskTest = riskTest;
        sp.edit().putInt(SpType.RISK_TEST, riskTest).commit();
        return mInstance;
    }

    public int getAppType() {
        if (appType == 0) {
            appType = sp.getInt(SpType.APP_TYPE, 0);
        }
        return appType;
    }

    public UserInfoInstance setAppType(int appType) {
        this.appType = appType;
        sp.edit().putInt(SpType.APP_TYPE, appType).commit();
        return mInstance;
    }

    public float getCanUseMoney() {
        if (canUseMoney == 0) {
            canUseMoney = sp.getFloat(SpType.CAN_USE_MONEY, 0);
        }
        return canUseMoney;
    }

    public UserInfoInstance setCanUseMoney(float canUseMoney) {
        this.canUseMoney = canUseMoney;
        sp.edit().putFloat(SpType.CAN_USE_MONEY, canUseMoney).commit();
        return mInstance;
    }


    public String getCityCode() {
        if (TextUtils.isEmpty(cityCode)) {
            cityCode = sp.getString(SpType.CITY_CODE, "");
        }
        return cityCode;
    }

    public UserInfoInstance setCityCode(String cityCode) {
        this.cityCode = cityCode;
        sp.edit().putString(SpType.CITY_CODE, cityCode).commit();
        return mInstance;
    }


    public String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    public String getLock() {
        lock = sp.getString(SpType.LOCK + getUid(), "");
        return lock;
    }

    public UserInfoInstance setLock(String lock) {
        this.lock = lock;
        sp.edit().putString(SpType.LOCK + getUid(), lock).commit();
        return mInstance;
    }

    public String getIcon() {
        if (TextUtils.isEmpty(icon)) {
            icon = sp.getString(SpType.ICON_URL, "");
        }
        return icon;
    }

    public UserInfoInstance setIcon(String icon) {
        this.icon = icon;
        sp.edit().putString(SpType.ICON_URL, icon).commit();
        return mInstance;
    }

    public String getUid() {
        if (TextUtils.isEmpty(uid)) {
            uid = sp.getString(SpType.UID, "");
        }
        return uid;
    }

    public UserInfoInstance setUid(String uid) {
        this.uid = uid;
        sp.edit().putString(SpType.UID, uid).commit();
        return mInstance;
    }

    public String getUserName() {
        if (TextUtils.isEmpty(userName)) {
            userName = sp.getString(SpType.USER_NAME, "");
        }
        return userName;
    }

    public UserInfoInstance setUserName(String userName) {
        this.userName = userName;
        sp.edit().putString(SpType.USER_NAME, userName).commit();
        return mInstance;
    }

    public String getPhone() {
        if (TextUtils.isEmpty(phone)) {
            phone = sp.getString(SpType.PHONE_NUM, "");
        }
        return phone;
    }

    public UserInfoInstance setPhone(String phone) {
        this.phone = phone;
        sp.edit().putString(SpType.PHONE_NUM, phone).commit();
        return mInstance;
    }

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = sp.getString(SpType.TOKEN, "");
        }
        return token;
    }

    public UserInfoInstance setToken(String token) {
        this.token = token;
        sp.edit().putString(SpType.TOKEN, token).commit();
        return mInstance;
    }


    public String getP2pUid() {
        if (TextUtils.isEmpty(p2pUid)) {
            p2pUid = sp.getString(SpType.P2P_UID, "");
        }
        return p2pUid;
    }

    public UserInfoInstance setP2pUid(String p2pUid) {
        this.p2pUid = p2pUid;
        sp.edit().putString(SpType.P2P_UID, p2pUid).commit();
        return mInstance;
    }

    public String getNickName() {
        if (TextUtils.isEmpty(nickName)) {
            nickName = sp.getString(SpType.NICK_NAME, "");
        }
        return nickName;
    }

    public UserInfoInstance setNickName(String nickName) {
        this.nickName = nickName;
        sp.edit().putString(SpType.NICK_NAME, nickName).commit();
        return mInstance;
    }


    public UserInfoInstance setLogin(boolean login) {
        this.login = login;
        return mInstance;
    }

    public boolean isLogin() {
        return login;
    }

    public UserInfoInstance setHasBindCard(boolean hasBindCard) {
        this.hasBindCard = hasBindCard;
        sp.edit().putBoolean(SpType.HAS_BIND_CARD, hasBindCard).commit();
        return mInstance;
    }

    public boolean isHasBindCard() {
        if (hasBindCard == false) {
            hasBindCard = sp.getBoolean(SpType.HAS_BIND_CARD, false);
        }
        return hasBindCard;
    }

    public boolean isCreateAccount() {
        if (createAccount == false) {
            createAccount = sp.getBoolean(SpType.HAS_IPS_ACCOUNT, false);
        }
        return createAccount;
    }

    public UserInfoInstance setCreateAccount(boolean createAccount) {
        this.createAccount = createAccount;
        sp.edit().putBoolean(SpType.HAS_IPS_ACCOUNT, createAccount).commit();
        return mInstance;
    }

    public boolean isJpush() {
        boolean Jpush = sp.getBoolean(SpType.JPUSH, false);
        return Jpush;
    }

    public UserInfoInstance setJpush(boolean jpush) {
        sp.edit().putBoolean(SpType.JPUSH, jpush).commit();
        return mInstance;
    }

    public boolean isFirstLogin() {
       boolean firstLogin = sp.getBoolean(SpType.FIRST_LOGIN + uid, false);
        return firstLogin;
    }

    public UserInfoInstance setFirstLogin(boolean firstLogin) {
        sp.edit().putBoolean(SpType.FIRST_LOGIN + uid, firstLogin).commit();
        return mInstance;
    }

    public UserInfoInstance setLocation(String location) {
        this.location = location;
        sp.edit().putString(SpType.LOCATION, location).commit();
        return mInstance;
    }

    public String getLocation() {
        if (TextUtils.isEmpty(location)) {
            location = sp.getString(SpType.LOCATION, "");
        }
        return location;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }
}
