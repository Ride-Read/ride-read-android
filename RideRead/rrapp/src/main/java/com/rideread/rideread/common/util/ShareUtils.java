package com.rideread.rideread.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;

/**
 * Created by SkyXiao on 2017/4/16.
 */

public class ShareUtils {


    public static void share(BaseActivity activity, int stringRes) {
        share(activity, activity.getString(stringRes));
    }


    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.share)));
    }
}
