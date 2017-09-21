package com.github.outerman.be.api.constant;


/**
 * Created by shenxy on 13/7/17.
 *
 * 错误编码
 */
public class ErrorCode {

    public static final String ENGINE_VALIDATE_ERROR_CODE = "708001";
    public static final String ENGINE_VALIDATE_ERROR_MESSAGE = "模板验证不正确！";

    /** 流水账信息为空 */
    public static final BusinessEngineException EXCEPTION_RECEIPT_EMPATY = new BusinessEngineException("70900", "流水账信息为空");

    /** 组织信息为空 */
    public static final BusinessEngineException EXCEPTION_ORG_EMPATY = new BusinessEngineException("70901", "组织信息为空");

    /** 结算方式模板未找到，可能为账号保存异常 */
    public static final String ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_CODE = "70901";
    public static final String ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_MSG = "结算方式模板未找到，可能为账号保存异常";

    /** 理票单未找到明细 */
    public static final String ENGINE_DOC_GENETARE_EMPTY_DOCACCOUNT_ERROR_CODE = "70902";
    public static final String ENGINE_DOC_GENETARE_EMPTY_DOCACCOUNT_ERROR_MSG = "业务科目没有查询到，请联系管理员！";

    public static final String ENGINE_DOC_GENETARE_EMPTY_BUSINESS_ERROR_CODE = "70903";
    public static final String ENGINE_DOC_GENETARE_EMPTY_BUSINESS_ERROR_MSG = "业务模板未找到";

    public static final String ENGINE_DOC_GENETARE_EMPTY_DETAIL_ERROR_MSG = "流水账未找到明细！";
    public static final String ENGINE_DOC_GENETARE_UNRESOVE_ERROR_MSG = "\"请会计处理\"的流水账不生成凭证！";
}
