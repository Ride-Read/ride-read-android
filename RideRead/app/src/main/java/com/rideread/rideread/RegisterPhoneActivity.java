package com.rideread.rideread;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.PreferenceUtils;


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
        registerPhone=(EditText)findViewById(R.id.register__edt_phone);
        indentfyCodeEdt =(EditText)findViewById(R.id.register__edt_identfycode);

    }

    //下一步
    public void onNext(View v){
        String code = indentfyCodeEdt.getText().toString().trim();
        if(!code.isEmpty()){
            new TestIdentfyCode().execute(code,Api.TEST_IDENTFY_CODE);
        }else{
            Toast.makeText(getBaseContext(),"未填写验证码",Toast.LENGTH_SHORT).show();
        }

    }

    //发送验证码
    public void onSendIdentCode(View v){

        telPhone=registerPhone.getText().toString().trim();
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
            //ttl后面的值是短信有效时间(分钟)
            String json = "{\"mobilePhoneNumber\":\"{0}\",\"ttl\":30,\"name\":\"注册\"}";
            json = json.replace("{0}", params[0]);
            return OkHttpUtils.getInstance().postJson("https://api.leancloud.cn/1.1/requestSmsCode", json);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                new CountDownTimer(30000,1000){

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

    class TestIdentfyCode extends AsyncTask<String,String,Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            //ttl后面的值是短信有效时间(分钟)
            String json = "{\"mobilePhoneNumber\":\"{0}\"}";
            json = json.replace("{0}", params[0]);
            return OkHttpUtils.getInstance().postJson("https://api.leancloud.cn/1.1/verifySmsCode/" + params[1], json);

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result){

                PreferenceUtils.getInstance().saveTelPhone(telPhone,getApplicationContext());
                startActivity(new Intent(RegisterPhoneActivity.this,RegisterSetPwdActivity.class));
            }else{
                Toast.makeText(getBaseContext(),"验证码不匹配",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void onBack(View v){
        finish();
    }

    public void readAgreement(View v){
        startActivity(new Intent(this,UserAgreement.class));
    }
}
