package com.rideread.rideread.common.util;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.rideread.rideread.data.CurCache;
import com.rideread.rideread.data.Storage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SkyXiao on 2017/4/4.
 */

public class AMapLocationUtils {

    private static final String LATEST_LATITUDE = "latest_latitude";
    private static final String LATEST_LONGITUDE = "latest_longitude";
    /**
     * 省份、国家(外国)
     */
    private static String mLastAddressFirst;
    /**
     * 城、地区，州(外国)
     */
    private static String mLastAddressSecond;

    private static double mLatitude;
    private static double mLongitude;

    private static AMapLocationClient mLocationClient;


    public static void init() {
        //初始化定位
        mLocationClient = new AMapLocationClient(Utils.getAppContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        ;
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(false);
        mLocationOption.setWifiActiveScan(false);
        mLocationOption.setHttpTimeOut(5000);////设置定位间隔,单位毫秒,默认为5000ms
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    private static AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    mLatitude = aMapLocation.getLatitude();//获取纬度
                    mLongitude = aMapLocation.getLongitude();//获取经度
                    //TODO 保存到SDCard
                    aMapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(aMapLocation.getTime());
                    df.format(date);//定位时间
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                }
            }
        }
    };


    public static String getmLastAddressFirst() {
        return mLastAddressFirst;
    }

    public static String getmLastAddressSecond() {
        return mLastAddressSecond;
    }

    public static double getLatitude() {
        return CurCache.get(LATEST_LATITUDE, Storage.get(LATEST_LATITUDE, 23.136552D));
    }

    public static double getLongitude() {
        return CurCache.get(LATEST_LONGITUDE, Storage.get(LATEST_LONGITUDE, 113.314086D));
    }
}