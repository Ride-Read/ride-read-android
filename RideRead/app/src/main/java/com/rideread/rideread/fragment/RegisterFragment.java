package com.rideread.rideread.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.rideread.rideread.App;
import com.rideread.rideread.R;
import com.rideread.rideread.UserAgreement;
import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.ConfirmPassword;
import com.rideread.rideread.common.FileUtils;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.PreferenceUtils;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private String tagPhone="phoneNumView",tagInvite="inviteCodeView",tagPwd="setPwdView",tagName="setUnameView";
    private View phoneNumView, inviteCodeView, setPwdView, setUnameView;
    private String inviteCode;
    private TextView indentfyCodeTv;
    private String telPhone,password;
    private final int REQUEST_IMAGE=1;
    private final int CROP=0;
    private final String TYPE="image/*";
    private String fileName,userName;//原图的路径+文件名
    private String TAG="Reg";
    private CircleImageView img;
    private EditText etUserName;
    private File file=null;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.register_fragment_layout,container,false);
        inviteCodeView =mView.findViewById(R.id.register_invite_include_layout);
        initButton(inviteCodeView,R.id.register_btn_next,tagInvite);

        phoneNumView =mView.findViewById(R.id.register_phonenum_include_ly);
        initButton(phoneNumView,R.id.register_btn_next,tagPhone);
        ((TextView)phoneNumView.findViewById(R.id.register_read_agreement_tv)).setOnClickListener(this);

        setPwdView =mView.findViewById(R.id.register_setpwd_include_ly);
        initButton(setPwdView,R.id.register_btn_next,tagPwd);

        setUnameView =mView.findViewById(R.id.register_setuname_include_ly);
        initButton(setUnameView,R.id.register_btn_complete,null);
        img=(CircleImageView)setUnameView.findViewById(R.id.register_civ_userhead);
        img.setOnClickListener(this);
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
                if(tag.equals(tagInvite)){
                    veifyInviteCode();
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

    private void changeView(View hideView,View showView){

        hideView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
    }

    /**
     * 验证邀请码
     */
    private void veifyInviteCode() {
        EditText editText=(EditText)inviteCodeView.findViewById(R.id.register_edt_invitationcode);
        inviteCode=editText.getText().toString().trim();
        if(inviteCode==null){
            Toast.makeText(getActivity(),"未填写邀请码",Toast.LENGTH_SHORT).show();
        }else{
            new InviteCodeAysncTask().execute(inviteCode, Api.CONFIM_INVITECODE);
        }
    }


    /**
     * 验证邀请码的异步线程
     */
    class InviteCodeAysncTask extends AsyncTask<String,String,Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            return OkHttpUtils.getInstance().testInviteCode(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);
            if(status==0){
                changeView(inviteCodeView, phoneNumView);
            }else if(status==1){
                Toast.makeText(getActivity(),"邀请码不存在",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"邀请码已被使用",Toast.LENGTH_SHORT).show();
            }
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        if(userName!=null&&!userName.isEmpty()){
            //在这里发送用户名和头像给后台
            App app=null;
            app.getUploadManager().put(fileName, key, Api.TOKEN, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    //res包含hash、key等信息，具体字段取决于上传策略的设置

                    if(info.isOK())
                    {
                        //如果上传成功则提交头像url和用户名，密码，手机号码给后台
                        new Send2Background().execute(userName,PreferenceUtils.getInstance().getTelPhone(getActivity().getApplicationContext()),password,
                                Api.USERHEAD_LINK+key,Api.SET_USERNAME);
                    }
                    else{
                        Log.e("qiniu", "Upload Fail");
                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    }
                    Log.e("------------>>>>",info.path+","+info.isOK()+",info="+info.toString()+",key:"+key);
                    Log.e("qiniu", key + ",\r\n " + info +","+response);

                }

            },null);

        }else{
            Toast.makeText(getActivity(),"未填写用户名",Toast.LENGTH_SHORT).show();
        }

    }
    //图片相册选择图片或者拍照
    private void setHeadImg(){

        Intent intent=new Intent(getActivity(), MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);

        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);

        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST,null);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    //裁切图片
    public void startPhotoZoomSec(Uri uri){
        if(uri!=null){
            Intent intent=new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, TYPE);
            intent.putExtra("crop", "true");//开启剪切
            intent.putExtra("aspectX", 1);//剪切的宽高比1:1
            intent.putExtra("aspectY", 1);//剪切的宽高比1:1
            intent.putExtra("outputX",100);
            intent.putExtra("outputY",100);
            intent.putExtra("return-data",true);
            intent.putExtra("output",Uri.fromFile(createImageFile()));//裁切后的保存路径
            startActivityForResult(intent,CROP);
        }
    }


    private File createImageFile(){

        try {
            //以时间戳来命名裁切过的图片
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File filePath=new File(FileUtils.root+ "/"+ PreferenceUtils.getInstance().getTelPhone(getActivity().getApplicationContext())+"/userhead/");
            if(filePath.exists()==false){
                filePath.mkdirs();
            }
            if(filePath.exists()==true){
                File[] files=filePath.listFiles();
                for(File file:files){
                    file.delete();
                }
            }
            file=File.createTempFile(timeStamp, ".jpg", filePath);
        }catch (Exception e){
            return null;
        }
        return file;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_IMAGE:
                    if(data!=null) {
                        List<String> path=data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        fileName=path.get(0);//原图的完整路径（包括文件名）
                        Log.i(TAG,path.get(0));
                        startPhotoZoomSec(Uri.fromFile(new File(path.get(0))));
                    }
                    break;
                case CROP:

                    img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    break;
            }
        }

    }

    //发送参数到后台
    class Send2Background extends AsyncTask<String,Void,LoginMessageEntity>{
        @Override
        protected LoginMessageEntity doInBackground(String... params) {

            return OkHttpUtils.getInstance().send2BackGround(params[0],params[1],
                    params[2], params[3],params[4]);
        }

        @Override
        protected void onPostExecute(LoginMessageEntity entity) {
            super.onPostExecute(entity);
            int resultCode=entity.getStatus();
            if(resultCode==0){
//                App app=null;
//                app.finishAll();

            }else{
                Toast.makeText(getActivity(),"设置失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
