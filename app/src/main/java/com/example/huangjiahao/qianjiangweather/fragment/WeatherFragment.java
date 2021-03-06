package com.example.huangjiahao.qianjiangweather.fragment;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import com.example.huangjiahao.qianjiangweather.MyApplication;
import com.example.huangjiahao.qianjiangweather.R;
import com.example.huangjiahao.qianjiangweather.base.BaseFragment;
import com.example.huangjiahao.qianjiangweather.bean.CondBean;
import com.example.huangjiahao.qianjiangweather.bean.DailyForecastBean;

import com.example.huangjiahao.qianjiangweather.bean.WeatherBean;
import com.example.huangjiahao.qianjiangweather.request.ProtocolHelp;
import com.example.huangjiahao.qianjiangweather.request.ProtocolManager;
import com.example.huangjiahao.qianjiangweather.request.RequestUrl;


import com.example.huangjiahao.qianjiangweather.service.AutoUpdateService;
import com.example.huangjiahao.qianjiangweather.util.JsonUtils;
import com.example.huangjiahao.qianjiangweather.util.Utils;
import com.example.huangjiahao.qianjiangweather.view.ChangeAddressPopWindow;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gson.Forecast;
import gson.Weather;

/**
 * Created by JH.H on 2017/3/30.
 */

public class WeatherFragment extends BaseFragment  {
    private FrameLayout mFlMain;
    private ScrollView mSvWeatherLayout;
    private TextView mTvTitleCity;
    private TextView mTvTitleUpdateTime;
    private TextView mTvDegree;
    private TextView mTvWeatherInfoLayout;
    private LinearLayout mLlForecast;
    private TextView mTvAQI;
    private TextView mTvPM25;
    private TextView mTvComfort,mTvComfortTxt;
    private TextView mTvCarWash,mTvCarWashTxt;
    private TextView mTvSport,mTvSportTxt;
    private ImageView mIvWeatherIcon;
    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout drawerLayout;
    private Button mBtnNav;
    private Map<String,String> param = new HashMap<>();
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener ;
    private  String mDistract = "朝阳区";

    private String result;
//    private WeatherBean weatherBean ;

    @Override
    protected int setLayout() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initViews() {

        mFlMain = (FrameLayout) view.findViewById(R.id.fl_main);
        mSvWeatherLayout = (ScrollView) view.findViewById(R.id.sv_weather_layout);
        mTvTitleCity = (TextView) view.findViewById(R.id.title_city);
        mTvTitleUpdateTime = (TextView) view.findViewById(R.id.title_update_time);
        mTvDegree = (TextView) view.findViewById(R.id.degree_text);
        mTvWeatherInfoLayout = (TextView) view.findViewById(R.id.weather_info_text);
        mLlForecast = (LinearLayout) view.findViewById(R.id.forecast_layout_ll);
        mTvAQI = (TextView) view.findViewById(R.id.aqi_text);
        mTvPM25 = (TextView) view.findViewById(R.id.pm25_text);
        mTvComfort = (TextView) view.findViewById(R.id.comfort_text);
        mTvComfortTxt = (TextView) view.findViewById(R.id.comfort_text_suggestion);
        mTvCarWash = (TextView) view.findViewById(R.id.car_wash_text);
        mTvCarWashTxt = (TextView) view.findViewById(R.id.car_wash_text_suggestion);
        mTvSport = (TextView) view.findViewById(R.id.sport_text);
        mTvSportTxt = (TextView) view.findViewById(R.id.sport_text_suggestion);
        mIvWeatherIcon = (ImageView) view.findViewById(R.id.iv_weather_icon);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        mBtnNav = (Button) view.findViewById(R.id.nav_button);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String weatherString = prefs.getString("weather", null);
        mTvTitleCity.setText("江干区");
        mTvTitleCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                ChangeAddressPopWindow mChangeAddressPooWindow = new ChangeAddressPopWindow(getActivity());
                mChangeAddressPooWindow.setAddress("广东", "深圳", "福田区");
                mChangeAddressPooWindow.showAtLocation(mTvTitleCity, Gravity.BOTTOM, 0, 0);
                mChangeAddressPooWindow.setAddresskListener(new ChangeAddressPopWindow.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city, String area) {
                        if (area.equals("")) {

                            mTvTitleCity.setText(city);
                        } else {

                            mTvTitleCity.setText(area);
                        }
                    }
                });

            }
        });
        final String weatherId;
        if (weatherString != null){
//            有缓存时直接解析天气
            WeatherBean weather = Utils.handleWeatherResponse(weatherString);
//            weatherId = weather.basic.id;
            showWeatherInfo(weather);
        }else {
//            weatherId = MyApplication.getInstance().getWeatherId();
//            weatherId = getActivity().getIntent().getStringExtra("weather_id");
//            weatherId = "CN101190401";
            mSvWeatherLayout.setVisibility(View.INVISIBLE);
            getWeatherInfo(mDistract);
        }
