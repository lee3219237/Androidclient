package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.AbstractBaseParamsVO;
import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/14 0014.
 */

public class LoginIn extends AbstractBaseParamsVO{
    private String from;//"0"普通登录，"1"微信登录
    private String phone;
    private String pwd;
    private String ipAddress;
    private String unionId;
    private String accessToken;
    private String openId;
    private String phoneModel;
    private String local;

    public LoginIn(String form, String phone, String pwd, String ipAddress, String phoneModel, String local) {
        this.from = form;
        this.phone = phone;
        this.pwd = pwd;
        this.ipAddress = ipAddress;
        this.phoneModel = phoneModel;
        this.local = local;
    }

    public LoginIn(String form, String ipAddress, String unionId, String accessToken, String openId, String phoneModel, String local) {
        this.from = form;
        this.ipAddress = ipAddress;
        this.unionId = unionId;
        this.accessToken = accessToken;
        this.openId = openId;
        this.phoneModel = phoneModel;
        this.local = local;
    }

    public String getForm() {
        return from;
    }

    public void setForm(String form) {
        this.from = form;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "LoginIn{" +
                "form='" + from + '\'' +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", unionId='" + unionId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", openId='" + openId + '\'' +
                ", phoneModel='" + phoneModel + '\'' +
                ", local='" + local + '\'' +
                '}';
    }
}
