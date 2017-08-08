package com.github.outerman.be.api.vo;

import java.io.Serializable;

public class SetColumnsSpecialVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8581124561462803114L;
	
	/** id */
	private Long id ;
	
	/** '行业id',*/
	private Long industryId; 
	/** '业务类型id',*/
	private Long businessId; 
	/** 业务类型code */
	private Long businessCode;
	/** '字段ID',*/
	private Long columnsId; 
	/** '票据类型Id，结算方式Id等',*/
	private Long optionId; 
	/** '票据类型Id，结算方式Id等',*/
	private Integer vatTaxpayer; 
	/** '选项值',*/
	private String optionValue; 
	/** '是否默认:0默认，1非默认',*/
	private Integer isDefault;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIndustryId() {
		return industryId;
	}
	public void setIndustryId(Long industryId) {
		this.industryId = industryId;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	/**
     * 获取业务类型code
     * @return 业务类型code
     */
    public Long getBusinessCode() {
        return businessCode;
    }
    /**
     * 设置业务类型code
     * @param businessCode 业务类型code
     */
    public void setBusinessCode(Long businessCode) {
        this.businessCode = businessCode;
    }
    public Long getColumnsId() {
		return columnsId;
	}
	public void setColumnsId(Long columnsId) {
		this.columnsId = columnsId;
	}
	public Long getOptionId() {
		return optionId;
	}
	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getVatTaxpayer() {
		return vatTaxpayer;
	}
	public void setVatTaxpayer(Integer vatTaxpayer) {
		this.vatTaxpayer = vatTaxpayer;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	} 
	
	
}
