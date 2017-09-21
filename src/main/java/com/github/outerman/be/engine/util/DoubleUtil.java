package com.github.outerman.be.engine.util;

import java.math.BigDecimal;

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

}
