package com.github.outerman.be.api.constant;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 流水账业务类型工具类
 * @author gaoxue
 *
 */
public final class BusinessTypeUtil {

    /**
     * "工资-发放-实发工资"特殊处理, 虽然是"结算方式",但是和其他"本表"的业务,一起单独排序
     */
    public final static List<Long> SPECIAL_ORDER = Arrays.asList(2025001010L, 2020002050L);

    //工资相关的单据类型id, 生成凭证时有特殊处理
    public final static List<Long> GONGZI_VOUCHERTYPE_LIST = Arrays.asList(AcmConst.VOUCHERTYPE_0003,
            AcmConst.VOUCHERTYPE_0004, AcmConst.VOUCHERTYPE_0005, AcmConst.VOUCHERTYPE_0006, AcmConst.VOUCHERTYPE_0007,
            AcmConst.VOUCHERTYPE_0008, AcmConst.VOUCHERTYPE_0009);

    /* 固定不做合并的业务: 账户内互转的三项. 因为这三项在模板上是"本表"平, 但是业务含义更接近"收支结算", 所以参照"结算情况"的处理方式不做合并 */
    public final static List<Long> NOT_MERGE_BUSINESS = Arrays.asList(401000L, 401050L, 402000L);

    /** 客户收款相关业务编码 */
    public final static String[] CUSTOMER_RECEIVED = { "101200", "101250", "5010001000", "5010001050", "5010001130",
            "5010001200" };

    /** 客户特殊应收相关业务编码 */
    public final static String[] CUSTOMER_SPECIAL_RECEIVABLE = { "5020001140", "5020001200" };

    /** 供应商付款相关业务编码 */
    public final static String[] VENDOR_PAID = { "2050001350", "2050001400", "5020001000", "5020001050", "5020001130",
            "5020001150" };

    /** 供应商特殊应付相关业务编码 */
    public final static String[] VENDOR_SPECIAL_PAYABLE = { "5010001140", "5010001150" };

    /** 收支统计不需要统计的业务编码（收入部分） */
    public final static String[] IN_EXCLUDE = { "101200", "101250" };

    /** 收支统计不需要统计的业务编码（支出部分） */
    public final static String[] OUT_EXCLUDE = { "2050001350", "2050001400", "2020002050", "2020002150", "2020002250",
            "2040001550" };

    /** 支出中抵减增值税税额的业务编码 */
    public final static String[] OUT_DEDUCTION_TAX = { "2030001670", "2030001671" };

    /** 流水账记客户，凭证记供应商的业务编码 */
    private final static String[] customer2VendorBusinessType = { "5010001130", "5020001140" };

    /** 流水账记供应商，凭证记客户的业务编码  */
    private final static String[] vendor2CustomerBusinessType = { "5010001140", "5020001130" };

    /**
     * 获取是否是流水账记客户，凭证记供应商的业务
     * @param businessTypeCode 业务编码
     * @return 是否是流水账记客户，凭证记供应商的业务
     */
    public final static boolean isCustomer2VendorBusinessType(String businessTypeCode) {
        return ArrayUtils.contains(customer2VendorBusinessType, businessTypeCode);
    }

    /**
     * 获取是否是流水账记供应商，凭证记客户的业务
     * @param businessTypeCode 业务编码
     * @return 是否是流水账记供应商，凭证记客户的业务
     */
    public final static boolean isVendor2CustomerBusinessType(String businessTypeCode) {
        return ArrayUtils.contains(vendor2CustomerBusinessType, businessTypeCode);
    }

    /** 请会计处理收入相关的业务编码 */
    public final static String[] IN_ACCOUNTING_PROCESS = { "1030001000", "1030001010", "1030001015", "1030001020",
            "1030001030", "1030001040", "1030001050", "1030001060", "1030001070", "1030001080" };

    /** 请会计处理支出相关的业务编码 */
    public final static String[] OUT_ACCOUNTING_PROCESS = { "2060001000", "2060001010", "2060001020", "2060001030",
            "2060001040", "2060001050", "2060001055", "2060001060" };

    /**支付预付款*/
    public final static String[] PAY_PREPAYMENT = { "2050001350", "5020001000" };
    /**支付赊购款*/
    public final static String[] PAY_ON_CREDIT = { "2050001400", "5020001050" };
    /** 请会计处理收入父业务类型编码 */
    public final static String IN_ACCOUNTING_PROCESS_PARENT_CODE = "103000";

    /** 请会计处理支出父业务类型编码 */
    public final static String OUT_ACCOUNTING_PROCESS_PARENT_CODE = "206000";
    /**无形资产*///购买无形资产/无形资产摊销/销售无形资产/资产模块无形资产摊销
    public final static String[] INTANGIBLE_ASSETS_BUSINESSCODE ={"2050001300","3020001200","1030001010","3020001210"};
    /**固定资产-不动产*///购买固定资产-不动产、销售固定资产-不动产、
    public final static String[] FIXED_ASSETS_REAL_ESTATE_BUSINESSCODE ={"2050001251","1030001001"};
    
    /**固定资产-动产*///购买固定资产-动产、销售固定资产-动产、
    public final static String[] FIXED_ASSETS_MOVABLE_PROPERTY_BUSINESSCODE ={"2050001250","1030001000"};
    /**固定资产*///固定资产折旧、资产模块固定资产折旧
    public final static String[] FIXED_ASSETS_BUSINESSCODE ={"3020001150","3020001160"};
    //契税
    public final static String[] DEED_TAX ={"2040001800"};
    
    /** 收取预收款业务 */
    public final static String[] RECEIVE_PRERECEIVE = { BusinessCode.BUSINESS_101200, BusinessCode.BUSINESS_5010001000 };

    /** 收回赊销款业务 */
    public final static String[] RECEIVE_ON_CREDIT = { BusinessCode.BUSINESS_101250, BusinessCode.BUSINESS_5010001050 };

    /**
     * 获取是否收回赊销款业务
     * @param businessTypeCode 业务编码
     * @return 是否收回赊销款业务
     */
    public final static boolean isReceiveOnCredit(String businessTypeCode) {
        return ArrayUtils.contains(RECEIVE_ON_CREDIT, businessTypeCode);
    }

    private BusinessTypeUtil() {
        // final util class, avoid instantiate
    }
}
