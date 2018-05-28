package com.github.outerman.be.model;

public class SourceVoucher {

    /** 来源单类型 id */
    private Long voucherTypeId;

    /** 来源单 id */
    private Long voucherId;

    /** 来源单编码 */
    private String voucherCode;

    /**
     * 获取来源单类型 id
     * @return 来源单类型 id
     */
    public Long getVoucherTypeId() {
        return voucherTypeId;
    }

    /**
     * 设置来源单类型 id
     * @param voucherTypeId 来源单类型 id
     */
    public void setVoucherTypeId(Long voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    /**
     * 获取来源单 id
     * @return 来源单 id
     */
    public Long getVoucherId() {
        return voucherId;
    }

    /**
     * 设置来源单 id
     * @param voucherId 来源单 id
     */
    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * 获取来源单编码
     * @return 来源单编码
     */
    public String getVoucherCode() {
        return voucherCode;
    }

    /**
     * 设置来源单编码
     * @param voucherCode 来源单编码
     */
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

}
