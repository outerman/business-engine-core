package com.github.outerman.be.model;

import java.io.Serializable;

/** 凭证结算模板 */
public class PaymentTemplateItem implements Serializable {

    private static final long serialVersionUID = 4424620273857892825L;

    /** 收支类型 */
    private Long paymentsType;

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
