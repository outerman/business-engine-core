package com.github.outerman.be.api.vo;

import java.io.Serializable;

public class SetColumnsTacticsDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5884850377012760449L;
	
	/** id */
	private Long id;
	/** 行业ID */
	private Long industryId;
	/** 收支分类ID */
	private Long paymentsId;
	/** 业务类型ID */
	private Long businessId;
	/** 业务类型Code */
	private Long businessCode;
	/** 票据类型 */
	private Long invoiceId;
	/** 数据项ID */
	private Long columnsId;
	/** 0：不显示；1、显示但非必录；2、显示且必录 */
	private Integer flag;
	/** 数据项name */
	private String columnsName;

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
	public Long getPaymentsId() {
		return paymentsId;
	}
	public void setPaymentsId(Long paymentsId) {
		this.paymentsId = paymentsId;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

    /**
     * 获取业务类型Code
     * @return 业务类型Code
     */
    public Long getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置业务类型Code
     * @param businessCode 业务类型Code
     */
    public void setBusinessCode(Long businessCode) {
        this.businessCode = businessCode;
    }

	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Long getColumnsId() {
		return columnsId;
	}
	public void setColumnsId(Long columnsId) {
		this.columnsId = columnsId;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    /**
     * 获取数据项name
     * @return 数据项name
     */
    public String getColumnsName() {
        return columnsName;
    }

    /**
     * 设置数据项name
     * @param columnsName 数据项name
     */
    public void setColumnsName(String columnsName) {
        this.columnsName = columnsName;
    }

}
