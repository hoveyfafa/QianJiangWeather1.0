package com.example.huangjiahao.qianjiangweather.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JH.H on 2017/4/5.
 */

public class WeatherBean implements Serializable{
    public AQIBean aqi;
    public BasicBean basic;
    public List<DailyForecastBean> daily_forecast;
    public List<HourlyForecastBean> hourly_forecast;
    public NowBean now;
    public String status;
    public SuggestionBean suggestion;
}
