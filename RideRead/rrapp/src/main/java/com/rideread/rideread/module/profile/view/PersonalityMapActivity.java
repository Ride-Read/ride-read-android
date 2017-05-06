package com.rideread.rideread.module.profile.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.AMapLocationUtils;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.function.net.qiniu.QiNiuUtils;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;
import com.rideread.rideread.module.circle.view.MomentDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class PersonalityMapActivity extends BaseActivity {

    @BindView(R.id.map_view) TextureMapView mMapView;

    public final static String USER_ID = "user_id";
    private AMap mAMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private List<Moment> mMoments;
    private int mCurUid;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_person_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (SDK_INT >= KITKAT) {
            getWindow().addFlags(FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        mMoments = new ArrayList<>();
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(AMapLocationUtils.sLatLng));
        mAMap.setOnMarkerClickListener(marker -> {

            Moment moment = (Moment) marker.getObject();
            if (null != moment) {
                Bundle bundle = new Bundle();
                int isFollow = moment.getUser().getIsFollowed();
                boolean isAttent = isFollow == 0 || isFollow == 1;
                bundle.putInt(MomentDetailActivity.SELECTED_MOMENT_MID, moment.getMid());
                bundle.putInt(MomentDetailActivity.USER_TYPE, isAttent ? MomentDetailActivity.USER_TYPE_ATTENTED : MomentDetailActivity.USER_TYPE_NEARBY);
                gotoActivity(MomentDetailActivity.class, bundle);
            }
            return false;
        });
        mCurUid = getIntent().getIntExtra(USER_ID, UserUtils.getUid());
        loadUserMoments();
    }

    private void loadUserMoments() {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.show(R.string.network_error_fail);
        }
        ApiUtils.showUserMoments(mCurUid, 0, new BaseCallback<BaseModel<List<Moment>>>() {
            @Override
            protected void onSuccess(BaseModel<List<Moment>> model) throws Exception {

                List<Moment> tMoments = model.getData();
                if (!ListUtils.isEmpty(tMoments)) {
                    mMoments.clear();
                    mMoments.addAll(tMoments);
                    showMomentsOnMap(mMoments);
                }
            }
        });
    }

    private void showMomentsOnMap(List<Moment> moments) {
        List<String> pictures;
        for (Moment moment : moments) {
            pictures = moment.getPictures();
            if (!ListUtils.isEmpty(pictures)) {
                addMoment2Map(moment);
            }
        }
    }

    private void addMoment2Map(Moment moment) {
        LatLng latLng = new LatLng(moment.getLatitude(), moment.getLongitude());
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(moment.getPictures().get(0) + QiNiuUtils.CROP_SMALL_100)).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, Utils.getAppContext());
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                addMarker(latLng, bitmap, moment);
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
            }
        }, CallerThreadExecutor.getInstance());
    }

    private void addMarker(LatLng latLng, Bitmap bitmap, Moment moment) {
        if (null == latLng || null == bitmap) return;
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);

        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        Marker marker = mAMap.addMarker(markerOption);
        marker.setObject(moment);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mMapView.onSaveInstanceState(bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
