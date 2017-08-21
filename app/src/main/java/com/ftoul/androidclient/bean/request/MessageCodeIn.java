package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.AbstractBaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/8 0008.
 */

public class MessageCodeIn extends AbstractBaseParamsVO {
    private String phone;
    private String imgCode;
    private String uuid;
    private String channel = "1";
    private int codeType;
    private String mobile;


    public MessageCodeIn(String phone, String imgCode, int codeType) {
        uuid = getMachineCode();
        this.phone = phone;
        mobile = phone;
        this.imgCode = imgCode;
        this.codeType = codeType;
        uid = null;
    }

    public MessageCodeIn(String phone, int codeType) {
        uuid = getMachineCode();
        this.phone = phone;
        mobile = phone;
        this.codeType = codeType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public int getCodeType() {
        return codeType;
    }

    public void setCodeType(int codeType) {
        this.codeType = codeType;
    }

    @Override
    public String toString() {
        return "MessageCodeIn{" +
                "phone='" + phone + '\'' +
                ", imgCode='" + imgCode + '\'' +
                ", codeType=" + codeType +
                '}';
    }
}
