package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by ftoul106 on 2017/6/20 0020.
 */

public class GetTiYanInvestOut {

    /**
     * page : {"items":[{"amount":2000,"createTime":"2017-06-03 09:43:54","fundSourceId":4,"id":20,"profit":98.63,"status":0,"userId":100},{"amount":1500,"createTime":"2017-06-03 09:43:54","fundSourceId":3,"id":19,"profit":73.97,"status":0,"userId":100}],"pageNum":1,"prePage":10,"totalPage":1,"totalRecords":8}
     */

    public PageBean page;

    public static class PageBean {
        /**
         * items : [{"amount":2000,"createTime":"2017-06-03 09:43:54","fundSourceId":4,"id":20,"profit":98.63,"status":0,"userId":100},{"amount":1500,"createTime":"2017-06-03 09:43:54","fundSourceId":3,"id":19,"profit":73.97,"status":0,"userId":100}]
         * pageNum : 1
         * prePage : 10
         * totalPage : 1
         * totalRecords : 8
         */

        public int pageNum;
        public int prePage;
        public int totalPage;
        public int totalRecords;
        public List<ItemsBean> items;

        public static class ItemsBean {
            /**
             * amount : 2000
             * createTime : 2017-06-03 09:43:54
             * fundSourceId : 4
             * id : 20
             * profit : 98.63
             * status : 0
             * userId : 100
             */

            public int amount;
            public String createTime;
            public int fundSourceId;
            public int id;
            public double profit;
            public int status;
            public int userId;
        }
    }
}
