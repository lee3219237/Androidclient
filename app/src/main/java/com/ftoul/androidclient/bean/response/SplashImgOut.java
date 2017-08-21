package com.ftoul.androidclient.bean.response;

/**
 * Created by ftoul106 on 2017/7/19 0019.
 */

public class SplashImgOut {


    /**
     * startPage : {"createTime":"2017-07-18 17:33:32","id":5,"imgUrl":"http://192.168.56.11:8888/group1/M00/00/03/wKhkallt1ViAG560AA2XGSFGG-E030.png","imgVersion":"00","linkUrl":"https://www.baidu.com/","remark":"测试内容区域","useStatus":1}
     */

    private StartPageBean startPage;

    public StartPageBean getStartPage() {
        return startPage;
    }

    public void setStartPage(StartPageBean startPage) {
        this.startPage = startPage;
    }

    public static class StartPageBean {
        /**
         * createTime : 2017-07-18 17:33:32
         * id : 5
         * imgUrl : http://192.168.56.11:8888/group1/M00/00/03/wKhkallt1ViAG560AA2XGSFGG-E030.png
         * imgVersion : 00
         * linkUrl : https://www.baidu.com/
         * remark : 测试内容区域
         * useStatus : 1
         */

        private String createTime;
        private int id;
        private String imgUrl;
        private String imgVersion;
        private String linkUrl;
        private String remark;
        private int useStatus;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgVersion() {
            return imgVersion;
        }

        public void setImgVersion(String imgVersion) {
            this.imgVersion = imgVersion;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getUseStatus() {
            return useStatus;
        }

        public void setUseStatus(int useStatus) {
            this.useStatus = useStatus;
        }
    }
}
