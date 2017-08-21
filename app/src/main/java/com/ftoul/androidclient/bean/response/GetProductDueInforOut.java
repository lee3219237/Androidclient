package com.ftoul.androidclient.bean.response;

import java.io.Serializable;

/**
 * Created by ftoul106 on 2017/6/17 0017.
 */

public class GetProductDueInforOut implements Serializable {


    /**
     * id : 53048
     * productId : null
     * bidId : 4713
     * createtime : 2017-08-01 11:22:59
     * userId : null
     * investAmount : 1000
     * fee : null
     * status : null
     * investPactUrl : null
     * investReqseqno : null
     * thirdBillno : null
     * assignmentId : null
     * investSite : 3
     * channelType : null
     * isFirst : 0
     * investType : null
     * contractId : null
     * extField1 : null
     * extField2 : null
     * extField3 : null
     * rateday : 2017-08-01 00:00:00
     * investobjstate : 5
     * receiveCorpus : 1000
     * welfareAmount : 0
     * receiveInterest : 16.66
     * addRateAmount : 0
     * loginName : null
     * realityName : null
     * calcRatedayValue : null
     * bidNo : null
     * loanNo : 3150155607958165
     * productName : 小贷通
     * title : T07_小贷通_按月付息_T+0
     * period : 2
     * apr : 10
     * welfareTypeValue : null
     * amount : null
     * repaymentTypeName : null
     * periodType : 1
     * welfareId : 0
     * idNumber : null
     * accountNo : null
     * bidStatus : 0
     * startInterestDate : 2017-08-02 14:29:25
     * refundType : 按月付息,到期还本
     * income : 8.33
     * dueIncome : 8.33
     * ssoUserInfo : null
     */


    private int id;
    private int bidId;
    private String createtime;
    private double investAmount;
    private int investSite;
    private int isFirst;
    private String rateday;
    private int investobjstate;
    private int receiveCorpus;
    private int welfareAmount;
    private double receiveInterest;
    private int addRateAmount;
    private String loanNo;
    private String productName;
    private String title;
    private int period;
    private int apr;
    private int periodType;
    private int welfareId;
    private int bidStatus;
    private String startInterestDate;
    private String refundType;
    private String income;
    private String dueIncome;
    private String investPactUrl;

    public String getInvestPactUrl() {
        return investPactUrl;
    }

    public void setInvestPactUrl(String investPactUrl) {
        this.investPactUrl = investPactUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public double getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(double investAmount) {
        this.investAmount = investAmount;
    }

    public int getInvestSite() {
        return investSite;
    }

    public void setInvestSite(int investSite) {
        this.investSite = investSite;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public String getRateday() {
        return rateday;
    }

    public void setRateday(String rateday) {
        this.rateday = rateday;
    }

    public int getInvestobjstate() {
        return investobjstate;
    }

    public void setInvestobjstate(int investobjstate) {
        this.investobjstate = investobjstate;
    }

    public int getReceiveCorpus() {
        return receiveCorpus;
    }

    public void setReceiveCorpus(int receiveCorpus) {
        this.receiveCorpus = receiveCorpus;
    }

    public int getWelfareAmount() {
        return welfareAmount;
    }

    public void setWelfareAmount(int welfareAmount) {
        this.welfareAmount = welfareAmount;
    }

    public double getReceiveInterest() {
        return receiveInterest;
    }

    public void setReceiveInterest(double receiveInterest) {
        this.receiveInterest = receiveInterest;
    }

    public int getAddRateAmount() {
        return addRateAmount;
    }

    public void setAddRateAmount(int addRateAmount) {
        this.addRateAmount = addRateAmount;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getApr() {
        return apr;
    }

    public void setApr(int apr) {
        this.apr = apr;
    }

    public int getPeriodType() {
        return periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public int getWelfareId() {
        return welfareId;
    }

    public void setWelfareId(int welfareId) {
        this.welfareId = welfareId;
    }

    public int getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(int bidStatus) {
        this.bidStatus = bidStatus;
    }

    public String getStartInterestDate() {
        return startInterestDate;
    }

    public void setStartInterestDate(String startInterestDate) {
        this.startInterestDate = startInterestDate;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getDueIncome() {
        return dueIncome;
    }

    public void setDueIncome(String dueIncome) {
        this.dueIncome = dueIncome;
    }
}
