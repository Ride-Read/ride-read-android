package com.rideread.rideread.module.profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;

import butterknife.ButterKnife;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class PersonalityMapActivity extends BaseActivity {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_person_map;
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
    }

}
