package com.example.huangjiahao.qianjiangweather.model;

import org.litepal.crud.DataSupport;

/**
 * Created by JiaHao.H on 2016/12/3.
 */
public class CityModel extends DataSupport {
    private int id;

    private String ciytName;

    private int cityCode;

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

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
