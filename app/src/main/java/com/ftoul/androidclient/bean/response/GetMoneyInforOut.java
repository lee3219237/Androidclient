package com.ftoul.androidclient.bean.response;

/**
 * Created by ftoul106 on 2017/6/17 0017.
 */

public class GetMoneyInforOut {

    /**
     * assetsDetail : {"availablebal":"10000.00","cumulativeInvest":"0.00","cumulativeRecharge":"0.00","dueInCorpus":"1400.00","dueInIncomes":"552.00","dueInInterest":"140.00","frozbl":"100.00","fundIncomes":"10.00","interestRateIncomes":"6.00","inviteIncomes":"0.00","joinInDays":"18","receivedInterest":"30.00","redpacketIncomes":"200.00","registerDate":"2017-05-29","totalAssets":"10000.00"}
     */

    public AssetsDetailBean assetsDetail;

    public static class AssetsDetailBean {
        /**
         * availablebal : 10000.00
         * cumulativeInvest : 0.00
         * cumulativeRecharge : 0.00
         * dueInCorpus : 1400.00
         * dueInIncomes : 552.00
         * dueInInterest : 140.00
         * frozbl : 100.00
         * fundIncomes : 10.00
         * interestRateIncomes : 6.00
         * inviteIncomes : 0.00
         * joinInDays : 18
         * receivedInterest : 30.00
         * redpacketIncomes : 200.00
         * registerDate : 2017-05-29
         * totalAssets : 10000.00
         */

        public String availablebal;
        public String cumulativeInvest;
        public String cumulativeRecharge;
        public String dueInCorpus;
        public String dueInIncomes;
        public String dueInInterest;
        public String frozbl;
        public String fundIncomes;
        public String interestRateIncomes;
        public String inviteIncomes;
        public String joinInDays;
        public String receivedInterest;
        public String redpacketIncomes;
        public String registerDate;
        public String totalAssets;
    }
}
