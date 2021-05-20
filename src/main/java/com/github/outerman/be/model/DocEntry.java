package com.github.outerman.be.model;

import java.io.Serializable;

public class DocEntry implements Serializable {

    private static final long serialVersionUID = 4609809951218436168L;

    /** 摘要[set_Summary]  */
    private String summary;

    /** 科目ID[set_account]  */
    private Long accountId;

    private String accountCode;

    /** 借方原币  */
    private Double origAmountDr;

    /** 贷方原币  */
    private Double origAmountCr;

    /** 借方本币  */
    private Double amountDr;

    /** 贷方本币  */
    private Double amountCr;

    /** 数量  */
    private Double quantity;

    /** 单价  */
    private Double price;

    /** 计量单位id[set_unit]  */
    private Long unitId;

    /** 币种ID[set_currency]  */
    private Long currencyId;

    /** 汇率  */
    private Double exchangeRate;

    /** 部门id[set_department]  */
    private Long departmentId;

    /** 人员id[set_person]  */
    private Long personId;

    /** 客户ID  */
    private Long customerId;

    /** 供应商ID  */
    private Long supplierId;

    /** 存货id[set_inventory]  */
    private Long inventoryId;

    /** 项目ID[set_project]  */
    private Long projectId;

    /** 账号ID[set_bank_account]  */
    private Long bankAccountId;

    /** 即征即退项目ID  */
    private Long levyAndRetreatId;

    /** 来源单据业务类型  */
    private Long sourceBusinessTypeId;

    /** 来源单据 模板中标识取值  */
    private String sourceFlag;

    /** 方向：0：借，1:贷  */
    private byte direction;

    /** 是否颠倒了借贷方向 */
    private boolean reversal = false;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getOrigAmountDr() {
        return origAmountDr;
    }

    public void setOrigAmountDr(Double origAmountDr) {
        this.origAmountDr = origAmountDr;
    }

    public Double getOrigAmountCr() {
        return origAmountCr;
    }

    public void setOrigAmountCr(Double origAmountCr) {
        this.origAmountCr = origAmountCr;
    }

    public Double getAmountDr() {
        return amountDr;
    }

    public void setAmountDr(Double amountDr) {
        this.amountDr = amountDr;
    }

    public Double getAmountCr() {
        return amountCr;
    }

    public void setAmountCr(Double amountCr) {
        this.amountCr = amountCr;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Long getLevyAndRetreatId() {
        return levyAndRetreatId;
    }

    public void setLevyAndRetreatId(Long levyAndRetreatId) {
        this.levyAndRetreatId = levyAndRetreatId;
    }

    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }

    /**
     * 获取是否颠倒了借贷方向
     * @return 是否颠倒了借贷方向
     */
    public boolean isReversal() {
        return reversal;
    }

    /**
     * 设置是否颠倒了借贷方向
     * @param reversal 是否颠倒了借贷方向
     */
    public void setReversal(boolean reversal) {
        this.reversal = reversal;
    }

    public Long getSourceBusinessTypeId() {
        return sourceBusinessTypeId;
    }

    public void setSourceBusinessTypeId(Long sourceBusinessTypeId) {
        this.sourceBusinessTypeId = sourceBusinessTypeId;
    }

    public String getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(String sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

}
