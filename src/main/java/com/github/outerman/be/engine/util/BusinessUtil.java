package com.github.outerman.be.engine.util;

import com.github.outerman.be.api.constant.CommonConst;

/**
 * Created by shenxy on 14/7/17.
 *
 * 工具类
 */
public class BusinessUtil {

    public static int paymentDirection(String businessCode) {
        if (businessCode == null) {
            return CommonConst.PAYMENT_DIRECTION_UNKNOWN;
        }

        if (businessCode.startsWith("10")) {  //收入
            return CommonConst.PAYMENT_DIRECTION_IN;
        }
        else if (businessCode.startsWith("20")) {  //支出
            return CommonConst.PAYMENT_DIRECTION_OUT;
        }
        else if (businessCode.startsWith("5010001140")) {	//收到退回来的订金、押金、保证金、暂付款等
            return CommonConst.PAYMENT_DIRECTION_OUT;
        }
        else if (businessCode.startsWith("5020001140")) { 	//退还客户订金、押金、保证金、暂存款等
            return CommonConst.PAYMENT_DIRECTION_IN;
        }
        else if (businessCode.startsWith("501000")) { 		//收款/付款 --> 收款
            return CommonConst.PAYMENT_DIRECTION_IN;
        }
        else if (businessCode.startsWith("502000")) { 		//收款/付款 --> 付款
            return CommonConst.PAYMENT_DIRECTION_OUT;
        }

        return CommonConst.PAYMENT_DIRECTION_UNKNOWN;
    }
}
