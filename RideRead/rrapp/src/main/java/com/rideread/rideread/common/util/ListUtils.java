package com.rideread.rideread.common.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JKL on 2017/3/15.
 */
public class ListUtils {
    public interface Func<TObj, TResult> {
        TResult call(final TObj target) throws Exception;
    }

    public static boolean isEmpty(@Nullable final List<?> objs) {
        return objs == null || objs.size() == 0;
    }

    public static <Input, Output> List<Output> convert(@NonNull final List<Input> objs, @NonNull final Func<Input, Output> callback) {
        final List<Output> values = new ArrayList<>(objs.size());
        for (final Input obj : objs) {
            try {
                values.add(callback.call(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    @Nullable
    public static <T> List<T> filter(@Nullable final List<T> objs, @NonNull final Func<T, Boolean> filter) {
        if (isEmpty(objs))
            return null;
        final List<T> values = new ArrayList<>(objs.size());
        for (final T obj : objs) {
            try {
                if (filter.call(obj))
                    values.add(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    public static <T> T find(@Nullable final List<T> objs, @NonNull final Func<T, Boolean> filter) {
        if (isEmpty(objs))
            return null;
        for (final T obj : objs) {
            try {
                if (filter.call(obj))
                    return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
