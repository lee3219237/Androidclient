package com.ftoul.androidclient.bean;


/**
 * Created by ftoul106 on 2016/12/26 0026.
 */

public class ReceiverBean {
    private int id;//消息的唯一编号
    private int type;//1代表消息，2代表公告
    private String url;
    private String title;
    private String content;
    private String sendTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
    private String noticeId;//公告编号
    private boolean isOpen;//是否被打开过，false代表未打开过

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public ReceiverBean(int type, String url, String title, String content, String sendTime, boolean isOpen, int id) {
        this.type = type;
        this.url = url;
        this.title = title;
        this.content = content;

        this.sendTime = sendTime;
        this.isOpen = isOpen;
        this.id = id;

    }

    public ReceiverBean( String url, String title, String content, String sendTime, boolean isOpen, String noticeId) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.sendTime = sendTime;
        this.isOpen = isOpen;
        this.noticeId = noticeId;

    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "ReceiverBean{" +
                "type=" + type +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTilte() {
        return title;
    }

    public void setTilte(String tilte) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}



