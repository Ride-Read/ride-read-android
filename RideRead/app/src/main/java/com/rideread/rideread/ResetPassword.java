package com.rideread.rideread;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.OkHttpUtils;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class ResetPassword extends RegisterBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.forgetpwd_resetpwd);

    }

    public void onComplete(View v){
        EditText resetEdt=(EditText)findViewById(R.id.forgetpwd_edt_resetpwd);
        String telphone=getIntent().getStringExtra("telPhone");
        String resetPassword=resetEdt.getText().toString().trim();
        if(resetPassword!=null&&!resetPassword.isEmpty()){
                new resetPwdAsyntask().execute(resetPassword,telphone, Api.RESET_PWD);
        }else{
            Toast.makeText(getApplicationContext(),"未填写密码",Toast.LENGTH_SHORT).show();
        }

    }

    class resetPwdAsyntask extends AsyncTask<String,Void,LoginMessageEntity>{
        @Override
        protected LoginMessageEntity doInBackground(String... params) {
            return OkHttpUtils.getInstance().resetPassword(params[0],params[1],params[2]);
        }

        @Override
        protected void onPostExecute(LoginMessageEntity loginMessageEntity) {
            super.onPostExecute(loginMessageEntity);
            int resultCode=loginMessageEntity.getResultCode();
            String msg=loginMessageEntity.getMsg();
            if(resultCode==1){
                App app=(App)getApplication();
                app.finishAll();
            }else{
                Toast.makeText(ResetPassword.this.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBack(View b){
        finish();
    }
}
