package com.rideread.rideread.common;

import android.util.Base64;

import com.avos.avoscloud.signature.Base64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jackbing on 2017/2/27.
 */

public class MD5Utils {

    public static String Md5(String password){

        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            return Base64Encoder.encode(md5.digest(password.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
