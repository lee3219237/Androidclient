package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by ftoul106 on 2017/6/19 0019.
 */

public class GetFengBiInforOut {

    /**
     * count : 50
     * page : {"items":[{"channel":"1","channelId":1,"coinType":2,"quantity":50,"remark":"一样","startTime":1496392421000},{"channel":"61儿童节","channelId":1,"coinType":1,"quantity":50,"remark":"33","startTime":1496306021000},{"channel":"端午节","channelId":1,"coinType":1,"quantity":50,"remark":"测试","startTime":1496219621000}],"pageNum":1,"prePage":15,"totalPage":1,"totalRecords":3}
     * alignOverdue : 0
     */

    public int count;
    public PageBean page;
    public int alignOverdue;

    public static class PageBean {
        /**
         * items : [{"channel":"1","channelId":1,"coinType":2,"quantity":50,"remark":"一样","startTime":1496392421000},{"channel":"61儿童节","channelId":1,"coinType":1,"quantity":50,"remark":"33","startTime":1496306021000},{"channel":"端午节","channelId":1,"coinType":1,"quantity":50,"remark":"测试","startTime":1496219621000}]
         * pageNum : 1
         * prePage : 15
         * totalPage : 1
         * totalRecords : 3
         */

        public int pageNum;
        public int prePage;
        public int totalPage;
        public int totalRecords;
        public List<ItemsBean> items;

        public static class ItemsBean {
            /**
             * channel : 1
             * channelId : 1
             * coinType : 2
             * quantity : 50
             * remark : 一样
             * startTime : 1496392421000
             */

            public String channel;
            public int channelId;
            public int coinType;
            public int quantity;
            public String remark;
            public long startTime;
        }
    }
}
