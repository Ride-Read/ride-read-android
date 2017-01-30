package com.rideread.rideread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rideread.rideread.common.PreferenceUtils;

/**
 * Created by Jackbing on 2017/1/22.
 * 设置密码
 */

public class RegisterSetPwdActivity extends RegisterBaseActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_setpassword);

    }

    public void onNext(View v){
        EditText setpwdEdt=(EditText)findViewById(R.id.register_edt_setpwd);
        String password=setpwdEdt.getText().toString().trim();
        if(password!=null&&password.isEmpty()){
            //此处还没有对密码是否规则进行判断
            Intent intent=new Intent(this,RegisterUnameActivtiy.class);
            intent.putExtra("password",password);
            startActivity(intent);
        }else{
            Toast.makeText(this,"未填写密码",Toast.LENGTH_SHORT).show();
        }


    }

    public void onBack(View v){
        finish();
    }
}
