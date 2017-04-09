package com.rideread.rideread.module.auth.view;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.util.KeyboardUtils;
import com.rideread.rideread.common.util.RegexUtils;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.UserInfo;
import com.rideread.rideread.data.result.VCode;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;
import com.rideread.rideread.module.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment implements TextView.OnEditorActionListener {

    @BindView(R.id.include_login_view) LinearLayout mIncludeLoginView;
    @BindView(R.id.include_code_view) LinearLayout mIncludeCodeView;
    @BindView(R.id.include_set_pwd_view) LinearLayout mIncludeSetPwdView;
    @BindView(R.id.edt_login_account) EditText mEdtLoginAccount;
    @BindView(R.id.edt_login_pwd) EditText mEdtLoginPwd;
    @BindView(R.id.edt_phone) EditText mEdtPhone;
    @BindView(R.id.edt_code) EditText mEdtCode;
    @BindView(R.id.tv_send_code) TextView mTvSendCode;
    @BindView(R.id.tv_agree_protocol) TextView mTvAgreeProtocol;
    @BindView(R.id.edt_password) EditText mEdtPassword;
    @BindView(R.id.edt_password_confirm) EditText mEdtPasswordConfirm;

    private CountDownTimer mDownTimer;
    private String mRandCode;

    private String mPhone;
    private double longtitude;//latitude": 23.136552
    private double laititude;//"longitude": 113.314086

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        String latestPhone = UserUtils.getPhone();
        if (!TextUtils.isEmpty(latestPhone)) {
            mEdtLoginAccount.setText(latestPhone);
            mEdtLoginAccount.setSelection(latestPhone.length());
        }

        mEdtLoginAccount.setOnEditorActionListener(this);
        mEdtLoginPwd.setOnEditorActionListener(this);
        mEdtCode.setOnEditorActionListener(this);
        mEdtPassword.setOnEditorActionListener(this);
        mEdtPasswordConfirm.setOnEditorActionListener(this);

        mDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                mTvSendCode.setClickable(false);
                mTvSendCode.setText((l / 1000) + "s后重新发送");
            }

            @Override
            public void onFinish() {
                mTvSendCode.setClickable(true);
                mTvSendCode.setText(R.string.resend);
            }
        };
    }

    @Override
    public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
                /*隐藏软键盘*/
            switch (v.getId()) {
                case R.id.edt_login_account:
                    mEdtLoginPwd.requestFocus();
                    break;
                case R.id.edt_login_pwd:
                    KeyboardUtils.hideSoftInput(getActivity());
                    login();//输入密码后可直接回车登录
                    break;
                case R.id.edt_code:
                    verifyCode();
                    break;
                case R.id.edt_password:
                    mEdtPasswordConfirm.requestFocus();
                    break;
                case R.id.edt_password_confirm:
                    KeyboardUtils.hideSoftInput(getActivity());
                    setPwdAndLogin();
                    break;
            }
            return true;
        }
        return false;
    }


    private void showSetPwdView() {
        mIncludeLoginView.setVisibility(View.GONE);
        mIncludeSetPwdView.setVisibility(View.VISIBLE);
        mIncludeCodeView.setVisibility(View.GONE);
    }

    private void showForgetPwdView() {
        mIncludeLoginView.setVisibility(View.GONE);
        mIncludeSetPwdView.setVisibility(View.GONE);
        mIncludeCodeView.setVisibility(View.VISIBLE);
        mTvAgreeProtocol.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(mPhone)) {
            mEdtPhone.setText(mPhone);
            mEdtPhone.setSelection(mPhone.length());
        }
    }

    @OnClick({R.id.btn_login_next, R.id.btn_code_next, R.id.btn_pwd_next, R.id.tv_forget_pwd, R.id.tv_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_next:
                login();
                break;
            case R.id.btn_code_next:
                verifyCode();
                break;
            case R.id.btn_pwd_next:
                setPwdAndLogin();
                break;
            case R.id.tv_send_code:
                getVerifyCode();
                break;
            case R.id.tv_forget_pwd:
                showForgetPwdView();
                break;
        }
    }

    private void login() {
        mPhone = mEdtLoginAccount.getText().toString().trim();
        String pwd = mEdtLoginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            ToastUtils.show(R.string.phone_rrid_cannot_null);
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show(R.string.pwd_cannot_null);
            return;
        }
        doLogin(mPhone, pwd);

    }

    private void doLogin(String phone, String pwd) {
        ApiUtils.login(phone, pwd, new BaseCallback<BaseModel<UserInfo>>() {
            @Override
            protected void onSuccess(BaseModel<UserInfo> model) throws Exception {
                UserInfo userInfo = model.getData();
                if (null != userInfo) {
                    UserUtils.login(userInfo);
                    getBaseActivity().gotoActivity(MainActivity.class, true);
                }
            }
        });
    }


    public void getVerifyCode() {
        mPhone = mEdtPhone.getText().toString().trim();
        if (!RegexUtils.isMobileSimple(mPhone)) {
            ToastUtils.show(R.string.please_fill_correct_phone_number);
            return;
        }
        ApiUtils.getVCode(mPhone, new BaseCallback<BaseModel<VCode>>() {
            @Override
            protected void onSuccess(BaseModel<VCode> model) throws Exception {
                mDownTimer.start();
                mRandCode = model.getData().getRandCode();
                ToastUtils.show("验证码已发送，请等待接收");
                mEdtCode.requestFocus();
            }
        });
    }

    private void verifyCode() {
        String vCode = mEdtCode.getText().toString();
        if (!TextUtils.isEmpty(mRandCode) && !TextUtils.isEmpty(vCode) && mRandCode.equals(vCode)) {
            showSetPwdView();
        } else {
            ToastUtils.show("验证失败，请输入正确的验证码");
        }
    }


    private void setPwdAndLogin() {

        final String pwdPre = mEdtPassword.getText().toString();
        String pwdConfirm = mEdtPasswordConfirm.getText().toString();
        //TODO  验证6-16位
        if (!pwdPre.equals(pwdConfirm)) {
            ToastUtils.show("密码不一致，请确认密码一致");
            return;
        }

        ApiUtils.resetPwd(mPhone, pwdPre, new BaseCallback<BaseModel<DefJsonResult>>() {
            @Override
            protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                ToastUtils.show("密码修改成功");
                doLogin(mPhone, pwdPre);
            }
        });
    }


    //
    //    @Override
    //    public void onLocationChanged(AMapLocation aMapLocation) {
    //
    //        if(aMapLocation.getErrorCode()==0){
    //            longtitude=aMapLocation.getLongitude();
    //            laititude=aMapLocation.getLatitude();
    //        }else{
    //            Toast.makeText(getContext(),"定位失败!",Toast.LENGTH_SHORT).show();
    //        }

    //        if(username==null||password==null){
    //            Toast.makeText(getActivity(),"未填写用户名或密码",Toast.LENGTH_SHORT).show();
    //        }else if(!hasNetWork()){
    //            Toast.makeText(getActivity(),"未连接到网络",Toast.LENGTH_SHORT).show();
    //        }else{
    //            accountEdt.setEnabled(false);
    //            passwordEdt.setEnabled(false);
    //
    ////            if(encodePwd==null){
    ////                Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
    ////                return;
    ////            }
    //
    //            new LoginAsyncTask().execute(username,password, Api.USER_LOGIN);
    //        }
    //    }
    //
    //

}
