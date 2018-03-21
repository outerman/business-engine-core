package com.github.outerman.be.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Doc implements Serializable {

    private static final long serialVersionUID = 4609809951218436368L;

    /** 凭证id */
    private Long docId;

    /** 凭证编号  */
    private String code;

    /** 凭证日期  */
    private Date voucherDate;

    /** 来源单据类型id */
    private Long sourceVoucherTypeId;

    /** 来源单据id */
    private Long sourceVoucherId;

    /** 来源单据编码 */
    private String sourceVoucherCode;

    /** 所附单据个数  */
    private Integer attachedVoucherNum;

    /** 分录信息 */
    private List<DocEntry> entrys = new ArrayList<DocEntry>();

    /**
     * 获取凭证id
     * @return 凭证id
     */
    public Long getDocId() {
        return docId;
    }

    /**
     * 设置凭证id
     * @param docId 凭证id
     */
    public void setDocId(Long docId) {
        this.docId = docId;
    }

    /**
     * 获取凭证编号
     * @return 凭证编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置凭证编号
     * @param code 凭证编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取凭证日期
     * @return 凭证日期
     */
    public Date getVoucherDate() {
        return voucherDate;
    }

    /**
     * 设置凭证日期
     * @param voucherDate 凭证日期
     */
    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    /**
     * 获取来源单据类型id
     * @return 来源单据类型id
     */
    public Long getSourceVoucherTypeId() {
        return sourceVoucherTypeId;
    }

    /**
     * 设置来源单据类型id
     * @param sourceVoucherTypeId 来源单据类型id
     */
    public void setSourceVoucherTypeId(Long sourceVoucherTypeId) {
        this.sourceVoucherTypeId = sourceVoucherTypeId;
    }

    /**
     * 获取来源单据id
     * @return 来源单据id
     */
    public Long getSourceVoucherId() {
        return sourceVoucherId;
    }

    /**
     * 设置来源单据id
     * @param sourceVoucherId 来源单据id
     */
    public void setSourceVoucherId(Long sourceVoucherId) {
        this.sourceVoucherId = sourceVoucherId;
    }

    /**
     * 获取来源单据编码
     * @return 来源单据编码
     */
    public String getSourceVoucherCode() {
        return sourceVoucherCode;
    }

    /**
     * 设置来源单据编码
     * @param sourceVoucherCode 来源单据编码
     */
    public void setSourceVoucherCode(String sourceVoucherCode) {
        this.sourceVoucherCode = sourceVoucherCode;
    }

    /**
     * 获取所附单据个数
     * @return 所附单据个数
     */
    public Integer getAttachedVoucherNum() {
        return attachedVoucherNum;
    }

    /**
     * 设置所附单据个数
     * @param attachedVoucherNum 所附单据个数
     */
    public void setAttachedVoucherNum(Integer attachedVoucherNum) {
        this.attachedVoucherNum = attachedVoucherNum;
    }

    /**
     * 获取分录信息
     * @return 分录信息
     */
    public List<DocEntry> getEntrys() {
        return entrys;
    }

    /**
     * 设置分录信息
     * @param entrys 分录信息
     */
    public void setEntrys(List<DocEntry> entrys) {
        this.entrys = entrys;
    }

}
