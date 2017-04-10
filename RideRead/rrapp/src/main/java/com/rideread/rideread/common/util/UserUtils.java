package com.rideread.rideread.common.util;

import android.support.annotation.NonNull;

import com.rideread.rideread.data.CurCache;
import com.rideread.rideread.data.Storage;
import com.rideread.rideread.data.result.UserInfo;


/**
 * 负责账号相关的数据存取
 */
public final class UserUtils {
    public static String USER_ID = "uid";
    public static String USER_TOKEN = "token";
    public static String USER_PHONE = "phone";
    public static String CUR_USER_INFO = "user_info";

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

    }
}
