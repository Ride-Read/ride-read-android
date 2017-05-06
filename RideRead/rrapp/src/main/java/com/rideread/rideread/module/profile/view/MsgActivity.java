package com.rideread.rideread.module.profile.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.MsgUserAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.event.MsgListRefreshEvent;
import com.rideread.rideread.common.util.MsgUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.data.been.MsgInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class MsgActivity extends BaseActivity {
    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    private List<MsgInfo> mMsgInfoList;
    private LinearLayoutManager layoutManager;
    private MsgUserAdapter mMsgUserAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_recycle;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText("消息").IsBack(true).setLeftOnClickListener(v -> finish()).build();
//        final AVIMClient client = AVImClientManager.getInstance().getClient();
        mMsgInfoList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        mMsgUserAdapter = new MsgUserAdapter(this, mMsgInfoList);
        mRecycleView.setAdapter(mMsgUserAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            refreshMsgUserList();
        });
        refreshMsgUserList();
    }

    private void refreshMsgUserList() {
        JSONObject msgsJson = MsgUtils.getMsgUserObj();
        if (null != msgsJson) {
            Set<String> keySet = msgsJson.keySet();
            Iterator keyCursor = keySet.iterator();
            String key;
            mMsgInfoList.clear();
            while (keyCursor.hasNext()) {
                key = (String) keyCursor.next();
                mMsgInfoList.add(msgsJson.getObject(key, MsgInfo.class));
                mMsgUserAdapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe(threadMode = MAIN, sticky = true)
    public void onRefreshMsgs(MsgListRefreshEvent event) {
        EventBus.getDefault().removeStickyEvent(MsgListRefreshEvent.class);
        refreshMsgUserList();
    }


}
