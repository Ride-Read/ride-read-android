package com.rideread.rideread.module.profile.view;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.UserUtils;

import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class SettingActivity extends BaseActivity {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btn_logout)
    public void onClick() {
        UserUtils.logout();
        finish();
    }
}
