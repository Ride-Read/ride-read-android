package com.rideread.rideread.module.auth.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ImageView;

import com.badoo.mobile.util.WeakHandler;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.MPermissionsActivity;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.module.main.MainActivity;

import butterknife.BindView;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class SplashActivity extends MPermissionsActivity {
    @BindView(R.id.img_splash_ad) ImageView mImgSplashAd;
    private WeakHandler mHandler;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
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
        //        getSupportActionBar().hide();
        //TODO 后期需要请求广告页面，再替换但前页面显示
        if (0 != UserUtils.getUid()) {
            mHandler = new WeakHandler();
            // 判断是否已经登录，已登录闪屏后进入主页
            mHandler.postDelayed(() -> {
//                ObjectAnimator.ofFloat(mImgSplashAd, "alpha", 0f, 1f).setDuration(1200l).start();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }, 2000l);
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }

        String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        requestPermission(permission, 0x0001);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

}
