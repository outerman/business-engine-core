package com.github.outerman.be.api.vo;

import java.io.Serializable;

/** 凭证结算模板 */
public class PaymentTemplateItem implements Serializable {

    private static final long serialVersionUID = 4424620273857892825L;

    /** 主键id */
    private Long id;

    /** 组织机构id */
    private Long orgId;

    /** 收支类型 */
    private Long paymentsType;

    /** 结算方式[set_settlement_type] */
    private Long settlement;

    /** 账户属性 */
    private Long accountType;

    /** 科目类型 */
    private String subjectType;

    /** 默认科目类型 */
    private String subjectDefault;

    /** 方向：0：借，1:贷 */
    private Boolean direction;

    /** 金额来源 */
    private String fundSource;

    /** 是否颠倒 */
    private Boolean reversal;

    private FiAccount account;

    public void setId(Long id) {
        this.id = id;
    }

    /** 主键id */
    public Long getId() {
        return id;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /** 组织机构id */
    public Long getOrgId() {
        return orgId;
    }

    public void setSettlement(Long settlement) {
        this.settlement = settlement;
    }

    /** 结算方式[set_settlement_type] */
    public Long getSettlement() {
        return settlement;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    /** 科目类型 */
    public String getSubjectType() {
        return subjectType;
    }

    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    /** 方向：0：借，1:贷 */
    public Boolean getDirection() {
        return direction;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    /** 金额来源 */
    public String getFundSource() {
        return fundSource;
    }

    public Boolean getReversal() {
        return reversal;
    }

    public void setReversal(Boolean reversal) {
        this.reversal = reversal;
    }

    public Long getPaymentsType() {
        return paymentsType;
    }

    public void setPaymentsType(Long paymentsType) {
        this.paymentsType = paymentsType;
    }

    public String getSubjectDefault() {
        return subjectDefault;
    }

    public void setSubjectDefault(String subjectDefault) {
        this.subjectDefault = subjectDefault;
    }

    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

    public FiAccount getAccount() {
        return account;
    }

    public void setAccount(FiAccount account) {
        this.account = account;
    }
}
