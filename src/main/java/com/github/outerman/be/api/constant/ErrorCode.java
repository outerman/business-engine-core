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

    /** 结算方式模板未找到，可能为账号保存异常 */
    public static final String ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_MSG = "结算方式模板未找到，可能为账号保存异常";

    public static final String ENGINE_DOC_GENETARE_EMPTY_DETAIL_ERROR_MSG = "流水账明细数据为空";
}
