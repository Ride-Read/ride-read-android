package com.rideread.rideread.module.auth.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.result.UserInfo;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;
import com.rideread.rideread.module.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class LoginFragment extends BaseFragment {
    @BindView(R.id.edt_login_account) EditText mEdtLoginAccount;
    @BindView(R.id.edt_login_pwd) EditText mEdtLoginPwd;
    @BindView(R.id.include_login_view) LinearLayout mIncludeLoginView;
    @BindView(R.id.include_code_view) LinearLayout mIncludeCodeView;
    @BindView(R.id.include_set_pwd_view) LinearLayout mIncludeSetPwdView;
    @BindView(R.id.edt_phone) EditText mEdtPhone;
    @BindView(R.id.edt_code) EditText mEdtCode;
    @BindView(R.id.tv_agree_protocol) TextView mTvAgreeProtocol;
    @BindView(R.id.edt_password) EditText mEdtPassword;
    @BindView(R.id.edt_password_confirm) EditText mEdtPasswordConfirm;


    private String mLoginAccount;
    private String mLoginPwd;
    private String mPhoneNumber;
    private String mVcode;
    private String mPwd;
    private String mPwdConfirm;
    private double longtitude;//latitude": 23.136552
    private double laititude;//"longitude": 113.314086

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
    }

    private void showSetPwdView() {
        mIncludeLoginView.setVisibility(View.GONE);
        mIncludeSetPwdView.setVisibility(View.VISIBLE);
        mIncludeCodeView.setVisibility(View.GONE);
    }

    private void showForgetPwdView() {
        mIncludeLoginView.setVisibility(View.GONE);
        mIncludeSetPwdView.setVisibility(View.GONE);
        mIncludeCodeView.setVisibility(View.VISIBLE);
        mTvAgreeProtocol.setVisibility(View.GONE);
    }

    @OnClick({R.id.btn_login_next, R.id.btn_code_next, R.id.btn_pwd_next, R.id.tv_forget_pwd, R.id.tv_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_next:
                login();
                break;
            case R.id.btn_code_next:
                verifyCode();
                break;
            case R.id.btn_pwd_next:
                setPwdAndLogin();
                break;
            case R.id.tv_send_code:
                getVerifyCode();
                break;
            case R.id.tv_forget_pwd:
                showForgetPwdView();
                break;
        }
    }

    private void login() {
        String account = mEdtLoginAccount.getText().toString().trim();
        String pwd = mEdtLoginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.show("手机号/骑阅号不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show("密码不能为空");
            return;
        }
        ApiUtils.login(account, pwd, new BaseCallback<BaseModel<UserInfo>>() {
            @Override
            protected void onSuccess(BaseModel<UserInfo> model) throws Exception {
                Logger.d("http", model.toString());
                getBaseActivity().gotoActivity(MainActivity.class, true);
            }
        });

    }

    public void getVerifyCode() {

    }

    private void verifyCode() {
        if (true) {
            showSetPwdView();
        }
    }


    private void setPwdAndLogin() {
        getBaseActivity().gotoActivity(MainActivity.class, true);
    }


    //
    //    @Override
    //    public void onClick(View v) {
    //        switch (v.getId()){
    //            case R.id.register_btn_next:
    //                String tag=(String)v.getTag();
    //                if(tag.equals(tagFindPwd)){
    //                    gotoFindPwd();
    //                }else if(tag.equals(tagSetPwd)){
    //                    onComplete();
    //                }
    //                break;
    //            case R.id.login_tv_forgetpwd:
    //                changeView(loginView,findPwdView);
    //                break;
    //            case R.id.login_btn:
    //                login();
    //                break;
    //            case R.id.register_tv_sendidentfycode:
    //                if(indentfyCodeTv==null){
    //                    indentfyCodeTv=(TextView)v;
    //                }
    //                onSendIdentCode();
    //                break;
    //
    //
    //        }
    //
    //    }
    //
    //    @Override
    //    public void onLocationChanged(AMapLocation aMapLocation) {
    //
    //        if(aMapLocation.getErrorCode()==0){
    //            longtitude=aMapLocation.getLongitude();
    //            laititude=aMapLocation.getLatitude();
    //        }else{
    //            Toast.makeText(getContext(),"定位失败!",Toast.LENGTH_SHORT).show();
    //        }

    //        if(username==null||password==null){
    //            Toast.makeText(getActivity(),"未填写用户名或密码",Toast.LENGTH_SHORT).show();
    //        }else if(!hasNetWork()){
    //            Toast.makeText(getActivity(),"未连接到网络",Toast.LENGTH_SHORT).show();
    //        }else{
    //            accountEdt.setEnabled(false);
    //            passwordEdt.setEnabled(false);
    //
    ////            if(encodePwd==null){
    ////                Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
    ////                return;
    ////            }
    //
    //            new LoginAsyncTask().execute(username,password, Api.USER_LOGIN);
    //        }
    //    }
    //
    //    //登录异步线程
    //    class LoginAsyncTask extends AsyncTask<String,String,LoginResponse>
    //    {
    //        @Override
    //        protected LoginResponse doInBackground(String... params) {
    //            return  OkHttpUtils.getInstance().userLogin(params[0],params[1],params[2],longtitude,laititude);
    //        }
    //
    //        @Override
    //        protected void onPostExecute(LoginResponse resp) {
    //            super.onPostExecute(resp);
    //            if(resp!=null){
    //                int resultCode=resp.getStatus();
    //                if(resultCode== Constants.SUCCESS){
    //
    //                    Intent intent=new Intent(getActivity(),MainActivity.class);
    //                    UserData data=resp.getData();
    //                    intent.putExtra("data",data);
    //                    intent.putExtra("timestamp",resp.getTimestamp());
    //                    Log.e("userdata","data="+data.toString());
    //                    startActivity(intent);
    //                    //连接leacloud im服务器
    //                    //openClient(resp.getData());
    //                }else if(resultCode== Constants.PASSWORD_ERROR) {
    //                    Toast.makeText(getActivity(),"密码错误",Toast.LENGTH_SHORT).show();
    //                }else if(resultCode==Constants.NO_EXITS){
    //                    Toast.makeText(getActivity(),"用户不存在",Toast.LENGTH_SHORT).show();
    //                }else{
    //                    Toast.makeText(getActivity(),"登录失败",Toast.LENGTH_SHORT).show();
    //                }
    //
    //            }else {
    //                Toast.makeText(getActivity(),"未知错误",Toast.LENGTH_SHORT).show();
    //            }
    //            accountEdt.setEnabled(true);
    //            passwordEdt.setEnabled(true);
    //
    //
    ////            Intent intent=new Intent(getActivity(),MainActivity.class);
    ////            intent.putExtra("data",new UserData());
    ////            startActivity(intent);
    //
    //        }
    //    }
    //
    //
    //    private void openClient(final UserData data){
    //        AVImClientManager.getInstance().open(data.getUid()+"",new AVIMClientCallback() {
    //            @Override
    //            public void done(AVIMClient avimClient, AVIMException e) {
    //                if(e==null){
    //                    accountEdt.setEnabled(true);
    //                    passwordEdt.setEnabled(true);
    //                    Intent intent=new Intent(getActivity(),MainActivity.class);
    //                    intent.putExtra("data",data);
    //                    startActivity(intent);
    //                }else{
    //                    Toast.makeText(getActivity(),"登录失败",Toast.LENGTH_SHORT).show();
    //                }
    //            }
    //        });
    //
    //    }
    //
    //    //跳转到找回密码
    //    private void gotoFindPwd(){
    //        EditText indentfyCodeEdt=(EditText)findPwdView.findViewById(R.id.register_edt_identfycode);
    //        String code = indentfyCodeEdt.getText().toString().trim();
    //        Log.e("----->>>>",code+","+telPhone);
    //        //startActivity(new Intent(FindPassword.this,RegisterSetPwdActivity.class));//下面的注释已经完成手机注册功能
    //        if(!code.isEmpty()){
    //            AVOSCloud.verifySMSCodeInBackground(code, telPhone,
    //                    new AVMobilePhoneVerifyCallback() {
    //                        @Override
    //                        public void done(AVException e) {
    //                            if (e == null) {
    ////                                Intent intent=new Intent(FindPassword.this,ResetPassword.class);
    ////                                intent.putExtra("telPhone",telPhone);
    ////                                startActivity(intent);
    //                                changeView(findPwdView,reSetPwdView);
    //                            } else {
    //                                Log.e("----->>>>",e.getMessage());
    //                                e.printStackTrace();
    //                                Toast.makeText(getActivity(),"验证码不匹配",Toast.LENGTH_SHORT).show();
    //                            }
    //                        }
    //                    });
    //        }else{
    //            Toast.makeText(getActivity(),"未填写验证码",Toast.LENGTH_SHORT).show();
    //        }
    //    }
    //
    //    //发送验证码
    //    private void onSendIdentCode() {
    //
    //        EditText findPhone=(EditText)findPwdView.findViewById(R.id.register_edt_phone);
    //        telPhone=findPhone.getText().toString().trim();
    //        if(telPhone!=null&&(!telPhone.isEmpty())){
    //            new SendCodeTask().execute(telPhone);
    //        }else{
    //            Toast.makeText(getActivity(),"未填手机号码",Toast.LENGTH_SHORT).show();
    //        }
    //    }
    //
    //    /**
    //     * 发送短信验证码
    //     * params : mobilePhoneNumber
    //     */
    //    private class SendCodeTask extends AsyncTask<String, Void, Boolean> {
    //        @Override
    //        protected Boolean doInBackground(String... params) {
    //            try{
    //
    //               AVOSCloud.requestSMSCode(params[0], "骑阅", "找回密码", 1);//有效时间1分钟
    //                return true;
    //            }catch (AVException e){
    //                e.printStackTrace();
    //                Log.e("-------.........",e.getMessage());
    //                return false;
    //            }
    //        }
    //
    //        @Override
    //        protected void onPostExecute(Boolean result) {
    //            super.onPostExecute(result);
    //            if (result) {
    //                new CountDownTimer(60000,1000){
    //
    //                    @Override
    //                    public void onTick(long millisUntilFinished) {
    //
    //                        indentfyCodeTv.setText(millisUntilFinished/1000+"s后重新发送");
    //                        indentfyCodeTv.setClickable(false);
    //                    }
    //
    //                    @Override
    //                    public void onFinish() {
    //                        indentfyCodeTv.setText("发送验证码");
    //                        indentfyCodeTv.setClickable(true);
    //                    }
    //                }.start();
    //            } else Toast.makeText(getActivity(), "验证码发送失败",Toast.LENGTH_SHORT).show();
    //        }
    //    }
    //
    //
    //    //完成重置密码
    //    private void onComplete(){
    //        EditText setpwdEdt=(EditText)reSetPwdView.findViewById(R.id.register_edt_setpwd);
    //        EditText rePwd=(EditText)reSetPwdView.findViewById(R.id.register_edt_repwd);
    //        String password=setpwdEdt.getText().toString().trim();
    //        String repassword=rePwd.getText().toString().trim();
    //        String result=new ConfirmPassword().confirmPwd(password,repassword);//验证密码是否符合规则
    //        if(result.equals("success")){
    //            new resetPwdAsyntask().execute(password,telPhone, Api.RESET_PWD);
    //        }else{
    //            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
    //        }
    //
    //    }
    //
    //    /**
    //     * 重置密码
    //     */
    //    class resetPwdAsyntask extends AsyncTask<String,Void,LoginMessageEntity>{
    //        @Override
    //        protected LoginMessageEntity doInBackground(String... params) {
    //            return OkHttpUtils.getInstance().resetPassword(params[0],params[1],params[2]);
    //        }
    //
    //        @Override
    //        protected void onPostExecute(LoginMessageEntity loginMessageEntity) {
    //            super.onPostExecute(loginMessageEntity);
    //            int resultCode=loginMessageEntity.getStatus();
    //            if(resultCode==0){
    //               changeView(reSetPwdView,loginView);
    //            }else{
    //                Toast.makeText(getActivity().getApplicationContext(),"重置密码失败",Toast.LENGTH_SHORT).show();
    //            }
    //        }
    //    }
    //
}
