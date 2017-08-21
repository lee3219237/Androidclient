package com.ftoul.androidclient.bean.response;

/**
 * Created by ftoul106 on 2017/8/11 0011.
 */

public class MessageBean {
    /**
     * id : 284
     * senderId : null
     * receiverId : null
     * content : 尊敬的用户殷靖雄：[2017-08-08 15:01:19] 您好，您投资的借款FYT159170808资金周转已经通过放款审核,请随时关注投标进度,祝您投资愉快。
     * time : 2017-08-08 15:01:19
     * state : 0
     * title : 放款
     * btype : 1
     * review : 1
     * infoId : 287
     */

    private long id;
    private Object senderId;
    private Object receiverId;
    private String content;
    private String time;
    private int state;
    private String title;
    private int btype;
    private int review;
    private long infoId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Object getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBtype() {
        return btype;
    }

    public void setBtype(int btype) {
        this.btype = btype;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public long getInfoId() {
        return infoId;
    }

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

}
