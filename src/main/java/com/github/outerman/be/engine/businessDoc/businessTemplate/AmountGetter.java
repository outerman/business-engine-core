package com.github.outerman.be.engine.businessDoc.businessTemplate;

import java.util.List;
import java.util.Map;

import com.github.outerman.be.engine.util.CommonUtil;
import com.github.outerman.be.engine.util.JexlUtil;

/**
 * Created by shenxy on 14/7/17.
 *
 * 金额来源表达式处理
 */
public class AmountGetter {

    private final static String MAOHAO = ":";
    private final static String JINGHAO = "#";

    private static String handleFundSource(String fundsource) {
        if (!fundsource.contains(MAOHAO)) {
            return fundsource;
        }
        String[] array1 = fundsource.split(MAOHAO);
        if (array1.length != 3) {
            return fundsource;
        }
        for (int index = 0, length = array1.length; index < length; index++) {
            String str = array1[index];
            if (!str.contains(JINGHAO)) {
                continue;
            }
            String[] array2 = str.split(JINGHAO);
            array1[index] = String.format("(%s ? %s : %s)", array2[0], array2[1], array2[2]);
        }
        return String.format("(%s ? %s : %s)", array1[0], array1[1], array1[2]);
    }

    public static boolean validateExpression(String fundsource) {
        fundsource = handleFundSource(fundsource);
        boolean result = JexlUtil.isExprValid(fundsource);
        return result;
    }

    public static <T> Double getAmount(T detail, String fundsource) {
        fundsource = handleFundSource(fundsource);
        Object obj = JexlUtil.evaluate(fundsource, detail);
        if (obj == null) {
            return 0.0;
        }
        Double result = Double.parseDouble(obj.toString());
        return result;
    }

    public static List<String> getAmountFieldNameList(String fundsource) {
        fundsource = handleFundSource(fundsource);
        List<String> fieldNameList = JexlUtil.getIdentifierName(fundsource);
        return fieldNameList;
    }

    public static String fundsource2Chinese(String fundsource) {
        fundsource = handleFundSource(fundsource);
        Map<String, String> map = CommonUtil.getFieldName2NameMap();
        String parsedText = JexlUtil.getParsedText(fundsource, map);
        return parsedText;
    }

}
