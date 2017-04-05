package com.example.huangjiahao.qianjiangweather.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangjiahao.qianjiangweather.MyApplication;
import com.example.huangjiahao.qianjiangweather.R;
import com.example.huangjiahao.qianjiangweather.model.CityModel;
import com.example.huangjiahao.qianjiangweather.model.CountyModel;
import com.example.huangjiahao.qianjiangweather.model.ProvinceModel;
import com.example.huangjiahao.qianjiangweather.util.HttpUtil;
import com.example.huangjiahao.qianjiangweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by JiaHao.H on 2016/12/13.
 */
public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog mPdProgress;

    private TextView mTvTitle;

    private Button mBtnBack;

    private ListView mListView;

    private ArrayAdapter<String> mAdapter;

    private List<String> dataList = new ArrayList<>();

//    省列表
    private List<ProvinceModel> provinceModelList;
//    市列表
    private List<CityModel> cityModelsList;
//    县列表
    private List<CountyModel>countyModelList;
//    选中的省份
    private ProvinceModel selectedProvince;
//    选中的城市
    private CityModel selectedCity;
//    当前选中的级别
    private int currentLevel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_area,container,false);
        mTvTitle = (TextView) view.findViewById(R.id.title_text);
        mBtnBack = (Button) view.findViewById(R.id.back_button);
        mListView = (ListView) view.findViewById(R.id.list_view);
//        ----------------
        mAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceModelList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityModelsList.get(position);
                    queryCounties();
                }else if (currentLevel == LEVEL_COUNTY){
                    String weatherId = countyModelList.get(position).getWeatherId();
//                    if (getActivity() instanceof MainActivity){
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        intent.putExtra("weather_id",weatherId);
//                        startActivity(intent);
//                        getActivity().finish();
//                    }else if (getActivity() instanceof MainActivity){
//                        MainActivity  activity = (MainActivity) getActivity();
//                        activity.drawerLayout.closeDrawers();
//                        activity.swipeRefresh.setRefreshing(true);
//                        activity.requestWeather(weatherId);
//                    }
                    MyApplication.getInstance().setWeatherId(weatherId);

                }
            }
        });
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }
    private void queryProvinces(){
        mTvTitle.setText("China");
        mBtnBack.setVisibility(View.GONE);
        provinceModelList = DataSupport.findAll(ProvinceModel.class);
        if (provinceModelList.size() > 0){
            dataList.clear();
            for (ProvinceModel province : provinceModelList){
                dataList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }
    private void queryCities(){
        mTvTitle.setText(selectedProvince.getProvinceName());
        mBtnBack.setVisibility(View.VISIBLE);
        cityModelsList = DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId())).find(CityModel.class);
        if (cityModelsList.size() > 0){
            dataList.clear();
            for (CityModel city : cityModelsList){
                dataList.add(city.getCiytName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address,"city");
        }
    }
    private void queryCounties(){
        mTvTitle.setText(selectedCity.getCiytName());
        mBtnBack.setVisibility(View.VISIBLE);
        countyModelList = DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(CountyModel.class);
        if (countyModelList.size() > 0){
            dataList.clear();
            for (CountyModel county : countyModelList){
                dataList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/"+ cityCode;
            queryFromServer(address,"county");

        }
    }
    private void queryFromServer(String address, final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
//               通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handleProvincesResponse(responseText);
                }else if ("city".equals(type)){
                    result = Utility.handleCitiesResponse(responseText,selectedProvince.getId());
                }else if ("county".equals(type)){
                    result = Utility.handleCountiesResponse(responseText,selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }
    private void showProgressDialog(){
        if (mPdProgress == null){
            mPdProgress = new ProgressDialog(getActivity());
            mPdProgress.setMessage("正在加载...");
            mPdProgress.setCanceledOnTouchOutside(false);
        }
        mPdProgress.show();
    }
    private void closeProgressDialog(){
        if (mPdProgress != null){
            mPdProgress.dismiss();
        }
    }
}
