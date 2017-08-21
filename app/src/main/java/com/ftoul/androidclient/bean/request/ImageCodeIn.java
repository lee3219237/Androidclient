package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.AbstractBaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/8 0008.
 */

public class ImageCodeIn extends AbstractBaseParamsVO {
    private String uuid;
    private int codeType;

    public ImageCodeIn(int codeType) {
        this.codeType = codeType;
        this.uuid = getMachineCode();
    }
    public ImageCodeIn() {
        this.uuid = getMachineCode();
    }

    public int getCodeType() {
        return codeType;
    }

    public void setCodeType(int codeType) {
        this.codeType = codeType;
    }

    @Override
    public String toString() {
        return "ImageCodeIn{" +
                "codeType='" + codeType + '\'' +
                '}';
    }
}
