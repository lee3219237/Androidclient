package com.ftoul.androidclient.bean;

/**
 * Created by ftoul106 on 2017/5/10 0010.
 */

public class BannerBean {


    /**
     * code : site_banner
     * id : 123463
     * imageUrl : http://192.168.56.11:8888/group1/M00/00/03/wKhkalle6v6AH78ZAAIGDCNy5C0856.png
     * linkTypeId : 9
     * title : APP首页banner
     * url :
     */

    private String code;
    private int id;
    private String imageUrl;
    private int linkTypeId;
    private String title;
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLinkTypeId() {
        return linkTypeId;
    }

    public void setLinkTypeId(int linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
