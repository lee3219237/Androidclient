package com.ftoul.androidclient.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ftoul106 on 2017/6/21 0021.
 */

public class BillCalendarCodeOut {

    /**
     * investDays : {"2017-06-16":[{"apr":1,"bidId":60,"channelType":0,"createtime":1497593808000,"investAmount":1000,"investType":0,"isFirst":1,"period":0,"periodType":0,"productName":"在测试一哈","repaymentTypeName":"等额本息","title":"123","welfareId":0},{"apr":13,"bidId":101,"channelType":1,"createtime":1497608156000,"investAmount":610,"period":0,"periodType":0,"productName":"小贷通","repaymentTypeName":"按月付息，到期还本","title":"小贷通20170616-02","welfareId":0}]}
     * repaymentDays : {"2017-11-17":[{"id":223,"receiveCorpus":0,"receiveInterest":36.11,"receiveTime":"2017-11-17 09:20:32","status":0,"title":"小贷通2017061603","totalInterest":0},{"id":235,"receiveCorpus":0,"receiveInterest":57.54,"receiveTime":"2017-11-17 09:20:34","status":0,"title":"小贷通2017061603","totalInterest":0}],"2017-11-16":[{"id":61,"receiveCorpus":0,"receiveInterest":10,"receiveTime":"2017-11-16 11:27:30","status":0,"title":"小贷通20170615-01","totalInterest":0}]}
     */

    public InvestDaysBean investDays;
    public RepaymentDaysBean repaymentDays;

    public static class InvestDaysBean {
        @SerializedName("2017-06-16")
        public List<_$20170616Bean> _$20170616;

        public static class _$20170616Bean {
            /**
             * apr : 1
             * bidId : 60
             * channelType : 0
             * createtime : 1497593808000
             * investAmount : 1000
             * investType : 0
             * isFirst : 1
             * period : 0
             * periodType : 0
             * productName : 在测试一哈
             * repaymentTypeName : 等额本息
             * title : 123
             * welfareId : 0
             */

            public int apr;
            public int bidId;
            public int channelType;
            public long createtime;
            public int investAmount;
            public int investType;
            public int isFirst;
            public int period;
            public int periodType;
            public String productName;
            public String repaymentTypeName;
            public String title;
            public int welfareId;
        }
    }

    public static class RepaymentDaysBean {
        @SerializedName("2017-11-17")
        public List<_$20171117Bean> _$20171117;
        @SerializedName("2017-11-16")
        public List<_$20171116Bean> _$20171116;

        public static class _$20171117Bean {
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
        }

        public static class _$20171116Bean {
            /**
             * id : 61
             * receiveCorpus : 0
             * receiveInterest : 10
             * receiveTime : 2017-11-16 11:27:30
             * status : 0
             * title : 小贷通20170615-01
             * totalInterest : 0
             */

            public int id;
            public int receiveCorpus;
            public int receiveInterest;
            public String receiveTime;
            public int status;
            public String title;
            public int totalInterest;
        }
    }
}
