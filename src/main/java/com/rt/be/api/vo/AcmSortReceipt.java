package com.rt.be.api.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/** 理票单 */
public class AcmSortReceipt implements Serializable{
	
    private static final long serialVersionUID = 6813334363348077459L;

    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	/**ID*/ 
	private Long id;

	/** 客户组织机构id[set_org]  */ 
	private Long orgId;
	
	/** 年份  */ 
	private int sortYear;
	
	/** 月份  */ 
	private int sortMonth;

	/** 理票单号  */ 
	private String code;
	
	/** 收支类型  */ 
	private Long paymentsType;

	/** 入账日期  */ 
	private Date inAccountDate;	
//	private String inAccountDateS;

	/** 凭证id（将来生成的凭证的id，可以获取凭证号，财务人员等信息）  */ 
	private Long docId;
	
	/** 凭证号  */ 
	private String docCode;
	
	/** 凭证号备份，用于流水账驳回之后再审核生成凭照使用 */
	private String docCodeBak;
	
    /** 理票单状态（1：草稿(暂存)；2：保存；3：已提交）  */ 
	private byte status;
	
	private int [] sts;
	private String [] order;  //用于查询时的排序

	/** 附件数 */ 
	private int appendNum;
	
	/** 理票员[set_person.userid]  */ 
	private Long creator;
	
	/** 制单人姓名 */
	private String creatorName;

    /** 创建时间  */ 
	private Date createTime;

	/** 修改时间  */ 
	private Date updateTime;
	
	/** 时间戳  */ 
	private String ts;
	/** 审核人  */ 
	private Long auditor;
	/** 审核时间  */ 
	private Date auditTime;
	
	/** 来源单类型 id */
	private Long sourceVoucherTypeId;
	
    /** 来源单 id */
	private Long sourceVoucherId;
	
	/** 来源单编码 */
	private String sourceVoucherCode;
	
    /** 所属理票详单列表  */ 
	private List<AcmSortReceiptDetail> acmSortReceiptDetailList;
	
	/** 所属结算方式列表  */ 
	private List<AcmSortReceiptSettlestyle> acmSortReceiptSettlestyleList;

	
	/** 显示字段－驳回意见  */ 
	private String rebutContext; 
	
	/** 显示字段－驳回时间  */ 
	private Date rebutTime; 
	
	/** 显示字段－理票状态名称  */ 
	private String statusName; 
	
	/** 显示字段－收支类型名称(1:收入2:支出)  */ 
	private String paymentsTypeName;
	
	/** 显示字段－含税金额  */ 
	private String taxInclusiveAmount;
	
	/** 显示字段－图片数 */ 
	private int picNum;
	
	/** 显示字段－业务类型 */ 
	private String businessTypeName;
	
	/** 显示字段-票据编码 */
	private String invoiceNo;
	
    /** 显示字段－结算方式名称 */ 
	private String settleStyleName;
	
	/** 显示字段－部门 */ 
	private String departmentName;
	
	/** 显示字段－摘要 */ 
	private String memo;
	
	/** 显示字段－增值税纳税人身份 */ 
	private byte vatTaxpayer;
	
	/** 查询条件－理票员  */ 
	private String operatorName;
	
	/** 查询条件－客户名称  */ 
	private String customerName;
	
	/** 理票单 ID 数组  */ 
	private long [] ids;
	
	/** 时间戳数组  */ 
	private String[] tss;
	
	/** 理票单号数组  */ 
	private String[] codes;
	
	/** 理票单驳回意见  */ 
	private String context;
	
	/**以下为关联字段**/
	/** 理票年月  */ 
	private String sortYearMonthDay;
	
	
	
	//审核人name
	private String auditorName;
	
	/**再次导入的发票上次发票状态*/
	private Long cerStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getPaymentsType() {
		return paymentsType;
	}

	public void setPaymentsType(Long paymentsType) {
		this.paymentsType = paymentsType;
	}

	public Date getInAccountDate() {
		return inAccountDate;
	}

