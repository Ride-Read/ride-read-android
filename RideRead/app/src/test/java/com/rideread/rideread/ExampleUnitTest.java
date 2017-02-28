package com.rideread.rideread;

import com.rideread.rideread.bean.LoginResponse;
import com.rideread.rideread.bean.UserData;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.MD5Utils;
import com.rideread.rideread.common.OkHttpUtils;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }



    @Test
    public void testLogin(){

       LoginResponse resp= OkHttpUtils.getInstance().userLogin("15622705224", "343er",Api.USER_LOGIN);

        if(resp!=null){

            System.out.println("status="+resp.getStatus());
            UserData data=resp.getData();
            System.out.println(data.toString());
        }else{
            System.out.println("resp is null");
        }

    }


}