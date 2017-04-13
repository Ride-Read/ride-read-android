package com.rideread.rideread.module.circle.view;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * Created by SkyXiao on 2017/4/6.
 */
public class ImagesActivity extends BaseActivity {


    @BindView(R.id.img_viewpager) ViewPager mImgViewpager;
    @BindView(R.id.tv_img_index) TextView mTvImgIndex;

    public static final String IMAGE_URLS = "image_urls";
    public static final String POSITION = "position";
    private String[] imageArray;
    private int index;
    private int count;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_images;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (SDK_INT >= KITKAT) {
            getWindow().addFlags(FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        getIntentValue();

        if (count <= 1) {
            mTvImgIndex.setVisibility(View.GONE);
        } else {
            mTvImgIndex.setVisibility(View.VISIBLE);
            mTvImgIndex.setText((index + 1) + "/" + count);
        }

        mImgViewpager.setAdapter(new SamplePagerAdapter(getLayoutInflater(), imageArray));
        mImgViewpager.setCurrentItem(index);
        mImgViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int arg0) {
                mTvImgIndex.setText((arg0 + 1) + "/" + count);
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        String urls = intent.getStringExtra(IMAGE_URLS);
        index = intent.getIntExtra(POSITION, 0);
        imageArray = urls.split(",");
        count = imageArray.length;
    }

    class SamplePagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        private String[] imageArray;

        public SamplePagerAdapter(LayoutInflater layoutInflater, String[] imageArray) {
            this.layoutInflater = layoutInflater;
            this.imageArray = imageArray;
        }

        @Override
        public int getCount() {
            return imageArray.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = layoutInflater.inflate(R.layout.view_image, null);

            final PhotoDraweeView photoDraweeView = (PhotoDraweeView) view.findViewById(R.id.pdv_image);
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(Uri.parse(imageArray[position]));
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
            photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    onBackPressed();
                }
            });
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}

