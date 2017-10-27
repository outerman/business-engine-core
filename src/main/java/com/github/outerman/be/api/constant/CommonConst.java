package com.github.outerman.be.api.constant;

import java.util.Arrays;
import java.util.List;

public final class CommonConst {

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

    /** 部门 column id */
    public static final Long DEPARTMENT_COLUMN_ID = 4L;

    /** 人员 column id */
    public static final Long PERSON_COLUMN_ID = 5L;

    /** 商品或服务 column id */
    public static final Long INVENTORY_COLUMN_ID = 7L;

    /** 资产 column id */
    public static final Long ASSET_COLUMN_ID = 9L;

    /** 资产类别 column id */
    public static final Long ASSET_TYPE_COLUMN_ID = 8L;

    /** 银行账号（结算方式）column id */
    public static final Long BANK_ACCOUNT_COLUMN_ID = 14L;

    /** 结算方式 column id */
    public static final Long SETTLE_STYLE_COLUMN_ID = 12L;

    /** 税率 column id */
    public static final Long TAX_RATE_COLUMN_ID = 16L;

    /** 罚款性质 column id */
    public static final Long PENALTY_TYPE_COLUMN_ID = 23L;

    /** 借款期限 column id */
    public static final Long LOAN_TERM_COLUMN_ID = 24L;

    /**  */
    public static final Long BANK_ACCOUNT_TWO_COLUMN_ID = 25L;

    /** 认证 column id */
    public static final Long QUALIFICATION_COLUMN_ID = 29L;

    /** 一般纳税人 column id */
    public static final Long VAT_TAX_PAYER_41_COLUMN_ID = 33L;

    /** 小规模纳税人 column id */
    public static final Long VAT_TAX_PAYER_42_COLUMN_ID = 32L;

    /** 会计准则 id 列表 */
    public static final List<Long> ACCOUNTING_STANDARD_ID_LIST = Arrays.asList(18L, 19L);

    /**
     * 企业会计准则: 18: 企业会计准则2007
     */
    public static final long ACCOUNTINGSTANDARDS_0001 = 18L;

    /**
     * 企业会计准则: 19: 小企业会计准则2013
     */
    public static final long ACCOUNTINGSTANDARDS_0002 = 19L;

    /** 影响因素部门属性 */
    public static final String INFLUENCE_DEPARTMENT_ATTR = "departmentAttr";

    /** 影响因素部门属性、人员属性 */
    public static final String INFLUENCE_DEPT_PERSON_ATTR = "departmentAttr,personAttr";

    /** 影响因素纳税人 */
    public static final String INFLUENCE_VAT_TAXPAYER = "vatTaxpayer";

    /** 影响因素纳税人、认证 */
    public static final String INFLUENCE_VAT_TAXPAYER_QUALIFICATION = "vatTaxpayer,qualification";

    /** 影响因素纳税人、计税方式 */
    public static final String INFLUENCE_VAT_TAXPAYER_TAXTYPE = "vatTaxpayer,taxType";

    /** 影响因素罚款性质 */
    public static final String INFLUENCE_PUNISHMENT_ATTR = "punishmentAttr";

    /** 影响因素借款期限 */
    public static final String INFLUENCE_BORROW_ATTR = "borrowAttr";

    /** 影响因素存货属性 */
    public static final String INFLUENCE_INVENTORY_ATTR = "inventoryAttr";

    /** 影响因素资产属性 */
    public static final String INFLUENCE_ASSET_ATTR = "assetAttr";

    /** 影响因素账户属性流入 */
    public static final String INFLUENCE_ACCOUNTIN_ATTR = "accountInAttr";

    /** 影响因素账户属性流出 */
    public static final String INFLUENCE_ACCOUNTOUT_ATTR = "accountOutAttr";

    /** 影响因素表达式 */
    public static final String INFLUENCE_FORMULA = "formula";

    private CommonConst() {
    }
}
