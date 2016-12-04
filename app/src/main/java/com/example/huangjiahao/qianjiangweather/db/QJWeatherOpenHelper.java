package com.example.huangjiahao.qianjiangweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JiaHao.H on 2016/12/3.
 */
public class QJWeatherOpenHelper extends SQLiteOpenHelper {
//    province 建表
    public static final String CREATE_PROVINCE = "create table Province("
            + "id integer primary key autoincreament,"
            + "province_name text,"
            + "province_code text)";

//    city 建表
    public static final String CREATE_CITY = "create table City("
            + "id integer primary key autoincreament,"
            + "city_name text,"
            + "city_code text,"
            + "province_id integer)";

//    county
    public  static final String CREATE_COUNTY = "create table county("
            + "id integer primary key autoincreament,"
            + "county_name text,"
            + "county_code text,"
            + "city_id integer)";

    public QJWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
