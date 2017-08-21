package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/21.
 */

public class PaymentCouponCodeOut {
    /**
     * page : {"items":[{"amount":1000,"createTime":"2017-06-06 11:45:06","expireTime":"2017-07-01 11:45:09","id":1,"receiveTime":"2017-06-16 15:06:05","receiveWelfarePkg":"{\"rateroll\":{\"add_day\":5,\"amount\":5,\"expiry_date\":\"有效期三天\",\"id\":5,\"invest_amount\":1000,\"invest_end\":50,\"invest_start\":20,\"used_product_ids\":\"小贷通,蜂计划\"},\"fmq\":{\"amount\":200,\"expiry_date\":\"有效期三天\",\"id\":2,\"invest_amount\":1000,\"invest_end\":50,\"invest_start\":20,\"used_product_ids\":\"小贷通,蜂计划\"},\"coins\":{\"amount\":2,\"id\":1}}","status":1,"userId":100,"welfarePkg":"{\"coins\":{\"id\":1,\"amount\":2},\"fmq\":[{\"id\":2,\"amount\":200,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"},{\"id\":3,\"amount\":300,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"},{\"id\":4,\"amount\":400,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"}],\"rateroll\":[{\"id\":5,\"amount\":5,\"add_day\":5,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"},{\"id\":6,\"amount\":6,\"add_day\":6,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"},{\"id\":7,\"amount\":7,\"add_day\":7,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"}]}"}],"pageNum":1,"prePage":10,"totalPage":1,"totalRecords":1}
     */

    public PageBean page;

    public PaymentCouponCodeOut(PageBean page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PaymentCouponCodeOut{" +
                "page=" + page +
                '}';
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * items : [{"amount":1000,"createTime":"2017-06-06 11:45:06","expireTime":"2017-07-01 11:45:09","id":1,"receiveTime":"2017-06-16 15:06:05","receiveWelfarePkg":"{\"rateroll\":{\"add_day\":5,\"amount\":5,\"expiry_date\":\"有效期三天\",\"id\":5,\"invest_amount\":1000,\"invest_end\":50,\"invest_start\":20,\"used_product_ids\":\"小贷通,蜂计划\"},\"fmq\":{\"amount\":200,\"expiry_date\":\"有效期三天\",\"id\":2,\"invest_amount\":1000,\"invest_end\":50,\"invest_start\":20,\"used_product_ids\":\"小贷通,蜂计划\"},\"coins\":{\"amount\":2,\"id\":1}}","status":1,"userId":100,"welfarePkg":"{\"coins\":{\"id\":1,\"amount\":2},\"fmq\":[{\"id\":2,\"amount\":200,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"},{\"id\":3,\"amount\":300,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"},{\"id\":4,\"amount\":400,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"}],\"rateroll\":[{\"id\":5,\"amount\":5,\"add_day\":5,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"},{\"id\":6,\"amount\":6,\"add_day\":6,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"},{\"id\":7,\"amount\":7,\"add_day\":7,\"used_product_ids\":\"小贷通,蜂计划\",\"invest_start\":20,\"invest_end\":50,\"invest_amount\":1000,\"expiry_date\":\"有效期三天\"}]}"}]
         * pageNum : 1
         * prePage : 10
         * totalPage : 1
         * totalRecords : 1
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
             * amount : 1000
             * createTime : 2017-06-06 11:45:06
             * expireTime : 2017-07-01 11:45:09
             * id : 1
             * receiveTime : 2017-06-16 15:06:05
             * receiveWelfarePkg : {"rateroll":{"add_day":5,"amount":5,"expiry_date":"有效期三天","id":5,"invest_amount":1000,"invest_end":50,"invest_start":20,"used_product_ids":"小贷通,蜂计划"},"fmq":{"amount":200,"expiry_date":"有效期三天","id":2,"invest_amount":1000,"invest_end":50,"invest_start":20,"used_product_ids":"小贷通,蜂计划"},"coins":{"amount":2,"id":1}}
             * status : 1
             * userId : 100
             * welfarePkg : {"coins":{"id":1,"amount":2},"fmq":[{"id":2,"amount":200,"used_product_ids":"小贷通,蜂计划","invest_start":20,"invest_end":50,"invest_amount":1000,"expiry_date":"有效期三天"},{"id":3,"amount":300,"used_product_ids":"小贷通,蜂计划","invest_start":20,"invest_end":50,"invest_amount":1000,"expiry_date":"有效期三天"},{"id":4,"amount":400,"used_product_ids":"小贷通,蜂计划","invest_start":20,"invest_end":50,"invest_amount":1000,"expiry_date":"有效期三天"}],"rateroll":[{"id":5,"amount":5,"add_day":5,"used_product_ids":"小贷通,蜂计划","invest_start":20,"invest_end":50,"invest_amount":1000,"expiry_date":"有效期三天"},{"id":6,"amount":6,"add_day":6,"used_product_ids":"小贷通,蜂计划","invest_start":20,"invest_end":50,"invest_amount":1000,"expiry_date":"有效期三天"},{"id":7,"amount":7,"add_day":7,"used_product_ids":"小贷通,蜂计划","invest_start":20,"invest_end":50,"invest_amount":1000,"expiry_date":"有效期三天"}]}
             */

            public int amount;
            public String createTime;
            public String expireTime;
            public int id;
            public String receiveTime;
            public String receiveWelfarePkg;
            public int status;
            public int userId;
            public String welfarePkg;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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

            public String getReceiveTime() {
                return receiveTime;
            }

            public void setReceiveTime(String receiveTime) {
                this.receiveTime = receiveTime;
            }

            public String getReceiveWelfarePkg() {
                return receiveWelfarePkg;
            }

            public void setReceiveWelfarePkg(String receiveWelfarePkg) {
                this.receiveWelfarePkg = receiveWelfarePkg;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getWelfarePkg() {
                return welfarePkg;
            }

            public void setWelfarePkg(String welfarePkg) {
                this.welfarePkg = welfarePkg;
            }
        }
    }
}
