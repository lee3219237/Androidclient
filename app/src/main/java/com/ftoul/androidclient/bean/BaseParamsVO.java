package com.ftoul.androidclient.bean;

import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.UserInfoInstance;

/**
 * Created by ftoul106 on 2017/6/8 0008.
 */

public class BaseParamsVO extends AbstractBaseParamsVO {
    private String token;

    public BaseParamsVO() {
        super();
        UserInfoInstance userInfo = UserInfoInstance.getInstance(MyApp.appContext);
        uid = userInfo.getUid();
      //  uid ="110";
        token = userInfo.getToken();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
