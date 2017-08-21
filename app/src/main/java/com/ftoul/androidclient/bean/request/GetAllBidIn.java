package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.AbstractBaseParamsVO;
import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/17 0017.
 */

public class GetAllBidIn extends AbstractBaseParamsVO {
    private String productId;
    private int pageSize;
    private int pageNo;

    public GetAllBidIn(String id, int pageSize, int pageNum) {
        this.productId = id;
        this.pageSize = pageSize;
        this.pageNo = pageNum;
    }
}
