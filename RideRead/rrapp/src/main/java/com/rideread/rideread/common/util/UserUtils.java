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

    public static int getUid() {
        return CurCache.load(USER_ID, () -> Storage.get(USER_ID, 0));
    }


    @NonNull
    public static String getToken() {
        final String token = CurCache.load(USER_TOKEN, () -> Storage.get(USER_TOKEN, ""));
        return token == null ? "" : token;
    }


    public static void logout() {
        CurCache.clear();
        Storage.delete(USER_ID);
        Storage.delete(USER_TOKEN);

    }

    public static void login(int uid, String token) {
        Storage.put(UserUtils.USER_ID, uid);
        Storage.put(UserUtils.USER_TOKEN, token);

        CurCache.put(UserUtils.USER_ID, uid);
        CurCache.put(UserUtils.USER_TOKEN, token);

    }
}
