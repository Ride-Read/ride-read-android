package com.rideread.rideread.module.profile.view;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.UserUtils;

import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class CollectActivity extends BaseActivity {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText("我的收藏").IsBack(true).build();
    }

    @OnClick(R.id.btn_logout)
    public void onClick() {
        UserUtils.logout();
        finish();
    }
}
