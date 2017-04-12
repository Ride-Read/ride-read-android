package com.rideread.rideread.common.util;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.common.other.DisplayImageOptions;

/**
 * Created by SkyXiao on 2017/4/9.
 */

public class ImgLoader {

    private volatile static ImgLoader instance;

    public static ImgLoader getInstance() {
        if (instance == null) {
            synchronized (ImgLoader.class) {
                if (instance == null) {
                    instance = new ImgLoader();
                }
            }
        }
        return instance;
    }

    public void displayImage(String photoUrl, SimpleDraweeView simpleDraweeView) {
        displayImage(photoUrl, simpleDraweeView, null, null);
    }

    public void displayImage(String photoUrl, SimpleDraweeView simpleDraweeView, DisplayImageOptions displayImageOptions) {
        displayImage(photoUrl, simpleDraweeView, displayImageOptions, null);
    }

    public void displayImage(String photoUrl, SimpleDraweeView simpleDraweeView, ControllerListener controllerListener) {
        displayImage(photoUrl, simpleDraweeView, null, controllerListener);
    }

    public void displayImage(String photoUrl, SimpleDraweeView simpleDraweeView, DisplayImageOptions displayImageOptions, ControllerListener controllerListener) {

        if (simpleDraweeView == null) return;

        if (displayImageOptions != null) {
            simpleDraweeView.getHierarchy().setPlaceholderImage(displayImageOptions.getDefaultPlaceHolder());
            simpleDraweeView.getHierarchy().setRoundingParams(displayImageOptions.getRoundingParams());
        }

        PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder();

        if (!TextUtils.isEmpty(photoUrl)) draweeControllerBuilder.setUri(Uri.parse(photoUrl));

        if (controllerListener != null) {
            draweeControllerBuilder.setControllerListener(controllerListener);
        }
        simpleDraweeView.setController(draweeControllerBuilder.build());
    }

}
