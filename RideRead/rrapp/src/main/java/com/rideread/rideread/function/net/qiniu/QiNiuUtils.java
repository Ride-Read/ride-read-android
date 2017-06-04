package com.rideread.rideread.function.net.qiniu;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.rideread.rideread.function.Action;

import java.util.concurrent.Callable;

import static android.R.attr.path;

/**
 *
 */
public final class QiNiuUtils {
    /**
     * 裁剪正中部分，等比缩小生成200x200缩略图
     */
    public final static String CROP_SMALL_300 = "?imageView2/1/w/300/h/300";
    /**
     * 宽度固定为200px，生成高度等比缩略图
     */
    public final static String SMALL_WIDTH_200 = "?imageView2/2/w/200";
    public final static String CROP_SMALL_100 = "?imageView2/1/w/100/h/100";


    private static UploadManager mUploadManager;

    static {
        Configuration config = new Configuration.Builder().chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                //                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
                //                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.httpAutoZone) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        mUploadManager = new UploadManager(config);
    }

    public static void uploadFile(@NonNull final String token, @NonNull final String path, @NonNull final String name, @Nullable final UpCompletionHandler complete, @Nullable final Action<Double> progress, @Nullable final Callable<Boolean> cancel) {
        mUploadManager.put(path, name, token, complete, new UploadOptions(null, null, false, (key, percent) -> {
            if (progress == null) return;
            try {
                progress.call(percent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, () -> {
            if (cancel == null) return false;
            try {
                return cancel.call();
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }));
    }

    //增加一个接受字节数组的方法
    public static void uploadFile(@NonNull final String token, @NonNull final byte[] bytes, @NonNull final String name, @Nullable final UpCompletionHandler complete, @Nullable final Action<Double> progress, @Nullable final Callable<Boolean> cancel) {
        mUploadManager.put(bytes, name, token, complete, new UploadOptions(null, null, false, (key, percent) -> {
            if (progress == null) return;
            try {
                progress.call(percent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, () -> {
            if (cancel == null) return false;
            try {
                return cancel.call();
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }));
    }
}
