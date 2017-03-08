package com.rideread.rideread.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.FollowingAdapter;
import com.rideread.rideread.bean.Following;
import com.rideread.rideread.bean.PersonalInfoFollowing;
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

public class MineAttentionActivity extends BaseActivity {

    private ListView listView;
    private List<Following> lists=new ArrayList<Following>();
    private FollowingAdapter adapter;
    private PostParams postParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_attention_layout);
        listView=(ListView)findViewById(R.id.mine_attention_listview);
        adapter=new FollowingAdapter(this,R.layout.mine_attention_list_item,lists);
        listView.setAdapter(adapter);
        postParams= PreferenceUtils.getInstance().getPostParams(getApplicationContext());
        new GetFolowings().execute();

    }

//    private void initData() {
//
//        List<Attention> lists=new ArrayList<Attention>();
//        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
//        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
//        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
//        lists.add(new Attention(R.mipmap.me,"张小明","个性签名"));
//        listView.setAdapter();
//    }

    class GetFolowings extends AsyncTask<String,Void,PersonalInfoFollowing>{
        @Override
        protected PersonalInfoFollowing doInBackground(String... params) {

            return OkHttpUtils.getInstance().getFollowing(postParams.getUid(),postParams.getToken(), TimeStamp.getTimeStamp(),Api.FOLLOWING);

        }

        @Override
        protected void onPostExecute(PersonalInfoFollowing personalInfoFollowing) {
            super.onPostExecute(personalInfoFollowing);
            if(personalInfoFollowing==null){
                Toast.makeText(MineAttentionActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
            }else{
                if(personalInfoFollowing.getStatus()== Constants.SUCCESS){
//                        Toast.makeText(MineAttentionActivity.this,"获取数据成功",Toast.LENGTH_SHORT).show();
                    if(personalInfoFollowing.getFollowing()!=null){
                        lists.clear();
                        lists.addAll(personalInfoFollowing.getFollowing());
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(MineAttentionActivity.this,"您没有关注任何人",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MineAttentionActivity.this,"您没有关注任何人",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
