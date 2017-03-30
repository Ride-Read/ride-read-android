package com.rideread.rideread.data;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.rideread.rideread.BuildConfig;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

import static com.tencent.mars.xlog.Xlog.AppednerModeAsync;
import static com.tencent.mars.xlog.Xlog.LEVEL_DEBUG;
import static com.tencent.mars.xlog.Xlog.LEVEL_INFO;

/**
 * 日志
 */

public final class Logger {
    public static void init(final Context ctx) {
        final String pkg = ctx.getApplicationContext().getPackageName();
        final String prefix = pkg.substring(pkg.lastIndexOf('.') + 1);

        System.loadLibrary("stlport_shared");
        System.loadLibrary("marsxlog");

        final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String logPath = SDCARD + "/" + prefix + "_logs";

        //init xlog
        if (BuildConfig.DEBUG) {
            Xlog.appenderOpen(LEVEL_DEBUG, AppednerModeAsync, ctx.getCacheDir().getAbsolutePath(), logPath, prefix);
            Xlog.setConsoleLogOpen(true);
        } else {
            Xlog.appenderOpen(LEVEL_INFO, AppednerModeAsync, ctx.getCacheDir().getAbsolutePath(), logPath, prefix);
            Xlog.setConsoleLogOpen(false);
        }
        Log.setLogImp(new Xlog());
    }

    public static void uninit() {
        Log.appenderClose();
    }

    public static void d(@NonNull final String tag, @NonNull final String message, @NonNull Object... args) {
        Log.d(tag, message, args);
    }

    public static void i(@NonNull final String tag, @NonNull final String message, @NonNull Object... args) {
        Log.i(tag, message, args);
    }

    public static void v(@NonNull final String tag, @NonNull final String message, @NonNull Object... args) {
        Log.v(tag, message, args);
    }

    public static void w(@NonNull final String tag, @NonNull final String message, @NonNull Object... args) {
        Log.w(tag, message, args);
    }

    public static void e(@NonNull final String tag, @NonNull final String message, @NonNull Object... args) {
        Log.e(tag, message, args);
    }
}
