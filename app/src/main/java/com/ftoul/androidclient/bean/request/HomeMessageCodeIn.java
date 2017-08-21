package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by 蜂投网-Wusha on 2017/6/19.
 */

public class HomeMessageCodeIn extends BaseParamsVO {
    private String id = "";

    public HomeMessageCodeIn(String id) {
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
        return "HomeMessageCodeIn{" +
                "id='" + id + '\'' +
                '}';
    }
}
