package com.rideread.rideread.module.profile.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.ChatMsgAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.event.ImTypeMsgEvent;
import com.rideread.rideread.common.event.ImTypeMsgResendEvent;
import com.rideread.rideread.common.util.MsgUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.result.UserBaseInfo;
import com.rideread.rideread.function.net.im.AVImClientManager;
import com.rideread.rideread.function.net.im.NotificationUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class ChatSingleActivity extends BaseActivity {
    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.edt_chat_input) EditText mEdtChatInput;

    public final static String CHAT_USER = "chat_user";
    public final static String CHAT_USER_NAME = "chat_user_name";
    private UserBaseInfo mUser;
    private String mUserName;
    private AVIMConversation mImConversation;
    private LinearLayoutManager layoutManager;

    private ChatMsgAdapter mMsgAdapter;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_chat_single;
    }

    @Override
    public void initView() {
        mUser = (UserBaseInfo) getIntent().getSerializableExtra(CHAT_USER);
        mUserName = getIntent().getStringExtra(CHAT_USER_NAME);
        if (TextUtils.isEmpty(mUserName)) mUserName = "消息";
        new TitleBuilder(this).setTitleText(mUserName).IsBack(true).setLeftOnClickListener(v -> finish()).build();

        mSwipeRefreshLayout.setEnabled(false);
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        mMsgAdapter = new ChatMsgAdapter();
        mRecycleView.setAdapter(mMsgAdapter);
        getConversation(mUser.getUid() + "");

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            AVIMMessage message = mMsgAdapter.getFirstMessage();
            if (null != mImConversation) {
                mImConversation.queryMessages(message.getMessageId(), message.getTimestamp(), 20, new AVIMMessagesQueryCallback() {
                    @Override
                    public void done(List<AVIMMessage> list, AVIMException e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (filterException(e)) {
                            if (null != list && list.size() > 0) {
                                mMsgAdapter.addMessageList(list);
                                mMsgAdapter.notifyDataSetChanged();

                                layoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
                            }
                        }
                    }
                });
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getConversation(final String memberId) {
        final AVIMClient client = AVImClientManager.getInstance().getClient();
        if (null == client) return;
        AVIMConversationQuery conversationQuery = client.getQuery();
        conversationQuery.withMembers(Arrays.asList(memberId), true);
        conversationQuery.whereEqualTo("customConversationType", 1);
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    //注意：此处仍有漏洞，如果获取了多个 conversation，默认取第一个
                    if (null != list && list.size() > 0) {
                        setConversation(list.get(0));
                    } else {
                        HashMap<String, Object> attributes = new HashMap<String, Object>();
                        attributes.put("customConversationType", 1);
                        client.createConversation(Arrays.asList(memberId), null, attributes, false, new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation avimConversation, AVIMException e) {
                                setConversation(avimConversation);
                            }
                        });
                    }
                }
            }
        });
    }

    public void setConversation(AVIMConversation conversation) {
        if (null != conversation) {
            mImConversation = conversation;
            mSwipeRefreshLayout.setEnabled(true);
            fetchMessages();
            NotificationUtils.addTag(conversation.getConversationId());
        }
    }

    /**
     * 拉取消息，必须加入 conversation 后才能拉取消息
     */
    private void fetchMessages() {
        if (null != mImConversation) {
            mImConversation.queryMessages(new AVIMMessagesQueryCallback() {
                @Override
                public void done(List<AVIMMessage> list, AVIMException e) {
                    if (filterException(e)) {
                        mMsgAdapter.setMessageList(list);
                        mMsgAdapter.setMyFaceUrl(UserUtils.getCurUser().getFaceUrl());
                        mMsgAdapter.setOtherFaceUrl(mUser.getFaceUrl());
                        mRecycleView.setAdapter(mMsgAdapter);
                        mMsgAdapter.notifyDataSetChanged();
                        scrollToBottom();
                    }
                }
            });
        }
    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(mMsgAdapter.getItemCount() - 1, 0);
    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            return false;
        } else {
            return true;
        }
    }

    @OnClick({R.id.img_emoj, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_emoj:
                break;
            case R.id.btn_send:
                sendMsg();
                break;
        }
    }

    private void sendMsg() {
        String msg = mEdtChatInput.getText().toString();
        AVIMTextMessage message = new AVIMTextMessage();
        if (!TextUtils.isEmpty(msg)) {
            message.setText(msg);
            mMsgAdapter.addMessage(message);
            mMsgAdapter.notifyDataSetChanged();
            scrollToBottom();
            mEdtChatInput.setText("");
            mImConversation.sendMessage(message, new AVIMConversationCallback() {
                @Override
                public void done(AVIMException e) {
                    mMsgAdapter.notifyDataSetChanged();
                    MsgUtils.addMsgInfo(mUser, msg, System.currentTimeMillis());
                }
            });
        }

    }

    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    @Subscribe(threadMode = MAIN)
    public void onSendMsg(ImTypeMsgEvent event) {
        if (null != mImConversation && null != event && mImConversation.getConversationId().equals(event.conversation.getConversationId())) {
            mMsgAdapter.addMessage(event.message);
            mMsgAdapter.notifyDataSetChanged();
            scrollToBottom();
        }
    }

    /**
     * 重新发送已经发送失败的消息
     */
    @Subscribe(threadMode = MAIN)
    public void onResendMsg(ImTypeMsgResendEvent event) {
        if (null != mImConversation && null != event) {
            if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == event.message.getMessageStatus() && mImConversation.getConversationId().equals(event.message.getConversationId())) {
                mImConversation.sendMessage(event.message, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        mMsgAdapter.notifyDataSetChanged();
                    }
                });
                mMsgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mImConversation) {
            NotificationUtils.addTag(mImConversation.getConversationId());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NotificationUtils.removeTag(mImConversation.getConversationId());
    }
}
