package com.github.outerman.be.api.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/** 理票结算方式 */
public class AcmSortReceiptSettlestyle implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID  */
    private Long id;

    /** 客户组织机构id[set_org]  */
    private Long orgId;

    /** 理票单id[acm_sort_receipt]  */
    private Long sortReceiptId;

    /** 结算方式(1:转账支票2:记应付)  */
    private Long settleStyle;
    private String settleStyleName;

    /** 金额  */
    private Double taxInclusiveAmount;

    /** 银行账号ID  */
    private Long bankAccountId;
    /** 银行账号名称  */
    private String bankAccountName;
    /** 银行账号属性  */
    private String bankAccountAttr;
    /** 账户启用状态 1启用 0不启用 */
    private Boolean bankAccountStatus;

    /** 票据号  */
    private String notesNum;

    /** 供应商  */
    private Long vendor;
    private String vendorName;

    /** 客户 */
    private Long consumer;
    private String consumerName;

    /** 员工  */
    private Long employee;
    private String employeeName;
    private String employeeMobile; // 员工手机, 仅用于外部系统传入, 唯一确定人员

    /** 时间戳  */
    private Timestamp ts;

    /** 支付方向  */
    private Integer payType;

    /** 摘要  */
    private String memo;

    public void setId(Long id) {
        this.id = id;
    }

    /** ID  */
    public Long getId() {
        return id;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /** 客户组织机构id[set_org]  */
    public Long getOrgId() {
        return orgId;
    }

    public Long getSortReceiptId() {
        return sortReceiptId;
    }

    public void setSortReceiptId(Long sortReceiptId) {
        this.sortReceiptId = sortReceiptId;
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

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public void setNotesNum(String notesNum) {
        this.notesNum = notesNum;
    }

    /** 票据号  */
    public String getNotesNum() {
        return notesNum;
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

    public Long getSettleStyle() {
        return settleStyle;
    }

    public void setSettleStyle(Long settleStyle) {
        this.settleStyle = settleStyle;
    }

    public String getSettleStyleName() {
        return settleStyleName;
    }

    public void setSettleStyleName(String settleStyleName) {
        this.settleStyleName = settleStyleName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Long getConsumer() {
        return consumer;
    }

    public void setConsumer(Long consumer) {
        this.consumer = consumer;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getBankAccountAttr() {
        return bankAccountAttr;
    }

    public void setBankAccountAttr(String bankAccountAttr) {
        this.bankAccountAttr = bankAccountAttr;
    }

    /**
     * 获取账户启用状态 1启用 0不启用
     * @return 账户启用状态 1启用 0不启用
     */
    public Boolean getBankAccountStatus() {
        return bankAccountStatus;
    }

    /**
     * 设置账户启用状态 1启用 0不启用
     * @param bankAccountStatus 账户启用状态 1启用 0不启用
     */
    public void setBankAccountStatus(Boolean bankAccountStatus) {
        this.bankAccountStatus = bankAccountStatus;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
