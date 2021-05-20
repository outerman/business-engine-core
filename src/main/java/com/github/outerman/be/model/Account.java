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

    /** 是否启用  */
    private Boolean isEnable;

    /** 占比，生成凭证分摊时使用 */
    private Double developRatio;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Boolean getIsEndNode() {
        return isEndNode;
    }

    public void setIsEndNode(Boolean isEndNode) {
        this.isEndNode = isEndNode;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public byte getBalanceDirection() {
        return balanceDirection;
    }

    public void setBalanceDirection(byte balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    public Long getCashTypeId() {
        return cashTypeId;
    }

    public void setCashTypeId(Long cashTypeId) {
        this.cashTypeId = cashTypeId;
    }

    public Boolean getIsIncidentalAcc() {
        return isIncidentalAcc;
    }

    public void setIsIncidentalAcc(Boolean isIncidentalAcc) {
        this.isIncidentalAcc = isIncidentalAcc;
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

    public Boolean getIsAuxAccBankAccount() {
        return isAuxAccBankAccount;
    }

    public void setIsAuxAccBankAccount(Boolean isAuxAccBankAccount) {
        this.isAuxAccBankAccount = isAuxAccBankAccount;
    }

    public Boolean getIsQuantityCalc() {
        return isQuantityCalc;
    }

    public void setIsQuantityCalc(Boolean isQuantityCalc) {
        this.isQuantityCalc = isQuantityCalc;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Boolean getIsMultiCalc() {
        return isMultiCalc;
    }

    public void setIsMultiCalc(Boolean isMultiCalc) {
        this.isMultiCalc = isMultiCalc;
    }
    
    public Long getCurrencyId(){
        return currencyId;
    }
    
    public void setCurrencyId(Long currencyId){
        this.currencyId = currencyId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Long getAccountingStandardsId() {
        return accountingStandardsId;
    }

    public void setAccountingStandardsId(Long accountingStandardsId) {
        this.accountingStandardsId = accountingStandardsId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

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

    /**
     * 获取是否启用
     * @return 是否启用
     */
    public Boolean getIsEnable() {
        return isEnable;
    }

    /**
     * 设置是否启用
     * @param isEnable 是否启用
     */
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * 获取占比，生成凭证分摊时使用
     * @return 占比，生成凭证分摊时使用
     */
    public Double getDevelopRatio() {
        return developRatio;
    }

    /**
     * 设置占比，生成凭证分摊时使用
     * @param developRatio 占比，生成凭证分摊时使用
     */
    public void setDevelopRatio(Double developRatio) {
        this.developRatio = developRatio;
    }

}
