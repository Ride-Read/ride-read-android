package com.rideread.rideread.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.rideread.rideread.bean.PostParams;

/**
 * Created by Jackbing on 2017/1/25.
 * 注意某些信息需要加密但是现在暂时还没有
 */

public class PreferenceUtils {
    static  PreferenceUtils preferenceUtils=null;


    public static PreferenceUtils getInstance(){
        if(preferenceUtils==null){
            synchronized (PreferenceUtils.class){
                if(preferenceUtils==null){
                    preferenceUtils=new PreferenceUtils();
                }
            }
        }
        return preferenceUtils;

    }

    /**
     *
     * @param token
     * @param timeStamp
     * @param uid
     * @param context
     */
    public void saveMustParams(String token,long timeStamp,int uid,Context context){
        SharedPreferences sf=context.getSharedPreferences("params",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sf.edit();
        editor.putString("token",token);
        editor.putLong("timeStamp",timeStamp);
        editor.putInt("uid",uid);
        editor.commit();
    }

    public PostParams getPostParams(Context context){
        SharedPreferences sf=context.getSharedPreferences("params",Context.MODE_PRIVATE);
        PostParams pp=new PostParams();
        pp.setToken(sf.getString("token",null));
        pp.setUid(sf.getInt("uid",-1));
        pp.setTimeStmap(sf.getLong("timeStamp",-1));
        return pp;

    }




    /**
     * 保存登录信息
     * @param username
     * @param password
     * @param context
     */
    public void saveLoginInfo(String username,String password, Context context){

        SharedPreferences sf=context.getSharedPreferences("info",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sf.edit();
        editor.putString("account",username);
        editor.putString("password",password);
        editor.commit();


    }

    /**
     * 如果返回的是nosave，则用户名没有保存，或者username这个key错误
     * @param context
     * @return
     */
    public String getUserNameFromPreference(Context context){

        SharedPreferences sf=context.getSharedPreferences("info",Context.MODE_PRIVATE);
        return sf.getString("account","nosave");

    }


    /**
     * 清楚数据
     * @param context
     */
    public void clearLoginInfo(Context context){
        SharedPreferences sf=context.getSharedPreferences("info",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sf.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存电话号码
     * @param tel
     * @param context
     */
    public void saveTelPhone(String tel,Context context){

        SharedPreferences sf=context.getSharedPreferences("tel",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sf.edit();
        editor.putString("telphone",tel);
        editor.commit();
    }

    /**
     * 保存裁切过的头像路径
     * @param imagePath
     * @param key 这个key是电话号码
     * @param context
     */
    public void saveImageHeadPath(String imagePath,String key,Context context){
        SharedPreferences sf=context.getSharedPreferences("headImage",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sf.edit();
        editor.putString(key,imagePath);
        editor.commit();
    }

    /**
     * 获取电话号码
     * @param context
     * @return
     */
    public String getTelPhone(Context context){
        SharedPreferences sf=context.getSharedPreferences("tel",Context.MODE_PRIVATE);
        return sf.getString("telphone",null);
    }

    /**
     * 获取头像路径
     * @param context
     * @return
     */
    public String getImageHeadPath(Context context){
        SharedPreferences sf=context.getSharedPreferences("headImage",Context.MODE_PRIVATE);
        return sf.getString(getTelPhone(context),null);
    }

}
