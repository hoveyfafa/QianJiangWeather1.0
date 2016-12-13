package com.example.huangjiahao.qianjiangweather.util;


import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by JiaHao.H on 2016/12/4.
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
