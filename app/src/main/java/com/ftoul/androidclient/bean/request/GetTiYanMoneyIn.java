package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/6/19 0019.
 */

public class GetTiYanMoneyIn extends BaseParamsVO {
    private String num;
    private String prePage;

    public GetTiYanMoneyIn(String num, String prePage) {
        this.num = num;
        this.prePage = prePage;
    }
}
