package com.rideread.rideread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.rideread.rideread.adapter.ChatMessageAdapter;
import com.rideread.rideread.bean.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/6.
 */

public class ChatActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.im_chat_layout);
        initDatas();
    }

    private void initDatas() {
        //模拟本地数据
        List<ChatMessage> datas=new ArrayList<ChatMessage>();
        datas.add(new ChatMessage(null,"消息内容",R.mipmap.me,null,1));

        datas.add(new ChatMessage(null,"消息内容消息内容消息内容",R.mipmap.me,null,0));
        datas.add(new ChatMessage(null,"消息内容",R.mipmap.me,null,1));
        datas.add(new ChatMessage(null,"消息内容消息内容消息内容消息内容消息内容",R.mipmap.me,null,0));

        initView(datas);

    }

    private void initView(List<ChatMessage> datas){

        ImageView back=(ImageView)findViewById(R.id.left_setting_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatActivity.this.onBackPressed();
            }
        });

        ListView chatlist=(ListView) findViewById(R.id.im_chat_listview);
        chatlist.setAdapter(new ChatMessageAdapter(this,datas,2));
    }
}
