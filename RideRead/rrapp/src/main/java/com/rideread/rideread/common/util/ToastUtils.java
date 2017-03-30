package com.rideread.rideread.common.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by SkyXiao on 2016/10/25.
 */

public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void show(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text) {
        show(Utils.getAppContext(), text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     */
    public static void show(Context context, @StringRes int textRes, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, textRes, duration);
        } else {
            mToast.setText(textRes);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void show(Context context, @StringRes int textRes) {
        show(context, textRes, Toast.LENGTH_SHORT);
    }

    public static void show(@StringRes int textRes) {
        show(Utils.getAppContext(), textRes, Toast.LENGTH_SHORT);
    }

}
