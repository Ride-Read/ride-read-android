package com.rideread.rideread.rrapp;

import android.support.multidex.MultiDex;

import com.rideread.rideread.common.util.Utils;

/**
 * Created by SkyXiao on 2017/3/30.
 */

public class RRApp extends FrescoApp {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Utils.init(this);//其他工具类初始化（缓存、hwak）
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
