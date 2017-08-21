package com.ftoul.androidclient.bean.response;

/**
 * Created by 蜂投网-Wusha on 2017/6/30.
 */

public class LoginErrorCodeOut  {
    /**
     * errMsg : 用户不存在
     */

    public String errMsg;

    public LoginErrorCodeOut(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
