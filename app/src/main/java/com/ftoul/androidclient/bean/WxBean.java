package com.ftoul.androidclient.bean;

import java.io.Serializable;

/**
 * Created by M on 2016/10/18.
 */

public class WxBean implements Serializable{
    String access_token;
    String openid;
    String unionid;
    public WxBean(String access_token, String openid, String unionid) {
        this.access_token = access_token;
        this.openid = openid;
        this.unionid = unionid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
