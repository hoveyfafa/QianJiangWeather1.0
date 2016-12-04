package com.example.huangjiahao.qianjiangweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huangjiahao.qianjiangweather.model.CityModel;
import com.example.huangjiahao.qianjiangweather.model.CountyModel;
import com.example.huangjiahao.qianjiangweather.model.ProvinceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiaHao.H on 2016/12/3.
 */
public class QJWeatherDB {
//    数据库名
    public static final String DB_NANE = "qianjiang_weather";

//    数据库版本
    public static  final int VERSION = 1;

    private static QJWeatherDB qjWeatherDB;

    private SQLiteDatabase db;

//    将构造方法私有化
    private QJWeatherDB(Context context){
        QJWeatherOpenHelper dbHelper = new QJWeatherOpenHelper(context,DB_NANE,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

//    获取QJWeatherDB的实例
    public synchronized static QJWeatherDB getInstance(Context context){
        if (qjWeatherDB == null){
            qjWeatherDB = new QJWeatherDB(context);
        }
        return qjWeatherDB;
    }

//    将Province实例存储到数据库
    public void saveProvince(ProvinceModel province){
        if (province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }

//    从数据库读取全国所有省份的信息
    public List<ProvinceModel> loadProvinces(){
        List<ProvinceModel> list = new ArrayList<ProvinceModel>();
        Cursor cursor = db.query(
                "Province",null,null,null,null,null,null
        );
        if (cursor.moveToFirst()){
            do {
                ProvinceModel province = new ProvinceModel();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

//    将City实例存储到数据库
    public void saveCity(CityModel city){
        if (city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCiytName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City", null, values);
        }
    }

//    从数据库读取某省下所有城市的信息
    public List<CityModel> loadCities(int provinceId){
        List<CityModel> list = new ArrayList<CityModel>();
        Cursor cursor = db.query("City",null,"province_id=?",
                new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do {

                    CityModel city = new CityModel();
                    city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    city.setCiytName(cursor.getString(cursor.getColumnIndex("city_name")));
                    city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                    city.setProvinceId(provinceId);
                    list.add(city);

            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

//    将County实例存储到数据库
    public void saveCounty(CountyModel county){
        if (county != null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County", null, values);
        }
    }

//    从数据库读取某城市下所有的县信息
    public List<CountyModel> loadCounties(int cityId){
        List<CountyModel> list = new ArrayList<CountyModel>();
        Cursor cursor = db.query("County",null,"city_id = ?",
                new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                CountyModel county = new CountyModel();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }
}
