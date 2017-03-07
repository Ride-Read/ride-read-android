package com.rideread.rideread.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.AttentionAdapter;
import com.rideread.rideread.bean.Follower;
import com.rideread.rideread.bean.PersonalInfoFollower;
import com.rideread.rideread.bean.PostParams;
import com.rideread.rideread.common.Api;
import com.rideread.rideread.common.Constants;
import com.rideread.rideread.common.OkHttpUtils;
import com.rideread.rideread.common.PreferenceUtils;
import com.rideread.rideread.common.TimeStamp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class MineFansActivity extends BaseActivity {

    private ListView listView;
    List<Follower> lists=new ArrayList<Follower>();
    private AttentionAdapter adapter;
    private PostParams postParams;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_fans_layout);
        listView=(ListView)findViewById(R.id.mine_fans_listview);
        adapter=new AttentionAdapter(this,R.layout.mine_attention_list_item,lists);
        listView.setAdapter(adapter);
        postParams=PreferenceUtils.getInstance().getPostParams(getApplicationContext());
        new GetFollower().execute();
    }

//    private void initData() {
//
//
////        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
////        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
////        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
////        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
//        listView.setAdapter();
//    }

    class GetFollower extends AsyncTask<String,Void,PersonalInfoFollower>{
        @Override
        protected PersonalInfoFollower doInBackground(String... params) {
            return OkHttpUtils.getInstance().getFollower(postParams.getUid(),postParams.getToken(), TimeStamp.getTimeStamp(), Api.FOLLOWER);
        }

        @Override
        protected void onPostExecute(PersonalInfoFollower personalInfoFollower) {
            super.onPostExecute(personalInfoFollower);

            if(personalInfoFollower==null){
                Toast.makeText(MineFansActivity.this,"获取粉丝列表失败",Toast.LENGTH_SHORT).show();
            }else{
                if(personalInfoFollower.getStatus()== Constants.SUCCESS){
                    lists.clear();
                    lists.addAll(personalInfoFollower.getFollower());
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MineFansActivity.this,"获取粉丝列表失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
