package com.ftoul.androidclient.bean.response;

import com.ftoul.androidclient.bean.BidInfosBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ftoul106 on 2017/6/20 0020.
 */

public class GetNewUserWelfareOut {


    /**
     * xszxBid : {"bidList":[{"apr":1,"title":"123","id":60,"period":2},{"apr":1,"title":"321","id":63,"period":12},{"apr":1,"title":"123","id":66,"period":200},{"apr":1,"title":"123","id":67,"period":2},{"apr":1.7,"title":"等额本息2017061901","id":104,"period":24},{"apr":1.7,"title":"等额本息2017061901","id":105,"period":24},{"apr":1.7,"title":"等额本息2017061901","id":106,"period":24},{"apr":1.7,"title":"等额本息测试20170619-01","id":111,"period":24},{"apr":1.7,"title":"等额本息测试20170619-01","id":112,"period":24},{"apr":1.7,"title":"等额本息测试20170619-01","id":113,"period":24},{"apr":7,"title":"等额本息测试20170619-01","id":114,"period":24},{"apr":2,"title":"等额本息20170619-02","id":116,"period":3},{"apr":9,"title":"等额本息-007","id":117,"period":4}],"productName":"新手专享"}
     * lctyBid : {"id":1,"period":1,"productName":"理财体验","rate":15,"title":"理财体验标"}
     */

    public XszxBidBean xszxBid;
    public LctyBidBean lctyBid;

    public static class XszxBidBean {
        /**
         * bidList : [{"apr":1,"title":"123","id":60,"period":2},{"apr":1,"title":"321","id":63,"period":12},{"apr":1,"title":"123","id":66,"period":200},{"apr":1,"title":"123","id":67,"period":2},{"apr":1.7,"title":"等额本息2017061901","id":104,"period":24},{"apr":1.7,"title":"等额本息2017061901","id":105,"period":24},{"apr":1.7,"title":"等额本息2017061901","id":106,"period":24},{"apr":1.7,"title":"等额本息测试20170619-01","id":111,"period":24},{"apr":1.7,"title":"等额本息测试20170619-01","id":112,"period":24},{"apr":1.7,"title":"等额本息测试20170619-01","id":113,"period":24},{"apr":7,"title":"等额本息测试20170619-01","id":114,"period":24},{"apr":2,"title":"等额本息20170619-02","id":116,"period":3},{"apr":9,"title":"等额本息-007","id":117,"period":4}]
         * productName : 新手专享
         */

        public String productName;
        public List<NewBidInfosBean> bidList;
    }

    public static class LctyBidBean implements Serializable {
        /**
         * id : 1
         * period : 1
         * productName : 理财体验
         * rate : 15.0
         * title : 理财体验标
         */

        public int id;
        public int period;
        public int periodType;
        public String productName;
        public double rate;
        public String title;
    }
}
