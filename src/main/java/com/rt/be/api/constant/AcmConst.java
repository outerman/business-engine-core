package com.rt.be.api.constant;

import java.util.Arrays;
import java.util.List;

public final class AcmConst {

    /**
     * 性别 code
     */
    public static final String ENUM_CODE_SEX = "sex";

    /**
     * 邀请方式 code
     */
    public static final String ENUM_CODE_INVITATION_WAY = "invitationWay";

    /**
     * 学历 code
     */
    public static final String ENUM_CODE_QUALIFICATIONS = "qualifications";

    /**
     * 婚姻状况 code
     */
    public static final String ENUM_CODE_MARITAL_STATUS = "maritalStatus";

    /**
     * 用户状态 code
     */
    public static final String ENUM_CODE_USER_STATUS = "userstatus";

    /**
     * 行业枚举 code
     */
    public static final String ENUM_CODE_INDUSTRY = "industry";

    /**
     * 注册类型 code
     */
    public static final String ENUM_CODE_COMPANY_NATURE = "companyNature";

    /**
     * 收入规模 code
     */
    public static final String ENUM_CODE_REVENUE_SCALE = "revenueScale";

    /**
     * 状态 code
     */
    public static final String ENUM_CODE_STATUS = "status";

    /**
     * 企业会计准则 code
     */
    public static final String ENUM_CODE_ACCOUNTING_STANDARDS = "accountingStandards";

    /**
     * 币种 code
     */
    public static final String ENUM_CODE_BASE_CURRENCY = "baseCurrency";

    /**
     * 每月票据量 code
     */
    public static final String ENUM_CODE_PERMONTH_RECEIPT = "perMonthReceipt";

    /**
     * 每月凭证量 code
     */
    public static final String ENUM_CODE_PERMONTH_DOC = "perMonthDoc";

    /**
     * 企业所得税申报时间 code
     */
    public static final String ENUM_CODE_BUSINESS_INCOMETAX_PERIOD = "businessIncomeTaxPeriod";

    /**
     * 企业所得税纳税申报方式 code
     */
    public static final String ENUM_CODE_BUSINESS_INCOMETAX_PROCESS = "businessIncomeTaxProcess";

    /**
     * 企业所得税征收方式 code
     */
    public static final String ENUM_CODE_BUSINESS_INCOMETAX_MODE = "businessIncomeTaxMode";

    /**
     * 企业所得税税率 code
     */
    public static final String ENUM_CODE_BUSINESS_INCOMETAX_RATE = "businessIncomeTaxRate";

    /**
     * 增值税纳税人身份 code
     */
    public static final String ENUM_CODE_VAT_TAXPAYER = "vatTaxpayer";

    /**
     * 增值税征收方式 code
     */
    public static final String ENUM_CODE_VAT_MODE = "vatMode";

    /**
     * 增值税申报时间 code
     */
    public static final String ENUM_CODE_VAT_PERIOD = "vatPeriod";

    /**
     * 增值税纳税申报方式 code
     */
    public static final String ENUM_CODE_VAT_PROCESS = "vatProcess";

    /**
     * 税率或征收率 code
     */
    public static final String ENUM_CODE_TAXRATE = "taxRate";

    /**
     * 定制财务报表 code
     */
    public static final String ENUM_CODE_FINANCIAL_STATEMENTS = "financialStatements";

    /**
     * 取票理票约定 code
     */
    public static final String ENUM_CODE_TICKET_PRINCIPLE = "ticketPrinciple";

    /**
     * 收支类型 code
     */
    public static final String ENUM_CODE_PAYMENTS_TYPE = "paymentsType";

    /**
     * 理票单状态 code
     */
    public static final String ENUM_CODE_SORTRECEIPT_TPYE = "sortReceiptType";

    /**
     * 理票业务类型 code
     */
    public static final String ENUM_CODE_BUSINESS_TYPE = "businessType";

    /**
     * 发票类型 code
     */
    public static final String ENUM_CODE_INVOICE_TYPE = "invoiceType";

