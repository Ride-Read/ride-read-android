package com.rideread.rideread.function.net.retrofit;

import com.rideread.rideread.data.result.Comment;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.FollowUser;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.data.result.QiniuToken;
import com.rideread.rideread.data.result.SearchUsers;
import com.rideread.rideread.data.result.ThumbsUpUser;
import com.rideread.rideread.data.result.UserInfo;
import com.rideread.rideread.data.result.VCode;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by SkyXiao on 2017/1/20.
 * * Call<T> get();必须是这种形式,这是2.0之后的新形式
 * 如果不需要转换成Json数据,可以用了ResponseBody;
 * 你也可以使用Call<GsonBean> get();这样的话,需要添加Gson转换器
 */
public interface ApiStore {

    public final static String USER_LOGIN = "http://121.42.195.113:3000/users/login";
    public final static String APP_KEY = "VQSndpkC1LxD73qK0pMcIWHl";
    public final static String APP_ID = "W8uDmfHGJ6bjr2x8vSG5O846-gzGzoHsz";
    public final static String USERHEAD_LINK = "http://om1ccbp21.bkt.clouddn.com/";
    public final static String TOKEN = "MRBbJCJr-MDVxa08p6eMv6SouPViY8wqSfZek7Pt:fYQJktezBxHakBx1UBvsfciRPjg=:eyJzY29wZSI6InJlYWRyaWRlIiwiZGVhZGxpbmUiOjE0ODU5MTMyNTd9";//测试用的token


    public final static String TEMP_USER_TOKEN = "34070eae5335938f171db8a11594cccf";//用户注册时，获取七牛云token时所用的用户token，这个token是模拟的，有安全隐患

    ////另一种方式，参数一一传递
    //    @POST("account/login")
    //    Call<BaseModel<LoginResult>> login(
    //            @Query("account") String account, @Query("password") String password,
    //            @Query("network_type") String network_type, @Query("firmware_version") String firmware_version,
    //            @Query("app_version") String app_version, @Query("hardware_version") String hardware_version,
    //            @Query("phone_type") String phone_type,
    //            @Query("apply_time") String apply_time, @Query("code") String code);
    @POST("users/login")
    Call<BaseModel<UserInfo>> login(@QueryMap Map<String, String> params);

    @POST("users/reset_password")
    Call<BaseModel<DefJsonResult>> resetPassword(@QueryMap Map<String, String> params);

    @POST("users/verify")
    Call<BaseModel<DefJsonResult>> verify(@QueryMap Map<String, String> params);

    @POST("users/register")
    Call<BaseModel<UserInfo>> register(@QueryMap Map<String, String> params);

    @POST("users/followers")
    Call<BaseModel<List<FollowUser>>> followers(@QueryMap Map<String, String> params);

    @POST("users/followings")
    Call<BaseModel<List<FollowUser>>> followings(@QueryMap Map<String, String> params);

    @POST("users/follow")
    Call<BaseModel<DefJsonResult>> follow(@QueryMap Map<String, String> params);


    @POST("users/unfollow")
    Call<BaseModel<DefJsonResult>> unfollow(@QueryMap Map<String, String> params);

    @POST("users/show_user_info")
    Call<BaseModel<DefJsonResult>> showUserInfo(@QueryMap Map<String, String> params);

    @POST("users/search_follower_or_following")
    Call<BaseModel<SearchUsers>> searchUser(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("users/update")
    Call<BaseModel<DefJsonResult>> update(@FieldMap Map<String, String> params);

    @POST("moments/post_moment")
    Call<BaseModel<DefJsonResult>> postMoment(@QueryMap Map<String, String> params);

    @POST("moments/show_user")
    Call<BaseModel<List<Moment>>> showUserMoments(@QueryMap Map<String, String> params);

    @POST("moments/add_thumbsup")
    Call<BaseModel<DefJsonResult>> addThumbsUp(@QueryMap Map<String, String> params);

    @POST("moments/add_comment")
    Call<BaseModel<Comment>> addComment(@QueryMap Map<String, String> params);

    @POST("moments/remove_comment")
    Call<BaseModel<DefJsonResult>> removeComment(@QueryMap Map<String, String> params);

    @POST("moments/remove_thumbsup")
    Call<BaseModel<DefJsonResult>> removeThumbsUp(@QueryMap Map<String, String> params);

    @POST("moments/remove_moment")
    Call<BaseModel<DefJsonResult>> removeMoment(@QueryMap Map<String, String> params);

    @POST("moments/show_thumbsup")
    Call<BaseModel<List<ThumbsUpUser>>> showThumbsUpUsers(@QueryMap Map<String, String> params);

    @POST("moments/show_moment")
    Call<BaseModel<List<Moment>>> showMoment(@QueryMap Map<String, String> params);

    @POST("moments/collect_moment")
    Call<BaseModel<DefJsonResult>> collectMoment(@QueryMap Map<String, String> params);

    @POST("util/qiniu_token")
    Call<BaseModel<QiniuToken>> getQiNiuToken(@QueryMap Map<String, String> params);

    @POST("util/yun_pian_code")
    Call<BaseModel<VCode>> getVCode(@QueryMap Map<String, String> params);


    //    @Multipart
    //    @POST("account/avatar/modify")
    //    Call<BaseModel<LoginResult>> modifyAvatar(@QueryMap Map<String, String> params, @Part MultipartBody.Part picture);

}
