package com.rideread.rideread;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.db.DBCopyUtil;
import com.rideread.rideread.common.FileUtils;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.PreferenceUtils;
import com.rideread.rideread.fragment.DateFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class MineEditMessageActivity extends BaseActivity implements View.OnClickListener{

    private final int REQUEST_IMAGE=1;
    private final int CROP=0;
    private final String TYPE="image/*";
    private String fileName;
    private CircleImageView civ;

    public final int REQUEST_DISTRICT=2;
    private TextView edtDistrict,editmsgSex;

    private File file=null;

    private TextView birthDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_editmessage_layout);
        initView();
    }

    private void initView() {
        birthDate=(TextView)findViewById(R.id.mine_editmsg_tv_birthdate);
        ImageView save=(ImageView)findViewById(R.id.right_search_icon);
        civ=(CircleImageView)findViewById(R.id.mine_editmsg_iv_head);
        edtDistrict=(TextView)findViewById(R.id.mine_editmsg_et_locale);
        ImageView back=(ImageView)findViewById(R.id.left_setting_icon);
        editmsgSex=(TextView)findViewById(R.id.mine_editmsg_et_sex);


        save.setOnClickListener(this);
        civ.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    public void onSelectSex(View v){
        final AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window=alertDialog.getWindow();
        View mView= LayoutInflater.from(this).inflate(R.layout.mine_editmsg_selectsex_layout,null);
        RadioGroup group=(RadioGroup) mView.findViewById(R.id.mine_editmsg_sex_radiogroup);
        final RadioButton maleBtn=(RadioButton)mView.findViewById(R.id.mine_editmsg_sex_radiomale);
        final RadioButton femaleBtn=(RadioButton)mView.findViewById(R.id.mine_editmsg_sex_radiofemale);
        String defaultSex=editmsgSex.getText().toString().trim();
        if(defaultSex!=null&&!defaultSex.isEmpty()){
            if(defaultSex.equals("男")){
                maleBtn.setChecked(true);
            }else{
                femaleBtn.setChecked(true);
            }
        }
        window.setContentView(mView);




        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleBtn.setChecked(true);
                editmsgSex.setText("男");
                alertDialog.cancel();
            }
        });
        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleBtn.setChecked(true);
                editmsgSex.setText("女");
                alertDialog.cancel();
            }
        });

    }

    //地区选择
    public void onSelectDitrict(View v){
        synchronized (this){
            DBCopyUtil.copyDataBaseFromAssets(this, "region.db");
        }
        Intent intent=new Intent(this,MineEdtMsgDistrictActivity.class);
        startActivityForResult(intent,REQUEST_DISTRICT);
    }



    //生日日期选择
    public void onPickData(View v){
        DateFragment dateFragment=new DateFragment();
        dateFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void setBirthDate(int year,int month,int dayofmonth){
        birthDate.setText(year+"-"+month+"-"+dayofmonth);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_search_icon:
                String name=((EditText)findViewById(R.id.mine_editmsg_et_name)).getText().toString().trim();
                String sex=((TextView)findViewById(R.id.mine_editmsg_et_sex)).getText().toString().trim();
                String telphone=((EditText)findViewById(R.id.mine_editmsg_et_phone)).getText().toString().trim();
                String signture=((EditText)findViewById(R.id.mine_editmsg_et_signture)).getText().toString().trim();
                String date=birthDate.getText().toString().trim();
                String locale=((TextView)findViewById(R.id.mine_editmsg_et_locale)).getText().toString().trim();
                String school =((EditText)findViewById(R.id.mine_editmsg_et_school)).getText().toString().trim();
                String job=((EditText)findViewById(R.id.mine_editmsg_et_job)).getText().toString().trim();
                String hometown=((EditText)findViewById(R.id.mine_editmsg_et_hometown)).getText().toString().trim();

                new Send2BackGround().execute(Api.EDIT_MESSAGE,fileName,telphone,date
                ,sex,name,signture,locale,school,job,hometown);
                break;
            case R.id.mine_editmsg_iv_head:
                setHeadImg();
                break;
            case R.id.left_setting_icon:
                onBackPressed();
                break;
        }

    }

    //图片相册选择图片或者拍照
    public void setHeadImg(){

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
            File filePath=new File(FileUtils.root+ "/"+ PreferenceUtils.getInstance().getTelPhone(getApplicationContext())+"/userhead/");
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
                        startPhotoZoomSec(Uri.fromFile(new File(path.get(0))));
                    }
                    break;
                case CROP:
                    civ.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    break;
                case REQUEST_DISTRICT:
                    String province = data.getStringExtra(MineEdtMsgDistrictActivity.REGION_PROVINCE);
                    String city = data.getStringExtra(MineEdtMsgDistrictActivity.REGION_CITY);
                    Log.e("district","province="+province+",city="+city);
                    edtDistrict.setText(province+"-"+city);
                    break;
            }
        }

    }


    class Send2BackGround extends AsyncTask<String,Void,LoginMessageEntity>{

        @Override
        protected LoginMessageEntity doInBackground(String... params) {

            return OkHttpUtils.getInstance().editMessage(params[0],params[1],
                    params[2],params[3],params[4],params[5],params[6],
                    params[7],params[8],params[9],params[10]);
        }

        @Override
        protected void onPostExecute(LoginMessageEntity loginMessageEntity) {
            super.onPostExecute(loginMessageEntity);
            if(loginMessageEntity.getResultCode()==1){
                //保存成功
            }else{
                //保存失败
            }
        }
    }
}
