package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.AbstractBaseParamsVO;

/**
 * Created by ftoul106 on 2017/7/5 0005.
 */

public class GetBackPwdIn extends AbstractBaseParamsVO {
    private String phone;
    private String pwd;
    private String code;
    private String uuid;


    public GetBackPwdIn(String phone, String pwd, String code) {
        this.phone = phone;
        this.pwd = pwd;
        this.code = code;
        uuid = getMachineCode();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
