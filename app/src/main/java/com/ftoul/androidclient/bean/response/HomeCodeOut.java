package com.ftoul.androidclient.bean.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.ftoul.androidclient.bean.BannerBean;
import com.ftoul.androidclient.bean.BidInfosBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/17.
 */

public class HomeCodeOut  {
    /**
     * appInfo : [{"createTime":1494038108000,"forcedUpgrade":0,"id":1,"installPkgUrl":"www","upgradeDesc":"IOS版本v1.0","versionNo":"1.0"},{"createTime":1493722597000,"forcedUpgrade":1,"id":2,"installPkgUrl":"www","upgradeDesc":"Android版本v1.0","versionNo":"1.0"},{"createTime":1493983158000,"forcedUpgrade":0,"id":3,"installPkgUrl":"http://192.168.100.106:8888/group1/M00/00/01/wKhkalkMzDWAPqA8AAJYxI9dLiU37.xlsx","upgradeDesc":"Android 版本v1.2.0","versionNo":"1.2.0"},{"createTime":1494038274000,"forcedUpgrade":0,"id":6,"installPkgUrl":"http://192.168.100.106:8888/group1/M00/00/01/wKhkalkNo4uAOos4AI-Tq1Kx6s491.xlsx","upgradeDesc":"IOS","versionNo":"1.2.0"}]
     * startPages : [{"content":"蜂投网携手湘潭市教育发展基金会 启动\u201c互联网+公益\u201d计划","createTime":1495762227000,"id":100301005,"title":"蜂投网携手湘潭市教育发展基金会 启动\u201c互联网+公益\u201d计划"},{"content":"【活动】\u201c聚情元宵，让爱回家\u201d活动上线公告","createTime":1495762168000,"id":100301004,"title":"【活动】\u201c聚情元宵，让爱回家\u201d活动上线公告"},{"content":"【活动】\u201c聚情元宵，让爱回家\u201d活动上线公告【活动】\u201c聚情元宵，让爱回家\u201d活动上线公告<br />","createTime":1495762120000,"id":100301003,"title":"【活动】\u201c聚情元宵，让爱回家\u201d活动上线公告"},{"content":"【活动】\u201c聚情元宵，让爱回家\u201d活动上线公告","createTime":1495706610000,"id":100301002,"title":"蜂投网携手湘潭市教育发展基金会 启动\u201c互联网+公益\u201d计划"},{"content":"【活动】\u201c聚情元宵，让爱回家\u201d活动上线公告","createTime":1495706392000,"id":100301001,"title":"【活动】\u201c聚情元宵，让爱回家\u201d活动上线公告"}]
     * showXsfl : 1
     * recommendBidInfo : {"apr":13,"title":"小贷通20170616-02","id":101,"period":5}
     * banner : []
     */

    public String showXsfl;
   // public BidInfosBean recommendBidInfo;
    public AppInfoBean appInfo;
    public ArrayList<StartPagesBean> startPages;
    public List<BannerBean> banner;

    @Override
    public String toString() {
        return "HomeCodeOut{" +
                "showXsfl='" + showXsfl + '\'' +

                ", appInfo=" + appInfo +
                ", startPages=" + startPages +
                ", banner=" + banner +
                '}';
    }

    public String getShowXsfl() {
        return showXsfl;
    }

    public void setShowXsfl(String showXsfl) {
        this.showXsfl = showXsfl;
    }



    public AppInfoBean getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfoBean appInfo) {
        this.appInfo = appInfo;
    }

    public ArrayList<StartPagesBean> getStartPages() {
        return startPages;
    }

    public void setStartPages(ArrayList<StartPagesBean> startPages) {
        this.startPages = startPages;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }



    public static class AppInfoBean {

        /**
         * createTime : 1500970008000
         * forcedUpgrade : 0
         * id : 1
         * installPkgUrl : http://192.168.56.11:8888/group1/M00/00/04/wKhkfVl2-iKAMPRXAJvV7qduKi8899.apk
         * modifyUserId : 1
         * siteType : 1
         * upgradeDesc : 123456
         * useStatus : 1
         * versionNo : 8.2
         */

        public long createTime;
        public int forcedUpgrade;
        public int id;
        public String installPkgUrl;
        public int modifyUserId;
        public int siteType;
        public String upgradeDesc;
        public int useStatus;
        public String versionNo;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getForcedUpgrade() {
            return forcedUpgrade;
        }

        public void setForcedUpgrade(int forcedUpgrade) {
            this.forcedUpgrade = forcedUpgrade;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInstallPkgUrl() {
            return installPkgUrl;
        }

        public void setInstallPkgUrl(String installPkgUrl) {
            this.installPkgUrl = installPkgUrl;
        }

        public int getModifyUserId() {
            return modifyUserId;
        }

        public void setModifyUserId(int modifyUserId) {
            this.modifyUserId = modifyUserId;
        }

        public int getSiteType() {
            return siteType;
        }

        public void setSiteType(int siteType) {
            this.siteType = siteType;
        }

        public String getUpgradeDesc() {
            return upgradeDesc;
        }

        public void setUpgradeDesc(String upgradeDesc) {
            this.upgradeDesc = upgradeDesc;
        }

        public int getUseStatus() {
            return useStatus;
        }

        public void setUseStatus(int useStatus) {
            this.useStatus = useStatus;
        }

        public String getVersionNo() {
            return versionNo;
        }

        public void setVersionNo(String versionNo) {
            this.versionNo = versionNo;
        }
    }

    public static class StartPagesBean implements Parcelable{
        /**
         * content : 蜂投网携手湘潭市教育发展基金会 启动“互联网+公益”计划
         * createTime : 1495762227000
         * id : 100301005
         * title : 蜂投网携手湘潭市教育发展基金会 启动“互联网+公益”计划
         */

        public String content;
        public String createTime;
        public int id;
        public String title;

        protected StartPagesBean(Parcel in) {
            content = in.readString();
            createTime = in.readString();
            id = in.readInt();
            title = in.readString();
        }

        public static final Creator<StartPagesBean> CREATOR = new Creator<StartPagesBean>() {
            @Override
            public StartPagesBean createFromParcel(Parcel in) {
                return new StartPagesBean(in);
            }

            @Override
            public StartPagesBean[] newArray(int size) {
                return new StartPagesBean[size];
            }
        };

        @Override
        public String toString() {
            return "StartPagesBean{" +
                    "content='" + content + '\'' +
                    ", createTime=" + createTime +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(content);
            dest.writeString(createTime);
            dest.writeInt(id);
            dest.writeString(title);
        }
    }
}