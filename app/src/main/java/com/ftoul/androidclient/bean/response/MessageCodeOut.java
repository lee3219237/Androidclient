package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/17.
 */

public class MessageCodeOut {
    public List<ContentListBean> contentList;

    public List<ContentListBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        return "MessageCodeOut{" +
                "contentList=" + contentList +
                '}';
    }

    public static class ContentListBean {
        /**
         * content : 蜂投网携手湘潭市教育发展基金会 启动“互联网+公益”计划
         * createTime : 1495762227000
         * id : 100301005
         * title : 蜂投网携手湘潭市教育发展基金会 启动“互联网+公益”计划
         */

        public String content;
        public long createTime;
        public int id;
        public String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "ContentListBean{" +
                    "content='" + content + '\'' +
                    ", createTime=" + createTime +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
