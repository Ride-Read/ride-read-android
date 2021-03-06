package com.rideread.rideread.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.rideread.rideread.R;
import com.rideread.rideread.adapter.ChatMessageAdapter;
import com.rideread.rideread.bean.ChatMessage;
import com.rideread.rideread.db.RideReadDBHelper;
import com.rideread.rideread.event.ImTypeMessageEvent;
import com.rideread.rideread.gen.DaoSession;
import com.rideread.rideread.im.AVImClientManager;
import com.rideread.rideread.im.NotificationUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/6.
 */

public class ChatActivity extends BaseActivity {

    private  List<ChatMessage> datas;
    private  ChatMessageAdapter adapter;
    private ListView chatlist;
    private String menberId;
    private EditText editor;
    private DaoSession mDaoSession;

    protected AVIMConversation imConversation;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.im_chat_layout);
        EventBus.getDefault().register(this);
        initDatas();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != imConversation) {
            NotificationUtils.addTag(imConversation.getConversationId());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NotificationUtils.removeTag(imConversation.getConversationId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initDatas() {
        if(mDaoSession==null){
            mDaoSession=RideReadDBHelper.getInstance().getmDaoSession();
        }
        menberId=getIntent().getStringExtra("menberid");
        //模拟本地数据,此处应该是查询本地聊天记录.
        //datas=new ArrayList<ChatMessage>();

        mDaoSession.insert(new ChatMessage(1,R.mipmap.me,menberId,"消息内容",null));
        mDaoSession.insert(new ChatMessage(0,R.mipmap.me,"me","消息内容消息内容消息内容",null));
//        datas.add();
//        datas.add();
//        datas.add(new ChatMessage(0,R.mipmap.me,"me","消息内容消息内容消息内容消息内容消息内容",null));


        datas= mDaoSession.loadAll(ChatMessage.class);


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
        editor=(EditText)findViewById(R.id.im_chat_editor);
        adapter=new ChatMessageAdapter(this,datas,2);
        chatlist.setAdapter(adapter);
        getConservation(menberId);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImTypeMessageEvent event) {
        if (null != imConversation && null != event &&
                imConversation.getConversationId().equals(event.conversation.getConversationId())) {
            if (event.message instanceof AVIMTextMessage) {
                String msg=((AVIMTextMessage) event.message).getText();
                ChatMessage chatMessage=new ChatMessage(0,R.mipmap.me,menberId,msg,null);
                adapter.addMessage(chatMessage);

                adapter.notifyDataSetChanged();
                chatlist.smoothScrollToPosition(adapter.getCount()-1);
            }
        }
    }

    public void getConservation(final String menberId){
        final AVIMClient client = AVImClientManager.getInstance().getClient();
        AVIMConversationQuery conversationQuery = client.getQuery();
        conversationQuery.withMembers(Arrays.asList(menberId), true);
        conversationQuery.whereEqualTo("customConversationType",1);
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    //注意：此处仍有漏洞，如果获取了多个 conversation，默认取第一个
                    if (null != list && list.size() > 0) {
                        imConversation=list.get(0);
                    } else {
                        HashMap<String,Object> attributes=new HashMap<String, Object>();
                        attributes.put("customConversationType",1);
                        client.createConversation(Arrays.asList(menberId), null, attributes, false , new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation avimConversation, AVIMException e) {
                                imConversation=avimConversation;
                            }
                        });
                    }
                }
            }
        });
    }

    private void sendTextMessage(String text){
        final  AVIMTextMessage message = new AVIMTextMessage();
        message.setText(text);
        datas.add(new ChatMessage(1,R.mipmap.me,null,text,null));
        adapter.notifyDataSetChanged();
        chatlist.smoothScrollToPosition(adapter.getCount()-1);
        editor.setText("");
        imConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if(filterException(e)){
                    Log.e("信息发送","信息发送成功");
                }else{
                    Log.e("信息发送","信息发送失败");
                }
            }
        });



    }

    public void sendMessage(View v){
        String text=editor.getText().toString();
        sendTextMessage(text);
    }


}
