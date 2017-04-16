package com.rideread.rideread.module.profile.view;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.TitleBuilder;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class CollectActivity extends BaseActivity {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_recycle;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText("我的收藏").IsBack(true).setLeftOnClickListener(v -> finish()).build();
    }


}
