package com.ftoul.androidclient.bean.response;

/**
 * Created by ftoul106 on 2017/5/9 0009.
 */

public class TreasureBean {
    public String imgUrl;
    public String name;
    public int process;

    public TreasureBean(String imgUrl, String name, int process) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.process = process;
    }
}
