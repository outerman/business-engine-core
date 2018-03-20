package com.github.outerman.be.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 * @author gaoxue
 *
 */
public final class StringUtil {

    public static String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public static String format(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String format(Date date) {
        DateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN);
        return format.format(date);
    }

    /**
     * 判断字符串是否为空或者空串
     *
     */
    public static boolean isEmpty(String str) {
        if (str == null || "null".equals(str) || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    private StringUtil() {
        // final util class, avoid instantiate
    }
}
