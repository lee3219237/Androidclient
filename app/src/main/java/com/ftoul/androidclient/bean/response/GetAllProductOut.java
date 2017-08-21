package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by ftoul106 on 2017/6/17 0017.
 */

public class GetAllProductOut {
    public List<RegularBidsBean> getRegularBids() {
        return regularBids;
    }

    public void setRegularBids(List<RegularBidsBean> regularBids) {
        this.regularBids = regularBids;
    }

    private java.util.List<RegularBidsBean> regularBids;

    public static class RegularBidsBean {


        /**
         *  name  : 蜂计划
         * bidType : fjh0001
         */

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @Override
    public String toString() {
        return "GetAllProductOut{" +
                "regularBids=" + regularBids +
                '}';
    }
}
