package com.example.huangjiahao.qianjiangweather.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JH.H on 2017/3/28.
 */

public abstract class BaseFragment extends Fragment {

    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(setLayout(), container, false);
            initViews();
            initData();
            setClickEvent();
        } else {
            ViewGroup p = (ViewGroup) view.getParent();
            if (p != null)
                p.removeAllViewsInLayout();
        }
        return view;
    }
    protected abstract int setLayout();

    protected abstract void initViews();
    protected abstract void initData();

    protected abstract void setClickEvent();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
