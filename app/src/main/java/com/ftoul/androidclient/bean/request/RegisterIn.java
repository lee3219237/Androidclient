package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.AbstractBaseParamsVO;
import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/7/1 0001.
 */

public class RegisterIn extends AbstractBaseParamsVO {
    private String mobile;//String	是	13667314945	手机号合法性验证	手机号码
    private String password;//String	是	Abc123	大于等于6位，字母数字，下划线	密码
    private String invitationCode;//String	是			注册渠道号
    private String smsCode;//String	是	25012		短信验证码
    private String phoneModel;//	String	否	FRD-001		手机型号
    private String scene;//String	否		客户互相推荐邀请码	邀请码
    private String uuid;//String	否		客户互相推荐邀请码	邀请码
    private String imageCode;//String	否		客户互相推荐邀请码	邀请码

    public RegisterIn(String mobile, String password, String invitationCode, String smsCode, String phoneModel, String scene,String imageCode) {
        this.mobile = mobile;
        this.password = password;
        this.invitationCode = invitationCode;
        this.smsCode = smsCode;
        this.phoneModel = phoneModel;
        this.scene = scene;
        this.uuid = getMachineCode();
        this.imageCode = imageCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
