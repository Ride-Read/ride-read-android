package com.rideread.rideread.rrapp;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.rideread.rideread.BuildConfig;
import com.rideread.rideread.common.util.AppUtils;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.function.net.im.MessageHandler;

/**
 * Created by SkyXiao on 2017/3/30.
 */

public class RRApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Utils.init(this);//其他工具类初始化（缓存、hwak）

        XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);

        // 请用你的AppId，AppKey。并在管理台启用手机号码短信验证
        AVOSCloud.initialize(this, AppUtils.APP_ID, AppUtils.APP_KEY);
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);
        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
