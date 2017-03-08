package com.rideread.rideread.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadOptions;
import com.rideread.rideread.App;
import com.rideread.rideread.activity.MainActivity;
import com.rideread.rideread.R;
import com.rideread.rideread.activity.UserAgreement;
import com.rideread.rideread.bean.LoginResponse;
import com.rideread.rideread.bean.QiNiuTokenResp;
import com.rideread.rideread.bean.UserData;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.ConfirmPassword;
import com.rideread.rideread.common.Constants;
import com.rideread.rideread.common.FileUtils;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.PreferenceUtils;
import com.rideread.rideread.common.SHA1Helper;
import com.rideread.rideread.common.TimeStamp;
import com.rideread.rideread.im.AVImClientManager;
import com.rideread.rideread.imageloader.GildeImageLoader;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private String tagPhone="phoneNumView",tagInvite="inviteCodeView",tagPwd="setPwdView",tagName="setUnameView";
    private View phoneNumView, inviteCodeView, setPwdView, setUnameView;
    private String inviteCode;
    private TextView indentfyCodeTv;
    private String telPhone,password,encodepwd,timeStamp;
    private final int REQUEST_IMAGE=1;
    private final int CROP=0;
    private final String TYPE="image/*";
    private String fileName,userName,shortFileName;//原图的路径+文件名
    private String TAG="Reg";
    private CircleImageView img;
    private EditText etUserName;
    private TextView setuserhead_texthint;//也就是头像那两个字在设置完头像后隐藏掉
    private String  filePath=null;


    private IHandlerCallBack iHandlerCallBack;
    private List<String> path = new ArrayList<>();
    private Activity mActivity;
    private Context mContext;
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    private GalleryConfig galleryConfig;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.register_fragment_layout,container,false);
        inviteCodeView =mView.findViewById(R.id.register_invite_include_layout);
        initButton(inviteCodeView,R.id.register_btn_next,tagInvite);

        phoneNumView =mView.findViewById(R.id.register_phonenum_include_ly);
        initButton(phoneNumView,R.id.register_btn_next,tagPhone);
        ((TextView)phoneNumView.findViewById(R.id.register_tv_sendidentfycode)).setOnClickListener(this);

        setPwdView =mView.findViewById(R.id.register_setpwd_include_ly);
        initButton(setPwdView,R.id.register_btn_next,tagPwd);

        setUnameView =mView.findViewById(R.id.register_setuname_include_ly);
        initButton(setUnameView,R.id.register_btn_complete,null);
        img=(CircleImageView)setUnameView.findViewById(R.id.register_civ_userhead);
        img.setOnClickListener(this);
        setuserhead_texthint=(TextView)setUnameView.findViewById(R.id.setuserhead_texthint);

        mContext=getContext();
        mActivity=getActivity();
        initGallery();
        return mView;
    }


    public void initButton(View v,int id,String tag){
        Button btn=(Button)v.findViewById(id);
        btn.setTag(tag);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.register_btn_next:
                String tag=(String)v.getTag();
                Button btnNext=(Button)v;
                if(tag.equals(tagInvite)){
                    //changeView(inviteCodeView, phoneNumView);
                    veifyInviteCode(btnNext);
                }else if(tag.equals(tagPhone)){
                    verifyCode();

                }else if(tag.equals(tagPwd)){
                    setPwd();
                }
                break;
            case R.id.register_btn_complete:
                onComplete();
                break;
            case R.id.register_tv_sendidentfycode:
                Toast.makeText(getContext(),"发送验证码",Toast.LENGTH_SHORT).show();
                if(indentfyCodeTv==null){
                    indentfyCodeTv=(TextView)v;
                }
                onSendIdentCode();
                break;
            case R.id.register_read_agreement_tv:
                readAgreement();
                break;
            case R.id.register_civ_userhead:
                setHeadImg();
                break;

        }

    }

    private void setHeadImg() {
        galleryConfig.getBuilder().isOpenCamera(false).build();
        initPermissions();
    }

    private void changeView(View hideView,View showView){

        hideView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
    }

    /**
     * 验证邀请码
     */
    private void veifyInviteCode(Button btnNext) {
        EditText editText=(EditText)inviteCodeView.findViewById(R.id.register_edt_invitationcode);
        inviteCode=editText.getText().toString().trim();
        if(inviteCode==null){
            Toast.makeText(getActivity(),"未填写邀请码",Toast.LENGTH_SHORT).show();
        }else{
            btnNext.setClickable(false);//避免点击多次
            new InviteCodeAysncTask().execute(inviteCode,Api.VERIFY_CODE);
        }
    }


    /**
     * 验证邀请码的异步线程
     */
    class InviteCodeAysncTask extends AsyncTask<String,String,Integer> {
        @Override
        protected Integer doInBackground(String... params) {

            return OkHttpUtils.getInstance().testInviteCode(params[0], TimeStamp.getTimeStamp(),params[1]);
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);
            if(status== Constants.SUCCESS){
                changeView(inviteCodeView, phoneNumView);
            }else if(status==Constants.FAILED){
                Toast.makeText(getActivity(),"邀请码不存在",Toast.LENGTH_SHORT).show();
            }else if(status==Constants.USED){
                Toast.makeText(getActivity(),"邀请码已被使用",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"邀请码发送失败",Toast.LENGTH_SHORT).show();
            }
            ((Button)inviteCodeView.findViewById(R.id.register_btn_next)).setClickable(true);//恢复可点击状态
        }
    }

    //验证验证码
    private void verifyCode(){
        EditText indentfyCodeEdt=(EditText)phoneNumView.findViewById(R.id.register_edt_identfycode);
        String code = indentfyCodeEdt.getText().toString().trim();
        if(!code.isEmpty()){
            AVOSCloud.verifySMSCodeInBackground(code, telPhone,
                    new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                PreferenceUtils.getInstance().saveTelPhone(telPhone,getActivity().getApplicationContext());
                                changeView(phoneNumView,setPwdView);
                            } else {
                                Log.e("----->>>>",e.getMessage());
                                e.printStackTrace();
                                Toast.makeText(getActivity(),"验证码不匹配",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(getActivity(),"未填写验证码",Toast.LENGTH_SHORT).show();
        }
    }

    //发送验证码
    private void onSendIdentCode(){
        EditText registerPhone=(EditText)phoneNumView.findViewById(R.id.register_edt_phone);
        telPhone=registerPhone.getText().toString().trim();
        if(telPhone!=null&&(!telPhone.isEmpty())){
            Log.i("手机号码：",telPhone);
            AVOSCloud.requestSMSCodeInBackground(telPhone,"骑阅","注册",1,new RequestMobileCodeCallback(){

                @Override
                public void done(AVException e) {
                    if(e==null){
                        new CountDownTimer(60000,1000){

                            @Override
                            public void onTick(long millisUntilFinished) {

                                indentfyCodeTv.setText(millisUntilFinished/1000+"s后重新发送");
                                indentfyCodeTv.setClickable(false);
                            }

                            @Override
                            public void onFinish() {
                                indentfyCodeTv.setText("发送验证码");
                                indentfyCodeTv.setClickable(true);
                            }
                        }.start();
                    }else{
                        Toast.makeText(getActivity(), "验证码发送失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getActivity(),"未填手机号码",Toast.LENGTH_SHORT).show();
        }

    }

    //阅读用户协议
    private void readAgreement(){
        startActivity(new Intent(getActivity(),UserAgreement.class));
    }

    //设置密码
    private void setPwd(){
        EditText setpwdEdt=(EditText)setPwdView.findViewById(R.id.register_edt_setpwd);
        EditText rePwd=(EditText)setPwdView.findViewById(R.id.register_edt_repwd);
        password=setpwdEdt.getText().toString().trim();
        String repassword=rePwd.getText().toString().trim();
        String result=new ConfirmPassword().confirmPwd(password,repassword);
        encodepwd= SHA1Helper.SHA1(password);
        Log.e("register encodepwd",encodepwd);
        if(result.equals("success")){
            changeView(setPwdView,setUnameView);
        }else{
            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
        }

    }


    //完成注册
    private void onComplete(){
        etUserName=(EditText)setUnameView.findViewById(R.id.register_edt_setusername);
        userName=etUserName.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//图片上传时间戳
        final String key = "icon_" + sdf.format(new Date());//这个key是保存在七牛云的文件名，把这个key传给后台
        if(userName!=null&&!userName.isEmpty()){
            //在这里发送用户名和头像给后台
            new AsyncTask<String,Void,QiNiuTokenResp>(){

                @Override
                protected QiNiuTokenResp doInBackground(String... params) {
                    return OkHttpUtils.getInstance().getToken(params[0],Api.TEMP_UID,TimeStamp.getTimeStamp(),params[1],params[2]);
                }

                @Override
                protected void onPostExecute(QiNiuTokenResp resp) {
                    super.onPostExecute(resp);
                    Log.e("s","七牛tioken="+resp.getQiniu_token());
                    if(resp!=null){
                        UploadOptions uops=new UploadOptions(null,"image/jpeg",false,null,null);
                        App app=(App)getActivity().getApplication();
                        //这里的TOKEN是要事先从服务器获取，目前用测试的Token来替换

                        //file是截图后头像保存在手机的完整路径，key是上传到七牛云后头像的名称
                        app.getUploadManager().put(filePath,key , resp.getQiniu_token(), new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                //res包含hash、key等信息，具体字段取决于上传策略的设置

                                if(info.isOK())
                                {
                                    Log.e("face_url",Api.USERHEAD_LINK+key);
                                    //如果上传成功则提交头像url和用户名，密码，手机号码给后台
                                    new Send2Background().execute(userName,PreferenceUtils.getInstance().getTelPhone(getActivity().getApplicationContext()),encodepwd,
                                            Api.USERHEAD_LINK+key,Api.REGISTER);
                                }
                                else{
                                    Log.e("qiniu", "Upload Fail");
                                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                }
                                Log.e("------------>>>>",info.path+","+info.isOK()+",info="+info.toString()+",key:"+key);
                                Log.e("qiniu", key + ",\r\n " + info +","+response);

                            }

                        },uops);
                    }else{
                        Toast.makeText(getContext(),"获取七牛token失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(key,Api.TEMP_USER_TOKEN,Api.QINIU_TOKEN);

//            try {
//                final AVFile avFile = AVFile.withAbsoluteLocalPath(timeStamp + ".jpg", file.getAbsolutePath());
//                avFile.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(AVException e) {
//                        if(e==null){
//                            new Send2Background().execute(userName,PreferenceUtils.getInstance().getTelPhone(getActivity().getApplicationContext()),password,
//                                avFile.getUrl(),Api.REGISTER);
//                        }else{
//                            Toast.makeText(getActivity(),"上传失败",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }catch(FileNotFoundException e){
//                Toast.makeText(getActivity(),"头像路径不可用",Toast.LENGTH_SHORT).show();
//            }



        }else{
            Toast.makeText(getActivity(),"未填写用户名",Toast.LENGTH_SHORT).show();
        }

    }


//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode== Activity.RESULT_OK){
//            switch (requestCode){
//                case REQUEST_IMAGE:
//                    if(data!=null) {
//                        List<String> path=data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                        fileName=path.get(0);//原图的完整路径（包括文件名）
//                        Toast.makeText(getContext(),fileName,Toast.LENGTH_LONG).show();
//                        Log.i(TAG,path.get(0));
//                    }
//                    break;
//                case CROP:
//
//                    img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                    if(setuserhead_texthint.getVisibility()==View.VISIBLE){
//                        setuserhead_texthint.setVisibility(View.GONE);
//                    }
//                    break;
//            }
//        }
//
//    }

    //发送参数到后台
    class Send2Background extends AsyncTask<String,Void,LoginResponse>{
        @Override
        protected LoginResponse doInBackground(String... params) {

            return OkHttpUtils.getInstance().send2BackGround(params[0],params[1],
                    params[2], params[3],params[4]);
        }

        @Override
        protected void onPostExecute(LoginResponse resp) {
            super.onPostExecute(resp);
            int resultCode=resp.getStatus();
            if(resp!=null){
                if(resultCode==Constants.SUCCESS){
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.putExtra("data",resp.getData());
                    intent.putExtra("timestamp",resp.getTimestamp());
                    startActivity(intent);
//               //连接leacloud im服务器
                    //openClient(resp.getData());
                }else if(resultCode==Constants.EXITED){
                    Toast.makeText(getActivity(),"用户已经存在",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity(),"未知错误",Toast.LENGTH_SHORT).show();
            }
        }

        private void openClient(final UserData data){
            AVImClientManager.getInstance().open(data.getUid()+"",new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if(e==null){
                        Intent intent=new Intent(getActivity(),MainActivity.class);
                        intent.putExtra("data",data);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(),"登录失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    //添加自定义回调
    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
//                path.clear();
//                for (String s : photoList) {
//                    Log.i(TAG, s);
//                    path.add(s);
//                }
                //因为我们当前是单选模式，所以只取第一张图片
                //img.setImageURI(Uri.fromFile(new File(photoList.get(0))));

                try {
                    filePath=photoList.get(0);
                    img.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    if(setuserhead_texthint.getVisibility()==View.VISIBLE){
                        setuserhead_texthint.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(),"头像获取失败",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GildeImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.rideread.test.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 100, 100)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath(FileUtils.root+"pic")          // 图片存放路径
                .build();

    }

    // 授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i(TAG, "拒绝过了");
                Toast.makeText(mContext, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "进行授权");
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            Log.i(TAG, "不需要授权 ");
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "同意授权");
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
            } else {
                Log.i(TAG, "拒绝授权");
            }
        }
    }

}
