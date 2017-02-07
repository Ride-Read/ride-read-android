package com.rideread.rideread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.rideread.rideread.adapter.ChatMessageAdapter;
import com.rideread.rideread.bean.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/6.
 */

public class ChatActivity extends BaseActivity {

    private  List<ChatMessage> datas;
    private  ChatMessageAdapter adapter;
    private ListView chatlist;

    protected AVIMConversation imConversation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.im_chat_layout);
        initDatas();

    }

    private void initDatas() {
        //模拟本地数据
        datas=new ArrayList<ChatMessage>();
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

        chatlist=(ListView) findViewById(R.id.im_chat_listview);

        adapter=new ChatMessageAdapter(this,datas,2);
        chatlist.setAdapter(adapter);

    }


    private void sendTextMessage(String text){
        AVIMTextMessage message = new AVIMTextMessage();
        message.setText(text);
        datas.add(new ChatMessage(null,text,R.mipmap.me,null,1));
        adapter.notifyDataSetChanged();
        chatlist.smoothScrollToPosition(adapter.getCount()-1);
        imConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {

            }
        });
    }
}
