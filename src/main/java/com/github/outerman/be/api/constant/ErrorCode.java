package com.github.outerman.be.api.constant;


/**
 * Created by shenxy on 13/7/17.
 *
 * 错误编码
 */
public class ErrorCode {

    /** 单据信息为空 */
    public static final BusinessEngineException EXCEPTION_VOUCHER_EMPATY = new BusinessEngineException("70900", "单据信息为空");

    /** 企业信息为空 */
    public static final BusinessEngineException EXCEPTION_ORG_EMPATY = new BusinessEngineException("70901", "企业信息为空");

    public static final String VOUCHER_EMPTY = "第%s条单据信息为空";

    public static final String VOUCHER_DETAIL_EMPTY = "第%s条单据明细数据为空";

    public static final String BUSINESS_CODE_EMPTY = "第%s条单据，第%s条明细数据业务编码为空";

    public static final String DOC_TEMPLATE_EMPTY = "业务编码%s没有找到匹配的凭证模板数据";

    public static final String ACCOUNT_CODE_INVALID = "编码%s没有找到对应的科目信息";

    public static final String ENTRY_EMPTY = "所有分录取到的金额为 0";

    public static final String SETTLE_TEMPLATE_EMPTY = "没有找到匹配的结算凭证模板数据，请检查账户属性、收支业务属性";

}
