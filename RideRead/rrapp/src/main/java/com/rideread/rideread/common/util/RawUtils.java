package com.rideread.rideread.common.util;

import android.content.Context;

import com.rideread.rideread.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yayij on 2017/6/3.
 */

public class RawUtils {

    //从raw对资源文件进行数据的读取
    public static byte[] getBytes(Context context,int idres) {
        InputStream in = context.getResources().openRawResource(idres);
        try {

            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            return buffer;
        } catch (Exception ex) {
            return null;
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