    /**
     * 结算方式 code
     */
    public static final String ENUM_CODE_SETTLE_STYLE = "settleStyle";

    /**
     * 部门属性 code
     */
    public static final String ENUM_CODE_DEPT_PROPERTY = "deptProperty";

    /**
     * 支付方式 code
     */
    public static final String ENUM_CODE_PAY_TYPE = "payType";
    /**
     * 核定方式code
     */
    public static final String ENUM_CODE_CHECKMODE_TYPE = "checkMode";

    /**
     * 已有管理员邀请密码提示信息
     */
    public static final String CUS_PASSD = "易嘉账户原有密码";

    /**
     * 定义orgId常量字符串
     */
    public static final String ORGID = "orgId";

    /**
     * 定义常量0
     */
    public static final int ZERO_INT = 0;

    /**
     * 定义常量空字符串
     */
    public static final String STR_EMPTY = "";

    /**
     * 定义常量 Q
     */
    public static final String STR_QUARTER = "Q";

    /**
     * 定义常量分页偏移量
     */
    public static final String OFFSET = "offset";

    /**
     * 定义常量分页显示条数
     */
    public static final String PAGESIZE = "pageSize";

    /**
     * 收支方向: 没有方向
     */
    public static final int PAYMENT_DIRECTION_UNKNOWN = 0;

    /**
     * 收支方向: 收入
     */
    public static final int PAYMENT_DIRECTION_IN = 1;

    /**
     * 收支防线: 支出
     */
    public static final int PAYMENT_DIRECTION_OUT = 2;

    /** 理票单状态，数据库表 acm_sort_receipt 字段 status*/
    /**
     * 草稿（暂存）
     */
    public static final byte SORT_RECEIPT_STATUS_DRAFT = 1;

    /**
     * 未审核
     */
    public static final byte SORT_RECEIPT_STATUS_NOT_APPROVE = 2;

    /**
     * 已审核
     */
    public static final byte SORT_RECEIPT_STATUS_APPROVED = 3;

    /**
     * 已驳回
     */
    public static final byte SORT_RECEIPT_STATUS_REJECTED = 4;

    /**
     * 定义常量收支类型－收入枚举值
     */
    public static final String ENUM_VALUE_PAYMENTS_TYPE_INCOME = "enumValuePaymentsTypeIncome";

    /**
     * 定义常量收支类型－其它枚举值
     */
    public static final String ENUM_VALUE_PAYMENTS_TYPE_OTHER = "enumValuePaymentsTypeOther";

    /**
     * 定义常量收支类型枚举值
     */
    public static final String ENUM_VALUE_PAYMENTS_TYPE = "enumValuePaymentsType";

    /**
     * 定义常量发票类型枚举值
     */
    public static final String ENUM_VALUE_INVOICE_TYPE = "enumValueInvoiceType";

    /**
     * 收支统计表在分享和打印时候的名字
     */
    public static final String IN_OUT_TABLE_NAME = "收支统计表";

    public static final String IN_OUT_TABLE_HEAD_ORG_PREFIX = "单位：";

    public static final String IN_OUT_TABLE_HEAD_ACCOUNT = "期间：";

    public static final String IN_OUT_TABLE_HEAD_SPLIT = "至";

    public static final String IN_OUT_TABLE_TABLE_TYPE = "类型";
    public static final String IN_OUT_TABLE_TABLE_SUB_TYPE = "业务类型";

    public static final String IN_OUT_TABLE_TABLE_DEPT = "部门";

    public static final String IN_OUT_TABLE_TABLE_PERSON = "职员";

    public static final String IN_OUT_TABLE_TABLE_PROJECT = "项目";

    public static final String IN_OUT_TABLE_TABLE_IN = "收入";
    public static final String IN_OUT_TABLE_TABLE_IN_SUM = "收入小计";

    public static final String IN_OUT_TABLE_TABLE_OUT = "支出";
    public static final String IN_OUT_TABLE_TABLE_OUT_SUM = "支出小计";

