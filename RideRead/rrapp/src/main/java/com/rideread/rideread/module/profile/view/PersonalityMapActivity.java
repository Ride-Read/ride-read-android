package com.rideread.rideread.module.profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class PersonalityMapActivity extends BaseActivity {
    @BindView(R.id.map_view) TextureMapView mMapView;
    private AMap mAMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象

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
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {

        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mMapView.onSaveInstanceState(bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
