package com.example.huangjiahao.qianjiangweather.fragment;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.huangjiahao.qianjiangweather.MyApplication;
import com.example.huangjiahao.qianjiangweather.R;
import com.example.huangjiahao.qianjiangweather.activity.MainActivity;
import com.example.huangjiahao.qianjiangweather.adapter.cityAdapter;
import com.example.huangjiahao.qianjiangweather.base.BaseFragment;
import com.example.huangjiahao.qianjiangweather.util.Utils;
import com.example.huangjiahao.qianjiangweather.view.WrapRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by JiaHao.H on 2017/4/24.
 */

public class ChooseAreaFragment extends BaseFragment  {
    private WrapRecyclerView mRecyclerView;
    private cityAdapter mCityAdapter;
    private ArrayList<String> mDataSet;
    private Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_choose_area;
    }

    @Override
    protected void initViews() {
        mRecyclerView = (WrapRecyclerView) view.findViewById(R.id.list_view);

        String[] adapterData = new String[]{"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
        mCityAdapter = new cityAdapter(getActivity());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCityAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setClickEvent() {

    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Utils.showToast("You choose!");
//    }
//
//    @Override
//    public void onDeleteBtnClick(View view, int position) {
//        Utils.showToast("YouChooseDelete");
//        mCityAdapter.removeData(position);
//    }

}
