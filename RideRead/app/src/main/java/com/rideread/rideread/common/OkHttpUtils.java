package com.rideread.rideread.common;

import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.bean.LoginResponse;
import com.rideread.rideread.bean.PersonalInfoFollower;
import com.rideread.rideread.bean.PersonalInfoFollowing;
import com.rideread.rideread.bean.QiNiuTokenResp;


import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jackbing on 2017/1/22.
 *
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
    public LoginResponse userLogin(String username, String password, String url,double longtitude,double latitude){


        try {
            Log.e("原密码：",password);
            JSONObject json=new JSONObject();
            String encodePwd= SHA1Helper.SHA1(password);//加密
            json.put("username",username);
            json.put("password",encodePwd);
            json.put("latitude",latitude);
            json.put("longitude",longtitude);
            Log.e("username:",username);
            Log.e("加密后的密码：",encodePwd);
            String resp=post(url,json.toString());
            if(resp==null){
                return null;
            }
            return new Gson().fromJson(resp,LoginResponse.class);

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }catch (Exception e){
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
        }catch (Exception e){
            return null;
        }

    }

    /**
     *注册
     * @param userName
     * @param telPhone
     * @param password
     * @param userHeadUrl
     * @param url
     * @return
     */
    public LoginResponse send2BackGround(String userName,String telPhone,String password,String userHeadUrl,String url){
        try{

            JSONObject json=new JSONObject();
            json.put("nickname",userName);
            json.put("face_url",userHeadUrl);
            json.put("phonenumber",telPhone);
            json.put("password",password);
            String resp=post(url,json.toString());
            if(resp==null){
                return null;
            }
            return new Gson().fromJson(resp,LoginResponse.class);
        }catch (JSONException e){
            return null;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 重置密码
     * @param resetPassword
     * @param telphone
     * @param url
     * @return
     */
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
     * 更新基本资料
     * @param url
     * @param
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

    public LoginResponse editMessage(String face_url,String phone
    ,String birthDate,int sex,String name,String signture,String locale,String school,
                                          String job,String hometown,long timeStamp,String url,int uid,String token,double longtitude,double latitude,List<String > tags){
        try{
        JSONObject json=new JSONObject();
        json.put("uid",uid);
        json.put("token",token);
        json.put("sign",timeStamp);
        json.put("school",school);
        json.put("location",locale);
        json.put("sex",sex);
        json.put("signature",signture);
        json.put("phonenumber",phone);
        json.put("face_url",face_url);
        json.put("career",job);
        json.put("nickname",name);
        json.put("hometown",hometown);
        json.put("birthday",birthDate);
        json.put("tags",tags);
        json.put("longtitude",longtitude);
        json.put("latitude",latitude);
        String resp=post(url,json.toString());
        if(resp==null){
            return null;
        }
        return new Gson().fromJson(resp,LoginResponse.class);
    }catch (JSONException e){
            e.printStackTrace();
        return null;
    }catch (Exception e){
            e.printStackTrace();
        return null;
     }
    }

    public PersonalInfoFollowing getFollowing(int  uid, String token, long timeStamp, String url){
        try{

            JSONObject json=new JSONObject();
            json.put("uid",uid);
            json.put("token",token);
            json.put("sign",timeStamp);
            String resp=post(url,json.toString());
            if(resp==null){
                return null;
            }
            return new Gson().fromJson(resp,PersonalInfoFollowing.class);
        }catch (JSONException e){
            return null;
        }catch (Exception e){
            return null;
        }
    }

    public PersonalInfoFollower getFollower(int  uid, String token, long timeStamp, String url){
        try{

            JSONObject json=new JSONObject();
            json.put("uid",uid);
            json.put("token",token);
            json.put("sign",timeStamp);
            String resp=post(url,json.toString());
            if(resp==null){
                return null;
            }
            return new Gson().fromJson(resp,PersonalInfoFollower.class);
        }catch (JSONException e){
            return null;
        }catch (Exception e){
            return null;
        }
    }

    public QiNiuTokenResp getToken(String filename, int uid, long timeStamp, String userToken, String url){

        try{

            JSONObject json=new JSONObject();
            json.put("uid",uid);
            json.put("token",userToken);
            json.put("timestamp",timeStamp);
            json.put("filename",filename);
            String resp=post(url,json.toString());
            if(resp==null){
                return null;
            }
            return new Gson().fromJson(resp,QiNiuTokenResp.class);
        }catch (JSONException e){
            return null;
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 登录注册的post
     * @param url
     * @param json
     * @return
     */
    private String post(String url, String json){
        String result=null;
        try {
            RequestBody requestBody = RequestBody.create(JSON, json);

            Request request = new Request.Builder().url(url)
                    .post(requestBody).build();
            Response response = clients.newCall(request).execute();

            result=response.body().string();
            System.out.println(result);
            return result;
        }catch(IOException e){
            e.printStackTrace();
            return result;
        }catch (Exception e){
            return null;
        }

    }




}
