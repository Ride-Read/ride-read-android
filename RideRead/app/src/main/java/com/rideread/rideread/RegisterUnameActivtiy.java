package com.rideread.rideread;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class RegisterUnameActivtiy extends RegisterBaseActivity {

    private final int CROP=1;//裁剪图片
    private final int TOCROP=2;//拍照
    private final int PICK=3;//选择图片
    private final String TYPE="image/*";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_set_username);
    }

    //完成注册
    public void onComplete(View v){

    }

    public void onBack(View v){
        finish();
    }

    //图片相册选择图片
    public void setHeadImg(View v){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,TYPE);
        intent.putExtra("crop",true);
        startActivityForResult(intent,PICK);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){

                case PICK:

                    break;
                default:

                    break;
            }
        }

    }


}
