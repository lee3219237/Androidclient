package com.ftoul.androidclient.bean.response;

/**
 * Created by 蜂投网-Wusha on 2017/6/17.
 */

public class EditNickNameCodeOut {
    /**
     * msg : 修改昵称成功
     */

    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "EditNickNameCodeOut{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
