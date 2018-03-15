package com.github.outerman.be.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AcmSortReceipt implements Serializable {

    private static final long serialVersionUID = 6813334363348077459L;

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

    /** 所属理票详单列表  */
    private List<AcmSortReceiptDetail> acmSortReceiptDetailList;

    /** 所属结算方式列表  */
    private List<AcmSortReceiptSettlestyle> acmSortReceiptSettlestyleList;

    /** 显示字段－收支类型名称(1:收入2:支出)  */
    private String paymentsTypeName;

    /** 显示字段-票据编码 */
    private String invoiceNo;

    /** 显示字段－摘要 */
    private String memo;

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

    public List<AcmSortReceiptDetail> getAcmSortReceiptDetailList() {
        return acmSortReceiptDetailList;
    }

    public void setAcmSortReceiptDetailList(List<AcmSortReceiptDetail> acmSortReceiptDetailList) {
        this.acmSortReceiptDetailList = acmSortReceiptDetailList;
    }

    public List<AcmSortReceiptSettlestyle> getAcmSortReceiptSettlestyleList() {
        return acmSortReceiptSettlestyleList;
    }

    public void setAcmSortReceiptSettlestyleList(List<AcmSortReceiptSettlestyle> acmSortReceiptSettlestyleList) {
        this.acmSortReceiptSettlestyleList = acmSortReceiptSettlestyleList;
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

}
