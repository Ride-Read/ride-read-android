package com.rideread.rideread.common.util;

/**
 * Created by yayiji on 2017/4/11.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * 申请权限的工具类：
 */
public class PermissionUtils {
    public final static int CODE = 0;//权限申请请求码
    private final Context mContext;

    public PermissionUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限,缺少权限则返回true
    private boolean lacksPermission(String permission) {
        int value = ContextCompat.checkSelfPermission(mContext, permission);
        return value == PackageManager.PERMISSION_DENIED;
    }

    public void grantPermission(Activity activity,String... permissions) {
        boolean bool = lacksPermissions(permissions);

        //返回true则申请权限，表示缺少权限
        if (bool) {
            //请求权限：
            ActivityCompat.requestPermissions(activity, permissions, CODE);
        }
    }
}