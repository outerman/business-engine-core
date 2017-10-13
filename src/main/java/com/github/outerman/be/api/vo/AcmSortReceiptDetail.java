package com.github.outerman.be.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/** 理票单明细表 */
public class AcmSortReceiptDetail implements Serializable {

    private static final long serialVersionUID = -3366840538566698521L;

    /** ID  */
    private Long id;

    /** 理票单id[acm_sort_receipt]  */
    private Long sortReceiptId;

    /** 客户组织机构id[set_org]  */
    private Long orgId;

    /** 业务类型(1:招待费2:差旅费3:交通费..)这个将来放到枚举表中  */
    private Long businessType;
    private String businessTypeName;
    private Long businessCode;

    /** 发票类型(1:普票2:专票 3:其它)  */
    private Long invoiceType;
    private String invoiceTypeName;

    /** 是否认证(0:否1：是)  */
    private Byte isQualification;

    /** 是否抵扣(0:否1：是)  */
    private Byte isDeduction;
    private String isDeductionName;

    /** 部门  */
    private Long department;
    private String departmentName;
    private Long departmentProperty;// 部门属性, 数据库里实际并没有存储这个字段

    /** 员工  */
    private Long employee;
    private String employeeName;
    private String employeeMobile; // 员工手机, 仅用于外部系统传入, 唯一确定人员
    private Long employeeAttribute;// 人员属性, 数据库里实际并没有存储这个字段

    /** 供应商  */
    private Long vendor;
    private String vendorName;

    /** 客户  */
    private Long consumer;
    private String consumerName;

    /* 关联的计量单位id */
    private Long unitId;
    /* 关联的计量单位name */
    private String unitName;

    /** 存货  */
    private Long inventory;
    private String inventoryName;
    private Long inventoryPropertyId;// 存货属性 id, 数据库里实际并没有存储这个字段

    /** 存货属性对应凭证模板使用的 id，存货属性数据区分纳税人性质，凭证模板不区分，不能直接使用存货属性 id */
    private Long inventoryPropertyTemplateId;

    /** 项目  */
    private Long project;
    /** 项目名称  */
    private String projectName;

    /** 摘要  */
    private String memo;

    /** 金额(无税)  */
    private Double amount;

    /** 税额  */
    private Double tax;

    /** 金额（含税）  */
    private Double taxInclusiveAmount;

    /** 折扣  */
    private Double discount;

    /** 金额(实收)  */
    private Double receiveAmount;

    /** 银行账号ID  */
    private Long bankAccountId;
    /** 银行账号名称  */
    private String bankAccountName;
    /** 银行账号类型 id */
    private Long bankAccountTypeId; // 数据库里实际并没有存储这个字段
    /** 银行名称 */
    private String bankName;
    /** 银行账户状态 */
    private Boolean bankAccountStatus;
    /** 银行账户默认状态 */
    private Boolean bankAccountDefault;

    /** 流入银行账号ID  */
    private Long inBankAccountId;
    /** 流入银行账号名称  */
    private String inBankAccountName;
    /** 流入银行账号类型 id */
    private Long inBankAccountTypeId;// 数据库里实际并没有存储这个字段
    /** 流入银行名称 */
    private String inBankName;
    /** 流入银行账户状态*/
    private Boolean inBankAccountStatus;
    /** 流入银行账户默认状态 */
    private Boolean inBankAccountDefault;

    /** 票据号  */
    private String notesNum;

    /** 结算方式  */
    private Long settleStyle;
    private String settleStyleName;

    /** 发票代码  */
    private String invoiceCode;

    /** 发票号码  */
    private String invoiceNo;

    /** 商品单价  */
    private Double price;

    /** 商品数量 */
    private Double commodifyNum;

    /** 税率 id 对应表 set_tax_rate */
    private Long taxRateId;
    /** 税率  */
    private Double taxRate;
    private String taxRateName; // 税率名称, 仅用于外部系统传入, 唯一确定税率

    /** 资产 id，对应表 set_inventory */
    private Long assetId;
    /** 资产类别(Id)  */
    private Long assetAttr;// 数据库里实际并没有存储这个字段
    /** 资产类别(明细)  */
    private Long assetType;// 数据库里有这个字段,但实际并没有值
    /** 资产名称  */
    private String assetName;

