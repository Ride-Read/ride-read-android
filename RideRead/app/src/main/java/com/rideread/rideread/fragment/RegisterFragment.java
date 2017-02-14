package com.rideread.rideread.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rideread.rideread.R;
import com.rideread.rideread.RegisterActivity;
import com.rideread.rideread.RegisterPhoneActivity;
import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.OkHttpUtils;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private String tagPhone="phoneNumView",tagInvite="inviteCodeView",tagPwd="setPwdView",tagName="setUnameView";
    private View phoneNumView, inviteCodeView, setPwdView, setUnameView;
    private String inviteCode;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.register_fragment_layout,container,false);
        inviteCodeView =mView.findViewById(R.id.register_invite_include_layout);
        initButton(inviteCodeView,R.id.register_btn_next,tagInvite);

        phoneNumView =mView.findViewById(R.id.register_phonenum_include_ly);
        initButton(phoneNumView,R.id.register_btn_next,tagPhone);

        setPwdView =mView.findViewById(R.id.register_setpwd_include_ly);
        initButton(setPwdView,R.id.register_btn_next,tagPwd);

        setUnameView =mView.findViewById(R.id.register_setuname_include_ly);
        initButton(setUnameView,R.id.register_btn_complete,null);
        return mView;
    }


    public void initButton(View v,int id,String tag){
        Button btn=(Button)v.findViewById(id);
        btn.setTag(tag);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.register_btn_next:
                String tag=(String)v.getTag();
                if(tag.equals(tagInvite)){
                    EditText editText=(EditText)inviteCodeView.findViewById(R.id.register_edt_invitationcode);
                    inviteCode=editText.getText().toString().trim();
                    if(inviteCode==null){
                        Toast.makeText(getActivity(),"未填写邀请码",Toast.LENGTH_SHORT).show();
                    }else{
                        new InviteCodeAysncTask().execute(inviteCode, Api.CONFIM_INVITECODE);
                    }
                }else if(tag.equals(tagPhone)){
                    changeView(phoneNumView, setPwdView);
                }else if(tag.equals(tagPwd)){
                    changeView(setPwdView, setUnameView);
                }
                break;
            case R.id.register_btn_complete:

                break;

        }

    }

    private void changeView(View hideView,View showView){

        hideView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
    }


    /**
     * 验证邀请码的异步线程
     */
    class InviteCodeAysncTask extends AsyncTask<String,String,LoginMessageEntity> {
        @Override
        protected LoginMessageEntity doInBackground(String... params) {
            return OkHttpUtils.getInstance().testInviteCode(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(LoginMessageEntity loginMessageEntity) {
            super.onPostExecute(loginMessageEntity);
            if(loginMessageEntity!=null){
                int resultCode=loginMessageEntity.getResultCode();
                String msg=loginMessageEntity.getMsg();
                if(resultCode==1){
                    changeView(inviteCodeView, phoneNumView);
                }else {
                    Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity(),"未知错误",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
