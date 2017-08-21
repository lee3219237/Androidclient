package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by 蜂投网-Wusha on 2017/6/21.
 */

public class DbaoListCodeIn  extends BaseParamsVO{
private int count=15;

    public DbaoListCodeIn(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DbaoListCodeIn{" +
                "count=" + count +
                '}';
    }
}
