package com.github.outerman.be.api.constant;


/**
 * Created by shenxy on 13/7/17.
 *
 * 错误编码
 */
public class ErrorCode {

    /** 流水账信息为空 */
    public static final BusinessEngineException EXCEPTION_RECEIPT_EMPATY = new BusinessEngineException("70900", "流水账信息为空");

    /** 组织信息为空 */
    public static final BusinessEngineException EXCEPTION_ORG_EMPATY = new BusinessEngineException("70901", "组织信息为空");

    /** 组织信息中纳税人身份为空 */
    public static final BusinessEngineException EXCEPTION_ORG_VATTAXPAYER_EMPTY = new BusinessEngineException("70902", "组织信息中纳税人身份为空");

    /** 业务类型凭证模板数据没有找到 */
    public static final String EXCEPTION_CODE_DOC_TEMPLATE_EMPTY = "70902";

    /** 结算方式模板未找到，可能为账号保存异常 */
    public static final String ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_CODE = "70901";
    public static final String ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_MSG = "结算方式模板未找到，可能为账号保存异常";

    public static final String ENGINE_DOC_GENERATE_RECEIPT_EMPTY = "流水账数据为空";
    public static final String ENGINE_DOC_GENETARE_EMPTY_DETAIL_ERROR_MSG = "流水账明细数据为空";
    public static final String ENGINE_DOC_GENETARE_UNRESOVE_ERROR_MSG = "\"请会计处理\"的流水账不生成凭证！";
}
