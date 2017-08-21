package com.ftoul.androidclient.bean;

import com.ftoul.androidclient.global.MyApp;

/**
 * Created by ftoul106 on 2017/6/8 0008.
 */

public class AbstractBaseParamsVO {
    protected String uid ="";
    private int device = 2;
    private String machineCode = MyApp.machineCode;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }
}
