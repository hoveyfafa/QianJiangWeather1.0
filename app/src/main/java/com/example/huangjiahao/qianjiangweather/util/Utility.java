package com.example.huangjiahao.qianjiangweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.example.huangjiahao.qianjiangweather.model.CityModel;
import com.example.huangjiahao.qianjiangweather.model.CountyModel;
import com.example.huangjiahao.qianjiangweather.model.ProvinceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JiaHao.H on 2016/12/4.
 */
public class Utility {
//    解析和处理服务器返回的省级数据
    public synchronized static boolean handleProvincesResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
            JSONArray allProvinces = new JSONArray(response);
            for (int i = 0 ;i<allProvinces.length(); i++) {
                JSONObject provinceObject = allProvinces.getJSONObject(i);
                ProvinceModel province = new ProvinceModel();
                province.setProvinceName(provinceObject.getString("name"));
                province.setProvinceCode(provinceObject.getInt("Id"));
                province.save();
            }
            return true;
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
//    解析和处理服务器返回的市级数据
    public static boolean handleCitiesResponse(String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0 ;i<allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    CityModel city = new CityModel();
                    city.setCiytName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("Id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
//    解析和处理服务器返回的县级数据
    public static boolean handleCountiesResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0 ;i<allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    CountyModel county = new CountyModel();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getInt("Id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

////    解析服务器返回的json数据，并将解析出的数据存储到本地
//    public static void handleWeatherResponse(Context context,String response){
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
//            String cityName = weatherInfo.getString("city");
//            String weatherCode = weatherInfo.getString("cityid");
//            String temp1 = weatherInfo.getString("temp1");
//            String temp2 = weatherInfo.getString("temp2");
//            String weatherDesp = weatherInfo.getString("weather");
//            String publishTime = weatherInfo.getString("ptime");
//            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//    }

////    将服务器返回的所有天气信息存储到SharedPreferences文件中
//    public static void saveWeatherInfo(Context context,String cityName,String weatherCode,
//                                       String temp1,String temp2,String weatherDesp,String publishTime){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
//        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
//        editor.putBoolean("city_selected",true);
//        editor.putString("city_name", cityName);
//        editor.putString("weather_code", weatherCode);
//        editor.putString("temp1", temp1);
//        editor.putString("temp2", temp2);
//        editor.putString("weather_desp", weatherDesp);
//        editor.putString("weather_time", publishTime);
//        editor.putString("current_date",simpleDateFormat.format(new Date()));
//        editor.commit();
//    }
}
