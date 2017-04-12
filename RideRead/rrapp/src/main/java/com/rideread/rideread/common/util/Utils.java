package com.rideread.rideread.common.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rideread.rideread.data.CurCache;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.Storage;
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
        //        PackageUtils.init(context);
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
