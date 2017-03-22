package com.rideread.rideread.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.rideread.rideread.App;
import com.rideread.rideread.R;
import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.bean.LoginResponse;
import com.rideread.rideread.bean.PostParams;
import com.rideread.rideread.bean.QiNiuTokenResp;
import com.rideread.rideread.bean.UserData;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.Constants;
import com.rideread.rideread.common.TimeStamp;
import com.rideread.rideread.db.DBCopyUtil;
import com.rideread.rideread.common.FileUtils;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.PreferenceUtils;
import com.rideread.rideread.fragment.BiaoQianFragment;
import com.rideread.rideread.fragment.DateFragment;
import com.rideread.rideread.fragment.RegisterFragment;
import com.rideread.rideread.imageloader.GildeImageLoader;
import com.rideread.rideread.selfinterface.MineEditListener;
import com.rideread.rideread.widget.MineEditDialogFragmen;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class MineEditMessageActivity extends BaseActivity implements View.OnClickListener,MineEditListener{

    private CircleImageView civ;
    private String TAG="MineEditMessageActivity";

    public final int REQUEST_DISTRICT=2;
    private TextView edtDistrict,editmsgSex;

    private UserData data;
    private Activity mActivity;
    private Context mContext;
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;
    private List<String> path = new ArrayList<>();
    private String filePath=null;//头像的绝对路径
    private int selectSex=0;
    private PostParams postParams;
    private MineEditDialogFragmen mineEditDialogFragmen;


    private TextView birthDate,realname,signtureedt,schooledt;
    private EditText phonenum,jobedt,hometownedt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_editmessage_layout);
        data=(UserData) getIntent().getSerializableExtra("data");
        postParams=PreferenceUtils.getInstance().getPostParams(getApplicationContext());
        mActivity=this;
        mContext=this;
        initView();
        initGallery();
    }

    private void initView() {
        birthDate=(TextView)findViewById(R.id.mine_editmsg_tv_birthdate);
        TextView save=(TextView) findViewById(R.id.right_title);
        civ=(CircleImageView)findViewById(R.id.mine_editmsg_iv_head);
        edtDistrict=(TextView)findViewById(R.id.mine_editmsg_et_locale);
        ImageView back=(ImageView)findViewById(R.id.left_setting_icon);
        editmsgSex=(TextView)findViewById(R.id.mine_editmsg_et_sex);
        realname=(TextView)findViewById(R.id.mine_editmsg_et_name);
        phonenum=(EditText)findViewById(R.id.mine_editmsg_et_phone);
        signtureedt=(TextView)findViewById(R.id.mine_editmsg_et_signture);
        schooledt=(TextView)findViewById(R.id.mine_editmsg_et_school);
        jobedt=(EditText)findViewById(R.id.mine_editmsg_et_job);
        hometownedt=(EditText)findViewById(R.id.mine_editmsg_et_hometown);

        setData(data);
        save.setOnClickListener(this);
        civ.setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.mine_change_headimg)).setOnClickListener(this);
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
            case R.id.right_title:
                String name=realname.getText().toString().trim();
                String sex=editmsgSex.getText().toString().trim();
                if(TextUtils.isEmpty(sex)){
                    selectSex=0;
                }else if(sex.equals("女")){
                    selectSex=2;
                }else{
                    selectSex=1;
                }
                String telphone=phonenum.getText().toString().trim();
                String signture=signtureedt.getText().toString().trim();
                String date=birthDate.getText().toString().trim();
                String locale=edtDistrict.getText().toString().trim();
                String school =schooledt.getText().toString().trim();
                String job=jobedt.getText().toString().trim();
                String hometown=hometownedt.getText().toString().trim();

                UpdateMessage(date,hometown,school,locale,job,name,telphone,postParams.getToken(),signture);
                break;
            case R.id.mine_editmsg_iv_head:
            case R.id.mine_change_headimg:
                setHeadImg();
                break;
            case R.id.left_setting_icon:
                onBackPressed();
                break;
        }

    }



    public void UpdateMessage(final String birthday, final String hometown, final String school, final String location
    , final String career,final  String nickname, final String phonenum, final String token, final String signture){

        final String key = "face.jpg";//这个key是保存在七牛云的文件名，把这个key传给后台

            //在这里发送用户名和头像给后台
            new AsyncTask<String,Void,QiNiuTokenResp>(){

                @Override
                protected QiNiuTokenResp doInBackground(String... params) {
                    return OkHttpUtils.getInstance().getToken(params[0],postParams.getUid(), TimeStamp.getTimeStamp(),params[1],params[2]);
                }

                @Override
                protected void onPostExecute(QiNiuTokenResp resp) {
                    super.onPostExecute(resp);
                    Log.e("s","七牛tioken="+resp.getQiniu_token());
                    if(resp!=null){

                        App app=(App)MineEditMessageActivity.this.getApplication();
                        //这里的TOKEN是要事先从服务器获取，目前用测试的Token来替换

                        UploadManager upl=app.getUploadManager();

                        if(TextUtils.isEmpty(filePath)){
                            new Send2BackGround().execute(data.getFace_url(),phonenum,birthday,nickname,signture,location
                                    ,school,career,hometown,Api.EDIT_MESSAGE,token);
                        }else{
                            //file是截图后头像保存在手机的完整路径，key是上传到七牛云后头像的名称
                            upl.put(filePath,key , resp.getQiniu_token(), new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置

                                    if(info.isOK())
                                    {
                                        Log.e("face_url",Api.USERHEAD_LINK+key);

                                        new Send2BackGround().execute(Api.USERHEAD_LINK+key,phonenum,birthday,nickname,signture,location
                                                ,school,career,hometown,Api.EDIT_MESSAGE,token);
                                    }
                                    else{
                                        Log.e("qiniu", "Upload Fail");
                                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                    }
                                    Log.e("------------>>>>",info.path+","+info.isOK()+",info="+info.toString()+",key:"+key);
                                    Log.e("qiniu", key + ",\r\n " + info +","+response);

                                }

                            },null);
                        }
                    }else{
                        Toast.makeText(MineEditMessageActivity.this,"获取七牛token失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(key,token,Api.QINIU_TOKEN);
    }

    //图片相册选择图片或者拍照
    public void setHeadImg(){
        galleryConfig.getBuilder().isOpenCamera(false).build();
        initPermissions();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){

                case REQUEST_DISTRICT:
                    String province = data.getStringExtra(MineEdtMsgDistrictActivity.REGION_PROVINCE);
                    String city = data.getStringExtra(MineEdtMsgDistrictActivity.REGION_CITY);
                    Log.e("district","province="+province+",city="+city);
                    edtDistrict.setText(province+"-"+city);
                    break;
            }
        }

    }

    public void setData(UserData data) {
        if(data!=null){
            if(data.getFace_url()!=null){
                Glide.with(this).load(data.getFace_url()).into(civ);
            }

            if(data.getLocation()!=null){
                edtDistrict.setText(data.getLocation());
            }
            if(data.getCareer()!=null){
                jobedt.setText(data.getCareer());
            }
            if(data.getBirthday()!=null){
                birthDate.setText(data.getBirthday());
            }
            if(data.getPhonenumber()!=null){
                phonenum.setText(data.getPhonenumber());
            }
            if(data.getSchool()!=null){
                schooledt.setText(data.getSchool());
            }
            if(data.getSignature()!=null){
                signtureedt.setText(data.getSignature());
            }
            switch (data.getSex()){
                case 1:
                    editmsgSex.setText("男");
                case 2:
                    editmsgSex.setText("女");
                    break;
                default:
                    editmsgSex.setText("未知");
                    break;

            }
            if(data.getUsername()!=null){
                realname.setText(data.getUsername());
            }


        }
    }

    //编辑昵称和学校，个性签名回调的接口
    @Override
    public void getResult(String tag, String content) {

        if(tag.equals("name")){
            realname.setText(content);
        }else if(tag.equals("school")){
            schooledt.setText(content);
        }else{
            signtureedt.setText(content);
        }

        if(mineEditDialogFragmen!=null){
            mineEditDialogFragmen.dismiss();
        }

    }


    public void onBiaoQian(View v){
        BiaoQianFragment labelFragment=new BiaoQianFragment();
        labelFragment.show(getSupportFragmentManager(),"labelFragment");

     }


    class Send2BackGround extends AsyncTask<String,Void,LoginResponse>{

        @Override
        protected LoginResponse doInBackground(String... params) {

            return OkHttpUtils.getInstance().editMessage(params[0],params[1],
                    params[2],selectSex,params[3],params[4],params[5],
                    params[6],params[7],params[8],TimeStamp.getTimeStamp(),params[9],postParams.getUid(),params[10],data.getLongitude(),data.getLatitude(),null);
        }

        @Override
        protected void onPostExecute(LoginResponse resp) {
            super.onPostExecute(resp);
            if(resp.getStatus()== Constants.SUCCESS){
                //保存成功
                setData(resp.getData());
            }else{
                //保存失败
                Toast.makeText(MineEditMessageActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                setData(data);
            }
        }
    }





    //添加自定义回调
    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
//                path.clear();
//                for (String s : photoList) {
//                    Log.i(TAG, s);
//                    path.add(s);
//                }
                //因为我们当前是单选模式，所以只取第一张图片
                //img.setImageURI(Uri.fromFile(new File(photoList.get(0))));

                try {
                    Glide.with(MineEditMessageActivity.this).load(photoList.get(0)).into(civ);
                }catch (Exception e){
                    Toast.makeText(MineEditMessageActivity.this,"头像获取失败",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GildeImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.rideread.test.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 100, 100)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath(FileUtils.root+"pic")          // 图片存放路径
                .build();

    }

    // 授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i(TAG, "拒绝过了");
                Toast.makeText(mContext, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "进行授权");
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            Log.i(TAG, "不需要授权 ");
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "同意授权");
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(this);
            } else {
                Log.i(TAG, "拒绝授权");
            }
        }
    }

    //设置昵称
    public void onEdit(View v){

        switch (v.getId()){
            case R.id.name_tablerow:
                showEditDialog("name","真实姓名","建议使用真实姓名");
                break;
            case R.id.school_tablerow:
                //编辑学校
                showEditDialog("school","毕业/在读学校","填写学校全称");
                break;
            case R.id.signture_tablerow:
                //编辑签名
                showEditDialog("signature","个性签名","填写个性签名");
                break;
        }
    }


    private void showEditDialog(String tag,String title,String hint){
        //编辑昵称
        if(mineEditDialogFragmen==null){
            mineEditDialogFragmen=new MineEditDialogFragmen();
        }
        Bundle bundle= new Bundle();
        bundle.putString("tag",tag);
        bundle.putString("title",title);
        bundle.putString("hint",hint);
        mineEditDialogFragmen.setArguments(bundle);
        mineEditDialogFragmen.show(getSupportFragmentManager(),"mineEditDialogFragmen");
    }
}
