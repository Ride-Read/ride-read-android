package com.rideread.rideread.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

/**
 * Created by JKL on 2017/2/24.
 * 负责永久存储数据
 */

public final class Storage {
    public static final String TAG = Storage.class.getSimpleName();

    public static void init(@NonNull final Context ctx) {
        if (Hawk.isBuilt())
            return;
        synchronized (Storage.class) {
            Hawk.init(ctx)
                    .setEncryption(new NoEncryption())
                    .setLogInterceptor(message -> Logger.d(TAG, message))
//                    .setConverter(new Converter())
//                    .setParser(new Parser())
//                    .setStorage(new Disk())
                    .build();
        }
    }

    public static boolean put(@NonNull final String key, @Nullable final Object value) {
        return !TextUtils.isEmpty(key) && (value == null ? Hawk.delete(key) : Hawk.put(key, value));
    }

    public static <T> T get(@NonNull final String key, @Nullable final T defValue) {
        return Hawk.get(key, defValue);
    }

    public static boolean delete(@NonNull final String key) {
        return Hawk.delete(key);
    }
}
