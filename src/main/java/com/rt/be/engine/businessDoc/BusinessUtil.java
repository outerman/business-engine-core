package com.rt.be.engine.businessDoc;

import com.rt.be.api.constant.AcmConst;

/**
 * Created by shenxy on 14/7/17.
 *
 * 工具类
 */
public class BusinessUtil {
    public static Long getPaymentTypeFromBusiness(String businessCode) {
        if (businessCode.startsWith("10")) {
            return AcmConst.PAYMENTSTYPE_10;
        }
        else if (businessCode.startsWith("20")) {
            return AcmConst.PAYMENTSTYPE_20;
        }
        else if (businessCode.startsWith("30")) {
            return AcmConst.PAYMENTSTYPE_30;
        }
        else if (businessCode.startsWith("40")) {
            return AcmConst.PAYMENTSTYPE_40;
        }
        else if (businessCode.startsWith("50")) {
            return AcmConst.PAYMENTSTYPE_50;
        }
        else {
            return AcmConst.PAYMENTSTYPE_60;
        }
    }

    public static int paymentDirection(Long businessCode) {
        if (businessCode == null) {
            return AcmConst.PAYMENT_DIRECTION_UNKNOWN;
        }

        String businessCodeStr = String.valueOf(businessCode);
        if (businessCodeStr.startsWith("10")) {  //收入
            return AcmConst.PAYMENT_DIRECTION_IN;
        }
        else if (businessCodeStr.startsWith("20")) {  //支出
            return AcmConst.PAYMENT_DIRECTION_OUT;
        }
        else if (businessCodeStr.startsWith("5010001140")) {	//收到退回来的订金、押金、保证金、暂付款等
            return AcmConst.PAYMENT_DIRECTION_OUT;
        }
        else if (businessCodeStr.startsWith("5020001140")) { 	//退还客户订金、押金、保证金、暂存款等
            return AcmConst.PAYMENT_DIRECTION_IN;
        }
        else if (businessCodeStr.startsWith("501000")) { 		//收款/付款 --> 收款
            return AcmConst.PAYMENT_DIRECTION_IN;
        }
        else if (businessCodeStr.startsWith("502000")) { 		//收款/付款 --> 付款
            return AcmConst.PAYMENT_DIRECTION_OUT;
        }

        return AcmConst.PAYMENT_DIRECTION_UNKNOWN;
    }
}
