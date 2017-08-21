package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/21 0021.
 * 投资
 */

public class InvestCommitIn extends BaseParamsVO {
    private double investAmount;
    private String bidId;

    public InvestCommitIn(double investAmount, String bidId) {
        this.investAmount = investAmount;
        this.bidId = bidId;
    }
}
