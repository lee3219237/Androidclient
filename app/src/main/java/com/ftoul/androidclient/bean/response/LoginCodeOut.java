package com.ftoul.androidclient.bean.response;

/**
 * Created by 蜂投网-Wusha on 2017/6/28.
 */

public class LoginCodeOut  {
    /**
     * id : 110
     * createTime : null
     * loginName : null
     * password : null
     * statuz : 1
     * passwordContinuousErrors : null
     * isPasswordErrorLocked : null
     * passwordErrorLockedTime : null
     * isAllowLogin : null
     * lockTime : null
     * loginCount : null
     * lastLoginTime : null
     * lastLoginIp : null
     * photo : default
     * city : default
     * autoLoginToken : null
     * nickName : null
     * mobile : 13632699001
     * isMobileVerified : null
     * specialInvitationCode : null
     * scene :
     * realityName : null
     * idCard : null
     */

    public int id;
    public Object createTime;//创建时间
    public Object loginName;//登录名
    public Object password;//密码
    public int statuz;
    public Object passwordContinuousErrors;//输入密码错误次数
    public Object isPasswordErrorLocked;//是否密码错误
    public Object passwordErrorLockedTime;//密码错误锁定时间
    public Object isAllowLogin;//是否允许登录
    public Object lockTime;//锁定的时间
    public Object loginCount;//登录次数
    public Object lastLoginTime;//最后登录的时间
    public Object lastLoginIp;//最后登录的IP地址
    public String photo;//照片 url
    public String city;//城市
    public Object autoLoginToken;//自动注册的Token
    public String nickName;//昵称
    public String mobile;//手机号码
    public Object isMobileVerified;//手机是否验证
    public Object specialInvitationCode;//特殊请求的代码
    public String scene;
    public Object realityName;//真实姓名
    public Object idCard;//身份证

    @Override
    public String toString() {
        return "LoginCodeOut{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", loginName=" + loginName +
                ", password=" + password +
                ", statuz=" + statuz +
                ", passwordContinuousErrors=" + passwordContinuousErrors +
                ", isPasswordErrorLocked=" + isPasswordErrorLocked +
                ", passwordErrorLockedTime=" + passwordErrorLockedTime +
                ", isAllowLogin=" + isAllowLogin +
                ", lockTime=" + lockTime +
                ", loginCount=" + loginCount +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp=" + lastLoginIp +
                ", photo='" + photo + '\'' +
                ", city='" + city + '\'' +
                ", autoLoginToken=" + autoLoginToken +
                ", nickName=" + nickName +
                ", mobile='" + mobile + '\'' +
                ", isMobileVerified=" + isMobileVerified +
                ", specialInvitationCode=" + specialInvitationCode +
                ", scene='" + scene + '\'' +
                ", realityName=" + realityName +
                ", idCard=" + idCard +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getLoginName() {
        return loginName;
    }

    public void setLoginName(Object loginName) {
        this.loginName = loginName;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public int getStatuz() {
        return statuz;
    }

    public void setStatuz(int statuz) {
        this.statuz = statuz;
    }

    public Object getPasswordContinuousErrors() {
        return passwordContinuousErrors;
    }

    public void setPasswordContinuousErrors(Object passwordContinuousErrors) {
        this.passwordContinuousErrors = passwordContinuousErrors;
    }

    public Object getIsPasswordErrorLocked() {
        return isPasswordErrorLocked;
    }

    public void setIsPasswordErrorLocked(Object isPasswordErrorLocked) {
        this.isPasswordErrorLocked = isPasswordErrorLocked;
    }

    public Object getPasswordErrorLockedTime() {
        return passwordErrorLockedTime;
    }

    public void setPasswordErrorLockedTime(Object passwordErrorLockedTime) {
        this.passwordErrorLockedTime = passwordErrorLockedTime;
    }

    public Object getIsAllowLogin() {
        return isAllowLogin;
    }

    public void setIsAllowLogin(Object isAllowLogin) {
        this.isAllowLogin = isAllowLogin;
    }

    public Object getLockTime() {
        return lockTime;
    }

    public void setLockTime(Object lockTime) {
        this.lockTime = lockTime;
    }

    public Object getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Object loginCount) {
        this.loginCount = loginCount;
    }

    public Object getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Object lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Object getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(Object lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getAutoLoginToken() {
        return autoLoginToken;
    }

    public void setAutoLoginToken(Object autoLoginToken) {
        this.autoLoginToken = autoLoginToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getIsMobileVerified() {
        return isMobileVerified;
    }

    public void setIsMobileVerified(Object isMobileVerified) {
        this.isMobileVerified = isMobileVerified;
    }

    public Object getSpecialInvitationCode() {
        return specialInvitationCode;
    }

    public void setSpecialInvitationCode(Object specialInvitationCode) {
        this.specialInvitationCode = specialInvitationCode;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public Object getRealityName() {
        return realityName;
    }

    public void setRealityName(Object realityName) {
        this.realityName = realityName;
    }

    public Object getIdCard() {
        return idCard;
    }

    public void setIdCard(Object idCard) {
        this.idCard = idCard;
    }
}
