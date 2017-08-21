package com.ftoul.androidclient.bean.response;

/**
 * Created by ftoul106 on 2017/7/1 0001.
 */

public class LoginOut {

    /**
     * icon : default
     * hasIpsAccount : 1
     * phone : 13632699001
     * token :
     * hasBindCard : 1
     * hasInvest : 1
     * riskTest : 保守型
     */

    private String icon;
    private int hasIpsAccount;
    private String phone;
    private String token;
    private int hasBindCard;
    private int hasInvest;
    private String riskTest;
    private String uid;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getHasIpsAccount() {
        return hasIpsAccount;
    }

    public void setHasIpsAccount(int hasIpsAccount) {
        this.hasIpsAccount = hasIpsAccount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getHasBindCard() {
        return hasBindCard;
    }

    public void setHasBindCard(int hasBindCard) {
        this.hasBindCard = hasBindCard;
    }

    public int getHasInvest() {
        return hasInvest;
    }

    public void setHasInvest(int hasInvest) {
        this.hasInvest = hasInvest;
    }

    public String getRiskTest() {
        return riskTest;
    }

    public void setRiskTest(String riskTest) {
        this.riskTest = riskTest;
    }
}
