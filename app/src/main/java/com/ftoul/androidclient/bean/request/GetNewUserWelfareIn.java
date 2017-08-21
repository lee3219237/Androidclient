package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.AbstractBaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/22 0022.
 */

public class GetNewUserWelfareIn extends AbstractBaseParamsVO {
    private int pageSize;    //是	10
    private int pageNo;//int	是	1

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNo;
    }

    public void setPageNum(int pageNum) {
        this.pageNo = pageNum;
    }

    public GetNewUserWelfareIn(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }
}
