package com.rideread.rideread.rrapp;

/**
 * Created by Jackbing on 2017/2/7.
 */

public class Constants {

    private static final String LEANMESSAGE_CONSTANTS_PREFIX = "com.leancloud.im.guide";

    public static final String MEMBER_ID = getPrefixConstant("member_id");
    public static final String CONVERSATION_ID = getPrefixConstant("conversation_id");

    public static final String ACTIVITY_TITLE = getPrefixConstant("activity_title");


    public static final String SQUARE_CONVERSATION_ID = "55cd829e60b2b52cda834469";

    private static String getPrefixConstant(String str) {
        return LEANMESSAGE_CONSTANTS_PREFIX + str;
    }

    public static final String CLIENT_ID="11";

    public static final String DEFAULT_DB_NAME="rideread.db";
    public static final int DEFAULT_DB_VERSION=1;

    public final static int SUCCESS=0;
    public final static int FAILED=1;
    public final static int USED=2;//邀请码已经被使用



    public final static int NO_EXITS=1002;//用户不存在
    public final static int PASSWORD_ERROR=1003;
    public final static int EXITED=1000;//用户已经存在
}