    /**资产类别(明细)名称*/
    private String assetTypeName;

    /** 罚款性质 id */
    private Long penaltyType; // TODO: 暂未根据name,转换id

    /** 借款期限 id */
    private Long loanTerm; // TODO: 暂未根据name,转换id

    /** 时间戳  */
    private Timestamp ts;

    /** 商品含税单价  */
    private Double taxInclusivePrice;

    /** 减免税款  */
    private Double privilegeTaxAmount;

    /** 债权人  */
    private Long creditor;
    /** 债权人名称  */
    private String creditorName;

    /** 债务人  */
    private Long debtor;
    /** 债务人名称  */
    private String debtorName;

    /** 投资人 id 对应表 set_investor */
    private Long investorId;

    /** 投资人姓名 */
    private String investorName;

    /** 投资人启用状态 1启用 0不启用 */
    private Boolean investorStatus;

    /** 被投资人 id 对应表 set_investor */
    private Long byInvestorId;

    /** 被投资人姓名 */
    private String byInvestorName;

    /** 被投资人启用状态 1启用 0不启用 */
    private Boolean byInvestorStatus;

    /**Double类型扩展字段*/
    private Double ext0;
    private Double ext1;
    private Double ext2;
    private Double ext3;
    private Double ext4;
    private Double ext5;
    private Double ext6;
    private Double ext7;
    private Double ext8;
    private Double ext9;

    /**字符串类型扩展字段*/
    private String extString0;
    private String extString1;
    private String extString2;
    private String extString3;
    private String extString4;

    private Date inAccountDate;

    /**可抵扣进项税额*/
    private Double deductibleInputTax;

    /**开票日期*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date billingDate;

    /**认证月份*/
    @JsonFormat(pattern = "yyyy-MM")
    private Date certificationMonth;

    /**是否认证后模板导入*/
    private Byte isAuthenTemplate;

    private Long status;

    /**发票认证生成凭证id*/
    private Long cerDocId;

    /**发票认证生成凭证code*/
    private String cerDocCode;

    /**凭证号备份，用于取消认证之后再认证生成凭证使用*/
    private String cerdocCodeBak;

    /**凭证月份*/
    private int docMonth;

    /** 流水账时间戳  */
    private String receiptTs;

    /**流水账code*/
    private String receiptCode;

    /**抵扣字段是否显示  0：不显示；1、显示但非必录；2、显示且必录*/
    private Integer flag;

    /**即征即退*/
    private Long drawbackPolicy;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getDocMonth() {
        return docMonth;
    }

