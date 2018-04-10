package com.github.outerman.be.util;

import java.util.Map;

/**
 * Created by shenxy on 14/7/17.
 *
 * 金额来源表达式处理
 */
public class AmountGetter {

    public static <T> Double getAmount(T detail, String amountSource) {
        return getAmount(detail, amountSource, null);
    }

    public static <T> Double getAmount(T detail, String amountSource, Map<String, Object> params) {
        Object obj = JexlUtil.evaluate(amountSource, detail, params);
        if (obj == null) {
            return 0.0;
        }
        Double result = Double.parseDouble(obj.toString());
        return result;
    }
}
