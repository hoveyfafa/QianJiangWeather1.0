package com.example.huangjiahao.qianjiangweather.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huangjiahao.qianjiangweather.R;
import com.example.huangjiahao.qianjiangweather.util.HttpCallbackListener;
import com.example.huangjiahao.qianjiangweather.util.HttpUtil;
import com.example.huangjiahao.qianjiangweather.util.Utility;

/**
 * Created by JiaHao.H on 2016/12/4.
 */
public class WeatherActicity extends Activity {

    private LinearLayout weatherInfoLayout;

    private TextView cityNameText;
    private TextView publishTimeText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_layout);
//        初始化控件
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.weather_city_name);
        publishTimeText = (TextView) findViewById(R.id.weather_publish_tv);
        weatherDespText = (TextView) findViewById(R.id.weather_desp_tv);
        temp1Text = (TextView) findViewById(R.id.weather_temp1);
        temp2Text = (TextView) findViewById(R.id.weather_temp2);
        currentDateText = (TextView) findViewById(R.id.weather_current_date_tv);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)){
//            有县级代号时查询天气
            publishTimeText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else{
//            没有县级带好就直接显示本地天气
            showWeather();
        }
    }

//  查询县级代号所对应的天气代号
    private void queryWeatherCode(String countyCode){
        String address = "http://wwww.weather.com.cn/data/list3/city"+countyCode+".xml";
        queryFromServer(address,"countyCode");
    }

//    查询天际代号所对应的天气
    private void queryWeatherInfo(String weatherCode){
        String address = "http://wwww.weather.com.cn/data/list3/city"+weatherCode+".html";
        queryFromServer(address,"weatherCode");
    }

//    根据传入的地址和类型去向服务器查询天气代号或者天气信息
    private void queryFromServer(final String address,final String type){
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
//                        从服务器返回的数据中解析处天气代号
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
//                    处理服务器返回的天气信息
                    Utility.handleWeatherResponse(WeatherActicity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishTimeText.setText("同步失败");
                    }
                });
            }
        });
    }
//    从SharedPreferences文件中读取存储的天气信息，并显示到界面上
    private void showWeather(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(preferences.getString("cityName",""));
        temp1Text.setText(preferences.getString("temp1",""));
        temp2Text.setText(preferences.getString("temp2",""));
        weatherDespText.setText(preferences.getString("weather_desp",""));
        publishTimeText.setText("今天"+preferences.getString("publish_time","")+"发布");
        currentDateText.setText(preferences.getString("current_date",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }
}
