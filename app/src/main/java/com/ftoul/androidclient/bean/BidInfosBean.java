package com.ftoul.androidclient.bean;

import java.io.Serializable;

/**
 * Created by ftoul106 on 2017/5/10 0010.
 */

public class BidInfosBean implements Serializable{

    /**
     * apr : 2.0
     * id : 64
     * minMoney : 12.0
     * period : 2
     * surplusMoney : -87250.0
     * title : 123
     */

    public float apr;
    public int id;
    public double minMoney;
    public int period;
    public int periodType;
    public float surplusMoney;
    public String title;
    public String refundType;
    public double maxMoney;

    public double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public int getPeriodType() {
        return periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public float getApr() {
        return apr;
    }

    public void setApr(float apr) {
        this.apr = apr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(double minMoney) {
        this.minMoney = minMoney;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public float getSurplusMoney() {
        return surplusMoney;
    }

    public void setSurplusMoney(float surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BidInfosBean{" +
                "apr=" + apr +
                ", id=" + id +
                ", minMoney=" + minMoney +
                ", period=" + period +
                ", periodType=" + periodType +
                ", surplusMoney=" + surplusMoney +
                ", title='" + title + '\'' +
                ", refundType='" + refundType + '\'' +
                ", maxMoney=" + maxMoney +
                '}';
    }
}
