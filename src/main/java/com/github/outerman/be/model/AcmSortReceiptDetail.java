package com.github.outerman.be.model;

import java.io.Serializable;
import java.util.Map;

public class AcmSortReceiptDetail implements Serializable {

    private static final long serialVersionUID = -3366840538566698521L;

    private Long businessType;

    private String businessCode;

    /** 业务属性：收入或者支出 */
    private Long businessPropertyId;

    /** 是否抵扣(0:否1：是)  */
    private Byte isDeduction;

    /** 部门  */
    private Long department;

    /** 员工  */
    private Long employee;

    /** 供应商  */
    private Long vendor;

    /** 客户  */
    private Long consumer;

    /* 关联的计量单位id */
    private Long unitId;

    /** 存货  */
    private Long inventory;

    /** 项目  */
    private Long project;

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

    /** 商品单价  */
    private Double price;

    /** 商品数量 */
    private Double commodifyNum;

    /** 税率 id 对应表 set_tax_rate */
    private Long taxRateId;
    /** 税率  */
    private Double taxRate;

    /** 资产 id，对应表 set_inventory */
    private Long assetId;

    /** 商品含税单价  */
    private Double taxInclusivePrice;

    /** 减免税款  */
    private Double privilegeTaxAmount;

    /** 债权人  */
    private Long creditor;

    /** 债务人  */
    private Long debtor;

    /** 投资人 id 对应表 set_investor */
    private Long investorId;

    /** 被投资人 id 对应表 set_investor */
    private Long byInvestorId;

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

    /**可抵扣进项税额*/
    private Double deductibleInputTax;

    /**即征即退*/
    private Long drawbackPolicy;

    /** 影响因素取值 map */
    private Map<String, String> influenceMap;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * 获取业务属性：收入或者支出
     * @return 业务属性：收入或者支出
     */
    public Long getBusinessPropertyId() {
        return businessPropertyId;
    }

    /**
     * 设置业务属性：收入或者支出
     * @param businessPropertyId 业务属性：收入或者支出
     */
    public void setBusinessPropertyId(Long businessPropertyId) {
        this.businessPropertyId = businessPropertyId;
    }

    public Long getConsumer() {
        return consumer;
    }

    public void setConsumer(Long consumer) {
        this.consumer = consumer;
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

    public Long getByInvestorId() {
        return byInvestorId;
    }

    public void setByInvestorId(Long byInvestorId) {
        this.byInvestorId = byInvestorId;
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

    public Double getDeductibleInputTax() {
        return deductibleInputTax;
    }

    public void setDeductibleInputTax(Double deductibleInputTax) {
        this.deductibleInputTax = deductibleInputTax;
    }

    public Long getDrawbackPolicy() {
        return drawbackPolicy;
    }

    public void setDrawbackPolicy(Long drawbackPolicy) {
        this.drawbackPolicy = drawbackPolicy;
    }

    /**
     * 获取影响因素取值 map
     * @return 影响因素取值 map
     */
    public Map<String, String> getInfluenceMap() {
        return influenceMap;
    }

    /**
     * 设置影响因素取值 map
     * @param influenceMap 影响因素取值 map
     */
    public void setInfluenceMap(Map<String, String> influenceMap) {
        this.influenceMap = influenceMap;
    }

}
