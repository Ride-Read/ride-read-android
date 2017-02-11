package com.rideread.rideread.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by Jackbing on 2017/2/8.
 */

public class ImTypeMessageEvent {
    public AVIMTypedMessage message;
    public AVIMConversation conversation;
}
