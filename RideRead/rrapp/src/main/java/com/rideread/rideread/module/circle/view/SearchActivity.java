package com.rideread.rideread.module.circle.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.SearchUserAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.data.result.FollowUser;
import com.rideread.rideread.data.result.SearchUsers;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.edt_search) EditText mEdtSearch;
    @BindView(R.id.img_clear) ImageView mImgClear;
    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    private SearchUserAdapter mAdapter;
    private List<FollowUser> mSearchUsers;


    private LinearLayoutManager mLayoutManager;

    private boolean isLoadingMore;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {

        mSearchUsers = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            search();
        });

        mRecycleView.setHasFixedSize(true);

        mAdapter = new SearchUserAdapter(this, mSearchUsers);
        mRecycleView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if (!isLoadingMore) {
                        search();
                    }
                }
            }
        });

        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence keyword, int start, int before, int count) {
                search();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void search() {
        String keyword = mEdtSearch.getText().toString();
        if (TextUtils.isEmpty(keyword)) {
            mImgClear.setVisibility(View.GONE);
        } else {
            mImgClear.setVisibility(View.VISIBLE);
            ApiUtils.searchUser(keyword.toString(), new BaseCallback<BaseModel<SearchUsers>>() {
                @Override
                protected void onSuccess(BaseModel<SearchUsers> model) throws Exception {
                    SearchUsers users = model.getData();
                    if (null != users) {
                        mSearchUsers.clear();
                        List<FollowUser> followeds = users.getFolloweds();
                        if (!ListUtils.isEmpty(followeds)) {
                            mSearchUsers.addAll(followeds);
                        }
                        List<FollowUser> followers = users.getFollowers();
                        if (!ListUtils.isEmpty(followers)) {
                            mSearchUsers.addAll(followers);
                        }
                        mAdapter.notifyDataSetChanged();
                    }


                }
            });
        }
    }


    @OnClick({R.id.img_clear, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                mEdtSearch.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }
}
