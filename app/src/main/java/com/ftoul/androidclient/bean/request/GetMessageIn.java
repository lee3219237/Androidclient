package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/7/14 0014.
 */

public class GetMessageIn extends BaseParamsVO {
    private int btype = 1;
    private String num;
    private String prePage;

    public GetMessageIn(int btype, String num, String prePage) {
        this.btype = btype;
        this.num = num;
        this.prePage = prePage;
    }

    public int getBtype() {
        return btype;
    }

    public void setBtype(int btype) {
        this.btype = btype;
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
