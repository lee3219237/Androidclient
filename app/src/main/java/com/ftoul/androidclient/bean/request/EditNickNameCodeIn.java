package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by 蜂投网-Wusha on 2017/6/17.
 */

public class EditNickNameCodeIn extends BaseParamsVO {
    private String nickName;


    public EditNickNameCodeIn(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "EditNickNameCodeIn{" +
                "nickName='" + nickName + '\'' +
                '}';
    }
}
