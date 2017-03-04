package com.rideread.rideread.common;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class Api {

    public final static String USER_LOGIN="http://121.42.195.113:3000/users/login";
    public final static String APP_KEY="VQSndpkC1LxD73qK0pMcIWHl";
    public final static String APP_ID="W8uDmfHGJ6bjr2x8vSG5O846-gzGzoHsz";
    public final static String REGISTER ="http://121.42.195.113:3000/users/register";//后端的设置用户名和头像的接口
    public final static String RESET_PWD="";//重置密码后台接口
    public final static String VERIFY_CODE ="http://121.42.195.113/rideread/users/verify_code";//判断邀请码是否有效的后台连接

    public final static String EDIT_MESSAGE="http://121.42.195.113/rideread/users/update";//编辑更新个人资料接口

    public final static String MY_TIMELINE="";//我的阅圈的接口

    public final static String FOLLOWER="http://121.42.195.113/rideread/users/follower";//获取粉丝列表接口
    public final static String FOLLOWING="http://121.42.195.113/rideread/users/following";//获取关注列表接口



    public final static String QINIU_TOKEN="http://121.42.195.113:3000/util/qiniu_token";//获取七牛云存储的token接口
    public final static String USERHEAD_LINK="http://om1ccbp21.bkt.clouddn.com/";
    public final static String TOKEN="MRBbJCJr-MDVxa08p6eMv6SouPViY8wqSfZek7Pt:fYQJktezBxHakBx1UBvsfciRPjg=:eyJzY29wZSI6InJlYWRyaWRlIiwiZGVhZGxpbmUiOjE0ODU5MTMyNTd9";//测试用的token


    public final static String TEMP_USER_TOKEN="34070eae5335938f171db8a11594cccf";//用户注册时，获取七牛云token时所用的用户token，这个token是模拟的，有安全隐患
    public final static int TEMP_UID=1;//临时uid，同样有隐患
}
