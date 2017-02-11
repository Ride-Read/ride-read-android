package com.rideread.rideread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rideread.rideread.adapter.MessageListAdapter;
import com.rideread.rideread.bean.MessageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/6.
 */

public class MessageListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.message_listview);
        initData();
    }

    private void initData() {

        final List<MessageEntity> datas=new ArrayList<MessageEntity>();

        datas.add(new MessageEntity("131","什么时候出发？",R.mipmap.me,"13:25"));
        datas.add(new MessageEntity("121","哦哦",R.mipmap.me,"13:25"));
        datas.add(new MessageEntity("一缕阳光","什么时候出发？",R.mipmap.me,"13:25"));
        datas.add(new MessageEntity("阅约匹配","菲菲等3人",R.mipmap.me,"13:25"));
        datas.add(new MessageEntity("系统","欢迎使用",R.mipmap.me,"13:25"));
        ListView listView=(ListView) findViewById(R.id.message_listview);
        listView.setAdapter(new MessageListAdapter(datas,this,R.layout.message_listitem));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menberId=datas.get(position).getAuthor();
                Intent intent=new Intent(MessageListActivity.this,ChatActivity.class);
                intent.putExtra("menberid",menberId);
                startActivity(intent);
            }
        });
    }
}
