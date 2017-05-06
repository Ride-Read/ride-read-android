package com.rideread.rideread.function.net.im;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.rideread.rideread.module.profile.view.ChatSingleActivity;
import com.rideread.rideread.rrapp.Constants;


/**
 * Created by Jackbing on 2017/2/9.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String conversationId = intent.getStringExtra(Constants.CONVERSATION_ID);
        if (!TextUtils.isEmpty(conversationId)) {
            gotoSingleChatActivity(context, intent);
        }
    }

    /**
     * 跳转至单聊页面
     *
     * @param context
     * @param intent
     */
    private void gotoSingleChatActivity(Context context, Intent intent) {
        Intent startActivityIntent = new Intent(context, ChatSingleActivity.class);
        startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityIntent.putExtra(Constants.MEMBER_ID, intent.getStringExtra(Constants.MEMBER_ID));
        context.startActivity(startActivityIntent);
    }
}
