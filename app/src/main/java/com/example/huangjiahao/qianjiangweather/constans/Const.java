package com.example.huangjiahao.qianjiangweather.constans;

import android.content.SharedPreferences;

/**
 * Created by JH.H on 2017/3/28.
 */

public class Const {
    public static String stime= AppSafeHelper.getSTime();
    public static String sign = AppSafeHelper.sign(Const.stime);
    public static SharedPreferences preferences;
    public static String REGULAR_PHONENO = "^((13[0-9])|(147)|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";
    public static String PWD = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,10}$";

    public static String PHONE = "phone";
    /**
     * 登陆的信息
     */
    public static final String LOGIN_USER = "login_user";
    public static final String DEVICEID = "deviceid";
    public static final String CARDETAIL = "carDetail";

}
