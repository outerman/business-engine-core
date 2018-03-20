package com.github.outerman.be.model;

import java.io.Serializable;

/** 组织机构表(业务库) */
public class SetOrg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -770416714281031442L;

	/** ID  */ 
	private Long id;

	/** 组织机构名称  账套名称  */
	private String name;

	/** 版本类型(例如：1:专业版 2:普及版)  */ 
	private Long version;

	/** 标志(1:贷账公司;2:个人用户;3:个人贷账)  */ 
	private Integer orgType;

	/** 行业  */ 
	private Long industry;
	
	/** 企业会计准则  */ 
	private Long accountingStandards;

	/*纳税人身份*/
	private Long vatTaxpayer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public Long getIndustry() {
		return industry;
	}

	public void setIndustry(Long industry) {
		this.industry = industry;
	}

	public Long getAccountingStandards() {
		return accountingStandards;
	}

	public void setAccountingStandards(Long accountingStandards) {
		this.accountingStandards = accountingStandards;
	}

    public Long getVatTaxpayer() {
        return vatTaxpayer;
    }

    public void setVatTaxpayer(Long vatTaxpayer) {
        this.vatTaxpayer = vatTaxpayer;
    }
}
