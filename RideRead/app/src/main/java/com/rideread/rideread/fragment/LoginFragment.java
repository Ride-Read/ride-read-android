package com.rideread.rideread.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.rideread.rideread.activity.MainActivity;
import com.rideread.rideread.R;
import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.bean.LoginResponse;
import com.rideread.rideread.bean.UserData;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.ConfirmPassword;
import com.rideread.rideread.common.Constants;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.SHA1Helper;
import com.rideread.rideread.im.AVImClientManager;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class LoginFragment  extends Fragment implements View.OnClickListener{

    private View loginView,findPwdView,reSetPwdView;
    private TextView indentfyCodeTv;
    private String tagLogin="loginView",tagFindPwd="findPwdView",tagSetPwd="reSetPwdView";
    private String telPhone;
    private EditText accountEdt,passwordEdt;
    private String username,encodePwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.login_fragment_layout,container,false);
        loginView=mView.findViewById(R.id.login_include_layout);
        initListener(loginView.findViewById(R.id.login_btn),tagLogin);
        initListener(loginView.findViewById(R.id.login_tv_forgetpwd),null);
        findPwdView=mView.findViewById(R.id.findpwd_include_layout);
        initListener(findPwdView.findViewById(R.id.register_btn_next),tagFindPwd);
        initListener(findPwdView.findViewById(R.id.register_tv_sendidentfycode),null);
        reSetPwdView=mView.findViewById(R.id.resetpwd_include_layout);
        Button reSetPwdBtn=(Button)reSetPwdView.findViewById(R.id.register_btn_next);
        reSetPwdBtn.setText(getString(R.string.register_text_complete));
        TextView setPwdTitle=(TextView)reSetPwdView.findViewById(R.id.setpwd_title_tv);
        setPwdTitle.setText(getString(R.string.forgetpwd_text_resetpwd));
        initListener(reSetPwdBtn,tagSetPwd);
        return mView;
    }

    private void changeView(View hideView,View showView){

        hideView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
    }

    private void initListener(View v, String tag){

        v.setOnClickListener(this);
        v.setTag(tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn_next:
                String tag=(String)v.getTag();
                if(tag.equals(tagFindPwd)){
                    gotoFindPwd();
                }else if(tag.equals(tagSetPwd)){
                    onComplete();
                }
                break;
            case R.id.login_tv_forgetpwd:
                changeView(loginView,findPwdView);
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.register_tv_sendidentfycode:
                if(indentfyCodeTv==null){
                    indentfyCodeTv=(TextView)v;
                }
                onSendIdentCode();
                break;


        }

    }

    private void login() {
        accountEdt=(EditText)loginView.findViewById(R.id.login_edt_account);
        passwordEdt=(EditText)loginView.findViewById(R.id.login_edt_password);
        username=accountEdt.getText().toString().trim();
        String password=passwordEdt.getText().toString().trim();
        if(username==null||password==null){
            Toast.makeText(getActivity(),"未填写用户名或密码",Toast.LENGTH_SHORT).show();
        }else if(!hasNetWork()){
            Toast.makeText(getActivity(),"未连接到网络",Toast.LENGTH_SHORT).show();
        }else{
            accountEdt.setEnabled(false);
            passwordEdt.setEnabled(false);
            encodePwd= SHA1Helper.SHA1(password);//加密
            Log.e("password",encodePwd);
            if(encodePwd==null){
                Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
                return;
            }

            new LoginAsyncTask().execute(username,encodePwd, Api.USER_LOGIN);
        }

    }

    //登录异步线程
    class LoginAsyncTask extends AsyncTask<String,String,LoginResponse>
    {
        @Override
        protected LoginResponse doInBackground(String... params) {
            return  OkHttpUtils.getInstance().userLogin(params[0],params[1],params[2]);
        }

        @Override
        protected void onPostExecute(LoginResponse resp) {
            super.onPostExecute(resp);
            if(resp!=null){
                int resultCode=resp.getStatus();
                if(resultCode== Constants.SUCCESS){

                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    UserData data=resp.getData();
                    intent.putExtra("data",data);
                    intent.putExtra("timestamp",resp.getTimestamp());
                    Log.e("userdata","data="+data.toString());
                    startActivity(intent);
                    //连接leacloud im服务器
                    //openClient(resp.getData());
                }else if(resultCode== Constants.PASSWORD_ERROR) {
                    Toast.makeText(getActivity(),"密码错误",Toast.LENGTH_SHORT).show();
                }else if(resultCode==Constants.NO_EXITS){
                    Toast.makeText(getActivity(),"用户不存在",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"登录失败",Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getActivity(),"未知错误",Toast.LENGTH_SHORT).show();
            }
            accountEdt.setEnabled(true);
            passwordEdt.setEnabled(true);


//            Intent intent=new Intent(getActivity(),MainActivity.class);
//            intent.putExtra("data",new UserData());
//            startActivity(intent);

        }
    }


    private void openClient(final UserData data){
        AVImClientManager.getInstance().open(data.getUid()+"",new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if(e==null){
                    accountEdt.setEnabled(true);
                    passwordEdt.setEnabled(true);
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.putExtra("data",data);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //跳转到找回密码
    private void gotoFindPwd(){
        EditText indentfyCodeEdt=(EditText)findPwdView.findViewById(R.id.register_edt_identfycode);
        String code = indentfyCodeEdt.getText().toString().trim();
        Log.e("----->>>>",code+","+telPhone);
        //startActivity(new Intent(FindPassword.this,RegisterSetPwdActivity.class));//下面的注释已经完成手机注册功能
        if(!code.isEmpty()){
            AVOSCloud.verifySMSCodeInBackground(code, telPhone,
                    new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
//                                Intent intent=new Intent(FindPassword.this,ResetPassword.class);
//                                intent.putExtra("telPhone",telPhone);
//                                startActivity(intent);
                                changeView(findPwdView,reSetPwdView);
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
    private void onSendIdentCode() {

        EditText findPhone=(EditText)findPwdView.findViewById(R.id.register_edt_phone);
        telPhone=findPhone.getText().toString().trim();
        if(telPhone!=null&&(!telPhone.isEmpty())){
            new SendCodeTask().execute(telPhone);
        }else{
            Toast.makeText(getActivity(),"未填手机号码",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送短信验证码
     * params : mobilePhoneNumber
     */
    private class SendCodeTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try{

               AVOSCloud.requestSMSCode(params[0], "骑阅", "找回密码", 1);//有效时间1分钟
                return true;
            }catch (AVException e){
                e.printStackTrace();
                Log.e("-------.........",e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
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
            } else Toast.makeText(getActivity(), "验证码发送失败",Toast.LENGTH_SHORT).show();
        }
    }


    //完成重置密码
    private void onComplete(){
        EditText setpwdEdt=(EditText)reSetPwdView.findViewById(R.id.register_edt_setpwd);
        EditText rePwd=(EditText)reSetPwdView.findViewById(R.id.register_edt_repwd);
        String password=setpwdEdt.getText().toString().trim();
        String repassword=rePwd.getText().toString().trim();
        String result=new ConfirmPassword().confirmPwd(password,repassword);//验证密码是否符合规则
        if(result.equals("success")){
            new resetPwdAsyntask().execute(password,telPhone, Api.RESET_PWD);
        }else{
            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 重置密码
     */
    class resetPwdAsyntask extends AsyncTask<String,Void,LoginMessageEntity>{
        @Override
        protected LoginMessageEntity doInBackground(String... params) {
            return OkHttpUtils.getInstance().resetPassword(params[0],params[1],params[2]);
        }

        @Override
        protected void onPostExecute(LoginMessageEntity loginMessageEntity) {
            super.onPostExecute(loginMessageEntity);
            int resultCode=loginMessageEntity.getStatus();
            if(resultCode==0){
               changeView(reSetPwdView,loginView);
            }else{
                Toast.makeText(getActivity().getApplicationContext(),"重置密码失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 判断网络状态
     * @return
     */
    public boolean hasNetWork(){

        ConnectivityManager cm=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo().isAvailable()){
            return true;
        }
        return false;
    }
}
