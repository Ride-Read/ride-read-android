package com.rideread.rideread.common.other;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.data.Logger;

/**
 * Created by SkyXiao on 2017/4/25.
 * 图片加载的封装builder类
 * 使用示例
 * <p>
 * ImgFrescoLoadBuilder.Start(getContext(),mImageUser,url_head)
 * .setIsCircle(true)
 * .build();
 * </p>
 */

public class ImgFrescoLoadBuilder {
    //必要参数
    SimpleDraweeView mSimpleDraweeView;
    String mUrl;

    //非必要参数
    String mUrlLow;//低分率图地址

    Drawable mPlaceHolderImage;//占位图
    Drawable mProgressBarImage;//loading图
    Drawable mRetryImage;//重试图
    Drawable mFailureImage;//失败图
    Drawable mBackgroundImage;//背景图

    ScalingUtils.ScaleType mActualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
    boolean mIsCircle = false;//是否圆形图片
    boolean mIsRadius = false;//是否圆角
    boolean mIsBorder = false;//是否有包边
    float mRadius = 4;//圆角度数 默认4
    ResizeOptions mResizeOptions = new ResizeOptions(2048, 2048);//图片的大小限制

    ControllerListener mControllerListener;//图片加载的回调

    BaseBitmapDataSubscriber mBitmapDataSubscriber;

    /**
     * 构造器的构造方法 传入必要参数
     *
     * @param mSimpleDraweeView
     * @param mUrl
     */
    public ImgFrescoLoadBuilder(SimpleDraweeView mSimpleDraweeView, String mUrl) {
        this.mSimpleDraweeView = mSimpleDraweeView;
        this.mUrl = mUrl;
    }

    public static ImgFrescoLoadBuilder start(SimpleDraweeView mSimpleDraweeView, String mUrl) {
        return new ImgFrescoLoadBuilder(mSimpleDraweeView, mUrl);
    }

    /**
     * 构造器的build方法 构造真正的对象 并返回
     * 构造之前需要检查
     *
     * @return
     */
    public ImgLoaderFresco build() {
        Logger.d("ImgLoader", "图片开始加载 viewId=" + this.mSimpleDraweeView.getId() + " url" + this.mUrl);
        //            if (TextUtils.isEmpty(mUrl)) {
        //                throw new IllegalArgumentException("URL不能为空");
        //            }

        //不能同时设定 圆形圆角
        if (mIsCircle && mIsRadius) {
            throw new IllegalArgumentException("图片不能同时设置圆角和圆形");
        }

        return new ImgLoaderFresco(this);
    }

    public ImgFrescoLoadBuilder setBitmapDataSubscriber(BaseBitmapDataSubscriber mBitmapDataSubscriber) {
        this.mBitmapDataSubscriber = mBitmapDataSubscriber;
        return this;
    }

    public ImgFrescoLoadBuilder setUrlLow(String urlLow) {
        this.mUrlLow = urlLow;
        return this;
    }

    public ImgFrescoLoadBuilder setActualImageScaleType(ScalingUtils.ScaleType mActualImageScaleType) {
        this.mActualImageScaleType = mActualImageScaleType;
        return this;
    }

    public ImgFrescoLoadBuilder setPlaceHolderImage(Drawable mPlaceHolderImage) {
        this.mPlaceHolderImage = mPlaceHolderImage;
        return this;
    }

    public ImgFrescoLoadBuilder setProgressBarImage(Drawable mProgressBarImage) {
        this.mProgressBarImage = mProgressBarImage;
        return this;
    }

    public ImgFrescoLoadBuilder setRetryImage(Drawable mRetryImage) {
        this.mRetryImage = mRetryImage;
        return this;
    }

    public ImgFrescoLoadBuilder setFailureImage(Drawable mFailureImage) {
        this.mFailureImage = mFailureImage;
        return this;
    }

    public ImgFrescoLoadBuilder setBackgroundImage(Drawable mBackgroundImage) {
        this.mBackgroundImage = mBackgroundImage;
        return this;
    }

    public ImgFrescoLoadBuilder setBackgroupImageColor(int colorId) {
        Drawable color = ContextCompat.getDrawable(Utils.getAppContext(), colorId);
        this.mBackgroundImage = color;
        return this;
    }

    public ImgFrescoLoadBuilder setIsCircle(boolean mIsCircle) {
        this.mIsCircle = mIsCircle;
        return this;
    }

    public ImgFrescoLoadBuilder setIsCircle(boolean mIsCircle, boolean mIsBorder) {
        this.mIsBorder = mIsBorder;
        this.mIsCircle = mIsCircle;
        return this;
    }

    public ImgFrescoLoadBuilder setIsRadius(boolean mIsRadius) {
        this.mIsRadius = mIsRadius;
        return this;
    }

    public ImgFrescoLoadBuilder setIsRadius(boolean mIsRadius, float mRadius) {
        this.mRadius = mRadius;
        return setIsRadius(mIsRadius);
    }

    public ImgFrescoLoadBuilder setRadius(float mRadius) {
        this.mRadius = mRadius;
        return this;
    }

    public ImgFrescoLoadBuilder setResizeOptions(ResizeOptions mResizeOptions) {
        this.mResizeOptions = mResizeOptions;
        return this;
    }

    public ImgFrescoLoadBuilder setControllerListener(ControllerListener mControllerListener) {
        this.mControllerListener = mControllerListener;
        return this;
    }
}

//获取bitmap
//ImageLoadBuilder.Start(getApplicationContext(), mImageUser, url)
//        .setBitmapDataSubscriber(new BaseBitmapDataSubscriber() {
//@Override
//protected void onNewResultImpl(Bitmap bitmap) {
//        if (bitmap == null) {
//        Logger.d("bitmap is null");
//        } else {
//        Logger.d("bitmap is not null");
//        Drawable backDrawable = new BitmapDrawable(getResources(), FastBlurUtil.doBlur(bitmap, 25, false));
//        if (Looper.getMainLooper() != Looper.myLooper()) {
//        mAppBar.post(new Runnable() {
//@Override
//public void run() {
//        mAppBar.setBackground(backDrawable);
//        }
//        });
//        } else {
//        mAppBar.setBackground(backDrawable);
//        }
//        }
//        }
//
//@Override
//protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//
//        }
//        })
//        .build();
