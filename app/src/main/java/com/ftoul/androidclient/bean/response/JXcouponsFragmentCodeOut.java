package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/21.
 */

public class JXcouponsFragmentCodeOut {
    /**
     * page : {"items":[{"addDay":5,"addRate":5,"channel":"五周年庆5","channelId":1,"createTime":"2017-06-07 16:33:41","currentMinAmount":1000,"expireTime":"2017-08-06 16:33:41","useTime":"2017-07-06 16:33:41","id":4,"investEnd":50,"investStart":20,"isUsed":1,"remark":"回款赠券","usedProductIds":"1,2","userId":100},{"addDay":5,"addRate":5,"channel":"五周年庆5","channelId":1,"createTime":"2017-06-07 16:33:41","currentMinAmount":1000,"expireTime":"2017-08-06 16:33:41","id":5,"investEnd":50,"investStart":20,"isUsed":0,"remark":"回款赠券","usedProductIds":"1,2","userId":100}],"pageNum":1,"prePage":10,"totalPage":1,"totalRecords":10}
     */

    public PageBean page;

    public JXcouponsFragmentCodeOut(PageBean page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "JXcouponsFragmentCodeOut{" +
                "page=" + page +
                '}';
    }


    public static class PageBean {
        /**
         * items : [{"addDay":5,"addRate":5,"channel":"五周年庆5","channelId":1,"createTime":"2017-06-07 16:33:41","currentMinAmount":1000,"expireTime":"2017-08-06 16:33:41","useTime":"2017-07-06 16:33:41","id":4,"investEnd":50,"investStart":20,"isUsed":1,"remark":"回款赠券","usedProductIds":"1,2","userId":100},{"addDay":5,"addRate":5,"channel":"五周年庆5","channelId":1,"createTime":"2017-06-07 16:33:41","currentMinAmount":1000,"expireTime":"2017-08-06 16:33:41","id":5,"investEnd":50,"investStart":20,"isUsed":0,"remark":"回款赠券","usedProductIds":"1,2","userId":100}]
         * pageNum : 1
         * prePage : 10
         * totalPage : 1
         * totalRecords : 10
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

        public static class ItemsBean {
            /**
             * addDay : 5
             * addRate : 5
             * channel : 五周年庆5
             * channelId : 1
             * createTime : 2017-06-07 16:33:41
             * currentMinAmount : 1000
             * expireTime : 2017-08-06 16:33:41
             * useTime : 2017-07-06 16:33:41
             * id : 4
             * investEnd : 50
             * investStart : 20
             * isUsed : 1
             * remark : 回款赠券
             * usedProductIds : 1,2
             * userId : 100
             */

            public int addDay;
            public int addRate;
            public String channel;
            public int channelId;
            public String createTime;
            public int currentMinAmount;
            public String expireTime;
            public String useTime;
            public int id;
            public int investEnd;
            public int investStart;
            public int isUsed;
            public String remark;
            public String usedProductIds;
            public int userId;

            public ItemsBean(int addDay, int addRate, String channel, int channelId, String createTime, int currentMinAmount, String expireTime, String useTime, int id, int investEnd, int investStart, int isUsed, String remark, String usedProductIds, int userId) {
                this.addDay = addDay;
                this.addRate = addRate;
                this.channel = channel;
                this.channelId = channelId;
                this.createTime = createTime;
                this.currentMinAmount = currentMinAmount;
                this.expireTime = expireTime;
                this.useTime = useTime;
                this.id = id;
                this.investEnd = investEnd;
                this.investStart = investStart;
                this.isUsed = isUsed;
                this.remark = remark;
                this.usedProductIds = usedProductIds;
                this.userId = userId;
            }

            public int getAddDay() {
                return addDay;
            }

            public void setAddDay(int addDay) {
                this.addDay = addDay;
            }

            public int getAddRate() {
                return addRate;
            }

            public void setAddRate(int addRate) {
                this.addRate = addRate;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCurrentMinAmount() {
                return currentMinAmount;
            }

            public void setCurrentMinAmount(int currentMinAmount) {
                this.currentMinAmount = currentMinAmount;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public String getUseTime() {
                return useTime;
            }

            public void setUseTime(String useTime) {
                this.useTime = useTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getInvestEnd() {
                return investEnd;
            }

            public void setInvestEnd(int investEnd) {
                this.investEnd = investEnd;
            }

            public int getInvestStart() {
                return investStart;
            }

            public void setInvestStart(int investStart) {
                this.investStart = investStart;
            }

            public int getIsUsed() {
                return isUsed;
            }

            public void setIsUsed(int isUsed) {
                this.isUsed = isUsed;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
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
