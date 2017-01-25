package com.rideread.rideread;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rideread.rideread.common.FileUtils;
import com.rideread.rideread.common.PreferenceUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


/**
 * Created by Jackbing on 2017/1/22.
 */

public class RegisterUnameActivtiy extends RegisterBaseActivity {

    private final int REQUEST_IMAGE=1;
    private final int CROP=0;
    private final String TYPE="image/*";
    private String fileName;//原图的路径+文件名
    private String TAG="Reg";
    private CircleImageView img;
    private File file=null;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_set_username);
        img=(CircleImageView)findViewById(R.id.register_civ_userhead);
    }

    //完成注册
    public void onComplete(View v){

    }

    public void onBack(View v){
        finish();
    }

    //图片相册选择图片或者拍照
    public void setHeadImg(View v){

       Intent intent=new Intent(getBaseContext(), MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);

        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);

        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST,null);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    //裁切图片
    public void startPhotoZoomSec(Uri uri){
        if(uri!=null){
            Intent intent=new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, TYPE);
            intent.putExtra("crop", "true");//开启剪切
            intent.putExtra("aspectX", 1);//剪切的宽高比1:1
            intent.putExtra("aspectY", 1);//剪切的宽高比1:1
            intent.putExtra("outputX",100);
            intent.putExtra("outputY",100);
            intent.putExtra("return-data",true);
            intent.putExtra("output",Uri.fromFile(createImageFile()));//裁切后的保存路径
            startActivityForResult(intent,CROP);
        }
    }


    private File createImageFile(){

        try {
            //以时间戳来命名裁切过的图片
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File filePath=new File(FileUtils.root+ "/"+PreferenceUtils.getInstance().getTelPhone(getApplicationContext())+"/userhead/");
            if(filePath.exists()==false){
                filePath.mkdirs();
            }
            if(filePath.exists()==true){
                File[] files=filePath.listFiles();
                for(File file:files){
                    file.delete();
                }
            }
            file=File.createTempFile(timeStamp, ".jpg", filePath);
        }catch (Exception e){
            return null;
        }
        return file;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_IMAGE:
                   if(data!=null) {
                       List<String> path=data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                       fileName=path.get(0);//原图的完整路径（包括文件名）
                       Log.i(TAG,path.get(0));
                       startPhotoZoomSec(Uri.fromFile(new File(path.get(0))));
                   }
                    break;
                case CROP:

                    img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    break;
            }
        }

    }


}
