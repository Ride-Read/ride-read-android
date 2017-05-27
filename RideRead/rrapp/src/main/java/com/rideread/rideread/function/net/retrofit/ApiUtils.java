package com.rideread.rideread.function.net.retrofit;


import android.support.annotation.NonNull;

import com.rideread.rideread.common.event.ShowLoadingEvent;
import com.rideread.rideread.common.util.AMapLocationUtils;
import com.rideread.rideread.common.util.DateUtils;
import com.rideread.rideread.common.util.EncryptUtils;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.result.CollectInfo;
import com.rideread.rideread.data.result.Comment;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.DetailUserInfo;
import com.rideread.rideread.data.result.FollowUser;
import com.rideread.rideread.data.result.MapMoment;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.data.result.QiniuToken;
import com.rideread.rideread.data.result.SearchUsers;
import com.rideread.rideread.data.result.ThumbsUpUser;
import com.rideread.rideread.data.result.UserInfo;
import com.rideread.rideread.data.result.VCode;

import org.greenrobot.eventbus.EventBus;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
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

    private static Map<String, String> getParams(Map<String, String> paramsMap) {
        //        paramsMap.put("timestamp", Long.toString(System.currentTimeMillis()));
        paramsMap.put("timestamp", Long.toString(UserUtils.getLoginTimestamp()));
        paramsMap.put("uid", Integer.toString(UserUtils.getUid()));
        paramsMap.put("token", UserUtils.getToken());
        return paramsMap;
    }


    public static void login(@NonNull final String account, @NonNull final String password, @NonNull final BaseCallback<BaseModel<UserInfo>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));
        params.put("phonenumber", account);
        params.put("password", password);
        params.put("password", EncryptUtils.encryptSHA1ToString(password));

        setCurrentCall(getApiStore().login(params), callBack);
    }

    public static void resetPwd(@NonNull final String phone, @NonNull final String newPwd, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("phonenumber", phone);
        params.put("new_password", EncryptUtils.encryptSHA1ToString(newPwd));
        params.put("timestamp", Long.toString(System.currentTimeMillis()));

        setCurrentCall(getApiStore().resetPassword(params), callBack);
    }

    public static void logout(@NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();

        setCurrentCall(getApiStore().logout(getParams(params)), callBack);
    }

    public static void verifyRideReadId(@NonNull final String rideReadId, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("ride_read_id", rideReadId);
        params.put("timestamp", Long.toString(System.currentTimeMillis()));

        setCurrentCall(getApiStore().verify(params), callBack);
    }

    public static void register(@NonNull final String rideReadId, @NonNull final String phone, @NonNull final String password, @NonNull final String userneme, @NonNull final String faceUrl, @NonNull final BaseCallback<BaseModel<UserInfo>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("ride_read_id", rideReadId);
        params.put("phonenumber", phone);
        params.put("password", EncryptUtils.encryptSHA1ToString(password));
        params.put("username", userneme);
        params.put("face_url", faceUrl);

        setCurrentCall(getApiStore().register(params), callBack);
    }

    public static void loadFollowers(@NonNull final BaseCallback<BaseModel<List<FollowUser>>> callBack) {
        if (!onStart(false)) return;
        Map<String, String> params = new HashMap<>();
        setCurrentCall(getApiStore().followers(getParams(params)), callBack);
    }

    public static void loadFollowing(@NonNull final BaseCallback<BaseModel<List<FollowUser>>> callBack) {
        if (!onStart(false)) return;
        Map<String, String> params = new HashMap<>();
        setCurrentCall(getApiStore().followings(getParams(params)), callBack);
    }

    public static void follow(@NonNull final int userId, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Integer.toString(userId));
        setCurrentCall(getApiStore().follow(getParams(params)), callBack);
    }

    public static void unfollow(@NonNull final int userId, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Integer.toString(userId));
        setCurrentCall(getApiStore().unfollow(getParams(params)), callBack);
    }

    public static void showUserInfo(final int uid, @NonNull final BaseCallback<BaseModel<DetailUserInfo>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Integer.toString(uid));
        if (0 != uid) {
            params.put("type", Integer.toString(2));
        }
        setCurrentCall(getApiStore().showUserInfo(getParams(params)), callBack);
    }

    public static void searchUser(final String shortname, @NonNull final BaseCallback<BaseModel<SearchUsers>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("shortname", shortname);
        setCurrentCall(getApiStore().searchUser(getParams(params)), callBack);
    }

    public static void update(@NonNull UserInfo user, String birthday, @NonNull final BaseCallback<BaseModel<UserInfo>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("sex", Integer.toString(user.getSex()));
        if (!ListUtils.isEmpty(user.getTags())) {
            String urlArrayStr = user.getTags().toString();
            int end = urlArrayStr.length() - 1;
            params.put("tags", urlArrayStr.substring(1, end));
        } else {
            params.put("tags", "");
        }
        params.put("signature", user.getSignature());
        params.put("birthday", birthday);
        params.put("school", user.getSchool());
        params.put("location", user.getLocation());
        params.put("hometown", user.getHometown());
        params.put("career", user.getCareer());
        params.put("face_url", user.getFaceUrl());
        params.put("phonenumber", UserUtils.getPhone());

        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));
        setCurrentCall(getApiStore().update(getParams(params)), callBack);
    }

    public static void postMoment(@NonNull final String content, @NonNull final String locInfo, @NonNull final List<String> pictureUrls, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("msg", content);
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));
        if (ListUtils.isEmpty(pictureUrls)) {
            params.put("type", Integer.toString(0));
        } else {
            params.put("type", Integer.toString(1));
            String urlArrayStr = pictureUrls.toString();
            int end = urlArrayStr.length() - 1;
            params.put("pictures_url", urlArrayStr.substring(1, end));
        }
        params.put("moment_location", locInfo);
        setCurrentCall(getApiStore().postMoment(getParams(params)), callBack);
    }

    public static void postMomentVedio(@NonNull final String content, @NonNull final String coverUrl, @NonNull final String vedioUrl, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("msg", content);
        params.put("cover", coverUrl);
        params.put("video_url", vedioUrl);
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));
        params.put("type", Integer.toString(2));

        setCurrentCall(getApiStore().postMoment(getParams(params)), callBack);
    }

    public static void showUserMoments(final int userId, final int pages, @NonNull final BaseCallback<BaseModel<List<Moment>>> callBack) {
        if (!onStart(false)) return;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Integer.toString(userId));
        params.put("pages", Integer.toString(pages));
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));

        setCurrentCall(getApiStore().showUserMoments(getParams(params)), callBack);
    }

    public static void updateThumbsUp(final int mid, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart(false)) return;
        Map<String, String> params = new HashMap<>();
        params.put("mid", Integer.toString(mid));
        setCurrentCall(getApiStore().updateThumbsUp(getParams(params)), callBack);
    }

    public static void addComment(final int mid, final int replyId, @NonNull String commentMsg, @NonNull final BaseCallback<BaseModel<Comment>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("mid", Integer.toString(mid));
        params.put("reply_uid", Integer.toString(replyId));
        params.put("msg", commentMsg);
        setCurrentCall(getApiStore().addComment(getParams(params)), callBack);
    }

    public static void cancelComment(final int commentId, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("comment_id", Integer.toString(commentId));
        setCurrentCall(getApiStore().removeComment(getParams(params)), callBack);
    }

    public static void cancelThumbsUp(final int thumbsUpId, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart(false)) return;
        Map<String, String> params = new HashMap<>();
        params.put("thumbs_up_id", Integer.toString(thumbsUpId));
        setCurrentCall(getApiStore().removeThumbsUp(getParams(params)), callBack);
    }

    public static void cancelMoment(final int mid, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("mid", Integer.toString(mid));
        setCurrentCall(getApiStore().removeMoment(getParams(params)), callBack);
    }

    public static void loadThumbsUpUser(final int mid, final int pages, @NonNull final BaseCallback<BaseModel<List<ThumbsUpUser>>> callBack) {
        if (!onStart(false)) return;
        Map<String, String> params = new HashMap<>();
        params.put("mid", Integer.toString(mid));
        params.put("pages", Integer.toString(pages));

        setCurrentCall(getApiStore().showThumbsUpUsers(getParams(params)), callBack);
    }

    public static void collectMoment(final int mid, final int type, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("mid", Integer.toString(mid));
        params.put("type", Integer.toString(type));

        setCurrentCall(getApiStore().collectMoment(getParams(params)), callBack);
    }

    public static void loadCollectMoment(@NonNull final BaseCallback<BaseModel<List<CollectInfo>>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        setCurrentCall(getApiStore().loadCollectMoment(getParams(params)), callBack);
    }

    public static void loadMoments(final int pages, final int type, @NonNull final BaseCallback<BaseModel<List<Moment>>> callBack) {
        if (!onStart(false)) return;
        Map<String, String> params = new HashMap<>();
        params.put("pages", Integer.toString(pages));
        params.put("type", Integer.toString(type));
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));

        setCurrentCall(getApiStore().showMoment(getParams(params)), callBack);
    }

    public static void loadOneMoment(final int mid, @NonNull final BaseCallback<BaseModel<Moment>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("mid", Integer.toString(mid));
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));

        setCurrentCall(getApiStore().showOneMoment(getParams(params)), callBack);
    }


    public static void loadMapMoments(final int scalingRatio, @NonNull final BaseCallback<BaseModel<List<MapMoment>>> callBack) {
        if (!onStart(false)) return;
        Map<String, String> params = new HashMap<>();
        params.put("scaling_ratio", Integer.toString(scalingRatio));
        params.put("longitude", Double.toString(AMapLocationUtils.getLongitude()));
        params.put("latitude", Double.toString(AMapLocationUtils.getLatitude()));

        setCurrentCall(getApiStore().showMapMoments(getParams(params)), callBack);
    }

    public static void getQiNiuToken(@NonNull final String filename, @NonNull final BaseCallback<BaseModel<QiniuToken>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("filename", filename);

        setCurrentCall(getApiStore().getQiNiuToken(getParams(params)), callBack);
    }

    public static void getQiNiuTokenTest(@NonNull final String filename, @NonNull final BaseCallback<BaseModel<QiniuToken>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("uid", "999");
        params.put("token", "testToken");
        params.put("filename", filename);
        params.put("timestamp", Long.toString(System.currentTimeMillis()));

        setCurrentCall(getApiStore().getQiNiuToken(params), callBack);
    }

    public static void getVCode(@NonNull final String phone, @NonNull final BaseCallback<BaseModel<VCode>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("phonenumber", phone);
        params.put("timestamp", Long.toString(System.currentTimeMillis()));

        setCurrentCall(getApiStore().getVCode(params), callBack);
    }


    public static void showUserMapMoment(@NonNull final int userId, @NonNull final BaseCallback<BaseModel<List<MapMoment>>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Integer.toString(userId));

        setCurrentCall(getApiStore().showUserMapMoments(getParams(params)), callBack);
    }

    public static void verifyVersion(@NonNull final String phone, @NonNull final BaseCallback<BaseModel<DefJsonResult>> callBack) {
        if (!onStart()) return;
        Map<String, String> params = new HashMap<>();
        params.put("phonenumber", phone);
        params.put("timestamp", Long.toString(System.currentTimeMillis()));

        setCurrentCall(getApiStore().verifyVersion(params), callBack);
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
