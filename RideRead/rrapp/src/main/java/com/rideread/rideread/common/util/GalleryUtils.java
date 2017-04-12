package com.rideread.rideread.common.util;

import android.app.Activity;

import com.rideread.rideread.common.event.SelectPicEvent;
import com.rideread.rideread.data.Logger;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SkyXiao on 2017/4/11.
 */

public class GalleryUtils {
    public final String TAG = this.getClass().getSimpleName();
    private List<String> path = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;

    public void getPictures(Activity mActivity,int countLimit) {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Logger.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Logger.i(TAG, "onSuccess: 返回数据");
                path.clear();
                for (String s : photoList) {
                    Logger.i(TAG, s);
                    path.add(s);
                }
                EventBus.getDefault().postSticky(new SelectPicEvent(path));
            }

            @Override
            public void onCancel() {
                Logger.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Logger.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Logger.i(TAG, "onError: 出错");
            }
        };

        galleryConfig = new GalleryConfig.Builder().imageLoader(new FrescoImgLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.rideread.rideread.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, countLimit)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(countLimit)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath(FileUtils.APP_SD_ROOT + "pic")          // 图片存放路径
                .build();
        GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(mActivity);

    }

}
