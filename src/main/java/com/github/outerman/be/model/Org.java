package com.github.outerman.be.model;

public class Org {

    /** ID  */
    private Long id;

    /** 本位币 id */
    private Long baseCurrencyId;

    /** 行业  */
    private Long industry;

    /** 企业会计准则  */
    private Long accountingStandards;

    /** 纳税人身份 */
    private Long vatTaxpayer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取本位币 id
     * @return 本位币 id
     */
    public Long getBaseCurrencyId() {
        return baseCurrencyId;
    }

    /**
     * 设置本位币 id
     * @param baseCurrencyId 本位币 id
     */
    public void setBaseCurrencyId(Long baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
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
