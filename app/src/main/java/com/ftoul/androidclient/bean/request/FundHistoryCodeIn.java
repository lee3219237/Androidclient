package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by 蜂投网-Wusha on 2017/6/19.
 */

public class FundHistoryCodeIn extends BaseParamsVO {
    private String type;
    private int pageSize;
    private int pageNo;


    public FundHistoryCodeIn(String type, int pageSize, int pageNo) {
        this.type = type;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public String toString() {
        return "FundHistoryCodeIn{" +
                "type='" + type + '\'' +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                '}';
    }
}
