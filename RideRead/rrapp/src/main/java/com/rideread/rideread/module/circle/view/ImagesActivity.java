package com.rideread.rideread.module.circle.view;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.rideread.rideread.common.util.AppUtils;
import com.rideread.rideread.common.util.FileUtils;
import com.rideread.rideread.common.util.ImageUtils;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.common.widget.SaveImgPopWin;

import java.io.File;

import butterknife.BindView;
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
    private String mCurImgUrl;


    private SaveImgPopWin mSaveImgPopWin;


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
        mCurImgUrl = imageArray[index];

        mImgViewpager.setAdapter(new SamplePagerAdapter(getLayoutInflater(), imageArray));
        mImgViewpager.setCurrentItem(index);
        mImgViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int tIndex) {
                mTvImgIndex.setText((tIndex + 1) + "/" + count);
                mCurImgUrl = imageArray[tIndex];
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });

        mSaveImgPopWin = new SaveImgPopWin(ImagesActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                File imageFile = ImgLoader.getInstance().getDiscCacheFile(imageUrl);
                ToastUtils.show("保存图片");
                mSaveImgPopWin.dismiss();

                saveImg(mCurImgUrl);
            }
        });
        //显示窗口

    }

    private void saveImg(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) return;

        File imageFile = ImgLoader.getInstance().getDiscCacheFile(imageUrl);

        String parent = imageFile.getParent();// /storage/emulated/0/ride-read/imageCache
        String path = imageFile.getPath();// /storage/emulated/0/ride-read/imageCache/48474458
        final String imageName = path.substring(parent.length() + 1, path.length()) + ".jpg";// /48474458
        // if (imageName.startsWith("-"))
        // imageName = "f" + imageName.substring(1);

        if (imageFile != null && imageFile.exists()) {
            final String dir = AppUtils.getMyCacheDir("IMAGE");// /storage/emulated/0/ride-read/IMAGE/
            File desFile = new File(dir);

            FileUtils.copyFile(imageFile, desFile, imageName, new FileUtils.CopyFileListener() {

                @Override
                public void exception(String msg) {
                    ToastUtils.show(msg);
                }

                @Override
                public void success(String path) {
                    ToastUtils.show(getString(R.string.save_image_to, path));

                    ImageUtils.scanPhotos(path, Utils.getAppContext());
                }
            });
        }
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
            photoDraweeView.setOnViewTapListener((view1, x, y) -> onBackPressed());

            photoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (null != mSaveImgPopWin) {
                        mSaveImgPopWin.showAtLocation(ImagesActivity.this.findViewById(R.id.root_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    }
                    return false;
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

