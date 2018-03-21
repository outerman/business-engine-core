package com.github.outerman.be.model;

import java.io.Serializable;

/** 凭证结算模板 */
public class SettleTemplate implements Serializable {

    private static final long serialVersionUID = 4424620273857892825L;

    /** 账户类型，枚举 bankAccountType */
    private Long bankAccountTypeId;

    /** 业务属性，枚举 businessProperty */
    private Long businessPropertyId;

    /** 科目编码 */
    private String accountCode;

    /** 余额方向：0借 1贷 */
    private Integer balanceDirection;

    /** 金额来源表达式 */
    private String amountSource;

    /** 金额为负时是否颠倒余额方向 */
    private Boolean reversal;

    /** 摘要 */
    private String summary;

    /** 科目信息 */
    private Account account;

    /**
     * 获取账户类型，枚举 bankAccountType
     * @return 账户类型，枚举 bankAccountType
     */
    public Long getBankAccountTypeId() {
        return bankAccountTypeId;
    }

    /**
     * 设置账户类型，枚举 bankAccountType
     * @param bankAccountTypeId 账户类型，枚举 bankAccountType
     */
    public void setBankAccountTypeId(Long bankAccountTypeId) {
        this.bankAccountTypeId = bankAccountTypeId;
    }

    /**
     * 获取业务属性，枚举 businessProperty
     * @return 业务属性，枚举 businessProperty
     */
    public Long getBusinessPropertyId() {
        return businessPropertyId;
    }

    /**
     * 设置业务属性，枚举 businessProperty
     * @param businessPropertyId 业务属性，枚举 businessProperty
     */
    public void setBusinessPropertyId(Long businessPropertyId) {
        this.businessPropertyId = businessPropertyId;
    }

    /**
     * 获取科目编码
     * @return 科目编码
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * 设置科目编码
     * @param accountCode 科目编码
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * 获取余额方向：0借 1贷
     * @return 余额方向：0借 1贷
     */
    public Integer getBalanceDirection() {
        return balanceDirection;
    }

    /**
     * 设置余额方向：0借 1贷
     * @param balanceDirection 余额方向：0借 1贷
     */
    public void setBalanceDirection(Integer balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    /**
     * 获取金额来源表达式
     * @return 金额来源表达式
     */
    public String getAmountSource() {
        return amountSource;
    }

    /**
     * 设置金额来源表达式
     * @param amountSource 金额来源表达式
     */
    public void setAmountSource(String amountSource) {
        this.amountSource = amountSource;
    }

    /**
     * 获取金额为负时是否颠倒余额方向
     * @return 金额为负时是否颠倒余额方向
     */
    public Boolean getReversal() {
        return reversal;
    }

    /**
     * 设置金额为负时是否颠倒余额方向
     * @param reversal 金额为负时是否颠倒余额方向
     */
    public void setReversal(Boolean reversal) {
        this.reversal = reversal;
    }

    /**
     * 获取摘要
     * @return 摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置摘要
     * @param summary 摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取科目信息 
     * @return 科目信息 
     */
    public Account getAccount() {
        return account;
    }

    /**
     * 设置科目信息 
     * @param account 科目信息 
     */
    public void setAccount(Account account) {
        this.account = account;
    }

}
