package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/19 0019.
 */

public class GetFengMiInfor extends BaseParamsVO {
    private int status;
    private String num;
    private String prePage;

    public GetFengMiInfor(int status, String num, String prePage) {
        this.status = status;
        this.num = num;
        this.prePage = prePage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }
}
