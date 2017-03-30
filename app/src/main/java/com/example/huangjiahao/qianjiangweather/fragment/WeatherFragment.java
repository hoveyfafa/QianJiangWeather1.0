package com.example.huangjiahao.qianjiangweather.fragment;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.huangjiahao.qianjiangweather.R;
import com.example.huangjiahao.qianjiangweather.base.BaseFragment;

/**
 * Created by JH.H on 2017/3/30.
 */

public class WeatherFragment extends BaseFragment {
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
    private ImageView mIvBingPic;
    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;
    private Button mBtnNav;
    @Override
    protected int setLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void initViews() {

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

    }
}
