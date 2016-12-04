package com.example.huangjiahao.qianjiangweather.util;

/**
 * Created by JiaHao.H on 2016/12/4.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
