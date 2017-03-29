package com.example.huangjiahao.qianjiangweather.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.huangjiahao.qianjiangweather.R;
import com.example.huangjiahao.qianjiangweather.activity.MainActivity;
import com.example.huangjiahao.qianjiangweather.util.SPUtils;

/**
 * Created by JH.H on 2017/3/28.
 */

public abstract class BaseActivity extends FragmentActivity {
    public static RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    protected View parentView;
    protected RelativeLayout rootView;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = LayoutInflater.from(this).inflate(R.layout.layout_base, null);
        rootView = (RelativeLayout) parentView.findViewById(R.id.root_view);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressBar);
        rootView.addView(LayoutInflater.from(this).inflate(setLayoutView(), null), 0, params);
        setContentView(parentView);
        initViews();
        initData();
        setClickEvent();
    }

    protected abstract int setLayoutView();

    protected abstract void initViews();

    protected abstract void initData();

    protected abstract void setClickEvent();
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 展示  加载进度框
     */
    public void showProgress() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    public void dissProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }


    public void turnToNextActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
    /**
     * 登出
     * @param context
     */
    public void logoutAndToHome(Context context) {
        //清空UserId
        SPUtils.saveString(this, "user_id", "");
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }
}
