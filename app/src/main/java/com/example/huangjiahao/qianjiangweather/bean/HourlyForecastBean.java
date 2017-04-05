package com.example.huangjiahao.qianjiangweather.bean;

import java.io.Serializable;

/**
 * Created by JH.H on 2017/4/5.
 */

public class HourlyForecastBean implements Serializable {
    public CondBean cond;
    public String date;
    public String hum;
    public String pop;
    public String pres;
    public String tmp;
    public WindBean wind;
}
