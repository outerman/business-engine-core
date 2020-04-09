package com.github.outerman.be.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 业务凭证模板 
 */
public class DocTemplate implements Serializable {

    private static final long serialVersionUID = -3818718679387513417L;

    /** 业务编码 */
    private String businessCode;

    /** 凭证分录标识 */
    private String flag;

    /** 影响因素 */
    private String influence;

    /** 影响因素取值 */
    private String influenceValue;

    /** 影响因素取值 map */
    private Map<String, String> influenceMap;

    /** 摘要 */
    private String summary;

    /** 余额方向：0借 1贷 */
    private Integer balanceDirection;

    /** 科目 id */
    private Long accountId;

    /** 科目编码 */
    private String accountCode;

    /** 基础档案用科目分类 */
    private Long accountClassification4BA;

    /** 金额来源表达式 */
    private String amountSource;

    /** 行业，枚举 industry */
    private Long industryId;

    /** 会计准则，枚举 accountingStandards */
    private Long accountingStandardsId;

    /** 纳税人身份，枚举 vatTaxpayer */
    private Long vatTaxpayerId;

    /** 科目信息 */
    private Account account;

    /** 以下字段用于同一凭证模板取到多条科目信息，例如资产分摊时，最后一条分录倒减金额使用 */
    /** 合计金额 */
    private Double sum;

    /** 是否分摊的最后一条分录 */
    private boolean isLast;

    /**
     * 获取业务编码
     * @return 业务编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置业务编码
     * @param businessCode 业务编码
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * 获取凭证分录标识
     * @return 凭证分录标识
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置凭证分录标识
     * @param flag 凭证分录标识
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * 获取影响因素
     * @return 影响因素
     */
    public String getInfluence() {
        return influence;
    }

    /**
     * 设置影响因素
     * @param influence 影响因素
     */
    public void setInfluence(String influence) {
        this.influence = influence;
    }

    /**
     * 获取影响因素取值
     * @return 影响因素取值
     */
    public String getInfluenceValue() {
        return influenceValue;
    }

    /**
     * 设置影响因素取值
     * @param influenceValue 影响因素取值
     */
    public void setInfluenceValue(String influenceValue) {
        this.influenceValue = influenceValue;
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

    /**
     * 获取余额方向：0借 1贷
     * @return 余额方向：0借 1贷
     */
    public Integer getBalanceDirection() {
        return balanceDirection;
    }

    /**
     * 设置余额方向：0借 1贷
     * @param balanceDirection 余额方向：0借 1贷
     */
    public void setBalanceDirection(Integer balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    /**
     * 获取科目 id
     * @return 科目 id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置科目 id
     * @param accountId 科目 id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取科目编码
     * @return 科目编码
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * 设置科目编码
     * @param accountCode 科目编码
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * 获取基础档案用科目分类
     * @return 基础档案用科目分类
     */
    public Long getAccountClassification4BA() {
        return accountClassification4BA;
    }

    /**
     * 设置基础档案用科目分类
     * @param accountClassification4BA 基础档案用科目分类
     */
    public void setAccountClassification4BA(Long accountClassification4BA) {
        this.accountClassification4BA = accountClassification4BA;
    }

    /**
     * 获取金额来源表达式
     * @return 金额来源表达式
     */
    public String getAmountSource() {
        return amountSource;
    }

    /**
     * 设置金额来源表达式
     * @param amountSource 金额来源表达式
     */
    public void setAmountSource(String amountSource) {
        this.amountSource = amountSource;
    }

    /**
     * 获取行业，枚举 industry
     * @return 行业，枚举 industry
     */
    public Long getIndustryId() {
        return industryId;
    }

    /**
     * 设置行业，枚举 industry
     * @param industryId 行业，枚举 industry
     */
    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    /**
     * 获取会计准则，枚举 accountingStandards
     * @return 会计准则，枚举 accountingStandards
     */
    public Long getAccountingStandardsId() {
        return accountingStandardsId;
    }

    /**
     * 设置会计准则，枚举 accountingStandards
     * @param accountingStandardsId 会计准则，枚举 accountingStandards
     */
    public void setAccountingStandardsId(Long accountingStandardsId) {
        this.accountingStandardsId = accountingStandardsId;
    }

    /**
     * 获取纳税人身份，枚举 vatTaxpayer
     * @return 纳税人身份，枚举 vatTaxpayer
     */
    public Long getVatTaxpayerId() {
        return vatTaxpayerId;
    }

    /**
     * 设置纳税人身份，枚举 vatTaxpayer
     * @param vatTaxpayerId 纳税人身份，枚举 vatTaxpayer
     */
    public void setVatTaxpayerId(Long vatTaxpayerId) {
        this.vatTaxpayerId = vatTaxpayerId;
    }

    /**
     * 获取科目信息 
     * @return 科目信息 
     */
    public Account getAccount() {
        return account;
    }

    /**
     * 设置科目信息 
     * @param account 科目信息 
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * 获取合计金额
     * @return 合计金额
     */
    public Double getSum() {
        return sum;
    }

    /**
     * 设置合计金额
     * @param sum 合计金额
     */
    public void setSum(Double sum) {
        this.sum = sum;
    }

    /**
     * 获取是否分摊的最后一条
     * @return 是否分摊的最后一条
     */
    public boolean isLast() {
        return isLast;
    }

    /**
     * 设置是否分摊的最后一条
     * @param isLast 是否分摊的最后一条
     */
    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }

}
