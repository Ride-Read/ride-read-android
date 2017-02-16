package com.rideread.rideread;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class FindPassword extends RegisterBaseActivity {

    private TextView indentfyCodeTv;
    private EditText findPhone, indentfyCodeEdt;
    private String telPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd_findpwd);
        indentfyCodeTv =(TextView)findViewById(R.id.register_tv_sendidentfycode);
        findPhone=(EditText)findViewById(R.id.register_edt_phone);
        indentfyCodeEdt =(EditText)findViewById(R.id.register_edt_identfycode);
    }


    public void onNext(View v) {
        String code = indentfyCodeEdt.getText().toString().trim();
        Log.e("----->>>>",code+","+telPhone);
        //startActivity(new Intent(FindPassword.this,RegisterSetPwdActivity.class));//下面的注释已经完成手机注册功能
        if(!code.isEmpty()){
            AVOSCloud.verifySMSCodeInBackground(code, telPhone,
                    new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Intent intent=new Intent(FindPassword.this,ResetPassword.class);
                                intent.putExtra("telPhone",telPhone);
                                startActivity(intent);
                            } else {
                                Log.e("----->>>>",e.getMessage());
                                e.printStackTrace();
                                Toast.makeText(getBaseContext(),"验证码不匹配",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(getBaseContext(),"未填写验证码",Toast.LENGTH_SHORT).show();
        }
    }


    public void onSendIdentCode(View v) {
        telPhone=findPhone.getText().toString().trim();
        if(telPhone!=null&&(!telPhone.isEmpty())){
            new SendCodeTask().execute(telPhone);
        }else{
            Toast.makeText(getBaseContext(),"未填手机号码",Toast.LENGTH_SHORT).show();
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
                        indentfyCodeTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.indetifycode_bg));
                        indentfyCodeTv.setClickable(false);

                    }

                    @Override
                    public void onFinish() {
                        indentfyCodeTv.setText("发送验证码");
                        indentfyCodeTv.setTextColor(Color.WHITE);
                        indentfyCodeTv.setBackgroundResource(R.drawable.login_btn_style_selector);
                        indentfyCodeTv.setClickable(true);
                    }
                }.start();
            } else Toast.makeText(getBaseContext(), "验证码发送失败",Toast.LENGTH_SHORT).show();
        }
    }


}
