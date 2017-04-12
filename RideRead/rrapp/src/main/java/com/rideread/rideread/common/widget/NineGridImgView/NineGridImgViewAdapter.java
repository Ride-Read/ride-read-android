package com.rideread.rideread.common.widget.NineGridImgView;

import android.content.Context;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by SkyXiao on 2017/4/12.
 */


public abstract class NineGridImgViewAdapter<T> {
    protected abstract void onDisplayImage(Context context, SimpleDraweeView imageView, T t);

    protected void onItemImageClick(Context context, SimpleDraweeView imageView, int index, List<T> list) {
    }

    protected SimpleDraweeView generateImageView(Context context) {
        SimpleDraweeView imageView = new GridImgView(context);
        imageView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        return imageView;
    }
}