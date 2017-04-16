package com.rideread.rideread.module.map.view;

import android.Manifest;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.PostPicAdapter;
import com.rideread.rideread.common.base.MPermissionsActivity;
import com.rideread.rideread.common.dialog.ConfirmDialogFragment;
import com.rideread.rideread.common.event.SelectPicEvent;
import com.rideread.rideread.common.util.AMapLocationUtils;
import com.rideread.rideread.common.util.DateUtils;
import com.rideread.rideread.common.util.GalleryUtils;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.QiniuToken;
import com.rideread.rideread.function.net.qiniu.QiNiuUtils;
import com.rideread.rideread.function.net.retrofit.ApiStore;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * Created by SkyXiao on 2017/46.
 */

public class PostMomentActivity extends MPermissionsActivity {

    @BindView(R.id.edt_moment) EditText mEdtMoment;
    @BindView(R.id.tv_post_loc) TextView mTvPostLoc;
    @BindView(R.id.cb_show_loc) CheckBox mCbShowLoc;
    @BindView(R.id.rv_pic_container) RecyclerView mRvPicContainer;

    public final int SELECT_PICTURE_MAX = 9;
    private PostPicAdapter mPostPicAdapter;
    private List<String> mSelectedPics = new ArrayList<>();
    private List<String> mPictureUrls = new ArrayList<>();
    private ConfirmDialogFragment mQuitEditDialog;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_post_moment;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText("发个阅圈").IsBack(true).setRightText("发送").build();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvPicContainer.setLayoutManager(linearLayoutManager);
        //设置适配器
        mPostPicAdapter = new PostPicAdapter(this, mSelectedPics);
        mRvPicContainer.setAdapter(mPostPicAdapter);
        mTvPostLoc.setText(AMapLocationUtils.getLocDetail());
    }


    @OnClick({R.id.img_top_bar_left, R.id.tv_top_bar_right, R.id.tv_post_loc, R.id.img_add_picture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_bar_left:
                showQuitDialog();
                break;
            case R.id.tv_top_bar_right:
                if (!ListUtils.isEmpty(mSelectedPics)) {
                    post2QiNiuYun(0);
                } else {
                    postMoment();
                }
                break;
            case R.id.tv_post_loc:
                break;
            case R.id.img_add_picture:
                //判断权限，获取相片
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,};
                requestPermission(permission, 0x0001);
                break;
        }
    }

    private void showQuitDialog() {
        if (null == mQuitEditDialog) {
            mQuitEditDialog = ConfirmDialogFragment.newInstance(R.string.query_2_quit_edit);
        }
        mQuitEditDialog.show(getSupportFragmentManager(), "dialog");
    }

    private void postMoment() {
        String momentText = mEdtMoment.getText().toString().trim();
        String locInfo = "";
        if (mCbShowLoc.isChecked()) locInfo = mTvPostLoc.getText().toString();
        ApiUtils.postMoment(momentText, locInfo, mPictureUrls, new BaseCallback<BaseModel<DefJsonResult>>() {
            @Override
            protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                ToastUtils.show("发送成功");
                finish();
            }
        });
    }

    private void post2QiNiuYun(int index) {
        final int listSize = mSelectedPics.size();
        final String picPath = mSelectedPics.get(index);
        final String filename = "moment" + "_" + UserUtils.getUid() + "_" + DateUtils.getCurDateFormat() + "_" + index + ".jpg";
        ApiUtils.getQiNiuTokenTest(filename, new BaseCallback<BaseModel<QiniuToken>>() {
            @Override
            protected void onSuccess(BaseModel<QiniuToken> model) throws Exception {
                String token = model.getData().getUpToken();

                QiNiuUtils.uploadFile(token, picPath, filename, (key, info, response) -> {
                    Logger.e("http", "key:" + key + "-info:" + info.toString() + "-response:" + response);
                    if (info.isOK()) {
                        mPictureUrls.add(ApiStore.USERHEAD_LINK + key);
                        int nextIndex = index + 1;
                        if (nextIndex >= listSize) {
                            postMoment();
                        } else {
                            post2QiNiuYun(nextIndex);
                        }

                    } else {
                        ToastUtils.show("图片上传失败");
                    }
                }, null, null);
            }

            @Override
            protected void onAfter() {
            }
        });
    }


    /**
     * 权限成功回调函数
     *
     * @param requestCode
     */
    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 0x0001:
                GalleryUtils galleryUtils = new GalleryUtils();
                galleryUtils.getPictures(this, SELECT_PICTURE_MAX - mSelectedPics.size());
                break;
        }

    }


    @Override
    public void doPositiveClick() {
        finish();
    }

    @Subscribe(threadMode = MAIN, sticky = true)
    public void onSelected(final SelectPicEvent event) {
        EventBus.getDefault().removeStickyEvent(SelectPicEvent.class);
        List<String> list = event.picList;
        if (!ListUtils.isEmpty(list)) {
            mSelectedPics.addAll(list);
            mPostPicAdapter.notifyDataSetChanged();
        }
    }
}
