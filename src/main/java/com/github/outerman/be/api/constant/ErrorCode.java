package com.github.outerman.be.api.constant;


/**
 * Created by shenxy on 13/7/17.
 *
 * 错误编码
 */
public class ErrorCode {
//    public static final BusinessException ENGINE_EMPTY_ORG_ERROR = new BusinessException("709001", "没有组织信息44");


    public static final String ENGINE_VALIDATE_ERROR_CODE = "708001";
    public static final String ENGINE_VALIDATE_ERROR_MESSAGE = "模板验证不正确！";


    /** 单号为空 */
    public static final String ENGINE_DOC_GENETARE_EMPTY_ACM_ERROR_CODE = "70900";
    public static final String ENGINE_DOC_GENETARE_EMPTY_ACM_ERROR_MSG = "流水单号为空";

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
