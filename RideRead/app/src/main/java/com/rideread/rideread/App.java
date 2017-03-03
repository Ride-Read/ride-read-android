package com.rideread.rideread;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.rideread.rideread.activity.BaseActivity;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.Constants;
import com.rideread.rideread.db.RideReadDBHelper;
import com.rideread.rideread.im.MessageHandler;

import java.util.LinkedList;

/**
 * Created by Jackbing on 2017/1/30.
 * 定义一些全局变量
 */

public class App extends Application {


    public static LinkedList<BaseActivity> baseQueue=new LinkedList<BaseActivity>();
    private  UploadManager uploadManager=null;

    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                Log.d("接收消息",((AVIMTextMessage)message).getText());
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }


    @Override
    public void onCreate() {

        // 请用你的AppId，AppKey。并在管理台启用手机号码短信验证
        AVOSCloud.initialize(this, Api.APP_ID,
                Api.APP_KEY);
        AVOSCloud.setDebugLogEnabled(true);//在应用发布之前，请关闭调试日志。
        Configuration config = new Configuration.Builder().zone(Zone.httpAutoZone).build();
        uploadManager = new UploadManager(config);

        //注册默认的消息处理逻辑
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));

        RideReadDBHelper.getInstance().init(this, Constants.DEFAULT_DB_NAME,null);
    }

    public UploadManager getUploadManager(){
        return uploadManager;
    }




}
