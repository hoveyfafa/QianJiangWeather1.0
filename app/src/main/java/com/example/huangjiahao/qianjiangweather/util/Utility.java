package com.example.huangjiahao.qianjiangweather.util;

import android.text.TextUtils;

import com.example.huangjiahao.qianjiangweather.db.QJWeatherDB;
import com.example.huangjiahao.qianjiangweather.model.CityModel;
import com.example.huangjiahao.qianjiangweather.model.CountyModel;
import com.example.huangjiahao.qianjiangweather.model.ProvinceModel;

/**
 * Created by JiaHao.H on 2016/12/4.
 */
public class Utility {
//    解析和处理服务器返回的省级数据
    public synchronized static boolean handleProvincesResponse(QJWeatherDB qjWeatherDB, String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length>0){
                for (String p:allProvinces){
                    String[] array = p.split("\\|");
                    ProvinceModel province = new ProvinceModel();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
//                    将解析出来的数据存储到Province表
                    qjWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
//    解析和处理服务器返回的市级数据
    public static boolean handleCitiesResponse(QJWeatherDB qjWeatherDB, String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length >0){
                for (String c : allCities){
                    String[] array = c.split("\\|");
                    CityModel city = new CityModel();
                    city.setCityCode(array[0]);
                    city.setCiytName(array[1]);
                    city.setProvinceId(provinceId);
//                    将解析出来的数据存储到City表
                    qjWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
//    解析和处理服务器返回的县级数据
    public static boolean handleCountiesResponse(QJWeatherDB qjWeatherDB,String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length >0){
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    CountyModel county = new CountyModel();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
//                    将解析出来的数据存储到County表
                    qjWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
