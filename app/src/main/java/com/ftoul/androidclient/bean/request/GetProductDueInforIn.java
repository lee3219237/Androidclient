package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/17 0017.
 */

public class GetProductDueInforIn extends BaseParamsVO {
    private String productId;
    private int status;
    private String pageNo;
    private String prePage;

    public GetProductDueInforIn(String productId, int status, String num, String prePage) {
        this.productId = productId;
        this.status = status;
        this.pageNo = num;
        this.prePage = prePage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNum() {
        return pageNo;
    }

    public void setNum(String num) {
        this.pageNo = num;
    }

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
