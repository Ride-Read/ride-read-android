package com.rideread.rideread;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.rideread.rideread.common.Api;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Jackbing on 2017/1/30.
 * 定义一些全局变量
 */

public class App extends Application {

    public static LinkedList<RegisterBaseActivity> queue=new LinkedList<RegisterBaseActivity>();

    public static LinkedList<BaseActivity> baseQueue=new LinkedList<BaseActivity>();
    private  UploadManager uploadManager=null;

    @Override
    public void onCreate() {

        // 请用你的AppId，AppKey。并在管理台启用手机号码短信验证
        AVOSCloud.initialize(this, Api.APP_ID,
                Api.APP_KEY);
        AVOSCloud.setDebugLogEnabled(true);//在应用发布之前，请关闭调试日志。
        Configuration config = new Configuration.Builder().zone(Zone.httpAutoZone).build();
        uploadManager = new UploadManager(config);

    }

    public UploadManager getUploadManager(){
        return uploadManager;
    }

    public void finishAll(){
        Iterator<RegisterBaseActivity> it=queue.iterator();
        Log.e("app ","size="+queue.size());
        while (it.hasNext()){
            RegisterBaseActivity activity=it.next();
            it.remove();
            activity.finish();
        }
    }

    public void finishAllBaseActivity(){
        Iterator<BaseActivity> it=baseQueue.iterator();
        Log.e("app ","size="+queue.size());
        while (it.hasNext()){
            BaseActivity activity=it.next();
            it.remove();
            activity.finish();
        }
    }

}
