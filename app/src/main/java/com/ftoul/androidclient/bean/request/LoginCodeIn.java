package com.ftoul.androidclient.bean.request;

/**
 * Created by 蜂投网-Wusha on 2017/6/28.
 */

public class LoginCodeIn  {
    private String username;
    private String password;
    private String isWeixin;

    public LoginCodeIn(String username, String password, String sWeixin) {
        this.username = username;
        this.password = password;
        this.isWeixin = sWeixin;
    }

    @Override
    public String toString() {
        return "LoginCodeIn{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sWeixin='" + isWeixin + '\'' +
                '}';
    }
}
