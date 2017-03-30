package com.rideread.rideread.common.util;

/**
 * Created by mrrobot on 16/10/21.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 */
public class ScreenUtils {
    private static final SparseArray<Object> cache = new SparseArray<>();

    private static final int KEY_SCREEN_WIDTH = "screenWidth".hashCode();
    private static final int KEY_SCREEN_HEIGHT = "screenHeight".hashCode();
    private static final int KEY_SCREEN_ORIENTATION = "screenOrientation".hashCode();
    private static final int KEY_SCREEN_DENSITY = "density".hashCode();
    private static final int KEY_SCREEN_SCALED_DENSITY = "scaledDensity".hashCode();

    public static void init(@NonNull final Context ctx) {
        final Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        cache.put(KEY_SCREEN_WIDTH, size.x);
        cache.put(KEY_SCREEN_HEIGHT, size.y);
        cache.put(KEY_SCREEN_ORIENTATION, ctx.getResources().getConfiguration().orientation);
        final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        cache.put(KEY_SCREEN_DENSITY, metrics.density);
        cache.put(KEY_SCREEN_SCALED_DENSITY, metrics.scaledDensity);
    }

    public static int getScreenWidth() {
        return (int) cache.get(KEY_SCREEN_WIDTH, 0);
    }

    public static int getScreenHeight() {
        return (int) cache.get(KEY_SCREEN_HEIGHT, 0);
    }

    public static int getOrientation() {
        return (int) cache.get(KEY_SCREEN_ORIENTATION, ORIENTATION_LANDSCAPE);
    }

    public static float getDensity() {
        return (float) cache.get(KEY_SCREEN_DENSITY, 0f);
    }

    public static float getScaledDensity() {
        return (float) cache.get(KEY_SCREEN_SCALED_DENSITY, 0f);
    }

    public static float dp2px(final float value) {
        return value * getDensity();
    }

    public static float sp2px(final float value) {
        return value * getScaledDensity();
    }

    public static int px2dp(int pxVal) {
        return (int) (pxVal / getDensity());
    }

    public static int px2sp(int pxVal) {
        return (int) (pxVal / getScaledDensity());
    }

    //return the size of screen in pixel unit
    public static int[] getScreenWidthAndHeight() {
        int[] wh = new int[2];
        DisplayMetrics metrics = Utils.getAppContext().getResources().getDisplayMetrics();
        wh[0] = metrics.widthPixels;
        wh[1] = metrics.heightPixels;
        return wh;
    }

    //return the height of statusBar
    public static int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = Utils.getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = Utils.getAppContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    //return a screen capture bitmap
    public static Bitmap getScreenCapture(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int[] wh = getScreenWidthAndHeight();
        Bitmap scaled = Bitmap.createBitmap(bitmap, 0, 0, wh[0], wh[1]);
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return scaled;
    }

}