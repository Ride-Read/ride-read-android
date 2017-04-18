package com.rideread.rideread.module.profile.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.DateUtils;
import com.rideread.rideread.common.util.GalleryUtils;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.result.QiniuToken;
import com.rideread.rideread.data.result.UserInfo;
import com.rideread.rideread.function.net.qiniu.QiNiuUtils;
import com.rideread.rideread.function.net.retrofit.ApiStore;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.rideread.rideread.R.string.locale;


/**
 * Created by SkyXiao on 2017/4/6.
 */

public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
    @BindView(R.id.tv_nickname) TextView mTvNickName;
    @BindView(R.id.tv_sex) TextView mTvSex;
    @BindView(R.id.tv_label) TextView mTvLabel;
    @BindView(R.id.tv_signature) TextView mTvSignature;
    @BindView(R.id.tv_birthday) TextView mTvBirthday;
    @BindView(R.id.tv_phone) TextView mTvPhone;
    @BindView(R.id.tv_school) TextView mTvSchool;
    @BindView(R.id.tv_locale) TextView mTvLocale;
    @BindView(R.id.tv_hometown) TextView mTvHometown;
    @BindView(R.id.tv_job) TextView mTvJob;
    private UserInfo mUserInfo;

    private String mModifyAvatarPath;
    private String mFaceUrl;
    private String mNick;
    private int mCurSexIndex;
    private String mCurSex;
    private List<String> mLabels;
    private String mSignature;
    private String mBirthday;
    private String mSchool;
    private String mHomeTown;
    private String mLocation;
    private String mCareer;
    private boolean hasModifyFace;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText(R.string.edit_user_info).IsBack(true).setRightText("保存").build();

        mUserInfo = UserUtils.getCurUser();
        mFaceUrl = mUserInfo.getFaceUrl();
        ImgLoader.getInstance().displayImage(mFaceUrl, mImgAvatar);
        mNick = mUserInfo.getUsername();
        mTvNickName.setText(mNick);


        if (2 != mUserInfo.getSex()) {
            mCurSex = "男";
            mCurSexIndex = 0;
        } else {
            mCurSex = "女";
            mCurSexIndex = 1;
        }
        mTvSex.setText(mCurSex);

        mLabels = mUserInfo.getTags();
        showLabels();

        mSignature = mUserInfo.getSignature();
        if (TextUtils.isEmpty(mSignature)) {
            mSignature = "";
            mTvSignature.setText("还没有个性签名");
        } else {
            mTvSignature.setText(mSignature);
        }


        long birthday = mUserInfo.getBirthday();
        if (0 != birthday) {
            mBirthday = DateUtils.getDateDayFormat(birthday);
            mTvBirthday.setText(mBirthday);
        } else {
            mBirthday = "";
        }

        String phoneNumber = mUserInfo.getPhonenumber();
        if (!TextUtils.isEmpty(phoneNumber)) mTvPhone.setText(phoneNumber);

        mSchool = mUserInfo.getSchool();
        if (TextUtils.isEmpty(mSchool)) mSchool = "";
        mTvSchool.setText(mSchool);

        mLocation = mUserInfo.getLocation();
        if (TextUtils.isEmpty(mLocation)) mLocation = "";
        mTvLocale.setText(mLocation);

        mHomeTown = mUserInfo.getHometown();
        if (TextUtils.isEmpty(mHomeTown)) mHomeTown = "";
        mTvHometown.setText(mHomeTown);

        mCareer = mUserInfo.getCareer();
        if (TextUtils.isEmpty(mCareer)) mCareer = "";
        mTvJob.setText(mCareer);

    }

    private void showLabels() {
        StringBuilder sb = new StringBuilder();
        if (!ListUtils.isEmpty(mLabels)) {
            for (String label : mLabels) {
                sb.append(label);
            }
        } else {
            mLabels = new ArrayList<>();
        }
        if (!TextUtils.isEmpty(sb.toString())) mTvLabel.setText(sb.toString());
    }


    @OnClick({R.id.img_top_bar_left, R.id.tv_top_bar_right, R.id.fl_avatar, R.id.fl_nick, R.id.fl_sex, R.id.fl_label, R.id.fl_signature, R.id.fl_birthday, R.id.fl_phone, R.id.fl_school, R.id.fl_locale, R.id.fl_hometown, R.id.fl_job})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_bar_left:
                finish();
                break;
            case R.id.tv_top_bar_right:
                if (hasModifyFace) {
                    uploadFace();
                } else {
                    saveCurInfo();
                }
                break;
            case R.id.fl_avatar:
                modifyAvatar();
                break;
            case R.id.fl_nick:
                modifyNick();
                break;
            case R.id.fl_sex:
                modifySex();
                break;
            case R.id.fl_label:
                modifyLabels();
                break;
            case R.id.fl_signature:
                modifySignature();
                break;
            case R.id.fl_birthday:
                showBirthdayDialog();
                break;
            case R.id.fl_phone:
                break;
            case R.id.fl_school:
                modifySchool();
                break;
            case R.id.fl_locale:
                showAddressDialog(true);
                break;
            case R.id.fl_hometown:
                showAddressDialog(false);
                break;
            case R.id.fl_job:
                modifyCareer();
                break;
        }
    }

    private void uploadFace() {
        if (TextUtils.isEmpty(mNick)) mNick = "user";
        final String filename = "face" + "_" + mNick + "_" + DateUtils.getCurDateFormat() + ".jpg";
        ApiUtils.getQiNiuTokenTest(filename, new BaseCallback<BaseModel<QiniuToken>>() {
            @Override
            protected void onSuccess(BaseModel<QiniuToken> model) throws Exception {
                String token = model.getData().getUpToken();

                QiNiuUtils.uploadFile(token, mModifyAvatarPath, filename, (key, info, response) -> {
                    Logger.e("http", "key:" + key + "-info:" + info.toString() + "-response:" + response);
                    if (info.isOK()) {
                        mFaceUrl = ApiStore.USERHEAD_LINK + key;
                        saveCurInfo();
                    } else {
                        ToastUtils.show("头像上传失败");
                    }
                }, null, null);
            }
        });

    }

    private void saveCurInfo() {
        UserInfo curInfo = new UserInfo(mFaceUrl, mNick, mCurSexIndex + 1, mLabels, mSignature, mSchool, mLocation, mHomeTown, mCareer);
        ApiUtils.update(curInfo, mBirthday, new BaseCallback<BaseModel<UserInfo>>() {
            @Override
            protected void onSuccess(BaseModel<UserInfo> model) throws Exception {
                UserInfo userInfo = model.getData();
                if (null != userInfo) {
                    UserUtils.saveUserInfo(userInfo);
                    finish();
                }
            }
        });
    }


    /**
     * 选择头像
     */
    private void modifyAvatar() {
        GalleryUtils galleryUtils = new GalleryUtils();
        galleryUtils.getPictures(this, 1, paths -> {
            if (!ListUtils.isEmpty(paths)) {
                mModifyAvatarPath = paths.get(0);
                mImgAvatar.setImageURI("file://" + mModifyAvatarPath);
                hasModifyFace = true;
            }
        });
    }

    private void modifyNick() {
        new MaterialDialog.Builder(this).title(R.string.nick).inputType(InputType.TYPE_CLASS_TEXT).input(getString(R.string.nick_tips), mNick, (dialog, input) -> {
            mNick = input.toString();
            mTvNickName.setText(mNick);
        }).show();
    }

    /**
     * 选择性别
     */
    private void modifySex() {
        final String[] sexs = getResources().getStringArray(R.array.sex_choices);
        new MaterialDialog.Builder(this).title(R.string.sex).items(R.array.sex_choices).itemsCallbackSingleChoice(mCurSexIndex, (dialog, view, position, text) -> {
            mCurSexIndex = position;
            mCurSex = sexs[position];
            mTvSex.setText(mCurSex);
            return true;
        }).positiveText(R.string.confirm).show();

    }


    private void modifyLabels() {

        List<Integer> indexs = new ArrayList<>();
        final String[] labels = getResources().getStringArray(R.array.tags_label);
        if (!ListUtils.isEmpty(mLabels)) {
            for (String label : mLabels) {
                for (int i = 0; i < labels.length; i++) {
                    if (label.equals(labels[i])) {
                        indexs.add(i);
                        break;
                    }
                }
            }
        }
        final int size = indexs.size();
        Integer[] indexArr = (Integer[]) indexs.toArray(new Integer[size]);
        new MaterialDialog.Builder(this).title(R.string.label).items(labels).itemsCallbackMultiChoice(indexArr, new MaterialDialog.ListCallbackMultiChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                if (which.length > 0) {
                    mLabels.clear();
                    for (Integer integer : which) {
                        mLabels.add(labels[integer]);
                    }
                    showLabels();
                }
                return true;
            }
        }).positiveText(R.string.confirm).show();
    }

    private void modifySignature() {
        if (TextUtils.isEmpty(mSignature)) mSignature = "";
        new MaterialDialog.Builder(this).title(R.string.signature).inputType(InputType.TYPE_CLASS_TEXT).input(getString(R.string.signature), mSignature, (dialog, input) -> {

            mSignature = input.toString();
            mTvSignature.setText(input.toString());

        }).show();
    }

    private void modifySchool() {

        new MaterialDialog.Builder(this).title(R.string.school).inputType(InputType.TYPE_CLASS_TEXT).input(mSchool, mSchool, (dialog, input) -> {
            mSchool = input.toString();
            mTvSchool.setText(mSchool);
        }).show();
    }


    private void modifyCareer() {
        final String[] careers = getResources().getStringArray(R.array.careers);
        int careerIndex = 0;
        if (!TextUtils.isEmpty(mCareer)) for (int i = 0; i < careers.length; i++) {
            if (mCareer.equals(careers[i])) {
                careerIndex = i;
                break;
            }
        }

        new MaterialDialog.Builder(this).title(R.string.career).items(R.array.careers).itemsCallbackSingleChoice(careerIndex, (dialog, view, which, text) -> {
            mCareer = careers[which];
            mTvJob.setText(mCareer);
            return true;
        }).positiveText(R.string.confirm).show();
    }

    private void showBirthdayDialog() {
        AlertDialog.Builder dialogBuider = new AlertDialog.Builder(this);
        dialogBuider.setTitle(R.string.birthday);
        final DatePicker birthPicker = new DatePicker(this);

        birthPicker.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            birthPicker.getWindowVisibleDisplayFrame(r);
        });

        int year = 1990;
        int month = 1;
        int date = 1;
        long birthDay = mUserInfo.getBirthday();
        if (0 != birthDay) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(birthDay);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            date = cal.get(Calendar.DAY_OF_MONTH);
        }
        ViewGroup tempGroup = ((ViewGroup) (((ViewGroup) birthPicker.getChildAt(0)).getChildAt(0)));
        birthPicker.setMaxDate(System.currentTimeMillis());
        birthPicker.init(year, month - 1, date, null);
        dialogBuider.setView(birthPicker);
        dialogBuider.setPositiveButton(R.string.confirm, (arg0, arg1) -> {
            mBirthday = String.format("%s-%02d-%02d", birthPicker.getYear(), birthPicker.getMonth() + 1, birthPicker.getDayOfMonth());
            mTvBirthday.setText(mBirthday);
        });

        birthPicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        tempGroup.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        birthPicker.setCalendarViewShown(false);
        // birthPicker.setFocusable(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tempGroup.getWindowToken(), 0);
        dialogBuider.show();

    }


    /**
     * 弹出地址选择 对话框
     */
    private void showAddressDialog(boolean isLocation) {
        readProvinceFromAsset();
        new MaterialDialog.Builder(this).title(locale).items(provinces).positiveText(R.string.cancel).itemsCallback((dialog, view, position, text) -> {
            final String province = provinces[position];
            readCitiesFromAsset(position);
            new MaterialDialog.Builder(UserInfoActivity.this).title(R.string.locale).items(cities).positiveText(R.string.cancel).itemsCallback(new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int pos, CharSequence text) {

                    String city = cities[pos];
                    if (isLocation) {
                        mTvLocale.setText(city);
                        mLocation = city;
                    } else {
                        mTvHometown.setText(city);
                        mHomeTown = city;
                    }

                }
            }).show();
        }).show();
    }

    private String[] provinces;
    private String[] cities;

    private void readProvinceFromAsset() {
        String fileName = "province.json";

        try {
            InputStream is = getAssets().open(fileName);
            if (is != null) {
                StringBuilder sb = new StringBuilder();
                BufferedReader d = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while (d.ready()) {
                    sb.append(d.readLine());
                }
                String content = sb.toString();
                is.close();
                JSONArray provinceArray = new JSONArray(content);
                provinces = new String[provinceArray.length()];
                for (int i = 0; i < provinceArray.length(); i++) {
                    provinces[i] = provinceArray.get(i).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readCitiesFromAsset(int index) {
        try {
            InputStream is = getAssets().open("city.json");
            if (is != null) {
                StringBuilder sb = new StringBuilder();
                BufferedReader d = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while (d.ready()) {
                    sb.append(d.readLine());
                }
                String content = sb.toString();
                is.close();
                JSONObject cityJsonObject = new JSONObject(content);
                JSONArray cityArray = cityJsonObject.getJSONArray(provinces[index]);
                cities = new String[cityArray.length()];
                for (int i = 0; i < cityArray.length(); i++) {
                    cities[i] = cityArray.get(i).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
