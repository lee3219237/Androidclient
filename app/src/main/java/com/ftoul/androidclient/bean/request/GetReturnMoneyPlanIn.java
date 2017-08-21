package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/19 0019.
 */

public class GetReturnMoneyPlanIn extends BaseParamsVO {
    private String bidId;

    public GetReturnMoneyPlanIn(String bidId) {
        this.bidId = bidId;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }
}
