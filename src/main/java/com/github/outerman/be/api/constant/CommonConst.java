package com.github.outerman.be.api.constant;

public final class CommonConst {

    /**
     * 业务属性:  4000040001: 收入
     */
    public static final long BUSINESSPROPERTY_income = 4000040001L;

    /**
     * 业务属性:  4000040002: 支出
     */
    public static final long BUSINESSPROPERTY_expenditure = 4000040002L;

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
