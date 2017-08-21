package com.ftoul.androidclient.bean.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.ftoul.androidclient.bean.BaseParamsVO;

import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/20.
 */

public class RiskTestResultCodeIn extends BaseParamsVO {

    /**
     * anwser : [{"subjectId":"15","result":"D","scope":"4"},{"subjectId":"16","result":"D","scope":"4"}]
     */


    private List<AnwserBean> anwser;

    public RiskTestResultCodeIn(List<AnwserBean> anwser) {
        this.anwser = anwser;
    }

    public List<AnwserBean> getAnwser() {
        return anwser;
    }

    public void setAnwser(List<AnwserBean> anwser) {
        this.anwser = anwser;
    }

    public static class AnwserBean implements Parcelable {
        /**
         * subjectId : 15
         * result : D
         * scope : 4
         */

        private String subjectId;
        private String result;
        private String scope;

        public AnwserBean() {
        }

        public AnwserBean(Parcel in) {
            subjectId = in.readString();
            result = in.readString();
            scope = in.readString();
        }

        public static final Creator<AnwserBean> CREATOR = new Creator<AnwserBean>() {
            @Override
            public AnwserBean createFromParcel(Parcel in) {
                return new AnwserBean(in);
            }

            @Override
            public AnwserBean[] newArray(int size) {
                return new AnwserBean[size];
            }
        };

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(subjectId);
            dest.writeString(result);
            dest.writeString(scope);
        }
    }
}