//        getWeatherInfo();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                requestWeather(weatherId);
                getWeatherInfo(mDistract);
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

        myListener = new MyLocationListener();
        //声明LocationClient类
        mLocationClient = new LocationClient(getContext());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setAddrType("all");
        mLocationClient.setLocOption(option);
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
//        mTvTitleCity.setText();
//        if (!mDistract.equals("朝阳区")  || mDistract != null){
//            mTvTitleCity.setText(mDistract);
//            mLocationClient.stop();
//        }else {
//            mTvTitleCity.setText("朝阳区");
//            mLocationClient.stop();
//        }
//        mTvTitleCity.setText(mDistract);
        Log.i("aaaaa","---mDistractmDistractmDistract----"+mDistract+"-------");
//        mTvTitleCity.setText(myListener.);

//        getWeatherInfo();
    }

    @Override
    protected void setClickEvent() {
        mTvTitleCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                ChangeAddressPopWindow mChangeAddressPooWindow = new ChangeAddressPopWindow(getActivity());
                mChangeAddressPooWindow.setAddress("广东", "深圳", "福田区");
                mChangeAddressPooWindow.showAtLocation(mTvTitleCity, Gravity.BOTTOM, 0, 0);
                mChangeAddressPooWindow.setAddresskListener(new ChangeAddressPopWindow.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city, String area) {
                        if (area.equals("")) {

                            mTvTitleCity.setText(city);
                            mDistract = city;
                        } else {
                            mDistract = city;
                            mTvTitleCity.setText(area);
                        }
                    }
                });

            }
        });
    }
//
//    public void requestWeather(final String weatherId){
//
//        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId +"&key=bc0418b57b2d4918819d3974ac1285d9";
//        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getContext(),"获取天气信息失败",Toast.LENGTH_SHORT).show();
//                        swipeRefresh.setRefreshing(false);
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String responseText = response.body().string();
//                final Weather weather = Utility.handleWeatherResponse(responseText);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (weather != null && "ok".equals(weather.status)){
//                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
//                            editor.putString("weather",responseText);
//                            editor.apply();
//                            showWeatherInfo(weather);
//                        }else {
//                            Toast.makeText(getContext(),"获取天气信息失败",Toast.LENGTH_SHORT).show();
//                        }
//                        swipeRefresh.setRefreshing(false);
//                    }
//                });
//            }
//        });
//
//    }

    private void showWeatherInfo(WeatherBean weather){
        String cityName = weather.basic.city;
        String updateTime = weather.basic.update.loc.split(" ")[1];
        String degree = weather.now.tmp + "℃";
        String weatherInfo = weather.now.cond.txt;
        mTvTitleCity.setText(cityName);
        mTvTitleUpdateTime.setText(updateTime);
        mTvDegree.setText(degree);
        mTvWeatherInfoLayout.setText(weatherInfo);
        mLlForecast.removeAllViews();
        for (DailyForecastBean forecast : weather.daily_forecast) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, mLlForecast, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.cond.txt);
            maxText.setText(forecast.tmp.max+"℃");
            minText.setText(forecast.tmp.min);
            mLlForecast.addView(view);
        }
        if (weather.aqi != null) {
            mTvAQI.setText(weather.aqi.city.aqi);
            mTvPM25.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度   " + weather.suggestion.comf.brf;
        String carWash = "洗车指数  " + weather.suggestion.cw.brf;
        String sport = "运动建议    " + weather.suggestion.sport.brf;
        String comfortTxt = weather.suggestion.comf.txt;
        String carWashTxt = weather.suggestion.cw.txt;
        String sportTxt = weather.suggestion.sport.txt;
        mTvComfort.setText(comfort);
        mTvCarWash.setText(carWash);
        mTvSport.setText(sport);
        mTvComfortTxt.setText(comfortTxt);
        mTvCarWashTxt.setText(carWashTxt);
        mTvSportTxt.setText(sportTxt);
        int id = getResources().getIdentifier("w"+weather.now.cond.code,"drawable","com.example.huangjiahao.qianjiangweather");
        mIvWeatherIcon.setImageResource(id);
        if (weather.now.cond.code.equals("100")){
            mFlMain.setBackgroundColor(getResources().getColor(R.color.good_weather));
        }else {
            mFlMain.setBackgroundColor(getResources().getColor(R.color.bad_weather));
        }
        mSvWeatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(getContext(),AutoUpdateService.class);
        getContext().startService(intent);
    }
