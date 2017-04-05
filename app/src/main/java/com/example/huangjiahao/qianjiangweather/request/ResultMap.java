package com.example.huangjiahao.qianjiangweather.request;

import com.example.huangjiahao.qianjiangweather.request.Result;
import com.tandong.sa.json.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xiaoping on 16/2/24.
 */
public class ResultMap<T> extends Result {

    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResultMap fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultMap.class, clazz);
        ResultMap resultMap;
        try {
            resultMap = gson.fromJson(json, objectType);
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }


    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultMap.class, clazz);
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