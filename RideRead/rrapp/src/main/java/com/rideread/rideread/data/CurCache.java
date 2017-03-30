package com.rideread.rideread.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;

import java.util.concurrent.Callable;

/**
 * 负责缓存数据
 */
public final class CurCache {
    private static final SparseArray<Object> CUR_CACHE = new SparseArray<>();

    public static void init() {

    }

    /**
     * 慎用：或造成defValue值的不必要获取
     *
     * @param key
     * @param defValue
     * @param <T>
     * @return
     */
    public static <T> T get(@NonNull final String key, @Nullable final T defValue) {
        if (TextUtils.isEmpty(key)) return defValue;
        final int hash = key.hashCode();
        return (T) CUR_CACHE.get(hash, defValue);
    }

    public static <T> T load(@NonNull final String key, @NonNull final Callable<T> getter) {
        final int hash = key.hashCode();
        if (!TextUtils.isEmpty(key)) {
            final T value = (T) CUR_CACHE.get(hash, null);
            if (value != null) return value;
        }
        try {
            final T value = getter.call();
            if (value != null) CUR_CACHE.put(hash, value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void put(@NonNull final String key, @Nullable final Object value) {
        if (TextUtils.isEmpty(key)) return;
        final int hash = key.hashCode();
        if (value == null) CUR_CACHE.remove(hash);
        else CUR_CACHE.put(hash, value);
    }

    public static void remove(@NonNull final String key) {
        if (TextUtils.isEmpty(key)) return;
        CUR_CACHE.remove(key.hashCode());
    }

    public static void clear() {
        CUR_CACHE.clear();
    }
}
