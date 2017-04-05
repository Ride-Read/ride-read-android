package com.rideread.rideread.function.net.retrofit;


import android.support.annotation.NonNull;

import com.rideread.rideread.common.event.ShowLoadingEvent;
import com.rideread.rideread.common.util.AMapLocationUtils;
import com.rideread.rideread.common.util.DateUtils;
import com.rideread.rideread.common.util.EncryptUtils;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.data.result.QiniuToken;
import com.rideread.rideread.data.result.UserInfo;

import org.greenrobot.eventbus.EventBus;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by SkyXiao on 2016/10/20.
 * retrofit执行http请求工具
 */

public class ApiUtils {
    private final static String TAG = ApiUtils.class.getSimpleName();

    private static ApiStore mApiStore;

    private final static Charset _charset = Charset.forName("UTF-8");
    private static Call sCurrentCall;

    private static String getTimeStamp() {
        return DateUtils.getCurDateFormat();
    }


    private static ApiStore getApiStore() {
        if (null == mApiStore) {
            mApiStore = RetrofitUtils.getInstance().create(ApiStore.class);
        }
        return mApiStore;
    }


    private static boolean onStart() {
        return onStart(true);
    }

    private static boolean onStart(final boolean showProgress) {
        //此处检查网络情况、设置进度条显示(使用Eventbus)
        if (!NetworkUtils.isConnected()) {
            ToastUtils.show("无网络连接，请稍后重试");
            return false;
        }
        if (showProgress) {
            EventBus.getDefault().post(new ShowLoadingEvent());
        }
        return true;
    }

    private static Map<String, String> getParams(Map<String, String> paramsMap, String timeStamp, String code) {
        paramsMap.put("timestamp", timeStamp);
        paramsMap.put("uid", code);
        paramsMap.put("token", code);
        return paramsMap;
    }

    public static void getQiNiuToken(@NonNull final String filename, @NonNull final BaseCallback<BaseModel<QiniuToken>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("filename", filename);

        setCurrentCall(getApiStore().getQiNiuToken(params), callBack);
    }

    public static void login(@NonNull final String account, @NonNull final String password, @NonNull final BaseCallback<BaseModel<UserInfo>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));
        params.put("phonenumber", account);
        params.put("password", EncryptUtils.encryptSHA1ToString(password));

        setCurrentCall(getApiStore().login(params), callBack);
    }

    public static void register(@NonNull final String account, @NonNull final String password, @NonNull final BaseCallback<BaseModel<UserInfo>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));
        params.put("phonenumber", account);
        params.put("password", EncryptUtils.encryptSHA1ToString(password));

        setCurrentCall(getApiStore().verify(params), callBack);
    }


    private static void setCurrentCall(Call call) {
        sCurrentCall = call;
    }

    private static <T extends BaseModel> void setCurrentCall(Call<T> call, BaseCallback<T> callBack) {
        setCurrentCall(call);
        if (null != call) {
            call.enqueue(callBack);
        }
    }

    public static void cancelCurrentCall() {
        if (null != sCurrentCall) {
            sCurrentCall.cancel();
        }
    }

}
