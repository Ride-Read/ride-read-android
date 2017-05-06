package com.rideread.rideread.common.util;

import android.support.annotation.NonNull;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.rideread.rideread.data.CurCache;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.Storage;
import com.rideread.rideread.data.result.UserInfo;
import com.rideread.rideread.function.net.im.AVImClientManager;


/**
 * 负责账号相关的数据存取
 */
public final class UserUtils {
    public static String USER_ID = "uid";
    public static String USER_TOKEN = "token";
    public static String USER_PHONE = "phone";
    public static String CUR_USER_INFO = "user_info";
    public static String LOGIN_TIMESTAMP = "login_timestamp";

    public static int getUid() {
        return CurCache.load(USER_ID, () -> Storage.get(USER_ID, 0));
    }


    @NonNull
    public static String getToken() {
        final String token = CurCache.load(USER_TOKEN, () -> Storage.get(USER_TOKEN, ""));
        return token == null ? "" : token;
    }

    @NonNull
    public static String getPhone() {
        final String phone = CurCache.load(USER_PHONE, () -> Storage.get(USER_PHONE, ""));
        return phone == null ? "" : phone;
    }

    @NonNull
    public static UserInfo getCurUser() {
        return CurCache.load(CUR_USER_INFO, () -> Storage.get(CUR_USER_INFO, null));
    }


    public static void logout() {
        CurCache.clear();
        Storage.delete(USER_ID);
        Storage.delete(USER_TOKEN);
    }

    public static void login(UserInfo userInfo) {
        CurCache.put(UserUtils.USER_ID, userInfo.getUid());
        CurCache.put(UserUtils.USER_TOKEN, userInfo.getToken());
        CurCache.put(UserUtils.USER_PHONE, userInfo.getPhonenumber());
        CurCache.put(UserUtils.CUR_USER_INFO, userInfo);

        Storage.put(UserUtils.USER_ID, userInfo.getUid());
        Storage.put(UserUtils.USER_TOKEN, userInfo.getToken());
        Storage.put(UserUtils.USER_PHONE, userInfo.getPhonenumber());
        Storage.put(UserUtils.CUR_USER_INFO, userInfo);
        openClient();
    }

    public static void openClient() {
        if (0 == UserUtils.getUid()) return;
        AVImClientManager.getInstance().open(UserUtils.getUid() + "", new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    Logger.d("LeanCloud", "登录成功!");
                } else {
                    Logger.d("LeanCloud", "登录失败!");
                }
            }
        });

    }

    public static void saveUserInfo(UserInfo userInfo) {
        CurCache.put(UserUtils.CUR_USER_INFO, userInfo);
        Storage.put(UserUtils.CUR_USER_INFO, userInfo);
    }

    public static void setLoginTimestamp(long timestamp) {
        CurCache.put(UserUtils.LOGIN_TIMESTAMP, timestamp);
        Storage.put(UserUtils.LOGIN_TIMESTAMP, timestamp);
    }

    public static long getLoginTimestamp() {
        return CurCache.load(LOGIN_TIMESTAMP, () -> Storage.get(LOGIN_TIMESTAMP, 0L));
    }
}
