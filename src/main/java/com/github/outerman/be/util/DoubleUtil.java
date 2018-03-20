package com.github.outerman.be.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数值工具类
 * @author gaoxue
 *
 */
public final class DoubleUtil {

    /**
     * Double 类型数据是否为 null 或者为 0
     * @param value
     * @return
     */
    public static boolean isNullOrZero(Double value) {
        if (value == null) {
            return true;
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(value.doubleValue());
        return bigDecimal.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Double 类型加法，当加数为空时直接返回被加数，使用 {@link BigDecimal} 进行计算，null 视为 0.0 进行计算
     * 
     * @param summand
     *            被加数
     * @param augends
     *            加数
     * @return 计算结果
     */
    public static Double add(Double summand, Double... augends) {
        int length = augends.length;
        if (summand == null) {
            summand = 0.0;
        }
        if (length == 0) {
            return formatDoubleScale2(summand);
        }
        BigDecimal result = BigDecimal.valueOf(summand.doubleValue());
        for (int index = 0; index < length; index++) {
            if (augends[index] == null) {
                continue;
            }
            BigDecimal temp = BigDecimal.valueOf(augends[index].doubleValue());
            result = result.add(temp);
        }
        return formatDoubleScale2(result.doubleValue());
    }

    /**
     * Double 类型除法，当除数为空时直接返回被除数，被除数为 null 返回 null，除数存在 null 或者 0.0 返回 null，使用
     * {@link BigDecimal} 进行计算，精度保留两位小数
     * 
     * @param dividend
     *            被除数
     * @param divisors
     *            除数
     * @return
     */
    public static Double div(Double dividend, Double... divisors) {
        int length = divisors.length;
        if (dividend == null) {
            return null;
        }
        if (length == 0) {
            return formatDoubleScale2(dividend);
        }

        BigDecimal result = BigDecimal.valueOf(dividend.doubleValue());
        for (int index = 0; index < length; index++) {
            if (isNullOrZero(divisors[index])) {
                return null;
            }
            BigDecimal temp = BigDecimal.valueOf(divisors[index]);
            result = result.divide(temp, 2, RoundingMode.HALF_UP);
        }
        return formatDoubleScale2(result.doubleValue());
    }

    /**
     * Double 类型格式化为 N 位小数
     * 
     * @param value
     *            进行格式化的 Double 数值
     * @param null2Zero
     *            Null是否返回0
     * @param scale
     *            小数位数
     * @return 格式化后的double
     */
    public static Double formatDouble(Double value, boolean null2Zero, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        if (value == null) {
            if (null2Zero) {
                return 0.00;
            } else {
                return null;
            }
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(value.doubleValue());
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * Double 类型格式化为 2 位小数
     * 
     * @param value
     *            进行格式化的 Double 数值
     * @param null2Zero
     *            Null是否返回0
     * @return 格式化后的double
     */
    public static Double formatDoubleScale2(Double value, boolean null2Zero) {
        return formatDouble(value, null2Zero, 2);
    }

    /**
     * Double 类型格式化为 2 位小数, Null是否返回0
     * 
     * @param value
     *            进行格式化的 Double 数值
     * @return 格式化后的double
     */
    public static Double formatDoubleScale2(Double value) {
        return formatDoubleScale2(value, true);
    }

    private DoubleUtil() {
        // final util class, avoid instantiate
    }
}
