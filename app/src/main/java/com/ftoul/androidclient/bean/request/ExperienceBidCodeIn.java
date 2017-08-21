package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by 蜂投网-Wusha on 2017/6/21.
 */

public class ExperienceBidCodeIn extends BaseParamsVO {
    private String bidId;

    public ExperienceBidCodeIn(String bidId) {
        this.bidId = bidId;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    @Override
    public String toString() {
        return "ExperienceBidCodeIn{" +
                "bidId='" + bidId + '\'' +
                '}';
    }
}
