package com.rideread.rideread;

import android.content.Intent;
import android.graphics.Color;
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
import com.avos.avoscloud.RequestMobileCodeCallback;


/**
 * Created by Jackbing on 2017/1/22.
 * 填写手机号码
 */

public class RegisterPhoneActivity extends RegisterBaseActivity {

    private TextView indentfyCodeTv;
    private EditText registerPhone, indentfyCodeEdt;
    private String telPhone;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phonenum);
        indentfyCodeTv =(TextView)findViewById(R.id.register_tv_sendidentfycode);
        registerPhone=(EditText)findViewById(R.id.register_edt_phone);
        indentfyCodeEdt =(EditText)findViewById(R.id.register_edt_identfycode);

    }

    //下一步
    public void onNext(View v){
        String code = indentfyCodeEdt.getText().toString().trim();
        Log.e("----->>>>",code+","+telPhone);
        //startActivity(new Intent(RegisterPhoneActivity.this,RegisterSetPwdActivity.class));//下面的注释已经完成手机注册功能
        if(!code.isEmpty()){
            AVOSCloud.verifySMSCodeInBackground(code, telPhone,
                    new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {

                                startActivity(new Intent(RegisterPhoneActivity.this,RegisterSetPwdActivity.class));
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

    //发送验证码
    public void onSendIdentCode(View v){

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
                    }else{
                        Toast.makeText(getBaseContext(), "验证码发送失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getBaseContext(),"未填手机号码",Toast.LENGTH_SHORT).show();
        }

    }


    public void onBack(View v){
        finish();
    }

    public void readAgreement(View v){
        startActivity(new Intent(this,UserAgreement.class));
    }
}
