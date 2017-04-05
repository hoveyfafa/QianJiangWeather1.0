package com.example.huangjiahao.qianjiangweather.util;

import android.text.TextUtils;

import com.tandong.sa.json.Gson;
import com.tandong.sa.json.GsonBuilder;
import com.tandong.sa.json.JsonSyntaxException;
import com.tandong.sa.json.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by JH.H on 2017/4/5.
 */

public class JsonUtils {
    /**
     * 将给定的 JSON 字符串转换成指定的类型对象。
     *
     * @param jsonStr   给定的 JSON 字符串。
     * @param className 要转换的目标类。
     * @return 给定的类型对象。
     */
    public static <T> T fromJson(String jsonStr, Class<T> className) {
        if (isEmpty(jsonStr))
            return null;

        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(jsonStr, className);
        } catch (JsonSyntaxException e) {
            throw e;
        }
        return t;
    }

    /**
     * 返回指定类型集合
     *
     * @param jsonStr
     * @param clazz
     * @param key
     */
    public static <T> List<T> fromJson(String jsonStr, Class<T> clazz,
                                       String key) {
        if (isEmpty(jsonStr)) {
            return null;
        }
        String json = getJsonStr(jsonStr, key);
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(gson.fromJson(jsonArray.getString(i), clazz));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 返回指定类型集合
     *
     * @param jsonStr
     * @param clazz
     */
//        public static <T> List<T> fromJson(String jsonStr, Class<T> clazz) {
//            if (isEmpty(jsonStr)) {
//                return null;
//            }
//            Gson gson = new Gson();
//            List<T> list = new ArrayList<T>();
//            try {
//                JSONArray jsonArray = new JSONArray(json);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    list.add(gson.fromJson(jsonArray.getString(i), clazz));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return list;
//        }
    /**
     * 将给定的 JSON 字符串转换成指定类型的List对象。
     *
     * @param jsonStr 给定的 JSON 字符串。
     * @return HashMap数组 ArrayList
     */
    @SuppressWarnings("hiding")
    public static <HashMap> ArrayList<HashMap> fromJsonList(String jsonStr) {
        if (isEmpty(jsonStr))
            return null;

        Gson gson = new Gson();
        TypeToken<ArrayList<HashMap>> token = new TypeToken<ArrayList<HashMap>>() {
        };
        return gson.fromJson(jsonStr, token.getType());
    }

    public static boolean isEmpty(String str) {
        boolean flag = false;
        if (str == null || "".equals(str)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据json的name获取值
     *
     * @param jsonStr
     * @param name
     * @return
     */
    public static String getJsonStr(String jsonStr, String name) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            return jsonObject.optString(name);
        } else {
            return "";
        }

    }

    /**
     * Flag为1的时候为成功，Flag为0的时候为失败
     *
     * @param JsonStr
     * @param name
     * @return
     */
    public static int getFlag(String JsonStr, String name) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.optInt(name);
    }

    /**
     * 把json 转换为ArrayList 形式
     *
     * @return
     * @author Vayne
     */
    public static List<Map<String, Object>> getList(String jsonString) {
        List<Map<String, Object>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(getMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将json 数组转换为Map 对象
     *
     * @param jsonString
     * @return
     * @author Vayne
     */
    public static Map<String, Object> getMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            @SuppressWarnings("unchecked")
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject string2JSON(String str) {

        try {

            return new JSONObject(str);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return new JSONObject();

    }

    /**
     * 对象转化为json对象
     *
     * @return
     */

    public static String ObjectToJson(Object object) {

        if (object == null) {

            return "";

        }

        Gson gson;

        try {

            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            return gson.toJson(object);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "";

    }
    public static Object jsonToObject(String json, Class<?> cls) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Object object = null;
        try {
            com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            object = gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
