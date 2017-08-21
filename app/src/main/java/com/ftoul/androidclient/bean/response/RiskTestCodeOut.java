package com.ftoul.androidclient.bean.response;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/19.
 */

public class RiskTestCodeOut  {
    public List<QuestionInfosBean> riskList;

    public RiskTestCodeOut(List<QuestionInfosBean> questionInfos) {
        this.riskList = questionInfos;
    }

    @Override
    public String toString() {
        return "RiskTestCodeOut{" +
                "questionInfos=" + riskList +
                '}';
    }

    public static class QuestionInfosBean {
        /**
         * id : 1
         * resulta : 无业（0分）
         * resultb : 自由职业或佣金收入（1分）
         * resultc : 自营事业（2分）
         * resultd : 企业职工（3分）
         * resulte : 机关、事业单位或社会团体人员（4分）
         * scopea : 0
         * scopeb : 1
         * scopec : 2
         * scoped : 3
         * scopee : 4
         * title : 您目前的就业状况是？
         */

        public int id;
        public String resulta;
        public String resultb;
        public String resultc;
        public String resultd;
        public String resulte;
        public int scopea;
        public int scopeb;
        public int scopec;
        public int scoped;
        public int scopee;
        public String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResulta() {
            return resulta;
        }

        public void setResulta(String resulta) {
            this.resulta = resulta;
        }

        public String getResultb() {
            return resultb;
        }

        public void setResultb(String resultb) {
            this.resultb = resultb;
        }

        public String getResultc() {
            return resultc;
        }

        public void setResultc(String resultc) {
            this.resultc = resultc;
        }

        public String getResultd() {
            return resultd;
        }

        public void setResultd(String resultd) {
            this.resultd = resultd;
        }

        public String getResulte() {
            return resulte;
        }

        public void setResulte(String resulte) {
            this.resulte = resulte;
        }

        public int getScopea() {
            return scopea;
        }

        public void setScopea(int scopea) {
            this.scopea = scopea;
        }

        public int getScopeb() {
            return scopeb;
        }

        public void setScopeb(int scopeb) {
            this.scopeb = scopeb;
        }

        public int getScopec() {
            return scopec;
        }

        public void setScopec(int scopec) {
            this.scopec = scopec;
        }

        public int getScoped() {
            return scoped;
        }

        public void setScoped(int scoped) {
            this.scoped = scoped;
        }

        public int getScopee() {
            return scopee;
        }

        public void setScopee(int scopee) {
            this.scopee = scopee;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "QuestionInfosBean{" +
                    "id=" + id +
                    ", resulta='" + resulta + '\'' +
                    ", resultb='" + resultb + '\'' +
                    ", resultc='" + resultc + '\'' +
                    ", resultd='" + resultd + '\'' +
                    ", resulte='" + resulte + '\'' +
                    ", scopea=" + scopea +
                    ", scopeb=" + scopeb +
                    ", scopec=" + scopec +
                    ", scoped=" + scoped +
                    ", scopee=" + scopee +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
