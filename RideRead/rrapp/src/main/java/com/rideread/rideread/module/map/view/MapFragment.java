package com.rideread.rideread.module.map.view;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.badoo.mobile.util.WeakHandler;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.dialog.SignInDialogFragment;
import com.rideread.rideread.common.util.AMapLocationUtils;
import com.rideread.rideread.common.util.KeyboardUtils;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


public class MapFragment extends BaseFragment implements LocationSource, PoiSearch.OnPoiSearchListener {
    @BindView(R.id.map_view) TextureMapView mMapView;
    @BindView(R.id.btn_sign_in) Button mBtnSignIn;
    @BindView(R.id.edt_search) EditText mEdtSearch;
    @BindView(R.id.img_clear) ImageView mImgClear;
    private AMap mAMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private SignInDialogFragment mSignInDialogFragment;
    private WeakHandler mHandler;

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
        mHandler = new WeakHandler();
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(true);
        mAMap.moveCamera(CameraUpdateFactory.zoomBy(18));
        // 设置定位监听
        mAMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.setMyLocationEnabled(true);
        //        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        //        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //        myLocationStyle.(2000);
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.blue_a20));
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(getResources().getColor(R.color.blue_a20));
        mAMap.setMyLocationStyle(myLocationStyle);

        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        AMapLocationUtils.init();

        mEdtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (KeyEvent.KEYCODE_ENTER == event.getKeyCode()) {
                searchKeyWord();
                KeyboardUtils.hideSoftInput(getActivity());
                return true;
            }
            return false;
        });
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence keyword, int start, int count, int after) {
                if (TextUtils.isEmpty(keyword)) {
                    mImgClear.setVisibility(View.GONE);
                } else {
                    mImgClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    private void searchKeyWord() {
        String keyWord = mEdtSearch.getText().toString();
        if (TextUtils.isEmpty(keyWord)) return;
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", "");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码

        PoiSearch poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (null == poiResult) return;
        List<PoiItem> poiList = poiResult.getPois();
        if (!ListUtils.isEmpty(poiList)) {
            PoiItem firstResult = poiList.get(0);
            LatLonPoint resultPoint = firstResult.getLatLonPoint();
            LatLng resultLatLng = new LatLng(resultPoint.getLatitude(), resultPoint.getLongitude());
            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(resultLatLng));
            ToastUtils.show("搜索结果为：" + firstResult.getAdName());
        } else {
            List<SuggestionCity> suggestionCitys = poiResult.getSearchSuggestionCitys();
            if (!ListUtils.isEmpty(suggestionCitys)) {
                ToastUtils.show("搜索失败，建议搜索填写：" + suggestionCitys.get(0).getCityName());
            }
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        //获取PoiItem获取POI的详细信息
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


    @OnClick({R.id.img_clear, R.id.btn_location, R.id.btn_recently})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_location:
                LatLng center = new LatLng(AMapLocationUtils.getLatitude(), AMapLocationUtils.getLongitude());
                mAMap.moveCamera(CameraUpdateFactory.changeLatLng(center));
                break;
            case R.id.btn_recently:
                break;
            case R.id.img_clear:
                mEdtSearch.setText("");
                break;
        }
    }

    @OnLongClick(R.id.btn_sign_in)
    public boolean onLongClick() {
        addSignMarker();
        return false;
    }

    private void addSignMarker() {
        mAMap.clear();
        MarkerOptions markerOption = new MarkerOptions();
        LatLng marketLatLng = new LatLng(AMapLocationUtils.getLatitude(), AMapLocationUtils.getLongitude());
        markerOption.position(marketLatLng);

        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        final Marker marker = mAMap.addMarker(markerOption);

        Animation animation = new AlphaAnimation(0F, 1F);
        //        Animation animation = new RotateAnimation(marker.getRotateAngle(),marker.getRotateAngle()+180,0,0,0);
        long duration = 800L;
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());

        marker.setAnimation(animation);
        marker.startAnimation();

        mHandler.postDelayed(() -> {
            mSignInDialogFragment = SignInDialogFragment.newInstance(AMapLocationUtils.getLocDetail());
            mSignInDialogFragment.show(getFragmentManager(), "sign_in");
        }, 1000L);


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
