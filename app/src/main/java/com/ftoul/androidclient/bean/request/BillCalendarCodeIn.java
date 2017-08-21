package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by 蜂投网-Wusha on 2017/6/20.
 */

public class BillCalendarCodeIn extends BaseParamsVO {
    private String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public BillCalendarCodeIn(String dateTime) {

        this.dateTime = dateTime;
    }
}
