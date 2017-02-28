package com.rideread.rideread.common;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.bean.LoginResponse;




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
     * @param username
     * @param password
     * @param url
     * @return 返回一个消息实体，
     */
    public LoginResponse userLogin(String username, String password, String url){


        try {
            System.out.println("account="+username+",pwd"+password);
            JSONObject json=new JSONObject();
            json.put("username","dsdseere");
            System.out.println("account="+username+",pwd="+password);
            json.put("password","efsdssf");

            System.out.println("u="+json.getString("username")+",pwd="+json.getString("password"));
            String resp=post(url,json.toString());
            if(resp==null){
                return null;
            }
            return new Gson().fromJson(resp,LoginResponse.class);

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
    public Integer  testInviteCode(String inviteCode,long timeStamp,String url){

        try{

            JSONObject json=new JSONObject();
            json.put("code",inviteCode);
            json.put("sign",timeStamp);
            String resp=post(url,json.toString());
            if(resp==null){
                return null;//归为未知错误
            }
            JSONObject jsonObject=JSONObject.parseObject(resp);
            Integer status=jsonObject.getInteger("status");
            return status;
        }catch (JSONException e){
            return null;
        }

    }

    /**
     *
     * @param userName
     * @param telPhone
     * @param password
     * @param userHeadUrl
     * @param url
     * @param timeStamp
     * @return
     */
    public LoginResponse send2BackGround(String userName,String telPhone,String password,String userHeadUrl,String url,long timeStamp){
        try{

            JSONObject json=new JSONObject();
            json.put("nickname",userName);
            json.put("face_url",userHeadUrl);
            json.put("phonenumber",telPhone);
            json.put("password",MD5Utils.Md5(password));
            json.put("sign",timeStamp);
            String resp=post(url,json.toString());
            if(resp==null){
                return null;
            }
            return new Gson().fromJson(resp,LoginResponse.class);
        }catch (JSONException e){
            return null;
        }
    }

    public LoginMessageEntity resetPassword(String resetPassword,String telphone,String url){
//        try{
//
//            JSONObject json=new JSONObject();
//            json.put("resetpwd",resetPassword);
//            json.put("telphone",telphone);
//            return post(url,json.toString());
//        }catch (JSONException e){
//            return null;
//        }
        return null;
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

        return null;
    }


    /**
     * 登录注册的post
     * @param url
     * @param json
     * @return
     */
    private String post(String url, String json){
        try {
            RequestBody requestBody = RequestBody.create(JSON, json);

            Request request = new Request.Builder().url(url)
                    .post(requestBody).build();
            Response response = clients.newCall(request).execute();
            System.out.println(response.body().string());
            return response.body().string();
        }catch(IOException e){
            return null;
        }

    }


}
