package com.rideread.rideread;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.rideread.rideread.adapter.FragmentAdapter;
import com.rideread.rideread.adapter.TabAdapterImp;
import com.rideread.rideread.bean.LoginMessageEntity;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.fragment.LoginFragment;
import com.rideread.rideread.fragment.RegisterFragment;
import com.rideread.rideread.im.AVImClientManager;
import com.rideread.rideread.widget.ScrollTabView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class LoginActivity extends AppCompatActivity {

    private EditText accountEdt,passwordEdt;
    private String account,password;
    private ViewPager viewPager;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main2);
        getSupportActionBar().hide();//隐藏标题栏
        accountEdt=(EditText) findViewById(R.id.login_edt_account);
        passwordEdt=(EditText)findViewById(R.id.login_edt_password);
        initData();
        initViewPager();
        initTabs();

    }

    private void initViewPager() {
        viewPager=(ViewPager) findViewById(R.id.login_tabview_viewpager);
        FragmentAdapter fa=new FragmentAdapter(getSupportFragmentManager(),buildFragments());
        viewPager.setAdapter(fa);
    }

    private List<Fragment> buildFragments() {

        List<Fragment> fragments = new ArrayList<Fragment>(2);
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        return fragments;
    }

    private void initTabs() {
        ImageView indicatorView=(ImageView)findViewById(R.id.login_tabview_square);
        ScrollTabView scrollTabView=(ScrollTabView)findViewById(R.id.login_scrollview_tab);
        scrollTabView.initViewPager(viewPager);

        TabAdapterImp tai=new TabAdapterImp(this);
        tai.titles=titles;
        scrollTabView.setTabAdapter(tai);
        scrollTabView.setIndicatorView(indicatorView);
    }

    private void initData() {
        titles=getResources().getStringArray(R.array.scrollview_tab_title);

    }


    public void onRegister(View v){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void onForgetPwd(View v){
        startActivity(new Intent(this,FindPassword.class));
    }

    public void onLogin(View v){
        account=accountEdt.getText().toString().trim();
        password=passwordEdt.getText().toString().trim();
        if(!TextUtils.isEmpty(account)){
            openClient(account);
        }else{
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
        }
//        if(account==null||password==null){
//            Toast.makeText(getBaseContext(),"未填写用户名或密码",Toast.LENGTH_SHORT).show();
//        }else if(!hasNetWork()){
//            Toast.makeText(getBaseContext(),"未连接到网络",Toast.LENGTH_SHORT).show();
//        }else{
//            new LoginAsyncTask().execute(account,password, Api.USER_LOGIN);
//        }

    }


    /**
     * 以用户名作为clientid登录leancloud的im服务器
     * @param account
     */
    private void openClient(String account) {

        accountEdt.setEnabled(false);
        passwordEdt.setEnabled(false);
        AVImClientManager.getInstance().open(account,new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if(e==null){
                    accountEdt.setEnabled(true);
                    passwordEdt.setEnabled(true);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class LoginAsyncTask extends AsyncTask<String,String,LoginMessageEntity>
    {
        @Override
        protected LoginMessageEntity doInBackground(String... params) {
            return  OkHttpUtils.getInstance().userLogin(params[0],params[1],params[3]);
        }

        @Override
        protected void onPostExecute(LoginMessageEntity entity) {
            super.onPostExecute(entity);
            if(entity!=null){
                int resultCode=entity.getResultCode();
                String msg=entity.getMsg();
                if(resultCode==1){
                    //从这里跳转主界面
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else if(resultCode==0){
                    Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
                }


            }else {
                Toast.makeText(getBaseContext(),"未知错误",Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * 判断网络状态
     * @return
     */
    public boolean hasNetWork(){

        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo().isAvailable()){
            return true;
        }
        return false;
    }
}
