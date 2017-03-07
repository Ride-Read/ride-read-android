package com.rideread.rideread.imageloader;

import android.app.Activity;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.rideread.rideread.R;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

/**
 * Created by Jackbing on 2017/3/7.
 */

public class GildeImageLoader implements ImageLoader {
    @Override
    public void clearMemoryCache() {

    }

    @Override
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {


        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.gallery_pick_photo)
                .centerCrop()
                .into(galleryImageView);
    }
}
