package com.github.outerman.be.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Account implements Serializable {

	private static final long serialVersionUID = 4609809840118436585L;

	/** 组织机构id */
    private Long orgId;

    /** 主键id */
    private Long id;

    /** 上级会计科目id */
    private Long parentId;

    /** 科目级次 */
    private int grade;

    /** 是否末级 */
    private Boolean isEndNode;

    /** 会计科目类别id：资产/负债/共同/权益/成本/损益 */
    private Long accountTypeId;

    /** 会计科目编码 */
    private String code;

    /** 会计科目名称 */
    private String name;
    
    /** 级次名称（含所有上级名） */
    private String gradeName;
    
    /** 会计科目名称简拼 */
    private String helpCode;

    /** 余额方向id：借/贷 */
    private byte balanceDirection;

    /** 现金（类型）id：现金、现金等价物、银行存款、{空} */
    private Long cashTypeId;
    
    /** 是否附科目 */
    private Boolean isIncidentalAcc;

    /** 是否启用辅助核算 */
    private Boolean isAuxAccCalc;

    /** 是否辅助核算：部门 */
    private Boolean isAuxAccDepartment;

    /** 是否辅助核算：个人 */
    private Boolean isAuxAccPerson;

    /** 是否辅助核算：客户 */
    private Boolean isAuxAccCustomer;

    /** 是否辅助核算：供应商 */
    private Boolean isAuxAccSupplier;

    /** 是否辅助核算：存货 */
    private Boolean isAuxAccInventory;

    /** 是否辅助核算：项目 */
    private Boolean isAuxAccProject;

    /** 是否辅助核算：银行账号 */
    private Boolean isAuxAccBankAccount;
    
    /** 是否辅助核算：即征即退 */
    private Boolean isAuxAccLevyAndRetreat;
    
    /** 是否辅助核算：进项转出 */
    private Boolean isAuxAccInputTax;

    /** 是否启用数量核算 */
    private Boolean isQuantityCalc;

    /** 计量单位id */
    private Long unitId;

    /** 是否启用多货币核算 */
    private Boolean isMultiCalc;
    
    /** 币种id */
    private Long currencyId;

    /** 是否启用 */
    private Boolean status;

    /** 是否系统预置 */
    private Boolean isSystem;

    /** 企业会计准则id：企业会计准则2007/小企业会计准则2013 */
    private Long accountingStandardsId;
    
    /** 行业id：工业/商贸/服务/其他 */
    private Long industryId;

    /** 时间戳 */
    private Timestamp ts;
    
    /** 账号ID */
    private Long bankId;
    
    /** 投资人ID */
    private Long investorId;

    /** 组织机构id */
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /** 主键id */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** 上级会计科目id */
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /** 科目级次 */
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    /** 是否末级 */
    public Boolean getIsEndNode() {
        return isEndNode;
    }

    public void setIsEndNode(Boolean isEndNode) {
        this.isEndNode = isEndNode;
    }

    /** 会计科目类别id：资产/负债/共同/权益/成本/损益 */
    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    /** 会计科目编码 */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /** 会计科目名称 */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 级次名称（含所有上级名） */
    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    /** 余额方向id：借/贷 */
    public byte getBalanceDirection() {
        return balanceDirection;
    }

    public void setBalanceDirection(byte balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    /** 现金（类型）id：现金、现金等价物、银行存款、{空} */
    public Long getCashTypeId() {
        return cashTypeId;
    }

    public void setCashTypeId(Long cashTypeId) {
        this.cashTypeId = cashTypeId;
    }

    /** 是否附科目 */
    public Boolean getIsIncidentalAcc() {
        return isIncidentalAcc;
    }

    public void setIsIncidentalAcc(Boolean isIncidentalAcc) {
        this.isIncidentalAcc = isIncidentalAcc;
    }

    /** 是否启用辅助核算 */
    public Boolean getIsAuxAccCalc() {
        return isAuxAccCalc;
    }

    public void setIsAuxAccCalc(Boolean isAuxAccCalc) {
        this.isAuxAccCalc = isAuxAccCalc;
    }

    /** 是否辅助核算：部门 */
    public Boolean getIsAuxAccDepartment() {
        return isAuxAccDepartment;
    }

    public void setIsAuxAccDepartment(Boolean isAuxAccDepartment) {
        this.isAuxAccDepartment = isAuxAccDepartment;
    }

    /** 是否辅助核算：个人 */
    public Boolean getIsAuxAccPerson() {
        return isAuxAccPerson;
    }

    public void setIsAuxAccPerson(Boolean isAuxAccPerson) {
        this.isAuxAccPerson = isAuxAccPerson;
    }

    /** 是否辅助核算：客户 */
    public Boolean getIsAuxAccCustomer() {
        return isAuxAccCustomer;
    }

    public void setIsAuxAccCustomer(Boolean isAuxAccCustomer) {
        this.isAuxAccCustomer = isAuxAccCustomer;
    }

    /** 是否辅助核算：供应商 */
    public Boolean getIsAuxAccSupplier() {
        return isAuxAccSupplier;
    }

    public void setIsAuxAccSupplier(Boolean isAuxAccSupplier) {
        this.isAuxAccSupplier = isAuxAccSupplier;
    }

    /** 是否辅助核算：存货 */
    public Boolean getIsAuxAccInventory() {
        return isAuxAccInventory;
    }

    public void setIsAuxAccInventory(Boolean isAuxAccInventory) {
        this.isAuxAccInventory = isAuxAccInventory;
    }

    /** 是否辅助核算：项目 */
    public Boolean getIsAuxAccProject() {
        return isAuxAccProject;
    }

    public void setIsAuxAccProject(Boolean isAuxAccProject) {
        this.isAuxAccProject = isAuxAccProject;
    }

    /** 是否辅助核算：银行账号 */
    public Boolean getIsAuxAccBankAccount() {
        return isAuxAccBankAccount;
    }

    public void setIsAuxAccBankAccount(Boolean isAuxAccBankAccount) {
        this.isAuxAccBankAccount = isAuxAccBankAccount;
    }

    /** 是否启用数量核算 */
    public Boolean getIsQuantityCalc() {
        return isQuantityCalc;
    }

    public void setIsQuantityCalc(Boolean isQuantityCalc) {
        this.isQuantityCalc = isQuantityCalc;
    }

    /** 计量单位id */
    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    /** 是否启用多货币核算 */
    public Boolean getIsMultiCalc() {
        return isMultiCalc;
    }

    public void setIsMultiCalc(Boolean isMultiCalc) {
        this.isMultiCalc = isMultiCalc;
    }
    
    /** 币种id */
    public Long getCurrencyId(){
        return currencyId;
    }
    
    public void setCurrencyId(Long currencyId){
        this.currencyId = currencyId;
    }

    /** 是否启用 */
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    /** 是否系统预置 */
    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    /** 企业会计准则id：企业会计准则2007/小企业会计准则2013 */
    public Long getAccountingStandardsId() {
        return accountingStandardsId;
    }

    public void setAccountingStandardsId(Long accountingStandardsId) {
        this.accountingStandardsId = accountingStandardsId;
    }

    /** 行业id：工业/商贸/服务/其他 */
    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    /** 时间戳 */
    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public String getHelpCode() {
        return helpCode;
    }

    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public Boolean getIsAuxAccLevyAndRetreat() {
        return isAuxAccLevyAndRetreat;
    }

    public void setIsAuxAccLevyAndRetreat(Boolean isAuxAccLevyAndRetreat) {
        this.isAuxAccLevyAndRetreat = isAuxAccLevyAndRetreat;
    }

    public Boolean getIsAuxAccInputTax() {
        return isAuxAccInputTax;
    }

    public void setIsAuxAccInputTax(Boolean isAuxAccInputTax) {
        this.isAuxAccInputTax = isAuxAccInputTax;
    }

}
