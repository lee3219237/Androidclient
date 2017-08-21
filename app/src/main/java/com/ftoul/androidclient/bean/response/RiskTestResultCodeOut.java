package com.ftoul.androidclient.bean.response;

/**
 * Created by 蜂投网-Wusha on 2017/6/20.
 */

public class RiskTestResultCodeOut {
    /**
     * content : 稳健型投资者
     * riskTest : 稳健型
     */
    public PreviewBean preview;

    public PreviewBean getPreview() {
        return preview;
    }

    public void setPreview(PreviewBean preview) {
        this.preview = preview;
    }

    public static class  PreviewBean{
//        "id": 75,
//                "userId": 110,
//                "scope": 8,
//                "testResult": "保守型",
//                "url": null,
//                "remark": "属于可以承担低风险类型的投资者",
//                "createtime": "2017-08-11 11:18:01"

        public String remark;
        public String testResult;
        public String haveTest;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTestResult() {
            return testResult;
        }

        public void setTestResult(String testResult) {
            this.testResult = testResult;
        }

        public String getHaveTest() {
            return haveTest;
        }

        public void setHaveTest(String haveTest) {
            this.haveTest = haveTest;
        }
    }
}
