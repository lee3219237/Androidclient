package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.AbstractBaseParamsVO;

/**
 * Created by ftoul106 on 2017/7/7 0007.
 */

public class WeiXinBindIn extends AbstractBaseParamsVO {
    private String phone;
    private String code;
    private String unionId;
    private String accessToken;
    private String openId;
    private String ipAddress;

    public WeiXinBindIn(String phone, String code, String unionId, String accessToken, String openId, String ipAddress) {
        this.phone = phone;
        this.code = code;
        this.unionId = unionId;
        this.accessToken = accessToken;
        this.openId = openId;
        this.ipAddress = ipAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
