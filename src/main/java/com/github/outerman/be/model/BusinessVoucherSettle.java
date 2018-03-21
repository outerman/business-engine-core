package com.github.outerman.be.model;

public class BusinessVoucherSettle {

    /** 账户 */
    private Long bankAccountId;

    /** 账户类型 */
    private Long bankAccountTypeId;

    /** 金额 */
    private Double taxInclusiveAmount;

    /** 供应商  */
    private Long supplierId;

    /** 客户 */
    private Long customerId;

    /** 员工 */
    private Long personId;

    /** 业务属性：收入或者支出 */
    private Long businessPropertyId;

    /** 备注 */
    private String remark;

    /**
     * 获取bankAccountId
     * @return bankAccountId
     */
    public Long getBankAccountId() {
        return bankAccountId;
    }

    /**
     * 设置bankAccountId
     * @param bankAccountId bankAccountId
     */
    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    /**
     * 获取bankAccountTypeId
     * @return bankAccountTypeId
     */
    public Long getBankAccountTypeId() {
        return bankAccountTypeId;
    }

    /**
     * 设置bankAccountTypeId
     * @param bankAccountTypeId bankAccountTypeId
     */
    public void setBankAccountTypeId(Long bankAccountTypeId) {
        this.bankAccountTypeId = bankAccountTypeId;
    }

    /**
     * 获取taxInclusiveAmount
     * @return taxInclusiveAmount
     */
    public Double getTaxInclusiveAmount() {
        return taxInclusiveAmount;
    }

    /**
     * 设置taxInclusiveAmount
     * @param taxInclusiveAmount taxInclusiveAmount
     */
    public void setTaxInclusiveAmount(Double taxInclusiveAmount) {
        this.taxInclusiveAmount = taxInclusiveAmount;
    }

    /**
     * 获取supplierId
     * @return supplierId
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置supplierId
     * @param supplierId supplierId
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取customerId
     * @return customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 设置customerId
     * @param customerId customerId
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取personId
     * @return personId
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * 设置personId
     * @param personId personId
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    /**
     * 获取businessPropertyId
     * @return businessPropertyId
     */
    public Long getBusinessPropertyId() {
        return businessPropertyId;
    }

    /**
     * 设置businessPropertyId
     * @param businessPropertyId businessPropertyId
     */
    public void setBusinessPropertyId(Long businessPropertyId) {
        this.businessPropertyId = businessPropertyId;
    }

    /**
     * 获取remark
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置remark
     * @param remark remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
