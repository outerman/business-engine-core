package com.github.outerman.be.api.vo;

import java.io.Serializable;

public class SetCurrency implements Serializable {

    private static final long serialVersionUID = -6959099919540575560L;
    /**  ID  */
    private Long id;

    /**  组织结构id  */
    private Long orgId;

    /**  编码  */
    private String code;

    /**  名称  */
    private String name;

    /**  汇率  */
    private Double exchangeRate;

    /**  是否本位币  */
    private Boolean isBaseCurrency;

    /**  是否启用 1启用 0停用  */
    private Boolean status;

    /**  ID  */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**  组织结构id  */
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**  编码  */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**  名称  */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**  汇率  */
    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**  是否本位币  */
    public Boolean getIsBaseCurrency() {
        return isBaseCurrency;
    }

    public void setIsBaseCurrency(Boolean isBaseCurrency) {
        this.isBaseCurrency = isBaseCurrency;
    }

    /**  是否启用 1启用 0停用  */
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
