package com.rideread.rideread.module.circle.view;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.CommentsAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.dialog.ShareCollectDialogFragment;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.KeyboardUtils;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.ScreenUtils;
import com.rideread.rideread.common.util.TimeUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.common.widget.HLinearLayout;
import com.rideread.rideread.common.widget.NineGridImgView.NineGridImgView;
import com.rideread.rideread.common.widget.NineGridImgView.NineGridImgViewAdapter;
import com.rideread.rideread.data.result.Comment;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.data.result.MomentUser;
import com.rideread.rideread.data.result.ThumbsUpUser;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;
import com.rideread.rideread.module.profile.view.UserMomentsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MomentDetailActivity extends BaseActivity {

    public static String SELECTED_MOMENT = "selected_moment";

    @BindView(R.id.lv_comments) ListView mLvComments;
    @BindView(R.id.edt_comment) EditText mEdtComment;
    @BindView(R.id.btn_thumbs_up) Button mBtnThumbsUp;

    private SimpleDraweeView mImgAvatar;
    private TextView mTvName;
    private TextView mTvTime;
    private ImageButton mBtnAttention;
    private TextView mTvMomentText;
    private NineGridImgView mNineGridImgView;
    private HLinearLayout mHllThumpUps;
    private TextView mTvCommentCount;
    private TextView mTvThumbCount;

    private ShareCollectDialogFragment mMoreDialog;
    private Moment mCurMoment;
    private MomentUser mCurMomentUser;
    private CommentsAdapter mAdapter;
    private List<Comment> mCommentList;

    private boolean isAttention;
    private boolean isThumbsUp;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_moment_detail;
    }


    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText(R.string.details).IsBack(true).setRightImage(R.drawable.icon_more).build();

        View momentHeader = initMomentHeader();

        mCurMoment = (Moment) getIntent().getExtras().getSerializable(SELECTED_MOMENT);
        if (null == mCurMoment) {
            ToastUtils.show("无数据");
            return;
        }

        mCurMomentUser = mCurMoment.getUser();
        if (null != mCurMomentUser) {
            ImgLoader.getInstance().displayImage(mCurMoment.getUser().getFaceUrl(), mImgAvatar);
            mTvName.setText(mCurMomentUser.getUsername());
            mTvTime.setText(TimeUtils.getFriendlyTimeSpanByNow(mCurMoment.getCreatedAt()));
            isAttention = 0 == mCurMomentUser.getIsFollowed();
            mBtnAttention.setBackgroundResource(isAttention ? R.drawable.icon_attented : R.drawable.icon_attention);
            mTvMomentText.setText(mCurMoment.getMsg());
            mTvCommentCount.setText("评论 " + mCurMoment.getComment().size());
            mTvThumbCount.setText(Integer.toString(mCurMoment.getThumbsUp().size()));

            if (mCurMomentUser.getUid() == UserUtils.getUid())
                mBtnAttention.setVisibility(View.GONE);

            mImgAvatar.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(UserMomentsActivity.SELECTED_UID, mCurMomentUser.getUid());
                bundle.putString(UserMomentsActivity.SELECTED_USERNAME, mCurMomentUser.getUsername());
                gotoActivity(UserMomentsActivity.class, bundle);
            });
            mBtnAttention.setOnClickListener(v -> {
                if (isAttention) {
                    ApiUtils.unfollow(mCurMomentUser.getUid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                        @Override
                        protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                            mBtnAttention.setBackgroundResource(R.drawable.icon_attention);
                            isAttention = !isAttention;
                        }
                    });
                } else {
                    ApiUtils.follow(mCurMomentUser.getUid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                        @Override
                        protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                            mBtnAttention.setBackgroundResource(R.drawable.icon_attented);
                            isAttention = !isAttention;
                        }
                    });
                }
            });


            List<String> pictures = mCurMoment.getPictures();
            if (!ListUtils.isEmpty(pictures)) {
                mNineGridImgView.setAdapter(nineGridAdapter);
                mNineGridImgView.setImagesData(pictures);
                mNineGridImgView.setVisibility(View.VISIBLE);
            } else {
                mNineGridImgView.setVisibility(View.GONE);
            }
        }


        List<ThumbsUpUser> thumbsUpUsers = mCurMoment.getThumbsUp();
        SimpleDraweeView thumbUpUserAvatar;
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(4f);
        roundingParams.setRoundAsCircle(true);
        for (ThumbsUpUser thumbsUpUser : thumbsUpUsers) {
            thumbUpUserAvatar = new SimpleDraweeView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) ScreenUtils.dp2px(28f), (int) ScreenUtils.dp2px(28f));
            thumbUpUserAvatar.setLayoutParams(params);
            thumbUpUserAvatar.getHierarchy().setRoundingParams(roundingParams);
            ImgLoader.getInstance().displayImage(thumbsUpUser.getFaceUrl(), thumbUpUserAvatar);
            mHllThumpUps.addView(thumbUpUserAvatar);
            if (!mHllThumpUps.canAddView()) {
                break;
            }
        }
        if (!ListUtils.isEmpty(thumbsUpUsers)) {
            mHllThumpUps.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(ThumbsUpUserActivity.MOMENT_ID, mCurMoment.getMid());
                gotoActivity(ThumbsUpUserActivity.class, bundle);
            });
        }

        for (ThumbsUpUser thumbsUpUser : thumbsUpUsers) {
            if (thumbsUpUser.getUid() == UserUtils.getUid()) {
                isThumbsUp = true;
                break;
            }
        }
        mBtnThumbsUp.setBackgroundResource(isThumbsUp ? R.drawable.ic_thumb_up_on : R.drawable.ic_thumb_up_off);

        mLvComments.addHeaderView(momentHeader);
        mAdapter = new CommentsAdapter(this);
        mCommentList = mCurMoment.getComment();
        mAdapter.setItems(mCommentList);
        mLvComments.setAdapter(mAdapter);
    }

    @NonNull
    private View initMomentHeader() {
        View momentHeader = LayoutInflater.from(this).inflate(R.layout.view_moment_detail_header, null);
        mImgAvatar = ButterKnife.findById(momentHeader, R.id.img_avatar);
        mTvName = ButterKnife.findById(momentHeader, R.id.tv_name);
        mTvTime = ButterKnife.findById(momentHeader, R.id.tv_time);
        mBtnAttention = ButterKnife.findById(momentHeader, R.id.btn_attention);
        mTvMomentText = ButterKnife.findById(momentHeader, R.id.tv_moment_text);
        mNineGridImgView = ButterKnife.findById(momentHeader, R.id.nine_grid_img_view);
        mHllThumpUps = ButterKnife.findById(momentHeader, R.id.hll_thump_ups);
        mTvCommentCount = ButterKnife.findById(momentHeader, R.id.tv_comment_count);
        mTvThumbCount = ButterKnife.findById(momentHeader, R.id.tv_thumb_count);
        mBtnAttention.setOnClickListener(v -> {
            isAttention = !isAttention;
            ToastUtils.show("关注" + isAttention);
        });
        return momentHeader;
    }

    NineGridImgViewAdapter<String> nineGridAdapter = new NineGridImgViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, SimpleDraweeView imageView, String url) {
            ImgLoader.getInstance().displayImage(url, imageView);
        }

        @Override
        protected void onItemImageClick(Context context, SimpleDraweeView imageView, int index, List<String> list) {
            super.onItemImageClick(context, imageView, index, list);

            StringBuilder urls = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                urls.append(list.get(i));
                urls.append(",");
            }
            urls.deleteCharAt(urls.length() - 1);

            Bundle bundle = new Bundle();
            bundle.putString(ImagesActivity.IMAGE_URLS, urls.toString());
            bundle.putInt(ImagesActivity.POSITION, index);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                gotoActivity(ImagesActivity.class, bundle);
            } else {
                Intent intent = new Intent(MomentDetailActivity.this, ImagesActivity.class);
                intent.putExtras(bundle);
                //5.0及以上系统实现共享元素动画
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MomentDetailActivity.this, imageView, getString(R.string.transition_image));
                ActivityCompat.startActivity(MomentDetailActivity.this, intent, options.toBundle());
            }
        }
    };

    @OnClick({R.id.img_top_bar_left, R.id.tv_top_bar_right, R.id.btn_thumbs_up, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_bar_left:
                finish();
                break;
            case R.id.tv_top_bar_right:
                showMoreDialog();
                break;
            case R.id.btn_thumbs_up:
                thumbsUp();

                break;
            case R.id.btn_send:
                postComment();
                break;
        }
    }

    private void thumbsUp() {
        if (isThumbsUp) {
            ApiUtils.cancelThumbsUp(mCurMoment.getMid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                @Override
                protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                    isThumbsUp = !isThumbsUp;
                    mBtnThumbsUp.setBackgroundResource(R.drawable.ic_thumb_up_off);

                }
            });
        } else {
            ApiUtils.addThumbsUp(mCurMoment.getMid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                @Override
                protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                    isThumbsUp = !isThumbsUp;
                    mBtnThumbsUp.setBackgroundResource(R.drawable.ic_thumb_up_on);

                }
            });

        }
    }

    private void postComment() {
        String commentStr = mEdtComment.getText().toString().trim();
        if (TextUtils.isEmpty(commentStr)) {
            ToastUtils.show("评论不能为空");
            return;
        }
        if (null == mCurMoment) {
            ToastUtils.show("加载动态失败");
            return;
        }
        ApiUtils.addComment(mCurMoment.getMid(), mCurMoment.getUid(), commentStr, new BaseCallback<BaseModel<Comment>>() {
            @Override
            protected void onSuccess(BaseModel<Comment> model) throws Exception {
                Comment curComment = model.getData();
                if (null != curComment) {
                    mCommentList.add(0, curComment);
                    mAdapter.notifyDataSetChanged();
                    mEdtComment.setText("");
                    KeyboardUtils.hideSoftInput(MomentDetailActivity.this);
                } else {
                    ToastUtils.show("评论失败");
                }
            }
        });

    }

    private void showMoreDialog() {
        if (null == mMoreDialog) {
            mMoreDialog = new ShareCollectDialogFragment();
        }
        mMoreDialog.show(getSupportFragmentManager(), "dialog");
    }

    public void shareMoment() {
        ToastUtils.show("分享");
    }

    public void collectMoment() {
        if (null == mCurMoment) {
            ToastUtils.show("加载动态失败");
            return;
        }
        ApiUtils.collectMoment(mCurMoment.getMid(), new BaseCallback<BaseModel<DefJsonResult>>() {
            @Override
            protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                ToastUtils.show("收藏成功");
            }
        });

    }

}
