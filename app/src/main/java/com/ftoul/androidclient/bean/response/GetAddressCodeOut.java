package com.ftoul.androidclient.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 蜂投网-Wusha on 2017/6/17.
 */

public class GetAddressCodeOut {

    /**
     * address : {"address":"111","city":"呼和浩特市","cityId":150100,"county":"市辖区","countyId":150101,"id":3,"province":"内蒙古自治区","provinceId":150000,"userId":110}
     */

//    private List<AddressBean>  address;
//
//    public List<AddressBean>  getAddress() {
//        return address;
//    }
//
//    public void setAddress(List<AddressBean>  address) {
//        this.address = address;
//    }

    private AddressBean  address;

    public AddressBean  getAddress() {
        return address;
    }

    public void setAddress(AddressBean  address) {
        this.address = address;
    }
    public static class AddressBean implements Serializable {
        /**
         * address : 111
         * city : 呼和浩特市
         * cityId : 150100
         * county : 市辖区
         * countyId : 150101
         * id : 3
         * province : 内蒙古自治区
         * provinceId : 150000
         * userId : 110
         */

        private String address;
        private String city;
        private int cityId;
        private String county;
        private int countyId;
        private int id;
        private String province;
        private int provinceId;
        private int userId;

        public AddressBean(String address, String city, int cityId, String county, int countyId,String province, int provinceId) {
            this.address = address;
            this.city = city;
            this.cityId = cityId;
            this.county = county;
            this.countyId = countyId;
            this.id = id;
            this.province = province;
            this.provinceId = provinceId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public int getCountyId() {
            return countyId;
        }

        public void setCountyId(int countyId) {
            this.countyId = countyId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