    public static final String IN_OUT_TABLE_TABLE_PROFIT = "收支差额";
    public static final String IN_OUT_TABLE_TABLE_TAX = "税额";
    public static final String IN_OUT_TABLE_TABLE_IN_OUT_PERCENT = "收入/支出";

    /** 流水账附件最大数量 */
    public static final int RECEIPT_MAX_ENCLOSURE_COUNT = 20;

    /** 流水账图片附件最大数量 */
    public static final int RECEIPT_MAX_IMG_ENCLOSURE_COUNT = 10;

    /** 流水账非图片附件最大数量 */
    public static final int RECEIPT_MAX_OTHER_ENCLOSURE_COUNT = 10;

    private AcmConst() {
    }
    
    public static final String IMPORT_FILENAME_DECLARATION_RECEIPT_EXCEL = "/template/acm/exportReceiptTemplate.xls";


    /**
     * 从 yj-common里移动过来
     */
    /**
     * 收支类型: 10000: 收入
     */
    public static final long PAYMENTSTYPE_10 = 10000L;

    /**
     * 收支类型: 10001: 支出
     */
    public static final long PAYMENTSTYPE_20 = 10001L;

    /**
     * 收支类型: 10002: 成本/折旧和摊销
     */
    public static final long PAYMENTSTYPE_30 = 10002L;

    /**
     * 收支类型: 10003: 存取现金/内部账户互转
     */
    public static final long PAYMENTSTYPE_40 = 10003L;

    /**
     * 收支类型: 10004: 收款/付款
     */
    public static final long PAYMENTSTYPE_50 = 10004L;

    /**
     * 收支类型: 10005: 请会计处理
     */
    public static final long PAYMENTSTYPE_60 = 10005L;

    /**
     * 部门属性: 200000000000071: 与生产相关
     *
     * 从 yj-common里移动过来
     */
    public static final long DEPTPROPERTY_002 = 200000000000071L;

    /**
     * 单据类型
     */
    public static final long VOUCHERTYPE = 39L;

    /**
     * 单据类型: 109: 理票单
     */
    public static final long VOUCHERTYPE_0001 = 109L;

    /**
     * 单据类型: 110: 工资单
     */
    public static final long VOUCHERTYPE_0002 = 110L;

    /**
     * 单据类型: 100001: 工资单-工资-计提
     */
    public static final long VOUCHERTYPE_0003 = 100001L;

    /**
     * 单据类型: 100002: 工资单-社保-计提
     */
    public static final long VOUCHERTYPE_0004 = 100002L;

    /**
     * 单据类型: 100003: 工资单-住房公积金-计提
     */
    public static final long VOUCHERTYPE_0005 = 100003L;

    /**
     * 单据类型: 100004: 工资单-工资-发放
     */
    public static final long VOUCHERTYPE_0006 = 100004L;

    /**
     * 单据类型: 100005: 工资单-社保-缴纳
     */
    public static final long VOUCHERTYPE_0007 = 100005L;

    /**
     * 单据类型: 100006: 工资单-住房公积金-缴纳
     */
    public static final long VOUCHERTYPE_0008 = 100006L;

    /**
     * 单据类型: 100007: 工资单-个税-缴纳
     */
    public static final long VOUCHERTYPE_0009 = 100007L;


    public static final String BUSINESS_TEMPLATE = "BusinessTemplate";

    /** 银行账号（结算方式）column id */
    public static final Long BANK_ACCOUNT_COLUMN_ID = 14L;

    /** 结算方式 column id */
    public static final Long SETTLE_STYLE_COLUMN_ID = 12L;

    /** 税率 column id */
    public static final Long TAX_RATE_COLUMN_ID = 16L;

    /** 一般纳税人 column id */
    public static final Long VAT_TAX_PAYER_41_COLUMN_ID = 33L;

    /** 小规模纳税人 column id */
    public static final Long VAT_TAX_PAYER_42_COLUMN_ID = 32L;

    /** 会计准则 id 列表 */
    public static final List<Long> ACCOUNTING_STANDARD_ID_LIST = Arrays.asList(18L, 19L);

}
