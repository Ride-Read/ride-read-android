package com.rideread.rideread.common;

import android.util.Log;

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

//        try{
//
//            JSONObject json=new JSONObject();
//            json.put("invitationcode",inviteCode);
//            return loginPost(url,json.toString());
//        }catch (JSONException e){
//            return null;
//        }
        return new LoginMessageEntity("成功",1);

    }

    /**
     * 发送用户名，手机号码，密码（暂时没加密），头像url给后台
     * @param userName
     * @param telPhone
     * @param password
     * @param userHeadUrl
     * @param url
     * @return
     */
    public LoginMessageEntity send2BackGround(String userName,String telPhone,String password,String userHeadUrl,String url){
//        try{
//
//            JSONObject json=new JSONObject();
//            json.put("userName",userName);
//            json.put("userPic",userHeadUrl);
//            json.put("telphone",telPhone);
//            json.put("password",password);
//            return loginPost(url,json.toString());
//        }catch (JSONException e){
//            return null;
//        }
        return new LoginMessageEntity("成功",1);
    }

    public LoginMessageEntity resetPassword(String resetPassword,String telphone,String url){
//        try{
//
//            JSONObject json=new JSONObject();
//            json.put("resetpwd",resetPassword);
//            json.put("telphone",telphone);
//            return loginPost(url,json.toString());
//        }catch (JSONException e){
//            return null;
//        }
        return new LoginMessageEntity("成功",1);
    }

    /**
     *
     * @param url
     * @param headPath
     * @param phone
     * @param birthDate
     * @param sex
     * @param name
     * @param signture
     * @param locale
     * @param school
     * @param job
     * @param hometown
     * @return
     */

    public LoginMessageEntity editMessage(String url,String headPath,String phone
    ,String birthDate,String sex,String name,String signture,String locale,String school,
                                          String job,String hometown){

        //如上

        return new LoginMessageEntity("成功",1);
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

    public Boolean postJson(String target, String json) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        //long timestamp = System.currentTimeMillis();
        //String sign = MD5Util.string2MD5(timestamp + Api.APP_KEY);
        Request request = new Request.Builder()
                .addHeader("X-LC-Id", Api.APP_ID)
                .addHeader("X-LC-Key", Api.APP_KEY)
                //.addHeader("ContentType","application/json")
                .url(target)
                .post(requestBody)
                .build();
        try {
            Response response = clients.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                return true;
            } else {
                Log.e("response",response.body().string());
            }
        } catch (Exception e) {
            Log.e("error",e.toString());
        }
        return false;
    }

}
