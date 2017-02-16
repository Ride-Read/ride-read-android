package com.rideread.rideread.common;

import android.widget.Toast;

/**
 * Created by Jackbing on 2017/2/16.
 * 验证密码是否符合规则
 */

public class ConfirmPassword {

    public String confirmPwd(String password,String repassword){

        if(password!=null&&!password.isEmpty()){
            if(repassword!=null&&!repassword.isEmpty()){
                if(password.equals(repassword)){
                    if(password.length()>6){
                        return "success";
                    }else{
                        return "密码长度大于6个字符";
                    }
                }else{
                    return "两次密码不一致";
                }
            }else{
                return "重复密码不能为空";
            }
        }else{
            return "密码不能为空";
        }

    }
}
