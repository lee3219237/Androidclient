package com.ftoul.androidclient.bean;

import java.io.Serializable;

/**
 * Created by ftoul106 on 2017/8/8 0008.
 */

public class NewBidInfosBean implements Serializable {

    /**
     * id : null
     * bidId : 4717
     * bidNo : null
     * title : T11_蜂计划_按月付息_T+0
     * loanNo : null
     * bidAmount : null
     * minInvestAmount : 1.0
     * maxInvestAmount : null
     * collectPeriod : null
     * collectStartTime : 2017-08-01 14:28:30
     * collectEndTime : null
     * apr : 10.0
     * periodType : 1
     * period : 2
     * bwtotalnum : null
     * bidReqseqno : null
     * thirdOrderNo : null
     * bidNotifyTime : null
     * isTransfer : null
     * refloanno : null
     * oldreqseq : null
     * investobjstate : 2
     * borrowerId : null
     * bwacname : null
     * bwacno : null
     * channelType : null
     * createtime : null
     * supportWelfare : null
     * fmGradeId : 103003
     * fmGradeName : A
     * repaymentTypeId : 112001
     * repaymentTypeName : 按月付息，到期还本
     * overPlus : 13000.0
     * investSchedule : 13.00
     * productId : null
     * supportWelfareName :
     * investTerm : 2个月
     * interestRate : 10.0
     * riskLevel : null
     */
//    public float apr;
//    public int id;
//    public double minMoney;
//    public int period;
//    public int periodType;
//    public float surplusMoney;
//    public String title;
//    public String refundType;
//    public double maxMoney;
    public int bidId;
    public String title;
    public double minInvestAmount;
    public double apr;
    public int periodType;
    public int period;
    public int investobjstate;
    public int fmGradeId;
    public String fmGradeName;
    public String repaymentTypeName;
    public double overPlus;
    public String investTerm;
    public String interestRate;
    public double maxInvestAmount;


    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(double minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }



    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public int getPeriodType() {
        return periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getInvestobjstate() {
        return investobjstate;
    }

    public void setInvestobjstate(int investobjstate) {
        this.investobjstate = investobjstate;
    }

    public int getFmGradeId() {
        return fmGradeId;
    }

    public void setFmGradeId(int fmGradeId) {
        this.fmGradeId = fmGradeId;
    }

    public String getFmGradeName() {
        return fmGradeName;
    }

    public void setFmGradeName(String fmGradeName) {
        this.fmGradeName = fmGradeName;
    }


    public String getRepaymentTypeName() {
        return repaymentTypeName;
    }

    public void setRepaymentTypeName(String repaymentTypeName) {
        this.repaymentTypeName = repaymentTypeName;
    }

    public double getOverPlus() {
        return overPlus;
    }

    public void setOverPlus(double overPlus) {
        this.overPlus = overPlus;
    }


    public String getInvestTerm() {
        return investTerm;
    }

    public void setInvestTerm(String investTerm) {
        this.investTerm = investTerm;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }
}
