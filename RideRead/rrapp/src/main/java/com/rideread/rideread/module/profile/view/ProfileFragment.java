package com.rideread.rideread.module.profile.view;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.event.RefreshMyMapEvent;
import com.rideread.rideread.common.event.RefreshProfileEvent;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.ShareUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.data.result.UserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.greenrobot.eventbus.ThreadMode.MAIN;


public class ProfileFragment extends BaseFragment {


    @BindView(R.id.map_view_bg) TextureMapView mMapView;
    @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_signature) TextView mTvSignature;
    @BindView(R.id.tv_msg) TextView mTvMsg;
    @BindView(R.id.tv_attention) TextView mTvAttention;
    @BindView(R.id.tv_fans) TextView mTvFans;
    @BindView(R.id.tv_tag_job) TextView mTvTagJob;
    @BindView(R.id.tv_tag_loc) TextView mTvTagLoc;
    @BindView(R.id.tv_tag_label) TextView mTvTagLabel;

    private AMap mAMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private UserInfo mUserInfo;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_profile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutRes(), container, false);
        }
        ButterKnife.bind(this, mRootView);
        mMapView.onCreate(savedInstanceState);
        initView();
        return mRootView;
    }

    @Override
    public void initView() {
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(1F));


        mUserInfo = UserUtils.getCurUser();
        ImgLoader.getInstance().displayImage(mUserInfo.getFaceUrl(), mImgAvatar);
        mTvName.setText(mUserInfo.getUsername());
        mTvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, mUserInfo.getSex() == 1 ? R.drawable.ic_sex_man : R.drawable.ic_sex_female, 0);

        String signature = mUserInfo.getSignature();
        if (TextUtils.isEmpty(signature)) signature = "人生需要留白";
        mTvSignature.setText(signature);

        String job = mUserInfo.getCareer();
        if (TextUtils.isEmpty(job)) {
            mTvTagJob.setVisibility(View.GONE);
        } else {
            mTvTagJob.setVisibility(View.VISIBLE);
            mTvTagJob.setText(job);
        }
        String location = mUserInfo.getLocation();
        if (TextUtils.isEmpty(location)) {
            mTvTagLoc.setVisibility(View.GONE);
        } else {
            mTvTagLoc.setVisibility(View.VISIBLE);
            mTvTagLoc.setText(location);
        }
        List<String> tags = mUserInfo.getTags();
        if (ListUtils.isEmpty(tags)) {
            mTvTagLabel.setVisibility(View.GONE);
        } else {
            mTvTagLabel.setVisibility(View.VISIBLE);
            mTvTagLabel.setText(tags.get(0));
        }

        mTvMsg.setText("消息 10");//TODO 获取消息数
        mTvAttention.setText("关注 " + mUserInfo.getFollowing());
        mTvFans.setText("阅粉 " + mUserInfo.getFollower());
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mMapView.onSaveInstanceState(bundle);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @OnClick({R.id.img_avatar, R.id.btn_personality_map, R.id.tv_msg, R.id.tv_attention, R.id.tv_fans, R.id.btn_post_date, R.id.btn_my_circle, R.id.btn_my_collect, R.id.btn_invite_friend, R.id.btn_setting})
    public void onViewClicked(View view) {
        Class<? extends Activity> targetActivity = null;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.img_avatar:
                targetActivity = UserInfoActivity.class;
                break;
            case R.id.btn_personality_map:
                bundle.putInt(PersonalityMapActivity.USER_ID, mUserInfo.getUid());
                getBaseActivity().gotoActivity(PersonalityMapActivity.class, bundle);
                return;
            case R.id.tv_msg:
                targetActivity = MsgActivity.class;
                break;
            case R.id.tv_attention:
                bundle.putInt(FollowUserActivity.USER_TYPE, FollowUserActivity.USER_TYPE_ATTENTION);
                getBaseActivity().gotoActivity(FollowUserActivity.class, bundle);
                return;
            case R.id.tv_fans:
                bundle.putInt(FollowUserActivity.USER_TYPE, FollowUserActivity.USER_TYPE_FANS);
                getBaseActivity().gotoActivity(FollowUserActivity.class, bundle);
                return;
            case R.id.btn_post_date:
                targetActivity = PostDateActivity.class;
                break;
            case R.id.btn_my_circle:
                targetActivity = UserMomentsActivity.class;
                break;
            case R.id.btn_my_collect:
                targetActivity = CollectActivity.class;
                break;
            case R.id.btn_invite_friend:
                ShareUtils.share(getBaseActivity(), getString(R.string.share_text, mUserInfo.getRideReadId()));
                //                targetActivity = InviteActivity.class;
                return;
            case R.id.btn_setting:
                targetActivity = SettingActivity.class;
                break;
        }
        if (null != targetActivity) getBaseActivity().gotoActivity(targetActivity);
    }

    @Subscribe(threadMode = MAIN, sticky = true)
    public void onRefreshMyMap(RefreshMyMapEvent event) {
        EventBus.getDefault().removeStickyEvent(RefreshMyMapEvent.class);
        List<Moment> moments = event.mMoments;
        if (!ListUtils.isEmpty(moments)) {
            mAMap.clear();
            for (Moment momentItem : moments) {
                addSignInMarker(new LatLng(momentItem.getLatitude(), momentItem.getLongitude()));
            }
        }
    }

    private void addSignInMarker(LatLng latLng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);

        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_map_label)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        mAMap.addMarker(markerOption);
    }

    @Subscribe(threadMode = MAIN, sticky = true)
    public void onRefreshProfile(RefreshProfileEvent event) {
        EventBus.getDefault().removeStickyEvent(RefreshMyMapEvent.class);
        initView();
    }

}
