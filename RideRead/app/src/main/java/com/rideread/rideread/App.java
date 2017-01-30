package com.rideread.rideread;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.rideread.rideread.common.Api;

/**
 * Created by Jackbing on 2017/1/30.
 * 定义一些全局变量
 */

public class App extends Application {


    private  UploadManager uploadManager=null;

    @Override
    public void onCreate() {

        // 请用你的AppId，AppKey。并在管理台启用手机号码短信验证
        AVOSCloud.initialize(this, Api.APP_ID,
                Api.APP_KEY);
        Configuration config = new Configuration.Builder().zone(Zone.httpAutoZone).build();
        uploadManager = new UploadManager(config);

    }

    public UploadManager getUploadManager(){
        return uploadManager;
    }


}