	public void setInAccountDate(Date inAccountDate) {
		this.inAccountDate = inAccountDate;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int[] getSts() {
		return sts;
	}

	public void setSts(int[] sts) {
		this.sts = sts;
	}
	
	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	/**
     * 获取凭证号备份，用于流水账驳回之后再审核生成凭照使用
     * @return 凭证号备份
     */
    public String getDocCodeBak() {
        return docCodeBak;
    }

    /**
     * 设置凭证号备份，用于流水账驳回之后再审核生成凭照使用
     * @param docCodeBak 凭证号备份
     */
    public void setDocCodeBak(String docCodeBak) {
        this.docCodeBak = docCodeBak;
    }
	
	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	/**
     * 获取制单人姓名
     * @return 制单人姓名
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * 设置制单人姓名
     * @param creatorName 制单人姓名
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPaymentsTypeName() {
		return paymentsTypeName;
	}

	public void setPaymentsTypeName(String paymentsTypeName) {
		this.paymentsTypeName = paymentsTypeName;
	}

	public String getTaxInclusiveAmount() {
		return taxInclusiveAmount;
	}

	public void setTaxInclusiveAmount(String taxInclusiveAmount) {
		this.taxInclusiveAmount = taxInclusiveAmount;
	}

	public int getPicNum() {
		return picNum;
	}

	public void setPicNum(int picNum) {
		this.picNum = picNum;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

    /**
     * 获取显示字段-票据编码
     * @return 显示字段-票据编码
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 设置显示字段-票据编码
     * @param invoiceNo 显示字段-票据编码
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getSortYearMonthDay() {
		return sortYearMonthDay;
	}

	public void setSortYearMonthDay(String sortYearMonthDay) {
		this.sortYearMonthDay = sortYearMonthDay;
	}

	public int getSortYear() {
		return sortYear;
	}

	public void setSortYear(int sortYear) {
		this.sortYear = sortYear;
	}

	public int getSortMonth() {
		return sortMonth;
	}

	public void setSortMonth(int sortMonth) {
		this.sortMonth = sortMonth;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<AcmSortReceiptDetail> getAcmSortReceiptDetailList() {
		return acmSortReceiptDetailList;
	}

	public void setAcmSortReceiptDetailList(List<AcmSortReceiptDetail> acmSortReceiptDetailList) {
		this.acmSortReceiptDetailList = acmSortReceiptDetailList;
	}

	public List<AcmSortReceiptSettlestyle> getAcmSortReceiptSettlestyleList() {
		return acmSortReceiptSettlestyleList;
	}

	public void setAcmSortReceiptSettlestyleList(List<AcmSortReceiptSettlestyle> acmSortReceiptSettlestyleList) {
		this.acmSortReceiptSettlestyleList = acmSortReceiptSettlestyleList;
	}

	public int getAppendNum() {
		return appendNum;
	}

	public void setAppendNum(int appendNum) {
		this.appendNum = appendNum;
	}

//	public String getInAccountDateS() {
//		return inAccountDateS;
//	}
//
//	public void setInAccountDateS(String inAccountDateS) throws ParseException {
//		this.inAccountDateS = inAccountDateS;
//		synchronized (sf) {
//		    this.setInAccountDate(sf.parse(inAccountDateS));
//        }
//	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public byte getVatTaxpayer() {
		return vatTaxpayer;
	}

	public void setVatTaxpayer(byte vatTaxpayer) {
		this.vatTaxpayer = vatTaxpayer;
	}

	public String getRebutContext() {
		return rebutContext;
	}

	public void setRebutContext(String rebutContext) {
		this.rebutContext = rebutContext;
	}

	public Date getRebutTime() {
		return rebutTime;
	}

	public void setRebutTime(Date rebutTime) {
		this.rebutTime = rebutTime;
	}

//	public SimpleDateFormat getSf() {
//		return sf;
//	}
//
//	public void setSf(SimpleDateFormat sf) {
//		this.sf = sf;
//	}

	public long[] getIds() {
		return ids;
	}

	public void setIds(long[] ids) {
		this.ids = ids;
	}

	public String[] getTss() {
		return tss;
	}

	public void setTss(String[] tss) {
		this.tss = tss;
	}

	public String[] getCodes() {
		return codes;
	}

	public void setCodes(String[] codes) {
		this.codes = codes;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSettleStyleName() {
		return settleStyleName;
	}

	public void setSettleStyleName(String settleStyleName) {
		this.settleStyleName = settleStyleName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getAuditor() {
		return auditor;
	}

	public void setAuditor(Long auditor) {
		this.auditor = auditor;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	/**
     * 获取来源单类型 id
     * @return 来源单类型 id
     */
    public Long getSourceVoucherTypeId() {
        return sourceVoucherTypeId;
    }

    /**
     * 设置来源单类型 id
     * @param sourceVoucherTypeId 来源单类型 id
     */
    public void setSourceVoucherTypeId(Long sourceVoucherTypeId) {
        this.sourceVoucherTypeId = sourceVoucherTypeId;
    }

    /**
     * 获取来源单 id
     * @return 来源单 id
     */
    public Long getSourceVoucherId() {
        return sourceVoucherId;
    }

    /**
     * 设置来源单 id
     * @param sourceVoucherId 来源单 id
     */
    public void setSourceVoucherId(Long sourceVoucherId) {
        this.sourceVoucherId = sourceVoucherId;
    }
	
    /**
     * 获取来源单编码
     * @return 来源单编码
     */
    public String getSourceVoucherCode() {
        return sourceVoucherCode;
    }

    /**
     * 设置来源单编码
     * @param sourceVoucherCode 来源单编码
     */
    public void setSourceVoucherCode(String sourceVoucherCode) {
        this.sourceVoucherCode = sourceVoucherCode;
    }

    public String[] getOrder() {
        return order;
    }

    public void setOrder(String[] order) {
        this.order = order;
    }


	public Long getCerStatus() {
		return cerStatus;
	}

	public void setCerStatus(Long cerStatus) {
		this.cerStatus = cerStatus;
	}
    
}
