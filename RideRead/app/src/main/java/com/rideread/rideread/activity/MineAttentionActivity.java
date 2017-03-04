package com.rideread.rideread.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.AttentionAdapter;
import com.rideread.rideread.bean.Attention;
import com.rideread.rideread.bean.PersonalInfoFollowing;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.TimeStamp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class MineAttentionActivity extends BaseActivity {

    private ListView listView;
    private int uId;
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_attention_layout);
        Intent intent=getIntent();
        token=intent.getStringExtra("token");
        uId=intent.getIntExtra("uid",-1);//-1表示获取失败
        listView=(ListView)findViewById(R.id.mine_attention_listview);
        new GetFolowings().execute();
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

    class GetFolowings extends AsyncTask<String,Void,PersonalInfoFollowing>{
        @Override
        protected PersonalInfoFollowing doInBackground(String... params) {

            OkHttpUtils.getInstance().getFollowing(uId,token, TimeStamp.getTimeStamp(),Api.FOLLOWING);
            return null;
        }

        @Override
        protected void onPostExecute(PersonalInfoFollowing personalInfoFollowing) {
            super.onPostExecute(personalInfoFollowing);
            if(personalInfoFollowing==null){
                Toast.makeText(MineAttentionActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
            }else{

            }
        }
    }


}
