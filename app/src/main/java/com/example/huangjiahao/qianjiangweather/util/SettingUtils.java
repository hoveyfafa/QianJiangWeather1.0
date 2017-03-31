package com.example.huangjiahao.qianjiangweather.util;

import android.content.Context;

import com.example.huangjiahao.qianjiangweather.constans.Const;

/**
 * Created by JH.H on 2017/3/31.
 */

public class SettingUtils {
    public static void setDeciceId(Context context, String deviceid) {
        SPUtils.saveString(context, Const.DEVICEID, deviceid);
    }

    /**
     * 获取设备号
     */
    public static String getDeciceId(Context context) {
        return SPUtils.getString(context,Const.DEVICEID,"");
    }
}
