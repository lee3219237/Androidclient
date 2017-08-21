package com.ftoul.androidclient.bean.response;

import com.ftoul.androidclient.bean.NewBidInfosBean;

/**
 * Created by ftoul106 on 2017/8/15 0015.
 */

public class RecommendProductsBeanOut {
    private NewBidInfosBean recommendBidInfo;

    public NewBidInfosBean getRecommendBidInfo() {
        return recommendBidInfo;
    }

    public void setRecommendBidInfo(NewBidInfosBean recommendBidInfo) {
        this.recommendBidInfo = recommendBidInfo;
    }
}
