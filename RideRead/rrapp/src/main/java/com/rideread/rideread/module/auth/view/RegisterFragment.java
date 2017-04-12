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
                    UserUtils.login(userInfo);
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
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getBaseActivity());
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

}
