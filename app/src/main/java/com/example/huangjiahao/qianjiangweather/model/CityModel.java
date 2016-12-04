package com.example.huangjiahao.qianjiangweather.model;

/**
 * Created by JiaHao.H on 2016/12/3.
 */
public class CityModel {
    private int id;
    private String ciytName;
    private String cityCode;
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCiytName() {
        return ciytName;
    }

    public void setCiytName(String ciytName) {
        this.ciytName = ciytName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
