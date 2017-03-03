package com.rideread.rideread.common;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.rideread.rideread.activity.BaseActivity;
import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/2/10.
 */

public class ImageShower extends BaseActivity {
    private String tag="ImageShower";
    private Intent intent;
    private String content;
    private ImageView bigImg;
    private Bitmap bm=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.imageshower);
        initUI();
        intent = getIntent();
        content = intent.getStringExtra("content");
        final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        //Bitmap bm = BitmapFactory.decodeFile(content);
        bm = BitmapFactory.decodeResource(getResources(),R.mipmap.test_img);
        bigImg.setImageBitmap(bm);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.dismiss();
            }
        }, 1000 * 1);

    }

    void initUI(){
        bigImg = (ImageView)findViewById(R.id.big_img);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(bm!=null){
            bm.recycle();
        }
        finish();
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bm!=null){
            bm.recycle();
        }
    }
}
