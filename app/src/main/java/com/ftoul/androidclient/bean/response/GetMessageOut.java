package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by ftoul106 on 2017/7/14 0014.
 */

public class GetMessageOut {

    /**
     * page : {"items":[{"btype":1,"content":"尊敬的孙先生: 2017-07-07 17:52:16 您充值￥10000.00元成功。","id":493,"infoId":593,"review":1,"state":0,"time":1499421136000,"title":"用户充值"},{"btype":1,"content":"尊敬的孙先生: [2017-07-07 09:10:05] 您申请的的借款:Test1010,已经成功放款 。","id":470,"infoId":570,"review":1,"state":0,"time":1499389806000,"title":"放款"},{"btype":1,"content":"尊敬的孙先生: [2017-07-06 18:59:06] 您申请的的借款:小贷通-100,已经成功放款 。","id":468,"infoId":568,"review":1,"state":0,"time":1499338746000,"title":"放款"}],"pageNum":1,"prePage":3,"totalPage":35,"totalRecords":103}
     */

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * items : [{"btype":1,"content":"尊敬的孙先生: 2017-07-07 17:52:16 您充值￥10000.00元成功。","id":493,"infoId":593,"review":1,"state":0,"time":1499421136000,"title":"用户充值"},{"btype":1,"content":"尊敬的孙先生: [2017-07-07 09:10:05] 您申请的的借款:Test1010,已经成功放款 。","id":470,"infoId":570,"review":1,"state":0,"time":1499389806000,"title":"放款"},{"btype":1,"content":"尊敬的孙先生: [2017-07-06 18:59:06] 您申请的的借款:小贷通-100,已经成功放款 。","id":468,"infoId":568,"review":1,"state":0,"time":1499338746000,"title":"放款"}]
         * pageNum : 1
         * prePage : 3
         * totalPage : 35
         * totalRecords : 103
         */

        private int pageNum;
        private int prePage;
        private int totalPage;
        private int totalRecords;
        private List<ItemsBean> items;

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
             * btype : 1
             * content : 尊敬的孙先生: 2017-07-07 17:52:16 您充值￥10000.00元成功。
             * id : 493
             * infoId : 593
             * review : 1
             * state : 0
             * time : 1499421136000
             * title : 用户充值
             */

            private int btype;
            private String content;
            private long id;
            private int infoId;
            private int review;
            private int state;
            private long time;
            private String title;

            public int getBtype() {
                return btype;
            }

            public void setBtype(int btype) {
                this.btype = btype;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getInfoId() {
                return infoId;
            }

            public void setInfoId(int infoId) {
                this.infoId = infoId;
            }

            public int getReview() {
                return review;
            }

            public void setReview(int review) {
                this.review = review;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
