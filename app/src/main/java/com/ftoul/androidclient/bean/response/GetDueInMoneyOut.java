package com.ftoul.androidclient.bean.response;

/**
 * Created by ftoul106 on 2017/6/17 0017.
 */

public class GetDueInMoneyOut {
    private String total;
    private String totalInterest;
    private String cumulateInterest;

    @Override
    public String toString() {
        return "GetDueInMoneyOut{" +
                "total='" + total + '\'' +
                ", totalInterest='" + totalInterest + '\'' +
                ", cumulateInterest='" + cumulateInterest + '\'' +
                '}';
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getCumulateInterest() {
        return cumulateInterest;
    }

    public void setCumulateInterest(String cumulateInterest) {
        this.cumulateInterest = cumulateInterest;
    }
}
