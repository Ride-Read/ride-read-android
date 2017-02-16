package com.rideread.rideread;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.common.OkHttpUtils;

/**
 * Created by Jackbing on 2017/1/21.
 * 填写邀请码界面
 */

public class RegisterActivity extends RegisterBaseActivity {

    private String inviteCode;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

    }


    public void onBack(View v){
        finish();
    }

    public void onNext(View v){
        EditText editText=(EditText)findViewById(R.id.register_edt_invitationcode);
        inviteCode=editText.getText().toString().trim();
        startActivity(new Intent(RegisterActivity.this,RegisterPhoneActivity.class));
//        if(inviteCode==null){
//            Toast.makeText(getBaseContext(),"未填写邀请码",Toast.LENGTH_SHORT).show();
//        }else{
//            new InviteCodeAysncTask().execute(inviteCode, Api.TEST_INVITE_CODE);
//        }


    }

    class InviteCodeAysncTask extends AsyncTask<String,String,LoginMessageEntity>{
        @Override
        protected LoginMessageEntity doInBackground(String... params) {
            return OkHttpUtils.getInstance().testInviteCode(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(LoginMessageEntity loginMessageEntity) {
            super.onPostExecute(loginMessageEntity);
            if(loginMessageEntity!=null){
                int resultCode=loginMessageEntity.getStatus();
                String msg=loginMessageEntity.getMsg();
                if(resultCode==1){
                    startActivity(new Intent(RegisterActivity.this,RegisterPhoneActivity.class));
                }else {
                    Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getBaseContext(),"未知错误",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
