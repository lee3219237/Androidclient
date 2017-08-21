package com.ftoul.androidclient.bean.request;

import com.ftoul.androidclient.bean.BaseParamsVO;

/**
 * Created by 蜂投网-Wusha on 2017/6/17.
 */

public class EditAddressCodeIn  extends BaseParamsVO {

    private String address;
    private String provinceName;
    private String cityName;
    private String areaName;
    private int province;
    private int city;
    private int area;
    private String id;


    public EditAddressCodeIn(String address, String provinceName, String cityName, String areaName, int province, int city, int area,String id) {
        this.address = address;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.province = province;
        this.city = city;
        this.area = area;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }


    @Override
    public String toString() {
        return "EditAddressCodeIn{" +
                ", address='" + address + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", province=" + province +
                ", city=" + city +
                ", area=" + area +
                '}';
    }
}
