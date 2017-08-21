package com.ftoul.androidclient.bean.response;

/**
 * Created by ftoul106 on 2017/6/30 0030.
 */

public class HuaXinAccountOut {

    /**
     * hxAccount : {"accountNo":"6236882280000059126","availablebal":5095754.1,"cardReqseqno":"P2P0652017062904400983145346","createtime":1496652322000,"frozbl":43425.69,"hxbalance":5139179.79,"id":5,"isOncard":1,"realityName":"孙先生","reqseqno":"P2P0652017062909001960659349","userId":110}
     * status : 1
     * userName : 孙先生
     */

    public HxAccountBean hxAccount;
    public int status;

    public HxAccountBean getHxAccount() {
        return hxAccount;
    }

    public void setHxAccount(HxAccountBean hxAccount) {
        this.hxAccount = hxAccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class HxAccountBean {
        /**
         * accountNo : 6236882280000059126
         * availablebal : 5095754.1
         * cardReqseqno : P2P0652017062904400983145346
         * createtime : 1496652322000
         * frozbl : 43425.69
         * hxbalance : 5139179.79
         * id : 5
         * isOncard : 1
         * realityName : 孙先生
         * reqseqno : P2P0652017062909001960659349
         * userId : 110
         */

        public String accountNo;
        public double availablebal;
        public String cardReqseqno;
        public long createtime;
        public double frozbl;
        public double hxbalance;
        public int id;
        public int isOncard;
        public String realityName;
        public String reqseqno;
        public int userId;

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public double getAvailablebal() {
            return availablebal;
        }

        public void setAvailablebal(double availablebal) {
            this.availablebal = availablebal;
        }

        public String getCardReqseqno() {
            return cardReqseqno;
        }

        public void setCardReqseqno(String cardReqseqno) {
            this.cardReqseqno = cardReqseqno;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public double getFrozbl() {
            return frozbl;
        }

        public void setFrozbl(double frozbl) {
            this.frozbl = frozbl;
        }

        public double getHxbalance() {
            return hxbalance;
        }

        public void setHxbalance(double hxbalance) {
            this.hxbalance = hxbalance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsOncard() {
            return isOncard;
        }

        public void setIsOncard(int isOncard) {
            this.isOncard = isOncard;
        }

        public String getRealityName() {
            return realityName;
        }

        public void setRealityName(String realityName) {
            this.realityName = realityName;
        }

        public String getReqseqno() {
            return reqseqno;
        }

        public void setReqseqno(String reqseqno) {
            this.reqseqno = reqseqno;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
