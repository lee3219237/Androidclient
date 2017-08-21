package com.ftoul.androidclient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ftoul106 on 2017/4/25 0025.
 */

public class MyData implements Parcelable {



    private int data1;
    private int data2;

    public MyData(){

    }

    protected MyData(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<MyData> CREATOR = new Creator<MyData>() {
        @Override
        public MyData createFromParcel(Parcel in) {
            return new MyData(in);
        }

        @Override
        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /** 将数据写入到Parcel **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(data1);
        dest.writeInt(data2);
    }

    /** 从Parcel中读取数据 **/
    public void readFromParcel(Parcel in){
        data1 = in.readInt();
        data2 = in.readInt();
    }


    public int getData2() {
        return data2;
    }

    public void setData2(int data2) {
        this.data2 = data2;
    }

    public int getData1() {
        return data1;
    }

    public void setData1(int data1) {
        this.data1 = data1;
    }

    @Override
    public String toString() {
        return "data1 = "+ data1 + ", data2="+ data2;
    }

    public static class RepaymentDaysBean {
        @SerializedName("2017-07-05")
        public List<_$20170705Bean> _$20170705;
        @SerializedName("2017-07-03")
        public List<_$20170703Bean> _$20170703;
        @SerializedName("2017-07-20")
        public List<_$20170720Bean> _$20170720;
        @SerializedName("2017-07-19")
        public List<_$20170719Bean> _$20170719;
        @SerializedName("2017-07-16")
        public List<_$20170716Bean> _$20170716;
        @SerializedName("2017-07-17")
        public List<_$20170717Bean> _$20170717;

        public static class _$20170705Bean {
            /**
             * id : 5
             * receiveCorpus : 100.0
             * receiveInterest : 10.0
             * receiveTime : 2017-07-05 16:03:50
             * status : 0
             * title : 22
             * totalInterest : 0.0
             */

            public int id;
            public double receiveCorpus;
            public double receiveInterest;
            public String receiveTime;
            public int status;
            public String title;
            public double totalInterest;
        }

        public static class _$20170703Bean {
            /**
             * id : 9
             * receiveCorpus : 100.0
             * receiveInterest : 10.0
             * receiveTime : 2017-07-03 16:03:50
             * status : 1
             * title : 小贷通2017060610
             * totalInterest : 0.0
             */

            public int id;
            public double receiveCorpus;
            public double receiveInterest;
            public String receiveTime;
            public int status;
            public String title;
            public double totalInterest;
        }

        public static class _$20170720Bean {
            /**
             * id : 831
             * receiveCorpus : 0.0
             * receiveInterest : 103.74
             * receiveTime : 2017-07-20 11:09:19
             * status : 0
             * title : 按月还息到期还本-01
             * totalInterest : 0.0
             */

            public int id;
            public double receiveCorpus;
            public double receiveInterest;
            public String receiveTime;
            public int status;
            public String title;
            public double totalInterest;
        }

        public static class _$20170719Bean {
            /**
             * id : 759
             * receiveCorpus : 4.32
             * receiveInterest : 0.65
             * receiveTime : 2017-07-19 18:05:13
             * status : 0
             * title : 等额本息测试20170619-01
             * totalInterest : 0.0
             */

            public int id;
            public double receiveCorpus;
            public double receiveInterest;
            public String receiveTime;
            public int status;
            public String title;
            public double totalInterest;
        }

        public static class _$20170716Bean {
            /**
             * id : 22
             * receiveCorpus : 0.0
             * receiveInterest : 6.0
             * receiveTime : 2017-07-16 14:20:22
             * status : 1
             * title : 小贷通20170614-05
             * totalInterest : 0.0
             */

            public int id;
            public double receiveCorpus;
            public double receiveInterest;
            public String receiveTime;
            public int status;
            public String title;
            public double totalInterest;
        }

        public static class _$20170717Bean {
            /**
             * id : 219
             * receiveCorpus : 0.0
             * receiveInterest : 36.11
             * receiveTime : 2017-07-17 09:20:32
             * status : 1
             * title : 小贷通2017061603
             * totalInterest : 0.0
             */

            public int id;
            public double receiveCorpus;
            public double receiveInterest;
            public String receiveTime;
            public int status;
            public String title;
            public double totalInterest;
        }
    }
}