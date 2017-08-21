package com.ftoul.androidclient.bean;

/**
 * Created by ftoul106 on 2017/6/21 0021.
 */

public class ReturnedMoneyBean {

    /**
     * id : 223
     * receiveCorpus : 0
     * receiveInterest : 36.11
     * receiveTime : 2017-11-17 09:20:32
     * status : 0
     * title : 小贷通2017061603
     * totalInterest : 0
     */

    public int id;
    public int receiveCorpus;
    public double receiveInterest;
    public String receiveTime;
    public int status;
    public String title;
    public int totalInterest;

    public ReturnedMoneyBean(String receiveTime, String title) {
        this.receiveTime = receiveTime;
        this.title = title;
    }
}
