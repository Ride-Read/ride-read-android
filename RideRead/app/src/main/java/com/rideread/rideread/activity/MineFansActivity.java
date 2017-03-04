package com.rideread.rideread.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.AttentionAdapter;
import com.rideread.rideread.bean.Attention;
import com.rideread.rideread.bean.PersonalInfoFollower;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.TimeStamp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class MineFansActivity extends BaseActivity {

    private ListView listView;
    private int uId;
    private String token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_fans_layout);
        listView=(ListView)findViewById(R.id.mine_fans_listview);
        Intent intent=getIntent();
        token=intent.getStringExtra("token");
        uId=intent.getIntExtra("uid",-1);//-1表示获取失败
        new GetFollower().execute();
        initData();
    }

    private void initData() {

        List<Attention> lists=new ArrayList<Attention>();
        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
        listView.setAdapter(new AttentionAdapter(this,R.layout.mine_attention_list_item,lists));
    }

    class GetFollower extends AsyncTask<String,Void,PersonalInfoFollower>{
        @Override
        protected PersonalInfoFollower doInBackground(String... params) {
            return OkHttpUtils.getInstance().getFollower(uId,token, TimeStamp.getTimeStamp(), Api.FOLLOWER);
        }

        @Override
        protected void onPostExecute(PersonalInfoFollower personalInfoFollower) {
            super.onPostExecute(personalInfoFollower);

            if(personalInfoFollower==null){

            }else{

            }
        }
    }


}
