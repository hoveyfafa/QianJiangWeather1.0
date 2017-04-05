package com.example.huangjiahao.qianjiangweather.bean;

import java.io.Serializable;

/**
 * Created by JH.H on 2017/4/5.
 */

public class DailyForecastBean implements Serializable {
    public AstroBean astro;
    public CondBean cond;
    public String date;
    public String hum;
    public String pcpn;
    public String pop;
    public String pres;
    public TemperatureBean tmp;
    public String uv;
    public String vis;
    public WindBean wind;
}
