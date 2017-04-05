package com.example.huangjiahao.qianjiangweather.bean;

import java.io.Serializable;

/**
 * Created by JH.H on 2017/4/5.
 */

public class NowBean implements Serializable {
    public CondBean cond;
    public String fl;
    public String hum;
    public String pcpn;
    public String pres;
    public String tmp;
    public String vis;
    public WindBean wind;
}
