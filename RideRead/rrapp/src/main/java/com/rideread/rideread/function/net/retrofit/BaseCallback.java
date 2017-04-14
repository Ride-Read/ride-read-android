package com.rideread.rideread.function.net.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.rideread.rideread.R;
import com.rideread.rideread.common.event.HideLoadingEvent;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SkyXiao on 2017/1/20.
 */

public abstract class BaseCallback<T extends BaseModel> implements Callback<T> {

    private static final int RESULT_CODE_OK = 0;

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onAfter();
        int code = response.raw().code();
        if (code == 200) {
            onSuccessFilter(response.body());//成功返回
        } else if (code == 204) {
            onNoData(ERR_MSG.NO_DATA);
        } else if (code == 400) {
            onFail(ERR_MSG.ERR0E_400);
        } else if (code == 500) {
            onFail(ERR_MSG.ERROR_500);
        } else {
            onFail(ERR_MSG.ERROR_NO_RESULT);
        }


    }

    /**
     * 请求成功的回调根据status过滤
     *
     * @param model
     */
    protected void onSuccessFilter(T model) {
        try {
            int status = model.getStatus();
            String message = model.getMsg();
            if (RESULT_CODE_OK == status) {
                onSuccess(model);
            } else {
                onErrorStatus(status, message);
            }
        } catch (Exception e) {
            if (null != model.getData())
                //                EventBus.getDefault().post(new ShowSnackbar(Utils.getAppContext().getString(R.string.error_client)));
                e.printStackTrace();
        }

    }

    /**
     * 请求成功的回调
     *
     * @param model
     */
    protected abstract void onSuccess(T model) throws Exception;

    protected void onErrorStatus(int status, String message) {
        if (TextUtils.isEmpty(message))
            onFail(Utils.getAppContext().getString(R.string.server_error_fail));
        else onFail(message);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        if (!NetworkUtils.isConnected()) {
            //网络不通直接阻断
            onFail(Utils.getAppContext().getString(R.string.network_error_fail));
        } else if (t instanceof SocketTimeoutException) {
            onFail(Utils.getAppContext().getString(R.string.network_error_timeout));
        } else {
            onFail(Utils.getAppContext().getString(R.string.server_error_fail));
            Log.e("https", "onFailure:" + t.getMessage());
        }

        //        onFail(t.getMessage());
        onAfter();
    }


    /**
     * 请求完的回调，可以在里面停止刷新控件，可以不实现
     */
    protected void onAfter() {
        EventBus.getDefault().post(new HideLoadingEvent());
    }

    /**
     * 没有数据的回调，可以不实现
     */
    protected void onNoData(String msg) {
    }

    /**
     * 请求失败的回调，可以不实现
     */
    protected void onFail(String msg) {
        ToastUtils.show(msg);
        //        EventBus.getDefault().post(new ShowSnackbar(msg));
    }


    /**
     * 自定义的错误信息
     */
    class ERR_MSG {
        private static final String NO_DATA = "暂无数据";
        private static final String ERR0E_400 = "请求失败";
        private static final String ERROR_500 = "服务器正在修复，请稍后重试";//500,501
        private static final String ERROR_NO_RESULT = "未知错误";
    }

}
