package com.rideread.rideread.common.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rideread.rideread.common.event.MsgListRefreshEvent;
import com.rideread.rideread.data.CurCache;
import com.rideread.rideread.data.Storage;
import com.rideread.rideread.data.been.MsgInfo;
import com.rideread.rideread.data.result.UserBaseInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by SkyXiao on 2017/4/21.
 */

public class MsgUtils {
    private final static String CUR_MSG_USERS_OBJ = "cur_msg_users_obj";
    private final static String CUR_MSG_USERS_LIST = "cur_msg_users_list";

    public static JSONObject getMsgUserObj() {
        String jsonStr = CurCache.load(CUR_MSG_USERS_OBJ, () -> Storage.get(CUR_MSG_USERS_OBJ, null));
        if (!TextUtils.isEmpty(jsonStr)) {
            JSONObject msgUserJson = JSON.parseObject(jsonStr);
            return msgUserJson;
        }
        return null;
    }

    public static JSONArray getMsgUserList() {
        String jsonStr = CurCache.load(CUR_MSG_USERS_LIST, () -> Storage.get(CUR_MSG_USERS_LIST, "[]"));
        JSONArray msgUserList = JSON.parseArray(jsonStr);
        return msgUserList;
    }

    public static void addMsgInfo(UserBaseInfo user, String msg, long time) {
        JSONObject msgUserJson = getMsgUserObj();
        if (null == msgUserJson) msgUserJson = new JSONObject();
        MsgInfo msginfo = new MsgInfo(user, msg, time);
        String key = Integer.toString(user.getUid());
        if (msgUserJson.containsKey(key))msgUserJson.remove(key);
        msgUserJson.put(key, msginfo);
        saveMsgUserList(msgUserJson);
    }

    private static void saveMsgUserList(JSONObject msgUserJson) {
        String jsonStr = msgUserJson.toJSONString();
        CurCache.put(CUR_MSG_USERS_OBJ, jsonStr);
        Storage.put(CUR_MSG_USERS_OBJ, jsonStr);
        EventBus.getDefault().postSticky(new MsgListRefreshEvent());
    }
}
