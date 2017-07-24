package com.rt.be.api.vo;

import java.sql.Timestamp;

/**
 * 税率档案 dto 实体定义类
 * @author gaoxue
 *
 */
public class SetTaxRateDto {

    /** 组织 id 对应表 set_org */
    private Long orgId;

    /** id */
    private Long id;

    /** 增值税纳税人身份 枚举 vatTaxpayer id */
    private Long vatTaxpayer;

    /** 税率名称 */
    private String name;

    /** 税率 */
    private Double taxRate;

    /** 实际征收税率 */
    private Double privilegeTaxRate;
    
    /** 计税方式 */
    private Long type;

    /** 时间戳 */
    private Timestamp ts;

    /**
     * 获取组织 id 对应表 set_org
     * @return 组织 id 对应表 set_org
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置组织 id 对应表 set_org
     * @param orgId 组织 id 对应表 set_org
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取增值税纳税人身份 枚举 vatTaxpayer id
     * @return 增值税纳税人身份 枚举 vatTaxpayer id
     */
    public Long getVatTaxpayer() {
        return vatTaxpayer;
    }

    /**
     * 设置增值税纳税人身份 枚举 vatTaxpayer id
     * @param vatTaxpayer 增值税纳税人身份 枚举 vatTaxpayer id
     */
    public void setVatTaxpayer(Long vatTaxpayer) {
        this.vatTaxpayer = vatTaxpayer;
    }

    /**
     * 获取税率名称
     * @return 税率名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置税率名称
     * @param name 税率名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取税率
     * @return 税率
     */
    public Double getTaxRate() {
        return taxRate;
    }

    /**
     * 设置税率
     * @param taxRate 税率
     */
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 获取实际征收税率
     * @return 实际征收税率
     */
    public Double getPrivilegeTaxRate() {
        return privilegeTaxRate;
    }

    /**
     * 设置实际征收税率
     * @param privilegeTaxRate 实际征收税率
     */
    public void setPrivilegeTaxRate(Double privilegeTaxRate) {
        this.privilegeTaxRate = privilegeTaxRate;
    }

    /**
     * 获取时间戳
     * @return 时间戳
     */
    public Timestamp getTs() {
        return ts;
    }

    /**
     * 设置时间戳
     * @param ts 时间戳
     */
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
