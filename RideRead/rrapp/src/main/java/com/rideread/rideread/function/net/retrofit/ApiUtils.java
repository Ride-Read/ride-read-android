package com.rideread.rideread.function.net.retrofit;


import com.rideread.rideread.common.util.DateUtils;

import java.nio.charset.Charset;

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



    /**
     * Retrofit无法监听到onStart，需要自己定义
     * 开始请求, 无网络连接显示一个ShowSnackbar，否则显示loading
     */
    private static void onStart() {
        //此处检查网络情况、设置进度条显示(使用Eventbus)
//        EventBus.getDefault().post(new ShowLoading());
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
