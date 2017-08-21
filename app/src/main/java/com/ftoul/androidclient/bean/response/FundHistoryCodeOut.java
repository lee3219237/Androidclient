package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/19.
 */

public class FundHistoryCodeOut {

    public List<FundHistoryBean> fundHistory;

    public FundHistoryCodeOut(List<FundHistoryBean> fundHistory) {
        this.fundHistory = fundHistory;
    }

    public List<FundHistoryBean> getFundHistory() {
        return fundHistory;
    }

    public void setFundHistory(List<FundHistoryBean> fundHistory) {
        this.fundHistory = fundHistory;
    }

    @Override
    public String toString() {
        return "FundHistoryCodeOut{" +
                "fundHistory=" + fundHistory +
                '}';
    }

    public static class FundHistoryBean {
        /**
         * summary : 华兴投标冻结
         * amount : 100
         * balance : 900
         * time : 1496835209000
         */

        public String summary;
        public String amount;
        public double balance;
        public String time;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
