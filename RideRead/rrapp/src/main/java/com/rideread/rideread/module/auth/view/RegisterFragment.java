package com.rideread.rideread.module.auth.view;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.util.DateUtils;
import com.rideread.rideread.common.util.FileUtils;
import com.rideread.rideread.common.util.FrescoImgLoader;
import com.rideread.rideread.common.util.KeyboardUtils;
import com.rideread.rideread.common.util.RegexUtils;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.QiniuToken;
import com.rideread.rideread.data.result.UserInfo;
import com.rideread.rideread.data.result.VCode;
import com.rideread.rideread.function.net.qiniu.QiNiuUtils;
import com.rideread.rideread.function.net.retrofit.ApiStore;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;
import com.rideread.rideread.module.main.MainActivity;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.rideread.rideread.R.id.btn_pwd_next;


public class RegisterFragment extends BaseFragment implements TextView.OnEditorActionListener {
    @BindView(R.id.edt_invite_id) EditText mEdtInviteId;
    @BindView(R.id.edt_phone) EditText mEdtPhone;
    @BindView(R.id.edt_code) EditText mEdtCode;
    @BindView(R.id.include_invite_id_view) LinearLayout mIncludeInviteView;
    @BindView(R.id.include_code_view) LinearLayout mIncludeCodeView;
    @BindView(R.id.include_set_pwd_view) LinearLayout mIncludeSetPwdView;
    @BindView(R.id.include_final_view) LinearLayout mIncludeFinalView;
    @BindView(R.id.edt_password) EditText mEdtPassword;
    @BindView(R.id.edt_password_confirm) EditText mEdtPasswordConfirm;
    @BindView(R.id.tv_send_code) TextView mTvSendCode;
    @BindView(R.id.img_avatar) CircleImageView mImgAvatar;
    @BindView(R.id.edt_nick) EditText mEdtNick;
    @BindView(R.id.btn_pwd_next) Button mBtnPwdNext;
    @BindView(R.id.tv_agree_protocol) TextView mTvAgreeProtocol;
    @BindView(R.id.tv_avatar_tips) TextView mTvAvatarTips;
    private String mPhone;
    private String mPassword;
    private CountDownTimer mDownTimer;
    private String mRandCode;
    private String mInvitedId;
    private String mFaceUrl;
    private String mMyRideReadId;


    private List<String> path = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;

    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    private String filePath;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView() {
        mEdtInviteId.setOnEditorActionListener(this);
        mEdtCode.setOnEditorActionListener(this);
        mEdtPassword.setOnEditorActionListener(this);
        mEdtPasswordConfirm.setOnEditorActionListener(this);
        initGallery();
        mDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                mTvSendCode.setClickable(false);
                mTvSendCode.setText((l / 1000) + "s后重新发送");
            }

