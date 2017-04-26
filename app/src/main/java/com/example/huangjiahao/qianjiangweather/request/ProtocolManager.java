package com.example.huangjiahao.qianjiangweather.request;

import com.example.huangjiahao.qianjiangweather.MyApplication;
import com.example.huangjiahao.qianjiangweather.util.MD5Utils;
import com.example.huangjiahao.qianjiangweather.util.Utils;
import com.example.xiaoping.okhttputil.OkHttpUtils;
import com.example.xiaoping.okhttputil.builder.OkHttpRequestBuilder;
import com.example.xiaoping.okhttputil.builder.PostFormBuilder;
import com.example.xiaoping.okhttputil.callback.StringCallback;
import com.example.xiaoping.okhttputil.request.RequestCall;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by xiaoping on 16/2/25.
 */
public class ProtocolManager {


    public enum HttpMethod {
        POST,
        GET,
    }

    private String BASE_URL = RequestUrl.IP;

    private static ProtocolManager mInstance;

    public static ProtocolManager getInstance() {
        if (mInstance == null) {
            synchronized (ProtocolManager.class) {
                if (mInstance == null) {
                    mInstance = new ProtocolManager();
                }
            }
        }
        return mInstance;
    }

    private ProtocolManager() {
    }


    public void request(String url, final ReponseCallback reponseCallback, HttpMethod httpMethod) {
        request(null, url, reponseCallback, httpMethod);
    }

    public void request(String url, final ReponseCallback reponseCallback) {
        request(null, url, reponseCallback, HttpMethod.POST);
    }

    public void request(Map<String, String> params, String url, final ReponseCallback reponseCallback) {
        request(params, url, reponseCallback, HttpMethod.POST);
    }

    public void request(Map<String, String> params, String url, final ReponseCallback reponseCallback, HttpMethod httpMethod) {
        if (!Utils.isNetworkConnected(MyApplication.getInstance())) {
            reponseCallback.fail("请检查网络连接");
            return;
        }
        if (params == null) {
            params = new HashMap<>();
        }
//        和风Key
        params.put("key","c6fa13bc8e2d4c3abf60751d669e22db");

        Map<String, String> headers = new HashMap<>();

        OkHttpRequestBuilder okHttpRequestBuilder = null;
        if (httpMethod == HttpMethod.POST) {
            okHttpRequestBuilder = OkHttpUtils.post();
        } else {
            okHttpRequestBuilder = OkHttpUtils.get();
        }
        okHttpRequestBuilder.url(BASE_URL + url)
                .params(params)
                // .headers(headers)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        reponseCallback.fail("链接超时，请稍后重试");
                    }

                    @Override
                    public void onResponse(String response) {
//                        if (response.indexOf("Exception") > 0 || response.indexOf("exception") > 0) {
//                            reponseCallback.fail("链接超时，请稍后重试");
//                        } else {
                            reponseCallback.success(response);
//                        }
                    }
                });
    }


    public void uploadFile(Map<String, String> params, String url, final ReponseCallback reponseCallback, Map<String, File> fileMap) {
        {
            if (!Utils.isConnect(MyApplication.getInstance())) {
                Utils.showToast(MyApplication.getInstance(), "请检查网络连接");
                reponseCallback.fail("请检查网络连接");
                return;
            }
            if (params == null) {
                params = new HashMap<>();
            }
            //params.put("token", SettingUtil.getUserToken());
            //Map<String, String> headers = new HashMap<>();
            //headers.put("token", SettingUtil.getUserToken());
            //headers.put("Content-Type", "application/json");
            //LogUtils.e(ProtocolManager.class, BASE_URL + url);
            PostFormBuilder postFormBuilder = OkHttpUtils.post();
            // postFormBuilder.url(BASE_URL + url).params(params).headers(headers);
            postFormBuilder.url(BASE_URL + url).params(params);
            for (String key : fileMap.keySet()) {
                postFormBuilder.addFile(key, MD5Utils.md5(fileMap.get(key).getName()), fileMap.get(key));
            }
            RequestCall requestCall = postFormBuilder.build();
            requestCall.readTimeOut(20000);
            requestCall.writeTimeOut(20000);
            requestCall.connTimeOut(20000);
            requestCall.execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    if (reponseCallback != null) {
                        reponseCallback.fail("链接超时，请稍后重试");
                    }
                }

                @Override
                public void onResponse(String response) {
                    if (reponseCallback != null) {
                        reponseCallback.success(response);
                    }
                }
            });
        }
    }


    public interface ReponseCallback {
        void fail(String msg);

        void success(String response);


    }
}
