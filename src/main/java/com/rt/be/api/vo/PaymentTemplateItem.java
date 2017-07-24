package com.rt.be.api.vo;

import java.io.Serializable;

/** 凭证结算模板 */
public class PaymentTemplateItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4424620273857892825L;

	/** 主键id  */ 
	private Long id;

	/** 组织机构id  */ 
	private Long orgId;
	
	/** 收支类型  */ 
	private Long paymentsType;

	/** 结算方式[set_settlement_type]  */ 
	private Long settlement;
	
	/** 账户属性  */ 
	private Long accountType;

	/** 科目类型  */ 
	private String subjectType;

	/** 默认科目类型  */ 
	private String subjectDefault;

	/** 方向：0：借，1:贷  */ 
	private Boolean direction;

	/** 金额来源  */ 
	private String fundSource;
	
	/** 是否颠倒  */ 
	private Boolean reversal;
	
	
	// 
	
	
	/** 辅助核算  */ 
	private String auxiliaryAccounting;
	
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
	
	/** 企业会计准则id：企业会计准则2007/小企业会计准则2013 [set_enum_detail]  */ 
	private Long accountingStandardsId;


	public void setId(Long id){
		this.id=id;
	}

	/** 主键id  */ 
	public Long getId(){
		return id;
	}

	public void setOrgId(Long orgId){
		this.orgId=orgId;
	}

	/** 组织机构id  */ 
	public Long getOrgId(){
		return orgId;
	}

	public void setSettlement(Long settlement){
		this.settlement=settlement;
	}

	/** 结算方式[set_settlement_type]  */ 
	public Long getSettlement(){
		return settlement;
	}

	public void setSubjectType(String subjectType){
		this.subjectType=subjectType;
	}

	/** 科目类型  */ 
	public String getSubjectType(){
		return subjectType;
	}


	public void setDirection(Boolean direction){
		this.direction=direction;
	}

	/** 方向：0：借，1:贷  */ 
	public Boolean getDirection(){
		return direction;
	}

	public void setFundSource(String fundSource){
		this.fundSource=fundSource;
	}

	/** 金额来源  */ 
	public String getFundSource(){
		return fundSource;
	}

	public Boolean getReversal() {
		return reversal;
	}

	public void setReversal(Boolean reversal) {
		this.reversal = reversal;
	}

	public Long getPaymentsType() {
		return paymentsType;
	}

	public void setPaymentsType(Long paymentsType) {
		this.paymentsType = paymentsType;
	}

	public String getAuxiliaryAccounting() {
		return auxiliaryAccounting;
	}

	public void setAuxiliaryAccounting(String auxiliaryAccounting) {
		this.auxiliaryAccounting = auxiliaryAccounting;
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

	public Long getAccountingStandardsId() {
		return accountingStandardsId;
	}

	public void setAccountingStandardsId(Long accountingStandardsId) {
		this.accountingStandardsId = accountingStandardsId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getSubjectDefault() {
		return subjectDefault;
	}

	public void setSubjectDefault(String subjectDefault) {
		this.subjectDefault = subjectDefault;
	}

	public Long getAccountType() {
		return accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

}
