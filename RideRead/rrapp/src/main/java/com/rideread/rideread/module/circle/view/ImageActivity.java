package com.rideread.rideread.module.circle.view;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by SkyXiao on 2017/4/6.
 */
public class ImageActivity extends BaseActivity  {

    public static final String IMAGE    = "IMAGE_ACTIVITY_IMAGE";
    private             String imageUrl = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_image;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageUrl = getIntent().getStringExtra(IMAGE);
        if (imageUrl.startsWith("/")){
            imageUrl = "file://" + imageUrl;
        }

        final PhotoDraweeView photoDraweeView = (PhotoDraweeView) findViewById(R.id.iv_image_activity);
        photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                onBackPressed();
            }
        });

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse(imageUrl));
        controller.setOldController(photoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        photoDraweeView.setController(controller.build());
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复

           onBackPressed();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

