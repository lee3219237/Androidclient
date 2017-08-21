package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by ftoul106 on 2017/6/19 0019.
 */

public class GetReturnMoneyPlanOut {

    public List<PlanInfosBean> planInfos;

    public static class PlanInfosBean {
        /**
         * id : 1
         * title : 123
         * status : 1
         * totalInterest : 16
         * receiveInterest : 10
         * receiveTime : 2017-06-03 16:03:24
         * receiveCorpus : 100
         */

        public int id;
        public String title;
        public int status;
        public float totalInterest;
        public float receiveInterest;
        public String receiveTime;
        public float receiveCorpus;
        public String description;
    }
}
