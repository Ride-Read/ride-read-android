package com.rideread.rideread.function.net.retrofit;

import com.google.gson.JsonObject;
import com.rideread.rideread.data.result.QiniuToken;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by SkyXiao on 2017/1/20.
 * * Call<T> get();必须是这种形式,这是2.0之后的新形式
 * 如果不需要转换成Json数据,可以用了ResponseBody;
 * 你也可以使用Call<GsonBean> get();这样的话,需要添加Gson转换器
 */
public interface ApiStore {

    public final static String USER_LOGIN="http://121.42.195.113:3000/users/login";
    public final static String APP_KEY="VQSndpkC1LxD73qK0pMcIWHl";
    public final static String APP_ID="W8uDmfHGJ6bjr2x8vSG5O846-gzGzoHsz";
    public final static String REGISTER ="http://121.42.195.113:3000/users/register";//后端的设置用户名和头像的接口
    public final static String RESET_PWD="";//重置密码后台接口
    public final static String VERIFY_CODE ="http://121.42.195.113:3000/users/verify";//判断邀请码是否有效的后台连接

    public final static String EDIT_MESSAGE="http://121.42.195.113:3000/users/update";//编辑更新个人资料接口

    public final static String MY_TIMELINE="";//我的阅圈的接口

    public final static String FOLLOWER="http://121.42.195.113:3000/users/followers";//获取粉丝列表接口
    public final static String FOLLOWING="http://121.42.195.113:3000/users/followings";//获取关注列表接口


    public final static String QINIU_TOKEN="http://121.42.195.113:3000/util/qiniu_token";//获取七牛云存储的token接口
    public final static String USERHEAD_LINK="http://om1ccbp21.bkt.clouddn.com/";
    public final static String TOKEN="MRBbJCJr-MDVxa08p6eMv6SouPViY8wqSfZek7Pt:fYQJktezBxHakBx1UBvsfciRPjg=:eyJzY29wZSI6InJlYWRyaWRlIiwiZGVhZGxpbmUiOjE0ODU5MTMyNTd9";//测试用的token


    public final static String TEMP_USER_TOKEN="34070eae5335938f171db8a11594cccf";//用户注册时，获取七牛云token时所用的用户token，这个token是模拟的，有安全隐患
    public final static int TEMP_UID=1;//临时uid，同样有隐患
//http://121.42.195.113:3000
    public static final String BaseUrl2 = "http://121.42.195.113";

    ////另一种方式，参数一一传递
//    @POST("account/login")
//    Call<BaseModel<LoginResult>> login(
//            @Query("account") String account, @Query("password") String password,
//            @Query("network_type") String network_type, @Query("firmware_version") String firmware_version,
//            @Query("app_version") String app_version, @Query("hardware_version") String hardware_version,
//            @Query("phone_type") String phone_type,
//            @Query("apply_time") String apply_time, @Query("code") String code);
    @POST("account/login")
    Call<BaseModel<JsonObject>> login(@QueryMap Map<String, String> params);

    @POST("util/qiniu_koken")
    Call<BaseModel<QiniuToken>> getQiNiuToken(@QueryMap Map<String, String> params);


//    @FormUrlEncoded
//    @POST("account/load/property/list")
//    Call<BaseModel<ListResult<DoorNode>>> fetchAgencies(@FieldMap Map<String, String> params);


//    @Multipart
//    @POST("account/avatar/modify")
//    Call<BaseModel<LoginResult>> modifyAvatar(@QueryMap Map<String, String> params, @Part MultipartBody.Part picture);

}
