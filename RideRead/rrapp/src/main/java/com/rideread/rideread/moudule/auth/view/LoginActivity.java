package com.rideread.rideread.moudule.auth.view;

import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseMVPActivity;
import com.rideread.rideread.moudule.auth.presenter.LoginPresenterImpl;

import butterknife.BindView;

/**
 * Created by SkyXiao on 2017/3/30.
 */

public class LoginActivity extends BaseMVPActivity<LoginPresenterImpl> implements ILoginView {
    @BindView(R.id.text) TextView mText;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setPresenter(new LoginPresenterImpl(this));
        mText.setText("OK");
    }

    @Override
    public void showData() {

    }

}
