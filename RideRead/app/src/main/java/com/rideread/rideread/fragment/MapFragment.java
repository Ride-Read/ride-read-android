package com.rideread.rideread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.rideread.rideread.activity.MainActivity;
import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/1/26.
 */

public class MapFragment extends Fragment implements LocationSource{

    private MainActivity parentActivity=null;
    private AMapLocationClient aMapLocationClient=null;
    private LocationSource.OnLocationChangedListener mListener;
    private static MapFragment fragment=null;

    public static final int POSITION=0;

    public static MapFragment newInstance(){
        if (fragment==null){
            synchronized(MapFragment.class){
                if(fragment==null){
                    fragment=new MapFragment();
                }
            }
        }
        return fragment;
    }

    private AMap aMap;
    private TextureMapView mapView;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView=inflater.inflate(R.layout.test,container,false);
//        if(aMap==null){
//            FragmentManager fm=getFragmentManager();
//            FragmentTransaction ts=fm.beginTransaction();
//            try{
//                MapsInitializer.initialize(getActivity());
//            }catch(RemoteException e){
//                e.printStackTrace();
//            }
//
//            SupportMapFragment supportMapFragment=new SupportMapFragment();
//            ts.replace(R.id.nav_map,supportMapFragment);
//            ts.commit();
//            aMap=supportMapFragment.getMap();
//        }
        if(mView==null){
            mView=inflater.inflate(R.layout.main_map_layout,null);

            mapView=(TextureMapView) mView.findViewById(R.id.main_map_map);
            mapView.onCreate(savedInstanceState);
            if(aMap==null){
                aMap=mapView.getMap();
            }
        }else{
            if(mView.getParent()!=null){
                ((ViewGroup)(mView.getParent())).removeView(mView);
            }
        }

        Log.e("eee","onCreateview");
        return mView;
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.e("eee","start");
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    private void initLocation() {

        aMapLocationClient.setLocationOption(parentActivity.mLocationOption);
        aMapLocationClient.startLocation();
        aMapLocationClient.setLocationListener(mAMapLocationListener);
    }

    private void setLocationMode(AMapLocationClientOption options){
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        options.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        options.setOnceLocation(true);
        options.setOnceLocationLatest(true);
        options.setInterval(1000);
        options.setNeedAddress(true);
        options.setLocationCacheEnable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        Log.e("eee","onresume");
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        //mapView.onSaveInstanceState(bundle);
        Log.e("eee","onsaveinstance");
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.e("eee","onpause");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("eee","onCreate");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
        Log.e("eee","ondestory");

    }


    AMapLocationListener mAMapLocationListener = new AMapLocationListener(){
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    amapLocation.getFloor();//获取当前室内定位的楼层
                    Log.e("location","定位地址="+amapLocation.getAddress());
                    if(mListener!=null){
                        mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                    }
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }


            }
        }
    };

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener=onLocationChangedListener;
        parentActivity=(MainActivity) getActivity();
        if(parentActivity.mLocationOption==null){
            //初始化AMapLocationClientOption对象
            parentActivity.mLocationOption = new AMapLocationClientOption();
        }
        if(aMapLocationClient==null){
            aMapLocationClient=new AMapLocationClient(getActivity().getApplicationContext());
        }
        setLocationMode(parentActivity.mLocationOption);
        initLocation();

    }

    @Override
    public void deactivate() {
        mListener=null;
        if(aMapLocationClient!=null){
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。

        }
        aMapLocationClient=null;

    }


}
