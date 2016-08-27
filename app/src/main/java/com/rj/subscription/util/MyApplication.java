package com.rj.subscription.util;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        //创建默认的ImageLoader配置参数  
        ImageLoaderConfiguration configuration= ImageLoaderConfiguration.createDefault(this);
        //Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(configuration);
        }
}
