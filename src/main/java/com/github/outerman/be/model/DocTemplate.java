package com.github.outerman.be.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 业务凭证模板 
 */
public class DocTemplate implements Serializable {

    private static final long serialVersionUID = -3818718679387513417L;

    private Long id;// id

    private Long industry;// 行业编码

    private Long businessCode;// 业务编码

    private String flag;// 标识取值

    private String influence;// 影响因素

    /** 影响因素取值 map */
    private Map<String, String> influenceMap;

    private Long vatTaxpayer;// 纳税人性质

    private Long departmentAttr;// 部门属性

    private Integer sort;// 排序字段

    private Boolean direction;// 借贷方向(0借1贷)

    private String fundSource;// 金额来源

    private String accountCode;// 科目编码

    private Integer accountingStandardsId;// 会计准则

    /** 摘要 */
    private String summary;

    private Account account;

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

    public Long getDepartmentAttr() {
        return departmentAttr;
    }

    public void setDepartmentAttr(Long departmentAttr) {
        this.departmentAttr = departmentAttr;
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

    public Integer getAccountingStandardsId() {
        return accountingStandardsId;
    }

    public void setAccountingStandardsId(Integer accountingStandardsId) {
        this.accountingStandardsId = accountingStandardsId;
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

    /**
     * 获取摘要
     * 
     * @return 摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置摘要
     * 
     * @param summary
     *            摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
