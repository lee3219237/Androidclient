package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by ftoul106 on 2017/8/14 0014.
 */

public class VerifyMobileIn extends BaseParamsVO {
//    "oldPhone":"15273133239",
//            "oldCode":"928847"
//       "newPhone":"15273133230",
//               "newCode":"191835",
//               "imageCode":"JYLM"

    private String oldPhone;
    private String oldCode;
    private String newPhone;
    private String newCode;
    private String imageCode;
    private String uuid;


    public VerifyMobileIn(String oldPhone, String oldCode) {
        this.oldPhone = oldPhone;
        this.oldCode = oldCode;
    }

    public VerifyMobileIn(String newPhone, String newCode, String imageCode) {
        this.newPhone = newPhone;
        this.newCode = newCode;
        this.imageCode = imageCode;
        uuid = getMachineCode();
    }
}
