package com.rideread.rideread.common.util;

import java.util.regex.Pattern;

/**
 * 正则相关工具类
 * <p>
 * Created by SkyXiao on 2017/2/28.
 */

public class RegexUtils {
    /**
     * 正则：手机号（简单）
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 正则：正整数
     */
    public static final String REGEX_POSITIVE_INTEGER = "^[0-9]\\d*$";

    /**
     * 正则：验证 设备编号 安全码
     */
    public static final String REGEX_DEVICE_NUMBER= "^[ -~]{8}$";


    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 验证手机号（简单）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证 设备编号 安全码
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isCodeSimple(CharSequence input) {
        return isMatch(REGEX_DEVICE_NUMBER, input);
    }

    /**
     * 数字
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isNumber(CharSequence input) {
        return isMatch(REGEX_POSITIVE_INTEGER, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }
}
