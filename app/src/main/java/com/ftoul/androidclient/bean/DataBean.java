package com.ftoul.androidclient.bean;

/**
 * Created by ftoul106 on 2017/6/8 0008.
 */

public class DataBean<T> {
    private String transcode;
    private String errCode;
    private String errMsg;
    private T body;

    public DataBean(String transcode) {
        this.transcode = transcode;
    }


    @Override
    public String toString() {
        return "DataBean{" +
                "transcode='" + transcode + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", body=" + body +
                '}';
    }

    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
