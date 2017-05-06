package com.rideread.rideread.common.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.bugtags.library.Bugtags;
import com.rideread.rideread.BuildConfig;
import com.rideread.rideread.data.CurCache;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.Storage;
import com.rideread.rideread.function.net.im.MessageHandler;
import com.rideread.rideread.rrapp.RRApp;

import java.lang.ref.WeakReference;

/**
 * Created by SkyXiao on 2017/3/30.
 */

public class Utils {
    private static WeakReference<RRApp> _app;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final RRApp context) {

        _app = new WeakReference<>(context);
        Logger.init(context);//日志库相关
        Storage.init(context);
        CurCache.init();
        ScreenUtils.init(context);
        AMapLocationUtils.init();
        //        XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);
        //在这里初始化
        Bugtags.start("b5f67bdeaa3897a93fa83553c4574397", context, Bugtags.BTGInvocationEventNone);
        //        PackageUtils.init(context);

        // 请用你的AppId，AppKey。并在管理台启用手机号码短信验证
        AVOSCloud.initialize(context, AppUtils.LEAN_CLOUD_APP_ID, AppUtils.LEAN_CLOUD_APP_KEY);
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);
        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(context));
        UserUtils.openClient();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    @Nullable
    public static RRApp getAppContext() {
        if (_app != null) return _app.get();
        throw new NullPointerException("u should init first");
    }

    public static String getString(int resId) {
        return getAppContext().getResources().getString(resId);
    }


    public static String getString(int resId, Object... formatArgs) {
        return getAppContext().getResources().getString(resId, formatArgs);
    }
}
