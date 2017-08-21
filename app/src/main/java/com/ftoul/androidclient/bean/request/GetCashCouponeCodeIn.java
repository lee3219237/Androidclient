package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by 蜂投网-Wusha on 2017/6/21.
 */

public class GetCashCouponeCodeIn  extends BaseParamsVO{
private String id;

    public GetCashCouponeCodeIn(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetCashCouponeCodeIn{" +
                "id='" + id + '\'' +
                '}';
    }
}
