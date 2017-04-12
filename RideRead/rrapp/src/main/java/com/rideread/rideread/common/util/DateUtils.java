package com.rideread.rideread.common.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Locale.CHINA;

/**
 * Created by SkyXiao on 2017/3/21.
 */

public class DateUtils {
    private DateUtils() {
        throw new AssertionError();
    }

    public static String format(@NonNull final String format, final long date) {
        final SimpleDateFormat fmt = new SimpleDateFormat(format, CHINA);
        return fmt.format(new Date(date));
    }

    @Nullable
    public static Date parse(@NonNull final String format, @NonNull final String value) {
        final SimpleDateFormat fmt = new SimpleDateFormat(format, CHINA);
        try {
            return fmt.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间
     * 常用日期格式，如：20170202210303456
     *
     * @return yyyyMMddHHmmssSSS
     */
    public static String getCurDateFormat() {
        SimpleDateFormat _fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINA);
        return _fmt.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 返回时间格式：yyyy-MM-dd'T'HH:mm:ss
     *
     * @param timeMillis
     * @return
     */
    public static String getDateFormat(long timeMillis) {
        SimpleDateFormat _fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return _fmt.format(new Date(timeMillis));
    }

    /**
     * 返回时间格式：yyyy-MM-dd:2015-01-06
     *
     * @param timeMillis
     * @return
     */
    public static String getDateDayFormat(long timeMillis) {
        SimpleDateFormat _fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return _fmt.format(new Date(timeMillis));
    }

    /**
     * 11:00->1110
     * @param timeMillis
     * @return
     */
    public static String getTimeHHMMSimple(long timeMillis) {
        SimpleDateFormat _fmt = new SimpleDateFormat("HHmm", Locale.CHINA);
        return _fmt.format(new Date(timeMillis));
    }

    public static String getTimeHMFormat(long timeMillis) {
        SimpleDateFormat _fmt = new SimpleDateFormat("ah:mm", Locale.CHINA);
        return _fmt.format(new Date(timeMillis));
    }

    public static Date getTimeHMParse(String timeStr) {
        SimpleDateFormat _fmt = new SimpleDateFormat("ah:mm", Locale.CHINA);
        try {
            return _fmt.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
