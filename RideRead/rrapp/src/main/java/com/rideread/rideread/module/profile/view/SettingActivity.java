package com.rideread.rideread.module.profile.view;

import android.view.View;
import android.widget.TextView;

import com.badoo.mobile.util.WeakHandler;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kyleduo.switchbutton.SwitchButton;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.dialog.ConfirmDialogFragment;
import com.rideread.rideread.common.util.AppUtils;
import com.rideread.rideread.common.util.FileUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.rrapp.FrescoApp;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.switch_voice) SwitchButton mSwitchVoice;
    @BindView(R.id.switch_vibrate) SwitchButton mSwitchVibrate;
    @BindView(R.id.tv_cache_size) TextView mTvCacheSize;
    private ConfirmDialogFragment mLogoutDialog;
    private WeakHandler mHandler;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText(R.string.setting).IsBack(true).setLeftOnClickListener(v -> finish()).build();
        mHandler = new WeakHandler();
        countCache();
    }

    private void countCache() {
        new Thread(() -> {
            try {
                File imageFile = new File(FrescoApp.FRESCO_PATH);

                final long fileSize = FileUtils.getDirSize(imageFile) + FileUtils.getDirSize(getCacheDir());
                mHandler.post(() -> mTvCacheSize.setText(formatDataSize((int) fileSize)));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public String formatDataSize(int size) {
        String ret = "";
        if (size < (1024 * 1024)) {
            ret = String.format("%dK", size / 1024);
        } else {
            ret = String.format("%.1fM", size / (1024 * 1024.0));
        }
        return ret;
    }


    @OnClick({R.id.tv_clear_cache, R.id.tv_about, R.id.tv_use_guide, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear_cache:
                cleanCache();
                break;
            case R.id.tv_about:
                gotoActivity(AboutUsActivity.class);
                break;
            case R.id.tv_use_guide:
                break;
            case R.id.btn_logout:
                showLogoutDialog();
                break;
        }
    }

    private void cleanCache() {
        File imageFile = new File(AppUtils.getMyCacheDir("/imageCache/"));

        FileUtils.deleteFile(imageFile);
        // 删除应用缓存
        Fresco.getImagePipeline().clearMemoryCaches();// Fresco缓存 包含在 应用缓存
    }

    private void showLogoutDialog() {
        if (null == mLogoutDialog) {
            mLogoutDialog = ConfirmDialogFragment.newInstance(R.string.query_2_logout);
        }
        mLogoutDialog.show(getSupportFragmentManager(), "dialog");
    }


    @Override
    public void doPositiveClick() {
        UserUtils.logout();
        finish();
    }
}
