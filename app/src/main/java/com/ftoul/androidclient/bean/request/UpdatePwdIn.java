package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/7/5 0005.
 */

public class UpdatePwdIn extends BaseParamsVO {
    private String phone;
    private String oldpwd;
    private String pwd;

    public UpdatePwdIn(String phone, String oldpwd, String pwd) {
        this.phone = phone;
        this.oldpwd = oldpwd;
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOldpwd() {
        return oldpwd;
    }

    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
