package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/20.
 */

public class FmCouponsCodeOut {
    /**
     * page : {"items":[{"amount":300,"channel":"新春快乐","channelId":1,"expireTime":"2017-06-01 00:00:00","id":3,"investAmount":2000,"investStart":1,"investEnd":50,"receiveTime":"2017-06-01 19:38:41","remark":"1","status":1,"usedProductIds":"2,3","userId":100},{"amount":300,"channel":"新春快乐","channelId":1,"expireTime":"2017-06-01 00:00:00","id":6,"investAmount":2000,"investStart":1,"receiveTime":"2017-06-01 19:38:41","remark":"1","status":1,"usedProductIds":"2,3","userId":100}],"pageNum":1,"prePage":10,"totalPage":1,"totalRecords":9}
     */

    public PageBean page;

    public FmCouponsCodeOut(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * items : [{"amount":300,"channel":"新春快乐","channelId":1,"expireTime":"2017-06-01 00:00:00","id":3,"investAmount":2000,"investStart":1,"investEnd":50,"receiveTime":"2017-06-01 19:38:41","remark":"1","status":1,"usedProductIds":"2,3","userId":100},{"amount":300,"channel":"新春快乐","channelId":1,"expireTime":"2017-06-01 00:00:00","id":6,"investAmount":2000,"investStart":1,"receiveTime":"2017-06-01 19:38:41","remark":"1","status":1,"usedProductIds":"2,3","userId":100}]
         * pageNum : 1
         * prePage : 10
         * totalPage : 1
         * totalRecords : 9
         */

        public int pageNum;
        public int prePage;
        public int totalPage;
        public int totalRecords;
        public List<ItemsBean> items;

        public PageBean(int pageNum, int prePage, int totalPage, int totalRecords, List<ItemsBean> items) {
            this.pageNum = pageNum;
            this.prePage = prePage;
            this.totalPage = totalPage;
            this.totalRecords = totalRecords;
            this.items = items;
        }

        @Override
        public String toString() {
            return "PageBean{" +
                    "pageNum=" + pageNum +
                    ", prePage=" + prePage +
                    ", totalPage=" + totalPage +
                    ", totalRecords=" + totalRecords +
                    ", items=" + items +
                    '}';
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * amount : 300
             * channel : 新春快乐
             * channelId : 1
             * expireTime : 2017-06-01 00:00:00
             * id : 3
             * investAmount : 2000
             * investStart : 1
             * investEnd : 50
             * receiveTime : 2017-06-01 19:38:41
             * remark : 1
             * status : 1
             * usedProductIds : 2,3
             * userId : 100
             */

            public int amount;
            public String channel;
            public int channelId;
            public String expireTime;
            public int id;
            public int investAmount;
            public int investStart;
            public int investEnd;
            public String receiveTime;
            public String remark;
            public int status;
            public String usedProductIds;
            public int userId;

            public ItemsBean(int amount, String channel, int channelId, String expireTime, int id, int investAmount, int investStart, int investEnd, String receiveTime, String remark, int status, String usedProductIds, int userId) {
                this.amount = amount;
                this.channel = channel;
                this.channelId = channelId;
                this.expireTime = expireTime;
                this.id = id;
                this.investAmount = investAmount;
                this.investStart = investStart;
                this.investEnd = investEnd;
                this.receiveTime = receiveTime;
                this.remark = remark;
                this.status = status;
                this.usedProductIds = usedProductIds;
                this.userId = userId;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getInvestAmount() {
                return investAmount;
            }

            public void setInvestAmount(int investAmount) {
                this.investAmount = investAmount;
            }

            public int getInvestStart() {
                return investStart;
            }

            public void setInvestStart(int investStart) {
                this.investStart = investStart;
            }

            public int getInvestEnd() {
                return investEnd;
            }

            public void setInvestEnd(int investEnd) {
                this.investEnd = investEnd;
            }

            public String getReceiveTime() {
                return receiveTime;
            }

            public void setReceiveTime(String receiveTime) {
                this.receiveTime = receiveTime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUsedProductIds() {
                return usedProductIds;
            }

            public void setUsedProductIds(String usedProductIds) {
                this.usedProductIds = usedProductIds;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
