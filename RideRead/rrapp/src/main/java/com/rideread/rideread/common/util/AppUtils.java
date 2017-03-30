package com.rideread.rideread.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.ArrayMap;

import java.io.File;
import java.util.List;
import java.util.Map;


public class AppUtils {

    public final static String APP_KEY="VQSndpkC1LxD73qK0pMcIWHl";
    public final static String APP_ID="W8uDmfHGJ6bjr2x8vSG5O846-gzGzoHsz";
    public final static String USERHEAD_LINK="http://om1ccbp21.bkt.clouddn.com/";
    public final static String TOKEN="MRBbJCJr-MDVxa08p6eMv6SouPViY8wqSfZek7Pt:fYQJktezBxHakBx1UBvsfciRPjg=:eyJzY29wZSI6InJlYWRyaWRlIiwiZGVhZGxpbmUiOjE0ODU5MTMyNTd9";//测试用的token
    public final static String TEMP_USER_TOKEN="34070eae5335938f171db8a11594cccf";//用户注册时，获取七牛云token时所用的用户token，这个token是模拟的，有安全隐患



    /**
     * 获取Version Name
     */
    public static String getVersionName() {
        ArrayMap vInfo = getVersion();
        if (vInfo.containsKey(Version.VERSION_NAME)) {
            return vInfo.get(Version.VERSION_NAME).toString();
        }
        return "";
    }

    public static int getVersionCode() {
        return (Integer) getVersion().get(Version.VERSION_CODE);
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ArrayMap getVersion() {
        ArrayMap vInfo = new ArrayMap<>();
        try {
            PackageManager pm = Utils.getAppContext().getPackageManager();
            PackageInfo info = pm.getPackageInfo(Utils.getAppContext().getPackageName(), 0);
            vInfo.put("VERSION_CODE", info.versionCode);
            vInfo.put("VERSION_NAME", info.versionName);
            vInfo.put(Version.VERSION_CODE, info.versionCode);
            vInfo.put(Version.VERSION_NAME, info.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vInfo;
    }

    /**
     * 获取version、android version
     *
     * @return
     */
    public static Map<String, String> getAppInfo() {
        Map<String, String> contact = new ArrayMap<>();
        // 在反馈记录
        // contact.put("nick", MyInfo.shareInstance().getNick());
        // contact.put("uid", MyInfo.shareInstance().getUid() + "");
        contact.put("version", AppUtils.getVersionName() + "");
        contact.put("android version", android.os.Build.VERSION.RELEASE);

        return contact;
    }

    /**
     * @return
     * @see {@linkplain #getMyCacheDir(String)}
     */
    public static String getMyCacheDir() {
        return getMyCacheDir(null);
    }

    /**
     * 获取或创建Cache目录
     *
     * @param bucket 临时文件目录，bucket = "/cache/" ，则放在"sdcard/ride-read/cache"; 如果bucket=""或null,则放在"sdcard/ride-read/"
     */
    public static String getMyCacheDir(String bucket) {
        String dir;

        // 保证目录名称正确
        if (bucket != null) {
            if (!bucket.equals("")) {
                if (!bucket.endsWith("/")) {
                    bucket = bucket + "/";
                }
            }
        } else bucket = "";

        String joyrun_default = "/ride-read/";

        if (isSDCardExist()) {
            dir = Environment.getExternalStorageDirectory().toString() + joyrun_default + bucket;
        } else {
            dir = Environment.getDownloadCacheDirectory().toString() + joyrun_default + bucket;
        }

        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
        return dir;
    }

    /**
     * Uri转成File path
     */
    public static String UriToPath(Activity activity, Uri uri) {
        if (uri == null) return null;

        String url = null;
        if (uri.toString().startsWith("file:")) {
            url = uri.toString().substring(7);

        } else if (activity != null) {
            String[] proj = {MediaStore.Images.Media.DATA};

            @SuppressWarnings("deprecation") Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);

            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            actualimagecursor.moveToFirst();

            String img_path = actualimagecursor.getString(actual_image_column_index);

            return img_path;
        }
        return url;
    }

    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断Activity是否存在
     *
     * @param context
     * @param activityClass
     * @return
     */
    public static boolean isActivityExist(Context context, Class<? extends Activity> activityClass) {
        try {
            context = context.getApplicationContext();
            Intent intent = new Intent(context, activityClass);
            ComponentName cmpName = intent.resolveActivity(context.getPackageManager());

            if (cmpName != null) { // 说明系统中存在这个activity
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<RunningTaskInfo> taskInfoList = am.getRunningTasks(70);

                for (RunningTaskInfo taskInfo : taskInfoList) {
                    if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }

        return false;
    }

    /**
     * 判断Service是否running
     *
     * @param context
     * @param serviceClass
     * @return
     */
    public static boolean isServiceRunning(Context context, Class<? extends Service> serviceClass) {
        try {
            context = context.getApplicationContext();

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(2000);

            for (ActivityManager.RunningServiceInfo info : serviceList) {
                String name = info.service.getClassName();

                if (name != null && name.contains(serviceClass.getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 打开一个指定包名的应用
     *
     * @param context
     * @return
     */
    public static boolean openApp(Context context, String target_packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> appsList = context.getPackageManager().queryIntentActivities(intent, 0);
        for (int i = 0; i < appsList.size(); i++) {

            ResolveInfo mResolveInfo = appsList.get(i);

            // package name
            String packageName = mResolveInfo.activityInfo.packageName;

            if (packageName.contains(target_packageName)) { // 我们想打开的包名

                // class name
                String className = mResolveInfo.activityInfo.name;
                ComponentName mComponentName = new ComponentName(packageName, className);

                Intent intent2 = new Intent();
                if (mComponentName != null) {
                    intent2.setComponent(mComponentName);
                }

                context.startActivity(intent2);

                return true;
            }
        }

        return false;
    }


    /**
     * 版本信息Key
     */
    public static class Version {
        public static String VERSION_CODE = "version_code";
        public static String VERSION_NAME = "version_name";
    }


    /**
     * 把字体大小变正常（即使用户在系统设置了字号）
     * <p/>
     * 只对当前Activity有效
     *
     * @param resource
     */
    public static void normalFontSize(Resources resource) {
        Configuration configuration = resource.getConfiguration();
        configuration.fontScale = 1f;
        resource.updateConfiguration(configuration, resource.getDisplayMetrics());
    }


}
