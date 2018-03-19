package com.github.outerman.be.api.vo;

import java.io.Serializable;

public class AcmSortReceiptSettlestyle implements Serializable {

    private static final long serialVersionUID = -4813159299386061246L;

    /** 业务属性：收入或者支出 */
    private Long businessPropertyId;

    /** 金额  */
    private Double taxInclusiveAmount;

    /** 银行账号ID  */
    private Long bankAccountId;

    /** 银行账号属性  */
    private String bankAccountAttr;

    /** 供应商  */
    private Long vendor;

    /** 客户 */
    private Long consumer;

    /** 员工  */
    private Long employee;

    /** 摘要  */
    private String memo;

    /**
     * 获取业务属性：收入或者支出
     * @return 业务属性：收入或者支出
     */
    public Long getBusinessPropertyId() {
        return businessPropertyId;
    }

    /**
     * 设置业务属性：收入或者支出
     * @param businessPropertyId 业务属性：收入或者支出
     */
    public void setBusinessPropertyId(Long businessPropertyId) {
        this.businessPropertyId = businessPropertyId;
    }

    public void setTaxInclusiveAmount(Double taxInclusiveAmount) {
        this.taxInclusiveAmount = taxInclusiveAmount;
    }

    /** 金额  */
    public Double getTaxInclusiveAmount() {
        return taxInclusiveAmount;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public void setVendor(Long vendor) {
        this.vendor = vendor;
    }

    /** 供应商  */
    public Long getVendor() {
        return vendor;
    }

    public void setEmployee(Long employee) {
        this.employee = employee;
    }

    /** 员工  */
    public Long getEmployee() {
        return employee;
    }

    public Long getConsumer() {
        return consumer;
    }

    public void setConsumer(Long consumer) {
        this.consumer = consumer;
    }

    public String getBankAccountAttr() {
        return bankAccountAttr;
    }

    public void setBankAccountAttr(String bankAccountAttr) {
        this.bankAccountAttr = bankAccountAttr;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