    public void setDocMonth(int docMonth) {
        this.docMonth = docMonth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSortReceiptId() {
        return sortReceiptId;
    }

    public void setSortReceiptId(Long sortReceiptId) {
        this.sortReceiptId = sortReceiptId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Byte getIsQualification() {
        return isQualification;
    }

    public void setIsQualification(Byte isQualification) {
        this.isQualification = isQualification;
    }

    public Byte getIsDeduction() {
        return isDeduction;
    }

    public void setIsDeduction(Byte isDeduction) {
        this.isDeduction = isDeduction;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    public Long getEmployee() {
        return employee;
    }

    public void setEmployee(Long employee) {
        this.employee = employee;
    }

    public Long getVendor() {
        return vendor;
    }

    public void setVendor(Long vendor) {
        this.vendor = vendor;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    /**
     * 获取存货属性 id
     * @return 存货属性 id
     */
    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    /**
     * 设置存货属性 id
     * @param inventoryPropertyId 存货属性 id
     */
    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    /**
     * 获取存货属性对应凭证模板使用的 id
     * <p>存货属性数据区分纳税人性质，凭证模板不区分，不能直接使用存货属性 id
     * @return 存货属性对应凭证模板使用的 id
     */
    public Long getInventoryPropertyTemplateId() {
        return inventoryPropertyTemplateId;
    }

    /**
     * 设置存货属性对应凭证模板使用的 id
     * <p>存货属性数据区分纳税人性质，凭证模板不区分，不能直接使用存货属性 id
     * @param inventoryPropertyTemplateId 存货属性对应凭证模板使用的 id
     */
    public void setInventoryPropertyTemplateId(Long inventoryPropertyTemplateId) {
        this.inventoryPropertyTemplateId = inventoryPropertyTemplateId;
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTaxInclusiveAmount() {
        return taxInclusiveAmount;
    }

    public void setTaxInclusiveAmount(Double taxInclusiveAmount) {
        this.taxInclusiveAmount = taxInclusiveAmount;
    }

    public Long getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Long businessType) {
        this.businessType = businessType;
    }

    public Long getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(Long businessCode) {
        this.businessCode = businessCode;
    }

    public Long getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Long invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getInvoiceTypeName() {
        return invoiceTypeName;
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName;
    }

    public String getIsDeductionName() {
        return isDeductionName;
    }

    public void setIsDeductionName(String isDeductionName) {
        this.isDeductionName = isDeductionName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public Long getConsumer() {
        return consumer;
    }

    public void setConsumer(Long consumer) {
        this.consumer = consumer;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Long getSettleStyle() {
        return settleStyle;
    }

    public void setSettleStyle(Long settleStyle) {
        this.settleStyle = settleStyle;
    }

    public String getSettleStyleName() {
        return settleStyleName;
    }

    public void setSettleStyleName(String settleStyleName) {
        this.settleStyleName = settleStyleName;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getNotesNum() {
        return notesNum;
    }

    public void setNotesNum(String notesNum) {
        this.notesNum = notesNum;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getEmployeeMobile() {
        return employeeMobile;
    }

    public void setEmployeeMobile(String employeeMobile) {
        this.employeeMobile = employeeMobile;
    }

    public String getTaxRateName() {
        return taxRateName;
    }

    public void setTaxRateName(String taxRateName) {
        this.taxRateName = taxRateName;
    }

    /**
     * 获取银行账号类型 id
     * @return 银行账号类型 id
     */
    public Long getBankAccountTypeId() {
        return bankAccountTypeId;
    }

    /**
     * 设置银行账号类型 id
     * @param bankAccountTypeId 银行账号类型 id
     */
    public void setBankAccountTypeId(Long bankAccountTypeId) {
        this.bankAccountTypeId = bankAccountTypeId;
    }

    /**
     * 获取流入银行账号ID
     * @return 流入银行账号ID
     */
    public Long getInBankAccountId() {
        return inBankAccountId;
    }

    /**
     * 设置流入银行账号ID
     * @param inBankAccountId 流入银行账号ID
     */
    public void setInBankAccountId(Long inBankAccountId) {
        this.inBankAccountId = inBankAccountId;
    }

    /**
     * 获取流入银行账号名称
     * @return 流入银行账号名称
     */
    public String getInBankAccountName() {
        return inBankAccountName;
    }

    /**
     * 设置流入银行账号名称
     * @param inBankAccountName 流入银行账号名称
     */
    public void setInBankAccountName(String inBankAccountName) {
        this.inBankAccountName = inBankAccountName;
    }

    /**
     * 获取流入银行类型 id
     * @return 流入银行类型 id
     */
    public Long getInBankAccountTypeId() {
        return inBankAccountTypeId;
    }

    /**
     * 设置流入银行类型 id
     * @param inBankAccountTypeId 流入银行类型 id
     */
    public void setInBankAccountTypeId(Long inBankAccountTypeId) {
        this.inBankAccountTypeId = inBankAccountTypeId;
    }

    public Date getInAccountDate() {
        return inAccountDate;
    }

    public void setInAccountDate(Date inAccountDate) {
        this.inAccountDate = inAccountDate;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCommodifyNum() {
        return commodifyNum;
    }

    public void setCommodifyNum(Double commodifyNum) {
        this.commodifyNum = commodifyNum;
    }

    /**
     * 获取税率 id 对应表 set_tax_rate
     * @return 税率 id 对应表 set_tax_rate
     */
    public Long getTaxRateId() {
        return taxRateId;
    }

    /**
     * 设置税率 id 对应表 set_tax_rate
     * @param taxRateId 税率 id 对应表 set_tax_rate
     */
    public void setTaxRateId(Long taxRateId) {
        this.taxRateId = taxRateId;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * 获取资产 id
     * @return 资产 id
     */
    public Long getAssetId() {
        return assetId;
    }

    /**
     * 设置 资产 id
     * @param assetId 资产 id
     */
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getAssetType() {
        return assetType;
    }

    public void setAssetType(Long assetType) {
        this.assetType = assetType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    /**
     * 获取罚款性质 id
     * @return 罚款性质 id
     */
    public Long getPenaltyType() {
        return penaltyType;
    }

    /**
     * 设置罚款性质 id
     * @param penaltyType 罚款性质 id
     */
    public void setPenaltyType(Long penaltyType) {
        this.penaltyType = penaltyType;
    }

    /**
     * 获取借款期限 id
     * @return
     */
    public Long getLoanTerm() {
        return loanTerm;
    }

    /**
     * 设置借款期限 id
     * @param loanTerm
     */
    public void setLoanTerm(Long loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Long getDepartmentProperty() {
        return departmentProperty;
    }

    public void setDepartmentProperty(Long departmentProperty) {
        this.departmentProperty = departmentProperty;
    }

    public Long getEmployeeAttribute() {
        return employeeAttribute;
    }

    public void setEmployeeAttribute(Long employeeAttribute) {
        this.employeeAttribute = employeeAttribute;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getTaxInclusivePrice() {
        return taxInclusivePrice;
    }

    public void setTaxInclusivePrice(Double taxInclusivePrice) {
        this.taxInclusivePrice = taxInclusivePrice;
    }

    public Double getPrivilegeTaxAmount() {
        return privilegeTaxAmount;
    }

    public void setPrivilegeTaxAmount(Double privilegeTaxAmount) {
        this.privilegeTaxAmount = privilegeTaxAmount;
    }

    public Long getAssetAttr() {
        return assetAttr;
    }

    public void setAssetAttr(Long assetAttr) {
        this.assetAttr = assetAttr;
    }

    public Long getCreditor() {
        return creditor;
    }

    public void setCreditor(Long creditor) {
        this.creditor = creditor;
    }

    public Long getDebtor() {
        return debtor;
    }

    public void setDebtor(Long debtor) {
        this.debtor = debtor;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    /**
     * 获取投资人 id 对应表 set_investor
     * @return 投资人 id 对应表 set_investor
     */
    public Long getInvestorId() {
        return investorId;
    }

    /**
     * 设置投资人 id 对应表 set_investor
     * @param investorId 投资人 id 对应表 set_investor
     */
    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    /**
     * 获取投资人姓名
     * @return 投资人姓名
     */
    public String getInvestorName() {
        return investorName;
    }

    /**
     * 设置投资人姓名
     * @param investorName 投资人姓名
     */
    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    /**
     * 获取投资人启用状态 1启用 0不启用
     * @return 投资人启用状态 1启用 0不启用
     */
    public Boolean getInvestorStatus() {
        return investorStatus;
    }

    /**
     * 设置投资人启用状态 1启用 0不启用
     * @param investorStatus 投资人启用状态 1启用 0不启用
     */
    public void setInvestorStatus(Boolean investorStatus) {
        this.investorStatus = investorStatus;
    }

    public Long getByInvestorId() {
        return byInvestorId;
    }

    public void setByInvestorId(Long byInvestorId) {
        this.byInvestorId = byInvestorId;
    }

    public String getByInvestorName() {
        return byInvestorName;
    }

    public void setByInvestorName(String byInvestorName) {
        this.byInvestorName = byInvestorName;
    }

    /**
     * 获取被投资人启用状态 1启用 0不启用
     * @return 被投资人启用状态 1启用 0不启用
     */
    public Boolean getByInvestorStatus() {
        return byInvestorStatus;
    }

    /**
     * 设置被投资人启用状态 1启用 0不启用
     * @param byInvestorStatus 被投资人启用状态 1启用 0不启用
     */
    public void setByInvestorStatus(Boolean byInvestorStatus) {
        this.byInvestorStatus = byInvestorStatus;
    }

    public String getExtString0() {
        return extString0;
    }

    public void setExtString0(String extString0) {
        this.extString0 = extString0;
    }

    public String getExtString1() {
        return extString1;
    }

    public void setExtString1(String extString1) {
        this.extString1 = extString1;
    }

    public String getExtString2() {
        return extString2;
    }

    public void setExtString2(String extString2) {
        this.extString2 = extString2;
    }

    public String getExtString3() {
        return extString3;
    }

    public void setExtString3(String extString3) {
        this.extString3 = extString3;
    }

    public String getExtString4() {
        return extString4;
    }

    public void setExtString4(String extString4) {
        this.extString4 = extString4;
    }

    public Double getExt0() {
        return ext0;
    }

    public void setExt0(Double ext0) {
        this.ext0 = ext0;
    }

    public Double getExt1() {
        return ext1;
    }

    public void setExt1(Double ext1) {
        this.ext1 = ext1;
    }

    public Double getExt2() {
        return ext2;
    }

    public void setExt2(Double ext2) {
        this.ext2 = ext2;
    }

    public Double getExt3() {
        return ext3;
    }

    public void setExt3(Double ext3) {
        this.ext3 = ext3;
    }

    public Double getExt4() {
        return ext4;
    }

    public void setExt4(Double ext4) {
        this.ext4 = ext4;
    }

    public Double getExt5() {
        return ext5;
    }

    public void setExt5(Double ext5) {
        this.ext5 = ext5;
    }

    public Double getExt6() {
        return ext6;
    }

    public void setExt6(Double ext6) {
        this.ext6 = ext6;
    }

    public Double getExt7() {
        return ext7;
    }

    public void setExt7(Double ext7) {
        this.ext7 = ext7;
    }

    public Double getExt8() {
        return ext8;
    }

    public void setExt8(Double ext8) {
        this.ext8 = ext8;
    }

    public Double getExt9() {
        return ext9;
    }

    public void setExt9(Double ext9) {
        this.ext9 = ext9;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getInBankName() {
        return inBankName;
    }

    public void setInBankName(String inBankName) {
        this.inBankName = inBankName;
    }

    public Boolean getBankAccountStatus() {
        return bankAccountStatus;
    }

    public void setBankAccountStatus(Boolean bankAccountStatus) {
        this.bankAccountStatus = bankAccountStatus;
    }

    public Boolean getInBankAccountStatus() {
        return inBankAccountStatus;
    }

    public void setInBankAccountStatus(Boolean inBankAccountStatus) {
        this.inBankAccountStatus = inBankAccountStatus;
    }

    public Boolean getBankAccountDefault() {
        return bankAccountDefault;
    }

    public void setBankAccountDefault(Boolean bankAccountDefault) {
        this.bankAccountDefault = bankAccountDefault;
    }

    public Boolean getInBankAccountDefault() {
        return inBankAccountDefault;
    }

    public void setInBankAccountDefault(Boolean inBankAccountDefault) {
        this.inBankAccountDefault = inBankAccountDefault;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public Byte getIsAuthenTemplate() {
        return isAuthenTemplate;
    }

    public void setIsAuthenTemplate(Byte isAuthenTemplate) {
        this.isAuthenTemplate = isAuthenTemplate;
    }

    public Double getDeductibleInputTax() {
        return deductibleInputTax;
    }

    public void setDeductibleInputTax(Double deductibleInputTax) {
        this.deductibleInputTax = deductibleInputTax;
    }

    public Date getCertificationMonth() {
        return certificationMonth;
    }

    public void setCertificationMonth(Date certificationMonth) {
        this.certificationMonth = certificationMonth;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getCerDocId() {
        return cerDocId;
    }

    public void setCerDocId(Long cerDocId) {
        this.cerDocId = cerDocId;
    }

    public String getCerDocCode() {
        return cerDocCode;
    }

    public void setCerDocCode(String cerDocCode) {
        this.cerDocCode = cerDocCode;
    }

    public String getCerdocCodeBak() {
        return cerdocCodeBak;
    }

    public void setCerdocCodeBak(String cerdocCodeBak) {
        this.cerdocCodeBak = cerdocCodeBak;
    }

    public String getReceiptTs() {
        return receiptTs;
    }

    public void setReceiptTs(String receiptTs) {
        this.receiptTs = receiptTs;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public Long getDrawbackPolicy() {
        return drawbackPolicy;
    }

    public void setDrawbackPolicy(Long drawbackPolicy) {
        this.drawbackPolicy = drawbackPolicy;
    }

}
