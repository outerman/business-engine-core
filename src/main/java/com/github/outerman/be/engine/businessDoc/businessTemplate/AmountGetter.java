package com.github.outerman.be.engine.businessDoc.businessTemplate;

import com.github.outerman.be.engine.util.JexlUtil;

/**
 * Created by shenxy on 14/7/17.
 *
 * 金额来源表达式处理
 */
public class AmountGetter {

    public static <T> Double getAmount(T detail, String fundsource) {
        Object obj = JexlUtil.evaluate(fundsource, detail);
        if (obj == null) {
            return 0.0;
        }
        Double result = Double.parseDouble(obj.toString());
        return result;
    }

}