            @Override
            public void onFinish() {
                mTvSendCode.setClickable(true);
                mTvSendCode.setText("重新发送");
            }
        };
    }

    @Override
    public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
                /*隐藏软键盘*/
            switch (v.getId()) {
                case R.id.edt_invite_id:
                    verifyInviteId();
                    break;
                case R.id.edt_code:
                    verifyCode();
                    break;
                case R.id.edt_password:
                    mEdtPasswordConfirm.requestFocus();
                    break;
                case R.id.edt_password_confirm:
                    KeyboardUtils.hideSoftInput(getActivity());
                    checkPwd();
                    break;
            }
            return true;
        }
        return false;
    }

    private void showCodeView() {
        mIncludeInviteView.setVisibility(View.GONE);
        mIncludeSetPwdView.setVisibility(View.GONE);
        mIncludeCodeView.setVisibility(View.VISIBLE);
        mIncludeFinalView.setVisibility(View.GONE);
        mTvAgreeProtocol.setVisibility(View.VISIBLE);
    }

    private void showSetPwdView() {
        mIncludeInviteView.setVisibility(View.GONE);
        mIncludeSetPwdView.setVisibility(View.VISIBLE);
        mIncludeCodeView.setVisibility(View.GONE);
        mIncludeFinalView.setVisibility(View.GONE);
        mBtnPwdNext.setText(R.string.next);
    }

    private void showFinalView() {
        mIncludeInviteView.setVisibility(View.GONE);
        mIncludeSetPwdView.setVisibility(View.GONE);
        mIncludeCodeView.setVisibility(View.GONE);
        mIncludeFinalView.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_invite_next, R.id.tv_send_code, R.id.btn_code_next, R.id.tv_agree_protocol, btn_pwd_next, R.id.img_avatar, R.id.btn_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_invite_next:
                verifyInviteId();
                break;
            case R.id.tv_send_code:
                getVerifyCode();
                break;
            case R.id.btn_code_next:
                verifyCode();
                break;
            case R.id.tv_agree_protocol:
                //TODO 跳转用户协议
                break;
            case btn_pwd_next:
                checkPwd();
                break;
            case R.id.img_avatar:
                setAvatarImg();
                break;
            case R.id.btn_complete:
                getFaceUrl();
                break;
        }
    }


    private void verifyInviteId() {
        mInvitedId = mEdtInviteId.getText().toString();
        if (TextUtils.isEmpty(mInvitedId)) {
            ToastUtils.show("骑阅邀请号不能为空");
            return;
        }
        ApiUtils.verifyRideReadId(mInvitedId, new BaseCallback<BaseModel<DefJsonResult>>() {
            @Override
            protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                showCodeView();
            }
        });
    }

    public void getVerifyCode() {
        mPhone = mEdtPhone.getText().toString().trim();
        if (!RegexUtils.isMobileSimple(mPhone)) {
            ToastUtils.show("请填写11位正确手机号");
            return;
        }
        ApiUtils.getVCode(mPhone, new BaseCallback<BaseModel<VCode>>() {
            @Override
            protected void onSuccess(BaseModel<VCode> model) throws Exception {
                mDownTimer.start();
                mRandCode = model.getData().getRandCode();
                ToastUtils.show("验证码已发送，请等待接收:" + mRandCode);
                mEdtCode.requestFocus();
            }
        });
    }

    private void verifyCode() {
        //        showSetPwdView();
        String vCode = mEdtCode.getText().toString();
        if (!TextUtils.isEmpty(mRandCode) && !TextUtils.isEmpty(vCode) && mRandCode.equals(vCode)) {
            showSetPwdView();
        } else {
            ToastUtils.show("验证失败，请输入正确的验证码");
        }
    }

    private void checkPwd() {
        mPassword = mEdtPassword.getText().toString();
        String pwdConfirm = mEdtPasswordConfirm.getText().toString();
        //TODO  验证6-16位
        if (!mPassword.equals(pwdConfirm)) {
            ToastUtils.show("密码不一致，请确认密码一致");
            return;
        }
        showFinalView();
    }

    private void getFaceUrl() {
        mMyRideReadId = mEdtNick.getText().toString();
        if (TextUtils.isEmpty(mMyRideReadId)) mMyRideReadId = "user";
        final String filename = "face" + "_" + mMyRideReadId + "_" + DateUtils.getCurDateFormat() + ".jpg";
        ApiUtils.getQiNiuTokenTest(filename, new BaseCallback<BaseModel<QiniuToken>>() {
            @Override
            protected void onSuccess(BaseModel<QiniuToken> model) throws Exception {
                String token = model.getData().getUpToken();

                QiNiuUtils.uploadFile(token, filePath, filename, (key, info, response) -> {
                    Logger.e("http", "key:" + key + "-info:" + info.toString() + "-response:" + response);
                    if (info.isOK()) {
                        mFaceUrl = ApiStore.USERHEAD_LINK + key;
                        register();
                    } else {
                        ToastUtils.show("头像上传失败");
                    }
                }, null, null);
            }
        });
    }

    private void register() {

        mMyRideReadId = mEdtNick.getText().toString();
        if (TextUtils.isEmpty(mMyRideReadId)) {
            ToastUtils.show("骑阅号不能为空");
            return;
        }
        ApiUtils.register(mMyRideReadId, mPhone, mPassword, mMyRideReadId, mFaceUrl, new BaseCallback<BaseModel<UserInfo>>() {
            @Override
            protected void onSuccess(BaseModel<UserInfo> model) throws Exception {
                UserInfo userInfo = model.getData();
                if (null != userInfo) {
                    UserUtils.login(userInfo.getUid(), userInfo.getToken(), userInfo.getPhonenumber());
                    Bundle bundle = new Bundle();
                    bundle.putString(MainActivity.INVITED_RIDE_READ_ID, mInvitedId);
                    getBaseActivity().gotoActivity(MainActivity.class, bundle, true);
                } else {
                    ToastUtils.show("注册异常");
                }
            }
        });

    }


    private void setAvatarImg() {
        galleryConfig.getBuilder().isOpenCamera(false).build();
        initPermissions();
    }


    //添加自定义回调
    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Logger.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Logger.i(TAG, "onSuccess: 返回数据");
                //                path.clear();
                //                for (String s : photoList) {
                //                    Logger.i(TAG, s);
                //                    path.add(s);
                //                }
                //因为我们当前是单选模式，所以只取第一张图片
                //img.setImageURI(Uri.fromFile(new File(photoList.get(0))));

                try {
                    filePath = photoList.get(0);
                    mImgAvatar.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    if (mTvAvatarTips.getVisibility() == View.VISIBLE) {
                        mTvAvatarTips.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "头像获取失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancel() {
                Logger.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Logger.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Logger.i(TAG, "onError: 出错");
            }
        };

        galleryConfig = new GalleryConfig.Builder().imageLoader(new FrescoImgLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.rideread.rideread.fileprovider")   // provider(必填)
                //                    .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 200, 200)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath(FileUtils.APP_SD_ROOT + "pic")          // 图片存放路径
                .build();

    }

    // 授权管理
    private void initPermissions() {
        Activity mActivity = getBaseActivity();
        if (null == mActivity) return;
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Logger.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Logger.i(TAG, "拒绝过了");
                ToastUtils.show("请在 设置-应用管理 中开启此应用的储存授权。");
            } else {
                Logger.i(TAG, "进行授权");
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            Logger.i(TAG, "不需要授权 ");
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logger.i(TAG, "同意授权");
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getBaseActivity());
            } else {
                Logger.i(TAG, "拒绝授权");
            }
        }
    }


    //    private String tagPhone="phoneNumView",tagInvite="inviteCodeView",tagPwd="setPwdView",tagName="setUnameView";
    //    private View phoneNumView, inviteCodeView, setPwdView, setUnameView;
    //    private String inviteCode;
    //    private TextView indentfyCodeTv;
    //    private String telPhone,password,encodepwd,timeStamp;
    //    private final int REQUEST_IMAGE=1;
    //    private final int CROP=0;
    //    private final String TYPE="image/*";
    //    private String fileName,userName,shortFileName;//原图的路径+文件名
    //    private String TAG="Reg";
    //    private CircleImageView img;
    //    private EditText etUserName;
    //    private TextView setuserhead_texthint;//也就是头像那两个字在设置完头像后隐藏掉
    //    private String  filePath=null;
    //
    //
    //    private IHandlerCallBack iHandlerCallBack;
    //    private List<String> path = new ArrayList<>();
    //    private Activity mActivity;
    //    private Context mContext;
    //    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    //    private GalleryConfig galleryConfig;
    //
    //
    //    @Nullable
    //    @Override
    //    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //        View mView=inflater.inflate(R.layout.register_fragment_layout,container,false);
    //        inviteCodeView =mView.findViewById(R.id.register_invite_include_layout);
    //        initButton(inviteCodeView,R.id.register_btn_next,tagInvite);
    //
    //        phoneNumView =mView.findViewById(R.id.register_phonenum_include_ly);
    //        initButton(phoneNumView,R.id.register_btn_next,tagPhone);
    //        ((TextView)phoneNumView.findViewById(R.id.register_tv_sendidentfycode)).setOnClickListener(this);
    //
    //        setPwdView =mView.findViewById(R.id.register_setpwd_include_ly);
    //        initButton(setPwdView,R.id.register_btn_next,tagPwd);
    //
    //        setUnameView =mView.findViewById(R.id.register_setuname_include_ly);
    //        initButton(setUnameView,R.id.register_btn_complete,null);
    //        img=(CircleImageView)setUnameView.findViewById(R.id.register_civ_userhead);
    //        img.setOnClickListener(this);
    //        setuserhead_texthint=(TextView)setUnameView.findViewById(R.id.setuserhead_texthint);
    //
    //        mContext=getContext();
    //        mActivity=getActivity();
    //        initGallery();
    //        return mView;
    //    }
    //
    //
    //    public void initButton(View v,int id,String tag){
    //        Button btn=(Button)v.findViewById(id);
    //        btn.setTag(tag);
    //        btn.setOnClickListener(this);
    //    }
    //
    //    @Override
    //    public void onClick(View v) {
    //
    //        switch (v.getId()){
    //            case R.id.register_btn_next:
    //                String tag=(String)v.getTag();
    //                Button btnNext=(Button)v;
    //                if(tag.equals(tagInvite)){
    //                    changeView(inviteCodeView, phoneNumView);
    //                    //veifyInviteCode(btnNext);
    //                }else if(tag.equals(tagPhone)){
    //                    verifyCode();
    //
    //                }else if(tag.equals(tagPwd)){
    //                    setPwd();
    //                }
    //                break;
    //            case R.id.register_btn_complete:
    //                onComplete();
    //                break;
    //            case R.id.register_tv_sendidentfycode:
    //                Toast.makeText(getContext(),"发送验证码",Toast.LENGTH_SHORT).show();
    //                if(indentfyCodeTv==null){
    //                    indentfyCodeTv=(TextView)v;
    //                }
    //                onSendIdentCode();
    //                break;
    //            case R.id.register_read_agreement_tv:
    //                readAgreement();
    //                break;
    //            case R.id.register_civ_userhead:
    //                setHeadImg();
    //                break;
    //
    //        }
    //
    //    }
    //
    //    private void setHeadImg() {
    //        galleryConfig.getBuilder().isOpenCamera(false).build();
    //        initPermissions();
    //    }
    //
    //    private void changeView(View hideView,View showView){
    //
    //        hideView.setVisibility(View.GONE);
    //        showView.setVisibility(View.VISIBLE);
    //    }
    //
    //    /**
    //     * 验证邀请码
    //     */
    //    private void veifyInviteCode(Button btnNext) {
    //        EditText editText=(EditText)inviteCodeView.findViewById(R.id.register_edt_invitationcode);
    //        inviteCode=editText.getText().toString().trim();
    //        if(inviteCode==null){
    //            Toast.makeText(getActivity(),"未填写邀请码",Toast.LENGTH_SHORT).show();
    //        }else{
    //            btnNext.setClickable(false);//避免点击多次
    //            new InviteCodeAysncTask().execute(inviteCode,Api.VERIFY_CODE);
    //        }
    //    }
    //
    //
    //    /**
    //     * 验证邀请码的异步线程
    //     */
    //    class InviteCodeAysncTask extends AsyncTask<String,String,Integer> {
    //        @Override
    //        protected Integer doInBackground(String... params) {
    //
    //            return OkHttpUtils.getInstance().testInviteCode(params[0], TimeStamp.getTimeStamp(),params[1]);
    //        }
    //
    //        @Override
    //        protected void onPostExecute(Integer status) {
    //            super.onPostExecute(status);
    //            if(status== Constants.SUCCESS){
    //                changeView(inviteCodeView, phoneNumView);
    //            }else if(status==Constants.FAILED){
    //                Toast.makeText(getActivity(),"邀请码不存在",Toast.LENGTH_SHORT).show();
    //            }else if(status==Constants.USED){
    //                Toast.makeText(getActivity(),"邀请码已被使用",Toast.LENGTH_SHORT).show();
    //            }else{
    //                Toast.makeText(getActivity(),"邀请码发送失败",Toast.LENGTH_SHORT).show();
    //            }
    //            ((Button)inviteCodeView.findViewById(R.id.register_btn_next)).setClickable(true);//恢复可点击状态
    //        }
    //    }
    //
    //    //验证验证码
    //    private void verifyCode(){
    //        EditText indentfyCodeEdt=(EditText)phoneNumView.findViewById(R.id.register_edt_identfycode);
    //        String code = indentfyCodeEdt.getText().toString().trim();
    //        if(!code.isEmpty()){
    //            AVOSCloud.verifySMSCodeInBackground(code, telPhone,
    //                    new AVMobilePhoneVerifyCallback() {
    //                        @Override
    //                        public void done(AVException e) {
    //                            if (e == null) {
    //                                PreferenceUtils.getInstance().saveTelPhone(telPhone,getActivity().getApplicationContext());
    //                                changeView(phoneNumView,setPwdView);
    //                            } else {
    //                                Log.e("----->>>>",e.getMessage());
    //                                e.printStackTrace();
    //                                Toast.makeText(getActivity(),"验证码不匹配",Toast.LENGTH_SHORT).show();
    //                            }
    //                        }
    //                    });
    //        }else{
    //            Toast.makeText(getActivity(),"未填写验证码",Toast.LENGTH_SHORT).show();
    //        }
    //    }
    //
    //    //发送验证码
    //    private void onSendIdentCode(){
    //        EditText registerPhone=(EditText)phoneNumView.findViewById(R.id.register_edt_phone);
    //        telPhone=registerPhone.getText().toString().trim();
    //        if(telPhone!=null&&(!telPhone.isEmpty())){
    //            Log.i("手机号码：",telPhone);
    //            AVOSCloud.requestSMSCodeInBackground(telPhone,"骑阅","注册",1,new RequestMobileCodeCallback(){
    //
    //                @Override
    //                public void done(AVException e) {
    //                    if(e==null){
    //                        new CountDownTimer(60000,1000){
    //
    //                            @Override
    //                            public void onTick(long millisUntilFinished) {
    //
    //                                indentfyCodeTv.setText(millisUntilFinished/1000+"s后重新发送");
    //                                indentfyCodeTv.setClickable(false);
    //                            }
    //
    //                            @Override
    //                            public void onFinish() {
    //                                indentfyCodeTv.setText("发送验证码");
    //                                indentfyCodeTv.setClickable(true);
    //                            }
    //                        }.start();
    //                    }else{
    //                        Toast.makeText(getActivity(), "验证码发送失败",Toast.LENGTH_SHORT).show();
    //                    }
    //                }
    //            });
    //        }else{
    //            Toast.makeText(getActivity(),"未填手机号码",Toast.LENGTH_SHORT).show();
    //        }
    //
    //    }
    //
    //    //阅读用户协议
    //    private void readAgreement(){
    //        startActivity(new Intent(getActivity(),UserAgreement.class));
    //    }
    //
    //    //设置密码
    //    private void setPwd(){
    //        EditText setpwdEdt=(EditText)setPwdView.findViewById(R.id.register_edt_setpwd);
    //        EditText rePwd=(EditText)setPwdView.findViewById(R.id.register_edt_repwd);
    //        password=setpwdEdt.getText().toString().trim();
    //        String repassword=rePwd.getText().toString().trim();
    //        String result=new ConfirmPassword().confirmPwd(password,repassword);
    //
    //        if(result.equals("success")){
    //            changeView(setPwdView,setUnameView);
    //        }else{
    //            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
    //        }
    //
    //    }
    //
    //
    //    //完成注册
    //    private void onComplete(){
    //        etUserName=(EditText)setUnameView.findViewById(R.id.register_edt_setusername);
    //        userName=etUserName.getText().toString().trim();
    //        final String key = "face.jpg";//这个key是保存在七牛云的文件名，把这个key传给后台
    //        if(userName!=null&&!userName.isEmpty()){
    //            //在这里发送用户名和头像给后台
    //            new AsyncTask<String,Void,QiNiuTokenResp>(){
    //
    //                @Override
    //                protected QiNiuTokenResp doInBackground(String... params) {
    //                    return OkHttpUtils.getInstance().getToken(params[0],Api.TEMP_UID,TimeStamp.getTimeStamp(),params[1],params[2]);
    //                }
    //
    //                @Override
    //                protected void onPostExecute(QiNiuTokenResp resp) {
    //                    super.onPostExecute(resp);
    ////                    Log.e("s","七牛tioken="+resp.getQiniu_token());
    //                    if(resp!=null){
    //
    //                        App app=(App)getActivity().getApplication();
    //                        //这里的TOKEN是要事先从服务器获取，目前用测试的Token来替换
    //
    //                        //file是截图后头像保存在手机的完整路径，key是上传到七牛云后头像的名称
    //                        app.getUploadManager().put(filePath,key , resp.getQiniu_token(), new UpCompletionHandler() {
    //                            @Override
    //                            public void complete(String key, ResponseInfo info, JSONObject response) {
    //                                //res包含hash、key等信息，具体字段取决于上传策略的设置
    //
    //                                if(info.isOK())
    //                                {
    //                                    Log.e("face_url",Api.USERHEAD_LINK+key);
    //                                    //如果上传成功则提交头像url和用户名，密码，手机号码给后台
    //                                    new Send2Background().execute(userName,PreferenceUtils.getInstance().getTelPhone(getActivity().getApplicationContext()),password,
    //                                            Api.USERHEAD_LINK+key,Api.REGISTER);
    //                                }
    //                                else{
    //                                    Log.e("qiniu", "Upload Fail");
    //                                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
    //                                }
    //                                Log.e("------------>>>>",info.path+","+info.isOK()+",info="+info.toString()+",key:"+key);
    //                                Log.e("qiniu", key + ",\r\n " + info +","+response);
    //
    //                            }
    //
    //                        },null);
    //                    }else{
    //                        Toast.makeText(getContext(),"获取七牛token失败",Toast.LENGTH_SHORT).show();
    //                    }
    //                }
    //            }.execute(key,Api.TEMP_USER_TOKEN,Api.QINIU_TOKEN);
    //
    ////            try {
    ////                final AVFile avFile = AVFile.withAbsoluteLocalPath(timeStamp + ".jpg", file.getAbsolutePath());
    ////                avFile.saveInBackground(new SaveCallback() {
    ////                    @Override
    ////                    public void done(AVException e) {
    ////                        if(e==null){
    ////                            new Send2Background().execute(userName,PreferenceUtils.getInstance().getTelPhone(getActivity().getApplicationContext()),password,
    ////                                avFile.getUrl(),Api.REGISTER);
    ////                        }else{
    ////                            Toast.makeText(getActivity(),"上传失败",Toast.LENGTH_SHORT).show();
    ////                        }
    ////                    }
    ////                });
    ////
    ////            }catch(FileNotFoundException e){
    ////                Toast.makeText(getActivity(),"头像路径不可用",Toast.LENGTH_SHORT).show();
    ////            }
    //
    //
    //
    //        }else{
    //            Toast.makeText(getActivity(),"未填写用户名",Toast.LENGTH_SHORT).show();
    //        }
    //
    //    }
    //
    //
    ////
    ////    @Override
    ////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    ////        super.onActivityResult(requestCode, resultCode, data);
    ////        if(resultCode== Activity.RESULT_OK){
    ////            switch (requestCode){
    ////                case REQUEST_IMAGE:
    ////                    if(data!=null) {
    ////                        List<String> path=data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
    ////                        fileName=path.get(0);//原图的完整路径（包括文件名）
    ////                        Toast.makeText(getContext(),fileName,Toast.LENGTH_LONG).show();
    ////                        Log.i(TAG,path.get(0));
    ////                    }
    ////                    break;
    ////                case CROP:
    ////
    ////                    img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
    ////                    if(setuserhead_texthint.getVisibility()==View.VISIBLE){
    ////                        setuserhead_texthint.setVisibility(View.GONE);
    ////                    }
    ////                    break;
    ////            }
    ////        }
    ////
    ////    }
    //
    //    //发送参数到后台
    //    class Send2Background extends AsyncTask<String,Void,LoginResponse>{
    //        @Override
    //        protected LoginResponse doInBackground(String... params) {
    //
    //            return OkHttpUtils.getInstance().send2BackGround(params[0],params[1],
    //                    params[2], params[3],params[4]);
    //        }
    //
    //        @Override
    //        protected void onPostExecute(LoginResponse resp) {
    //            super.onPostExecute(resp);
    //            int resultCode=resp.getStatus();
    //            if(resp!=null){
    //                if(resultCode==Constants.SUCCESS){
    //                    Intent intent=new Intent(getActivity(),MainActivity.class);
    //                    intent.putExtra("data",resp.getData());
    //                    intent.putExtra("timestamp",resp.getTimestamp());
    //                    startActivity(intent);
    ////               //连接leacloud im服务器
    //                    //openClient(resp.getData());
    //                }else if(resultCode==Constants.EXITED){
    //                    Toast.makeText(getActivity(),"用户已经存在",Toast.LENGTH_SHORT).show();
    //                }
    //            }else{
    //                Toast.makeText(getActivity(),"未知错误",Toast.LENGTH_SHORT).show();
    //            }
    //        }
    //
    //        private void openClient(final UserData data){
    //            AVImClientManager.getInstance().open(data.getUid()+"",new AVIMClientCallback() {
    //                @Override
    //                public void done(AVIMClient avimClient, AVIMException e) {
    //                    if(e==null){
    //                        Intent intent=new Intent(getActivity(),MainActivity.class);
    //                        intent.putExtra("data",data);
    //                        startActivity(intent);
    //                    }else{
    //                        Toast.makeText(getActivity(),"登录失败",Toast.LENGTH_SHORT).show();
    //                    }
    //                }
    //            });
    //
    //        }
    //    }
    //
    //
    //
    //
}
