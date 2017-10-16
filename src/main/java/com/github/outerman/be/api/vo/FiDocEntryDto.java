package com.github.outerman.be.api.vo;

import java.io.Serializable;

public class FiDocEntryDto implements Serializable {

//	public FiDocEntryDto(){
//		accountList = new ArrayList<>();
//	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4609809951218436168L;
	
	/** ID  */
	private Long id;

	/** 行状态 0未改变，1新增，2修改，3删除 */
	private Integer rowStatus;
	
	/** 摘要[set_Summary]  */
	private String summary;

	/** 科目ID[set_account]  */
	private Long accountId;

	/** 科目Code[set_account]  */
	private String accountCode;
	
	/** 科目Name[set_account]  */
	private String accountName;
	
	/** 科目GradeName[set_account]  */
	private String accountGradeName;
//
//	/** 科目Dto[set_account]  */
//	private FiAccountDto accountDto;
//
//	/** 科目Dto列表，标志科目可选范围  */
//	private List<FiAccountDto> accountList;

	/** 辅助项Code  */
	private String auxiliaryCode;

	/** 辅助项  */
	private String auxiliaryItems;

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

	/** 计量单位Code[set_unit]  */
	private String unitCode;

	/** 计量单位Name[set_unit]  */
	private String unitName;

	/** 币种ID[set_currency]  */
	private Long currencyId;
	
	/** 币种Code */
	private String currencyCode;

	/** 币种Name */
	private String currencyName;

	/** 汇率  */
	private Double exchangeRate;

	/** 部门id[set_department]  */
	private Long departmentId;

	/** 部门code[set_department]  */
	private String departmentCode;

	/** 部门Name[set_department]  */
	private String departmentName;

	/** 人员id[set_person]  */
	private Long personId;

	/** 人员code[set_person]  */
	private String personCode;

	/** 人员Name[set_person]  */
	private String personName;

	/** 客户ID  */
	private Long customerId;

	/** 客户Code  */
	private String customerCode;

	/** 客户Name  */
	private String customerName;

	/** 供应商ID  */
	private Long supplierId;

	/** 供应商Code  */
	private String supplierCode;

	/** 供应商Name  */
	private String supplierName;

	/** 存货id[set_inventory]  */
	private Long inventoryId;

	/** 存货Code[set_inventory]  */
	private String inventoryCode;

	/** 存货Name[set_inventory]  */
	private String inventoryName;

	/** 项目ID[set_project]  */
	private Long projectId;

	/** 项目Code[set_project]  */
	private String projectCode;

	/** 项目Name[set_project]  */
	private String projectName;

	/** 账号ID[set_bank_account]  */
	private Long bankAccountId;
	
	/** 账号Code[set_bank_account]  */
	private String bankAccountCode;
	
	/** 账号Name[set_bank_account]  */
	private String bankAccountName;

   /** 即征即退项目ID  */
    private Long levyAndRetreatId;

	/** 票据Code  */
	private String billCode;
	/** 票据类型  */
	private Long billTypeId;
	/** 来源单据业务类型  */
	private Long sourceBusinessTypeId;
	/** 税率  */
	private Double taxRate;
	/** 来源单据 模板中标识取值  */
	private String sourceFlag;
	
	/** 顺序号  */
	private Integer sequenceNumber;

	/** 是否辅助项数据  */
	private Boolean isAuxAccCalc;

	/** 对方科目ID集合  */
	private String otherSideAccountIds;

	/** 时间戳  */
	private String ts;

	/** 方向：0：借，1:贷  */
	private byte direction;
	
	/** 方向  */
	private String directionName;

	/** 待抵扣进项税ID  */
	private Long InPutTaxDeductId;

	/** ID  */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/** 摘要[set_Summary]  */
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/** 汇率  */
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/** 辅助项Code  */
	public String getAuxiliaryCode() {
		return auxiliaryCode;
	}
	public void setAuxiliaryCode(String auxiliaryCode) {
		this.auxiliaryCode = auxiliaryCode;
	}

	/** 辅助项  */
	public String getAuxiliaryItems() {
		return auxiliaryItems;
	}
	public void setAuxiliaryItems(String auxiliaryItems) {
		this.auxiliaryItems = auxiliaryItems;
	}

	/** 借方原币  */
	public Double getOrigAmountDr() {
		return origAmountDr;
	}
	public void setOrigAmountDr(Double origAmountDr) {
		this.origAmountDr = origAmountDr;
	}

