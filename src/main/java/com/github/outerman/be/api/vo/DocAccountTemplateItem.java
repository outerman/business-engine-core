package com.github.outerman.be.api.vo;

import java.io.Serializable;

/** 业务凭证模板  */
public class DocAccountTemplateItem implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3818718679387513417L;

	private Long id;//id                     
	
	private Long orgId;//orgId                     
	
	private Long industry;//行业编码  
	
	private Long businessId;//业务ID       
	
	private Long businessCode;//业务编码            
	
	private String businessName;//业务名称            
	
	private String flag;//标识取值            
	
	private String influence;//影响因素            
	
	private Long vatTaxpayer;//纳税人性质  
	
	private Long departmentAttr;//部门属性            
	
	private Long personAttr;//人员属性            
	
	private Boolean taxType;//计税方式
	
	private Boolean qualification;//认证
	
	
	private Long extendAttr;//扩展属性(包含资产，惩罚性质，借款期限，账号属性等)
	
	private Integer sort;//排序字段
	
	
	private Boolean direction;//借贷方向(0借1贷)  
	
	private String fundSource;//金额来源            
	
	private String accountCode;//科目编码            
	
	private String accountName;//科目名称            
	
	private Integer accountingStandardsId;//会计准则            
	
	private Boolean isSettlement;//是否结算       
	
   /** 摘要 */
    private String summary;

	/** 科目Id  */ 
	private Long accountId;
	/** 是否启用辅助核算 1启用 0不启用  */ 
	private Boolean isAuxAccCalc;
	
	/** 是否辅助核算：部门 1启用 0不启用  */ 
	private Boolean isAuxAccDepartment;
	
	/** 是否辅助核算：个人 1启用 0不启用  */ 
	private Boolean isAuxAccPerson;
	
	/** 是否辅助核算：客户 1启用 0不启用  */ 
	private Boolean isAuxAccCustomer;
	
	/** 是否辅助核算：供应商 1启用 0不启用  */ 
	private Boolean isAuxAccSupplier;
	
	/** 是否辅助核算：存货 1启用 0不启用  */ 
	private Boolean isAuxAccInventory;
	
	/** 是否辅助核算：项目 1启用 0不启用  */ 
	private Boolean isAuxAccProject;
	
	/** 是否启用数量核算 1启用 0不启用 启用数量核算，则必须启用存货辅助核算  */ 
	private Boolean isQuantityCalc;
	
	/** 是否启用多货币核算 1启用 0不启用  */ 
	private Boolean isMultiCalc;
	
	/** 是否辅助核算：银行账号 1启用 0不启用  */ 
	private Boolean isAuxAccBankAccount;

	/** 是否辅助核算：即征即退 */
    private Boolean isAuxAccLevyAndRetreat;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(Long businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getInfluence() {
		return influence;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}

	public Long getDepartmentAttr() {
		return departmentAttr;
	}

	public void setDepartmentAttr(Long departmentAttr) {
		this.departmentAttr = departmentAttr;
	}

	public Long getPersonAttr() {
		return personAttr;
	}

	public void setPersonAttr(Long personAttr) {
		this.personAttr = personAttr;
	}

	public Boolean getDirection() {
		return direction;
	}

	public void setDirection(Boolean direction) {
		this.direction = direction;
	}

	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getAccountingStandardsId() {
		return accountingStandardsId;
	}

	public void setAccountingStandardsId(Integer accountingStandardsId) {
		this.accountingStandardsId = accountingStandardsId;
	}

	public Boolean getIsAuxAccCalc() {
		return isAuxAccCalc;
	}

	public void setIsAuxAccCalc(Boolean isAuxAccCalc) {
		this.isAuxAccCalc = isAuxAccCalc;
	}

	public Boolean getIsAuxAccDepartment() {
		return isAuxAccDepartment;
	}

	public void setIsAuxAccDepartment(Boolean isAuxAccDepartment) {
		this.isAuxAccDepartment = isAuxAccDepartment;
	}

	public Boolean getIsAuxAccPerson() {
		return isAuxAccPerson;
	}

	public void setIsAuxAccPerson(Boolean isAuxAccPerson) {
		this.isAuxAccPerson = isAuxAccPerson;
	}

	public Boolean getIsAuxAccCustomer() {
		return isAuxAccCustomer;
	}

	public void setIsAuxAccCustomer(Boolean isAuxAccCustomer) {
		this.isAuxAccCustomer = isAuxAccCustomer;
	}

	public Boolean getIsAuxAccSupplier() {
		return isAuxAccSupplier;
	}

	public void setIsAuxAccSupplier(Boolean isAuxAccSupplier) {
		this.isAuxAccSupplier = isAuxAccSupplier;
	}

	public Boolean getIsAuxAccInventory() {
		return isAuxAccInventory;
	}

	public void setIsAuxAccInventory(Boolean isAuxAccInventory) {
		this.isAuxAccInventory = isAuxAccInventory;
	}

	public Boolean getIsAuxAccProject() {
		return isAuxAccProject;
	}

	public void setIsAuxAccProject(Boolean isAuxAccProject) {
		this.isAuxAccProject = isAuxAccProject;
	}

	public Boolean getIsQuantityCalc() {
		return isQuantityCalc;
	}

	public void setIsQuantityCalc(Boolean isQuantityCalc) {
		this.isQuantityCalc = isQuantityCalc;
	}

	public Boolean getIsMultiCalc() {
		return isMultiCalc;
	}

	public void setIsMultiCalc(Boolean isMultiCalc) {
		this.isMultiCalc = isMultiCalc;
	}

	public Boolean getIsAuxAccBankAccount() {
		return isAuxAccBankAccount;
	}

	public void setIsAuxAccBankAccount(Boolean isAuxAccBankAccount) {
		this.isAuxAccBankAccount = isAuxAccBankAccount;
	}

	/**
     * 获取是否辅助核算：即征即退
     * @return 是否辅助核算：即征即退
     */
    public Boolean getIsAuxAccLevyAndRetreat() {
        return isAuxAccLevyAndRetreat;
    }

    /**
     * 设置是否辅助核算：即征即退
     * @param isAuxAccLevyAndRetreat 是否辅助核算：即征即退
     */
    public void setIsAuxAccLevyAndRetreat(Boolean isAuxAccLevyAndRetreat) {
        this.isAuxAccLevyAndRetreat = isAuxAccLevyAndRetreat;
    }

	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getIndustry() {
		return industry;
	}

	public void setIndustry(Long industry) {
		this.industry = industry;
	}

	public Long getVatTaxpayer() {
		return vatTaxpayer;
	}

	public void setVatTaxpayer(Long vatTaxpayer) {
		this.vatTaxpayer = vatTaxpayer;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Boolean getIsSettlement() {
		return isSettlement;
	}

	public void setIsSettlement(Boolean isSettlement) {
		this.isSettlement = isSettlement;
	}

    /**
     * 获取摘要
     * @return 摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置摘要
     * @param summary 摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

	public Long getExtendAttr() {
		return extendAttr;
	}

	public void setExtendAttr(Long extendAttr) {
		this.extendAttr = extendAttr;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getQualification() {
		return qualification;
	}

	public void setQualification(Boolean qualification) {
		this.qualification = qualification;
	}

	public Boolean getTaxType() {
		return taxType;
	}

	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}


	
}
