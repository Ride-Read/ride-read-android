package com.rideread.rideread.function.net.retrofit;

import com.alibaba.fastjson.JSONObject;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.data.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SkyXiao on 2017/1/20.
 */
public abstract class JsonCallback<T> implements Callback<T> {
    private static final int RESULT_CODE_OK = 0;
    private final boolean showProgress;

    protected JsonCallback() {
        this(true);
    }

    protected JsonCallback(final boolean showProgress) {
        this.showProgress = showProgress;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        int code = response.raw().code();
        if (code == 200) {
            onSuccessFilter((JSONObject) response.body());//成功返回
        } else if (code == 204) {
            onNoData(ERR_MSG.NO_DATA);
        } else if (code == 400) {
            onFail(ERR_MSG.ERR0E_400);
        } else if (code == 500) {
            onFail(ERR_MSG.ERROR_500);
        } else {
            onFail(ERR_MSG.ERROR_NO_RESULT);
        }

        onAfter();
    }

    /**
     * 请求成功的回调根据status过滤
     *
     * @param model
     */
    protected void onSuccessFilter(JSONObject model) {
        String msg = null;
        try {
            int status = model.getInteger("status");
            msg = model.getString("msg");
            if (RESULT_CODE_OK == status) {
                String content = ("content");
                if (null != content) {
                    Logger.e("callback", content);
                }

                onSuccess(model.toJSONString());
                //            } else {
                //                Logger.e("callback",message);
                //                onErrorStatus(resultCode, message);
            }
        } catch (Exception e) {
            //            if (null != message)
            //错误提示
            //                EventBus.getDefault().post(new ShowSnackbar(RuijiaApp.getAppContext().getString(R.string.error_client)));
            e.printStackTrace();
        }
    }

    /**
     * 请求成功的回调
     *
     * @param model
     */
    protected abstract void onSuccess(String model) throws Exception;

    protected void onErrorStatus(int resultCode, String msg) {
        //        switch (resultCode) {
        //            case 501:
        //                //                onFail(RuijiaApp.getAppContext().getString(R.string.error_network_fail));
        //                break;
        //            case 200:
        //            case 10007:
        //            case 10009:
        //            case 9999:
        //                //                //TODO:
        //                break;
        //            default_header:
        //                //                if (TextUtils.isEmpty(message))
        //                //                    onFail(RuijiaApp.getAppContext().getString(R.string.error_server_fail));
        //                //                else
        //                //                    onFail(message);
        //        }
        ToastUtils.show(msg);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        //        if (!NetworkUtils.isConnected(RuijiaApp.getAppContext())) {
        //            //网络不通直接阻断
        //            onFail(RuijiaApp.getAppContext().getString(R.string.error_network_fail));
        //        } else if (t instanceof SocketTimeoutException) {
        //            onFail(RuijiaApp.getAppContext().getString(R.string.error_network_timeout));
        //        } else {
        //            onFail(RuijiaApp.getAppContext().getString(R.string.error_server_fail));
        //            Log.e("https", "onFailure:" + t.getMessage());
        //        }

        //        onFail(t.getMessage());
        onAfter();
    }

    /**
     * 请求完的回调，可以在里面停止刷新控件，可以不实现
     */
    protected void onAfter() {
        if (showProgress) {
            //            EventBus.getDefault().post(new HideProgressEvent());
        }
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
