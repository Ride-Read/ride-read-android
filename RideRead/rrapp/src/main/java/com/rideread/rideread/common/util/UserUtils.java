package com.rideread.rideread.common.util;

import android.support.annotation.NonNull;

import com.rideread.rideread.data.CurCache;
import com.rideread.rideread.data.Storage;


/**
 * 负责账号相关的数据存取
 */
public final class UserUtils {
    public static String USER_ID = "uid";
    public static String USER_TOKEN = "token";
    public static String USER_PHONE = "phone";

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


    public static void logout() {
        CurCache.clear();
        Storage.delete(USER_ID);
        Storage.delete(USER_TOKEN);

    }

    public static void login(int uid, String token, String phone) {
        Storage.put(UserUtils.USER_ID, uid);
        Storage.put(UserUtils.USER_TOKEN, token);
        Storage.put(UserUtils.USER_PHONE, phone);

        CurCache.put(UserUtils.USER_ID, uid);
        CurCache.put(UserUtils.USER_TOKEN, token);
        CurCache.put(UserUtils.USER_PHONE, phone);

    }
}
