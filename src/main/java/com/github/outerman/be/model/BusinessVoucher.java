package com.github.outerman.be.model;

import java.util.Date;
import java.util.List;

public class BusinessVoucher {

    /** 理票单号  */
    private String code;

    /** 收支类型  */
    private Long paymentsType;

    /** 入账日期  */
    private Date inAccountDate;

    /** 凭证id（将来生成的凭证的id，可以获取凭证号，财务人员等信息）  */
    private Long docId;

    /** 凭证号  */
    private String docCode;

    /** 凭证号备份，用于流水账驳回之后再审核生成凭照使用 */
    private String docCodeBak;

    /** 附件数 */
    private int appendNum;

    /** 来源单类型 id */
    private Long sourceVoucherTypeId;

    /** 来源单 id */
    private Long sourceVoucherId;

    /** 来源单编码 */
    private String sourceVoucherCode;

    /** 显示字段－收支类型名称(1:收入2:支出)  */
    private String paymentsTypeName;

    /** 显示字段-票据编码 */
    private String invoiceNo;

    /** 显示字段－摘要 */
    private String memo;

    /** 单据信息是否合法 */
    private Boolean valid = true;

    /** 业务明细信息 */
    private List<BusinessVoucherDetail> details;

    /** 结算明细信息 */
    private List<BusinessVoucherSettle> settles;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPaymentsType() {
        return paymentsType;
    }

    public void setPaymentsType(Long paymentsType) {
        this.paymentsType = paymentsType;
    }

    public Date getInAccountDate() {
        return inAccountDate;
    }

    public void setInAccountDate(Date inAccountDate) {
        this.inAccountDate = inAccountDate;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    /**
     * 获取凭证号备份，用于流水账驳回之后再审核生成凭照使用
     * @return 凭证号备份
     */
    public String getDocCodeBak() {
        return docCodeBak;
    }

    /**
     * 设置凭证号备份，用于流水账驳回之后再审核生成凭照使用
     * @param docCodeBak 凭证号备份
     */
    public void setDocCodeBak(String docCodeBak) {
        this.docCodeBak = docCodeBak;
    }

    public String getPaymentsTypeName() {
        return paymentsTypeName;
    }

    public void setPaymentsTypeName(String paymentsTypeName) {
        this.paymentsTypeName = paymentsTypeName;
    }

    /**
     * 获取显示字段-票据编码
     * @return 显示字段-票据编码
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 设置显示字段-票据编码
     * @param invoiceNo 显示字段-票据编码
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public int getAppendNum() {
        return appendNum;
    }

    public void setAppendNum(int appendNum) {
        this.appendNum = appendNum;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取来源单类型 id
     * @return 来源单类型 id
     */
    public Long getSourceVoucherTypeId() {
        return sourceVoucherTypeId;
    }

    /**
     * 设置来源单类型 id
     * @param sourceVoucherTypeId 来源单类型 id
     */
    public void setSourceVoucherTypeId(Long sourceVoucherTypeId) {
        this.sourceVoucherTypeId = sourceVoucherTypeId;
    }

    /**
     * 获取来源单 id
     * @return 来源单 id
     */
    public Long getSourceVoucherId() {
        return sourceVoucherId;
    }

    /**
     * 设置来源单 id
     * @param sourceVoucherId 来源单 id
     */
    public void setSourceVoucherId(Long sourceVoucherId) {
        this.sourceVoucherId = sourceVoucherId;
    }

    /**
     * 获取来源单编码
     * @return 来源单编码
     */
    public String getSourceVoucherCode() {
        return sourceVoucherCode;
    }

    /**
     * 设置来源单编码
     * @param sourceVoucherCode 来源单编码
     */
    public void setSourceVoucherCode(String sourceVoucherCode) {
        this.sourceVoucherCode = sourceVoucherCode;
    }

    /**
     * @return 获取单据信息是否合法
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 设置单据信息是否合法
     * @param valid 单据信息是否合法
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * 获取业务明细信息
     * @return 业务明细信息
     */
    public List<BusinessVoucherDetail> getDetails() {
        return details;
    }

    /**
     * 设置业务明细信息
     * @param details 业务明细信息
     */
    public void setDetails(List<BusinessVoucherDetail> details) {
        this.details = details;
    }

    /**
     * 获取结算明细信息
     * @return 结算明细信息
     */
    public List<BusinessVoucherSettle> getSettles() {
        return settles;
    }

    /**
     * 设置结算明细信息
     * @param settles 结算明细信息
     */
    public void setSettles(List<BusinessVoucherSettle> settles) {
        this.settles = settles;
    }

}
