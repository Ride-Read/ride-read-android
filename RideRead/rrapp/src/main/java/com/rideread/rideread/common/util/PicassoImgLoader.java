package com.rideread.rideread.common.util;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Created by SkyXiao on 2017/4/9.
 */

public class PicassoImgLoader {
    public static void displayImage(ImageView iv, @NonNull String url) {
        Picasso.with(Utils.getAppContext()).load(url).into(iv);
    }

    public static void displayImage(ImageView iv, int resId) {
        Picasso.with(iv.getContext()).load(resId).into(iv);
    }

    public static void displayImage(ImageView iv, @NonNull File file) {
        Picasso.with(iv.getContext()).load(file).into(iv);
    }

    public static void displayImage(ImageView iv, String url, @NonNull Transformation transformation) {
        Picasso.with(iv.getContext()).load(url).transform(transformation).into(iv);
    }

    public static void displayImage(ImageView iv, String url, int errImgResId, int placeHolderImgResId) {
        Picasso.with(iv.getContext()).load(url).error(errImgResId).placeholder(placeHolderImgResId).into(iv);
    }

    public static void displayImage(ImageView iv, String url, int errImgResId, int placeHolderImgResId, @NonNull Transformation transformation) {
        Picasso.with(iv.getContext()).load(url).error(errImgResId).placeholder(placeHolderImgResId).transform(transformation).into(iv);
    }
}
