package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by ftoul106 on 2017/6/20 0020.
 */

public class GetTiYanMoneyOut {


    /**
     * page : {"items":[{"amount":2000,"channel":"体验标投资","channelId":1,"createTime":"2017-06-20 18:17:11","expireTime":"2017-06-19 00:00:00","expired":0,"id":5,"remark":"理财体验标","status":2,"userId":110}],"pageNum":1,"prePage":15,"totalPage":1,"totalRecords":1}
     * income : 30.05
     * money : 0.0
     */

    public PageBean page;
    public double income;
    public double money;

    public static class PageBean {
        /**
         * items : [{"amount":2000,"channel":"体验标投资","channelId":1,"createTime":"2017-06-20 18:17:11","expireTime":"2017-06-19 00:00:00","expired":0,"id":5,"remark":"理财体验标","status":2,"userId":110}]
         * pageNum : 1
         * prePage : 15
         * totalPage : 1
         * totalRecords : 1
         */

        public int pageNum;
        public int prePage;
        public int totalPage;
        public int totalRecords;
        public List<ItemsBean> items;

        public static class ItemsBean {
            public ItemsBean(double amount, String channel, int channelId, String createTime, String expireTime, int expired, int id, String remark, int status, int userId) {
                this.amount = amount;
                this.channel = channel;
                this.channelId = channelId;
                this.createTime = createTime;
                this.expireTime = expireTime;
                this.expired = expired;
                this.id = id;
                this.remark = remark;
                this.status = status;
                this.userId = userId;
            }

            /**
             * amount : 2000.0
             * channel : 体验标投资
             * channelId : 1
             * createTime : 2017-06-20 18:17:11
             * expireTime : 2017-06-19 00:00:00
             * expired : 0
             * id : 5
             * remark : 理财体验标
             * status : 2
             * userId : 110
             */

            public double amount;
            public String channel;
            public int channelId;
            public String createTime;
            public String expireTime;
            public int expired;
            public int id;
            public String remark;
            public int status;
            public int userId;
        }
    }
}
