package com.rideread.rideread.module.map.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.dialog.SignInDialogFragment;
import com.rideread.rideread.common.util.AMapLocationUtils;
import com.rideread.rideread.common.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapFragment extends BaseFragment implements LocationSource {
    @BindView(R.id.map_view) TextureMapView mMapView;
    @BindView(R.id.btn_sign_in) Button mBtnSignIn;
    private AMap mAMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private SignInDialogFragment mSignInDialogFragment;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_map;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutRes(), container, false);
        }
        ButterKnife.bind(this, mRootView);
        mMapView.onCreate(savedInstanceState);
        initView();
        return mRootView;
    }


    @Override
    public void initView() {
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(true);
        mAMap.moveCamera(CameraUpdateFactory.zoomBy(18));
        mAMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                ToastUtils.show("弹窗选择发布");
            }
        });
        // 设置定位监听
        mAMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.setMyLocationEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        //        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //        myLocationStyle.(2000);
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.blue_a20));
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(getResources().getColor(R.color.blue_a20));
        mAMap.setMyLocationStyle(myLocationStyle);

        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
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
        //        if(null != mlocationClient){
        //            mlocationClient.onDestroy();
        //        }
    }


    @OnClick({R.id.btn_refresh, R.id.btn_right, R.id.btn_sign_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_refresh:
                break;
            case R.id.btn_right:
                break;
            case R.id.btn_sign_in:
                if (null == mSignInDialogFragment) {
                    mSignInDialogFragment = new SignInDialogFragment();
                }
                mSignInDialogFragment.show(getFragmentManager(), "sign_in");
                break;
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        AMapLocationUtils.setOnLocationChangedListener(onLocationChangedListener);
    }

    @Override
    public void deactivate() {
        //        mListener = null;
        //        if (mlocationClient != null) {
        //            mlocationClient.stopLocation();
        //            mlocationClient.onDestroy();
        //        }
        //        mlocationClient = null;
    }
}
