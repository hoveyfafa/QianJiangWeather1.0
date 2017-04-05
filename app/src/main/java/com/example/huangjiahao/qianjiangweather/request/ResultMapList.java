package com.example.huangjiahao.qianjiangweather.request;

import com.example.huangjiahao.qianjiangweather.request.Result;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by xiaoping on 16/2/24.
 */
public class ResultMapList<T> extends Result {

    private ArrayList<T> data;

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public static ResultMapList fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultMapList.class, clazz);
        ResultMapList resultMapList;
        try {
            resultMapList = gson.fromJson(json, objectType);
            return  resultMapList;
        }catch (Exception e){
            return null;
        }
    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultMapList.class, clazz);
        return gson.toJson(this, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
