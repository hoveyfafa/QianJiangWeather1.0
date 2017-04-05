package com.example.huangjiahao.qianjiangweather.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huangjiahao.qianjiangweather.MyApplication;
import com.example.huangjiahao.qianjiangweather.R;
import com.example.huangjiahao.qianjiangweather.base.BaseFragment;
import com.example.huangjiahao.qianjiangweather.service.AutoUpdateService;
import com.example.huangjiahao.qianjiangweather.util.HttpUtil;
import com.example.huangjiahao.qianjiangweather.util.Utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gson.Forecast;
import gson.Weather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by JH.H on 2017/3/30.
 */

public class WeatherFragment extends BaseFragment implements View.OnClickListener {
    private ScrollView mSvWeatherLayout;
    private TextView mTvTitleCity;
    private TextView mTvTitleUpdateTime;
    private TextView mTvDegree;
    private TextView mTvWeatherInfoLayout;
    private LinearLayout mLlForecast;
    private TextView mTvAQI;
    private TextView mTvPM25;
    private TextView mTvComfort;
    private TextView mTvCarWash;
    private TextView mTvSport;
    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;
    private Button mBtnNav;
    private Map<String,String> params = new HashMap<>();
    @Override
    protected int setLayout() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initViews() {

        mSvWeatherLayout = (ScrollView) view.findViewById(R.id.sv_weather_layout);
        mTvTitleCity = (TextView) view.findViewById(R.id.title_city);
        mTvTitleUpdateTime = (TextView) view.findViewById(R.id.title_update_time);
        mTvDegree = (TextView) view.findViewById(R.id.degree_text);
        mTvWeatherInfoLayout = (TextView) view.findViewById(R.id.weather_info_text);
        mLlForecast = (LinearLayout) view.findViewById(R.id.forecast_layout_ll);
        mTvAQI = (TextView) view.findViewById(R.id.aqi_text);
        mTvPM25 = (TextView) view.findViewById(R.id.pm25_text);
        mTvComfort = (TextView) view.findViewById(R.id.comfort_text);
        mTvCarWash = (TextView) view.findViewById(R.id.car_wash_text);
        mTvSport = (TextView) view.findViewById(R.id.sport_text);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        mBtnNav = (Button) view.findViewById(R.id.nav_button);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String weatherString = prefs.getString("weather", null);

        final String weatherId;
        if (weatherString != null){
//            有缓存时直接解析天气
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        }else {
            weatherId = MyApplication.getInstance().getWeatherId();
            mSvWeatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });
        mBtnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT>=21){
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void setClickEvent() {
        mTvTitleCity.setOnClickListener(this);
    }

    public void requestWeather(final String weatherId){

        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId +"&key=bc0418b57b2d4918819d3974ac1285d9";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        }else {
                            Toast.makeText(getContext(),"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });

    }

    private void showWeatherInfo(Weather weather){
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        mTvTitleCity.setText(cityName);
        mTvTitleUpdateTime.setText(updateTime);
        mTvDegree.setText(degree);
        mTvWeatherInfoLayout.setText(weatherInfo);
        mLlForecast.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, mLlForecast, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min+"℃");
            mLlForecast.addView(view);
        }
        if (weather.aqi != null) {
            mTvAQI.setText(weather.aqi.city.aqi);
            mTvPM25.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度" + weather.suggestion.comfort.info;
        String carWash = "洗车指数" + weather.suggestion.carWash.info;
        String sport = "运动建议" + weather.suggestion.sport.info;
        mTvComfort.setText(comfort);
        mTvCarWash.setText(carWash);
        mTvSport.setText(sport);
        mSvWeatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(getContext(),AutoUpdateService.class);
        getContext().startService(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
