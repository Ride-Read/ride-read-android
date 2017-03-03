package com.rideread.rideread.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.AttentionAdapter;
import com.rideread.rideread.bean.Attention;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class MineAttentionActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_attention_layout);
        listView=(ListView)findViewById(R.id.mine_attention_listview);
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


}
