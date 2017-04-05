package com.example.huangjiahao.qianjiangweather;

import android.app.Application;
import android.content.Context;

import com.example.huangjiahao.qianjiangweather.util.SPUtils;
import com.tandong.sa.zUImageLoader.cache.disc.naming.Md5FileNameGenerator;
import com.tandong.sa.zUImageLoader.cache.memory.impl.WeakMemoryCache;
import com.tandong.sa.zUImageLoader.core.ImageLoader;
import com.tandong.sa.zUImageLoader.core.ImageLoaderConfiguration;
import com.tandong.sa.zUImageLoader.core.assist.QueueProcessingType;

/**
 * Created by JH.H on 2017/3/31.
 */

public class MyApplication extends Application {
    private static MyApplication thisApplication;
    public static MyApplication getInstance() {
        return thisApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thisApplication =this;
        initImageLoader(thisApplication);
    }
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)//
                .threadPriority(Thread.NORM_PRIORITY - 2)//
                // .denyCacheImageMultipleSizesInMemory()//
                .memoryCache(new WeakMemoryCache())//
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//
                .tasksProcessingOrder(QueueProcessingType.LIFO)//
                // .writeDebugLogs()//
                .build();
        ImageLoader.getInstance().init(config);
    }
    /**
     * 保存 userId
     *
     * @param userId
     * @return
     */
    public void setUserId(String userId) {
        SPUtils.saveString(this, "user_id", userId);
    }

    public String getUserId() {
        return SPUtils.getString(this, "user_id","");
    }

    public void setLogin(boolean islogin) {
        SPUtils.saveBoolean(this, "is_login", islogin);
    }

    public boolean isLogin() {
        return SPUtils.getBoolean(this, "is_login",false);
    }

    //首次使用app
    public boolean isFirstUseApp(){
        return SPUtils.getBoolean(this,"is_first_use_app",true);
    }

    //保存首次使用app状态标志
    public void saveFirstUseApp(boolean isFirst){
        SPUtils.saveBoolean(this,"is_first_use_app",isFirst);
    }
    //    注册时userId
    public void setSignUserId(String userId){
        SPUtils.saveString(this,"sign_user_id",userId);
    }
    public String getSignUserId(){
        return SPUtils.getString(this,"sign_user_id","");
    }

//    weatherId
    public void setWeatherId(String weatherId){
        SPUtils.saveString(this,"weather_id",weatherId);
    }
    public String getWeatherId(){
        return SPUtils.getString(this,"weather_id","");
    }
}
