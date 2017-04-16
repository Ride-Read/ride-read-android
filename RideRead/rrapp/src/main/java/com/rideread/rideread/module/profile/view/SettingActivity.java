package com.rideread.rideread.module.profile.view;

import android.view.View;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.switch_voice) SwitchButton mSwitchVoice;
    @BindView(R.id.switch_vibrate) SwitchButton mSwitchVibrate;
    @BindView(R.id.tv_cache_size) TextView mTvCacheSize;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText("设置").IsBack(true).build();
    }




    @OnClick({R.id.img_top_bar_left, R.id.tv_clear_cache, R.id.tv_about, R.id.tv_use_guide, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_bar_left:
                finish();
                break;
            case R.id.tv_clear_cache:
                break;
            case R.id.tv_about:
                break;
            case R.id.tv_use_guide:
                break;
            case R.id.btn_logout:
                UserUtils.logout();
                finish();
                break;
        }
    }
}
