package com.rideread.rideread.module.profile.view;


import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;

import butterknife.OnClick;


public class ProfileFragment extends BaseFragment {


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_profile;
    }

    @Override
    public void initView() {

    }


    @OnClick(R.id.btn_setting)
    public void onClick() {
        getBaseActivity().gotoActivity(SettingActivity.class);
    }
}
