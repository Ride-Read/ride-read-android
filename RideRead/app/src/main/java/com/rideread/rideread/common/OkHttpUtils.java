package com.rideread.rideread.common;

import com.google.gson.Gson;
import com.rideread.rideread.bean.LoginMessageEntity;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class OkHttpUtils {

    final OkHttpClient clients=new OkHttpClient();
    public static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    final Gson gson=new Gson();

    private static   OkHttpUtils okHttpUtils = null;


    public static OkHttpUtils getInstance(){
        if(okHttpUtils==null){
            synchronized (OkHttpUtils.class){
                if(okHttpUtils==null){
                    return new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }

    /**
     * 用户登录
     * @param account
     * @param password
     * @param url
     * @return 返回一个消息实体，包括resultCode，msg
     */
    public LoginMessageEntity userLogin(String account, String password, String url){

        try {
            JSONObject json=new JSONObject();
            json.put("account",account);
            json.put("password",password);
            return loginPost(url,json.toString());

        }catch (JSONException e){
            return null;
        }

    }

    /**
     * 邀请码验证
     * @param inviteCode
     * @param url
     * @return
     */
    public LoginMessageEntity testInviteCode(String inviteCode,String url){

        try{

            JSONObject json=new JSONObject();
            json.put("invitationcode",inviteCode);
            return loginPost(url,json.toString());
        }catch (JSONException e){
            return null;
        }

    }

    /**
     * 验证手机号
     * @param telphone
     * @param url
     * @return
     */
    public LoginMessageEntity testTelPhone(String telphone,String url){
        try{

            JSONObject json=new JSONObject();
            json.put("telphone",telphone);
            return loginPost(url,json.toString());
        }catch (JSONException e){
            return null;
        }
    }


    /**
     * 登录注册的post
     * @param url
     * @param json
     * @return
     */
    private LoginMessageEntity loginPost(String url,String json){
        try {
            RequestBody requestBody = RequestBody.create(JSON, json);

            Request request = new Request.Builder().url(url)
                    .post(requestBody).build();
            Response response = clients.newCall(request).execute();
            return gson.fromJson(response.body().string(),LoginMessageEntity.class);
        }catch(IOException e){
            return null;
        }

    }

}