//
//    @Override
//    public void onClick(View view) {
//
//    }


    private class MyLocationListener implements BDLocationListener {
        private String distract;
        @Override
        public void onReceiveLocation(BDLocation location) {

            Log.i("aaaaa","---city----"+location.getDistrict()+"-------");
            distract = location.getDistrict();
            mDistract = location.getDistrict();
            Log.i("aaaaa","---Ddistract----"+distract+"-------");
            Log.i("aaaaa","---mDistractmDistract----"+mDistract+"-------");
//            if (location.getDistrict()!= null){
////                mTvTitleCity.setText(location.getDistrict());
//                mDistract = location.getDistrict();
//            }else {
//               mTvTitleCity.setText("朝阳区");
//            }
//            mTvTitleCity.setText(location.getDistrict());
//            mDistract = location.getDistrict();
            mTvTitleCity.setText(distract);
            mTvTitleCity.setText(mDistract);
            showTitle();
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    public void getWeatherInfo(final String city) {

        param.clear();
        param.put("city",city);
        ProtocolHelp.getInstance(getActivity()).protocolHelp(param, RequestUrl.WEATHER,
                ProtocolManager.HttpMethod.POST, WeatherBean.class,
                new ProtocolHelp.HttpCallBack() {
                    @Override
                    public void fail(String message) {
                        Toast.makeText(getContext(),"a获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                    @Override
                    public void success(String object) {

                        result = object;
                        final WeatherBean weatherBean = Utils.handleWeatherResponse(result);
                        getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (weatherBean != null){
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                                editor.putString("weather",result);
                                editor.apply();
                                showWeatherInfo(weatherBean);
                            }else {
                                Toast.makeText(getContext(),"b获取天气信息失败",Toast.LENGTH_SHORT).show();
                            }
                            swipeRefresh.setRefreshing(false);
                    }
                });
            }
//                        Gson gson = new Gson();
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(object);
//                            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
//                            String weatherContent = jsonArray.getJSONObject(0).toString();
//                            WeatherBean bean = gson.fromJson(weatherContent, WeatherBean.class);
//                            mIvWeatherIcon.getDrawable().setLevel(Integer.parseInt(bean.now.cond.code));
//                            showWeatherInfo(bean);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        Log.i("weather-code","------"+object+"--------");
//                        WeatherBean data = gson.fromJson(object,WeatherBean.class);
//                        显示天气状况
//                        WeatherBean data = (WeatherBean) object;
////                        WeatherBean data = gson.fromJson(object,WeatherBean.class);
////                        List<DailyForecastBean> dailyForecastBeen = data.daily_forecast;
//                        Log.i("weather-code","------"+data.now.cond.code+"--------");

//                        int code = Integer.parseInt(data.now.cond.code);
//                        Log.i("weather-code","------"+code+"--------");

                });
    }

    public void showTitle(){
        mTvTitleCity.setText(mDistract);
        mLocationClient.stop();
    }









    public void onDestroy(){
        mLocationClient.stop();
        super.onDestroy();
    }

    public void onResume(){
        super.onResume();
//        mTvTitleCity.setText(mDistract);

    }

}
