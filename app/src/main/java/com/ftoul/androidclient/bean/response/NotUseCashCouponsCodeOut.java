package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/20.
 */

public class NotUseCashCouponsCodeOut  {

    /**
     * page : {"items":[{"amount":30.05,"applyStatus":1,"applyTime":"2017-06-21 09:56:09","cashReqNo":"OGW01201706211112453659OGW929512","channel":"体验标投资","channelId":1,"expireTime":"2017-06-28 00:00:00","id":10,"md5":"19d724859d185f500b23accd80e2a471","receiveTime":"2017-06-21 09:52:07","remark":"体验金收益","status":1,"transStatus":1,"transTime":1495245787000},{"amount":50,"applyStatus":1,"applyTime":"2017-06-02 18:55:25","cashReqNo":"OGW01201706171110740771OGW464657","channel":"蜂计划投资","channelId":1,"expireTime":"2017-06-30 00:00:00","id":2,"receiveTime":"2017-05-31 19:17:55","remark":"资金站岗利息补贴","status":1,"transStatus":1,"transTime":1495267191000},{"amount":100.22,"applyStatus":1,"applyTime":"2017-06-02 18:55:30","cashReqNo":"OGW01201706171110758821OGW325632","channel":"蜂狂合伙人奖励","channelId":1,"expireTime":"2017-06-30 00:00:00","id":3,"receiveTime":"2017-05-31 19:17:55","remark":"被邀请人首投返现","status":1,"transStatus":1,"transTime":1495270255000}],"pageNum":1,"prePage":15,"totalPage":1,"totalRecords":3}
     */

    public PageBean page;

    public NotUseCashCouponsCodeOut(PageBean page) {
        this.page = page;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "NotUseCashCouponsCodeOut{" +
                "page=" + page +
                '}';
    }

    public static class PageBean {
        /**
         * items : [{"amount":30.05,"applyStatus":1,"applyTime":"2017-06-21 09:56:09","cashReqNo":"OGW01201706211112453659OGW929512","channel":"体验标投资","channelId":1,"expireTime":"2017-06-28 00:00:00","id":10,"md5":"19d724859d185f500b23accd80e2a471","receiveTime":"2017-06-21 09:52:07","remark":"体验金收益","status":1,"transStatus":1,"transTime":1495245787000},{"amount":50,"applyStatus":1,"applyTime":"2017-06-02 18:55:25","cashReqNo":"OGW01201706171110740771OGW464657","channel":"蜂计划投资","channelId":1,"expireTime":"2017-06-30 00:00:00","id":2,"receiveTime":"2017-05-31 19:17:55","remark":"资金站岗利息补贴","status":1,"transStatus":1,"transTime":1495267191000},{"amount":100.22,"applyStatus":1,"applyTime":"2017-06-02 18:55:30","cashReqNo":"OGW01201706171110758821OGW325632","channel":"蜂狂合伙人奖励","channelId":1,"expireTime":"2017-06-30 00:00:00","id":3,"receiveTime":"2017-05-31 19:17:55","remark":"被邀请人首投返现","status":1,"transStatus":1,"transTime":1495270255000}]
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
             * amount : 30.05
             * applyStatus : 1
             * applyTime : 2017-06-21 09:56:09
             * cashReqNo : OGW01201706211112453659OGW929512
             * channel : 体验标投资
             * channelId : 1
             * expireTime : 2017-06-28 00:00:00
             * id : 10
             * md5 : 19d724859d185f500b23accd80e2a471
             * receiveTime : 2017-06-21 09:52:07
             * remark : 体验金收益
             * status : 1
             * transStatus : 1
             * transTime : 1495245787000
             */

            public double amount;
            public int applyStatus;
            public String applyTime;
            public String cashReqNo;
            public String channel;
            public int channelId;
            public String expireTime;
            public int id;
            public String md5;
            public String receiveTime;
            public String remark;
            public int status;
            public int transStatus;
            public long transTime;

            public ItemsBean(double amount, int applyStatus, String applyTime, String cashReqNo, String channel, int channelId, String expireTime, int id, String md5, String receiveTime, String remark, int status, int transStatus, long transTime) {
                this.amount = amount;
                this.applyStatus = applyStatus;
                this.applyTime = applyTime;
                this.cashReqNo = cashReqNo;
                this.channel = channel;
                this.channelId = channelId;
                this.expireTime = expireTime;
                this.id = id;
                this.md5 = md5;
                this.receiveTime = receiveTime;
                this.remark = remark;
                this.status = status;
                this.transStatus = transStatus;
                this.transTime = transTime;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getApplyStatus() {
                return applyStatus;
            }

            public void setApplyStatus(int applyStatus) {
                this.applyStatus = applyStatus;
            }

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public String getCashReqNo() {
                return cashReqNo;
            }

            public void setCashReqNo(String cashReqNo) {
                this.cashReqNo = cashReqNo;
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

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
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

            public int getTransStatus() {
                return transStatus;
            }

            public void setTransStatus(int transStatus) {
                this.transStatus = transStatus;
            }

            public long getTransTime() {
                return transTime;
            }

            public void setTransTime(long transTime) {
                this.transTime = transTime;
            }

            @Override
            public String toString() {
                return "ItemsBean{" +
                        "amount=" + amount +
                        ", applyStatus=" + applyStatus +
                        ", applyTime='" + applyTime + '\'' +
                        ", cashReqNo='" + cashReqNo + '\'' +
                        ", channel='" + channel + '\'' +
                        ", channelId=" + channelId +
                        ", expireTime='" + expireTime + '\'' +
                        ", id=" + id +
                        ", md5='" + md5 + '\'' +
                        ", receiveTime='" + receiveTime + '\'' +
                        ", remark='" + remark + '\'' +
                        ", status=" + status +
                        ", transStatus=" + transStatus +
                        ", transTime=" + transTime +
                        '}';
            }
        }
    }
}