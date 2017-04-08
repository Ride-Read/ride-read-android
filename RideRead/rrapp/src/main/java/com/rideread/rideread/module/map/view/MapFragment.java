package com.rideread.rideread.module.map.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapFragment extends BaseFragment {
    @BindView(R.id.map_view) TextureMapView mMapView;
    private AMap mAMap;

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
        mAMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                ToastUtils.show("弹窗选择发布");
            }
        });
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


    @OnClick({R.id.btn_refresh, R.id.btn_right, R.id.btn_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_refresh:
                break;
            case R.id.btn_right:
                break;
            case R.id.btn_sign:
                break;
        }
    }
}