	/** 贷方原币  */
	public Double getOrigAmountCr() {
		return origAmountCr;
	}
	public void setOrigAmountCr(Double origAmountCr) {
		this.origAmountCr = origAmountCr;
	}

	/** 借方本币  */
	public Double getAmountDr() {
		return amountDr;
	}
	public void setAmountDr(Double amountDr) {
		this.amountDr = amountDr;
	}

	/** 贷方本币  */
	public Double getAmountCr() {
		return amountCr;
	}
	public void setAmountCr(Double amountCr) {
		this.amountCr = amountCr;
	}

	/** 贷方  */
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	/** 单价  */
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	/** 计量单位id[set_unit]  */
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	/** 顺序号  */
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/** 币种ID[set_currency]  */
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	/** 科目ID[set_account]  */
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/** 部门id[set_department]  */
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/** 人员id[set_person]  */
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	/** 客户ID  */
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/** 供应商ID  */
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	/** 存货id[set_inventory]  */
	public Long getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	/** 项目ID[set_project]  */
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/** 账号ID[set_bank_account]  */
	public Long getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	/** 时间戳  */
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	/** 行状态  */
	public Integer getRowStatus() {
		return rowStatus;
	}
	public void setRowStatus(Integer rowStatus) {
		this.rowStatus = rowStatus;
	}
	

	/** 币种Name  */
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	/** 币种Code  */
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	/** 科目Code[set_account]  */
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	
	/** 科目Name[set_account]  */
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/** 部门Name[set_department]  */
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	/** 人员Name[set_person]  */
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	/** 客户Name  */
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/** 供应商Name  */
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/** 存货Name[set_inventory]  */
	public String getInventoryName() {
		return inventoryName;
	}
	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}

	public String getInventoryCode() {
		return inventoryCode;
	}
	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}
	/** 项目Name[set_project]  */
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/** 账号Name[set_bank_account]  */
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountCode() {
		return bankAccountCode;
	}
	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}
	
    public Long getLevyAndRetreatId() {
        return levyAndRetreatId;
    }

    public void setLevyAndRetreatId(Long levyAndRetreatId) {
        this.levyAndRetreatId = levyAndRetreatId;
    }
	
	/** 计量单位id[set_unit]  */
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	/** 方向Name  */
	public String getDirectionName() {
		return directionName;
	}
	public void setDirectionName(String directionName) {
		this.directionName = directionName;
	}
//	public FiAccountDto getAccountDto() {
//		return accountDto;
//	}
//	public void setAccountDto(FiAccountDto accountDto) {
//		this.accountDto = accountDto;
//	}
	public byte getDirection() {
		return direction;
	}
	public void setDirection(byte direction) {
		this.direction = direction;
	}
	public Boolean getIsAuxAccCalc() {
		return isAuxAccCalc;
	}
	public void setIsAuxAccCalc(Boolean isAuxAccCalc) {
		this.isAuxAccCalc = isAuxAccCalc;
	}
	public String getOtherSideAccountIds() {
		return otherSideAccountIds;
	}
	public void setOtherSideAccountIds(String otherSideAccountIds) {
		this.otherSideAccountIds = otherSideAccountIds;
	}
	public String getAccountGradeName() {
		return accountGradeName;
	}
	public void setAccountGradeName(String accountGradeName) {
		this.accountGradeName = accountGradeName;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public Long getBillTypeId() {
		return billTypeId;
	}
	public void setBillTypeId(Long billTypeId) {
		this.billTypeId = billTypeId;
	}
	public Long getSourceBusinessTypeId() {
		return sourceBusinessTypeId;
	}
	public void setSourceBusinessTypeId(Long sourceBusinessTypeId) {
		this.sourceBusinessTypeId = sourceBusinessTypeId;
	}
	public Double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	public String getSourceFlag() {
		return sourceFlag;
	}
	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}
//	public List<FiAccountDto> getAccountList() {
//		return accountList;
//	}
//	public void setAccountList(List<FiAccountDto> accountList) {
//		this.accountList = accountList;
//	}
	public Long getInPutTaxDeductId() {
		return InPutTaxDeductId;
	}
	public void setInPutTaxDeductId(Long inPutTaxDeductId) {
		InPutTaxDeductId = inPutTaxDeductId;
	}
}
