package com.github.outerman.be.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Doc implements Serializable {

    private static final long serialVersionUID = 4609809951218436368L;

    /** orgId  */
    private Long orgId;

    /** 是否为插入凭证  */
    private Boolean isInsert;

    /** 接口用途 1:产品内部正常接口   2:外部系统对接*/
    private Integer purpose;

    /** 凭证ID  */
    private Long docId;

    /** 凭证类别  */
    private String docType;

    /** 凭证编号  */
    private String code;

    /** 年  */
    private Integer currentYear;

    /** 期间  */
    private Integer currentPeriod;

    /** 单据日期  */
    private String voucherDate;

    /** 借方原币合计  */
    private Double origAmountDrSum;

    /** 贷方原币合计  */
    private Double origAmountCrSum;

    /** 借方本币合计  */
    private Double amountDrSum;

    /** 贷方本币合计  */
    private Double amountCrSum;

    /** 制单人ID  */
    private Long makerId;

    /** 制单人  */
    private String maker;

    /** 创建日期  */
    private Date createTime;

    /** 审核人ID  */
    private Long auditorId;

    /** 审核人  */
    private String auditor;

    /** 审核日期  */
    private Date auditedDate;

    /** 单据来源[set_enum_detail]  */
    private Long docSourceTypeId;

    /** 来源单据id  */
    private Long sourceVoucherId;

    /** 来源单据明细ID 不存数据库 */
    private Long sourceVoucherDetailId;

    /** 来源单据Code  */
    private String sourceVoucherCode;

    /** 是否现金流量分配 0否 1是  */
    private Boolean isCashFlowed;

    /** 是否数量凭证 0否 1是  */
    private Boolean isQuantityDoc;

    /** 是否外币凭证 0否 1是  */
    private Boolean isForeignCurrencyDoc;

    /** 凭证业务类型[set_enum_detail]  */
    private Long docBusinessType;

    /** 所附单据个数  */
    private Integer attachedVoucherNum;

    /** 单据状态[set_enum_detail]  */
    private Long voucherState;

    /** 单据状态Name[set_enum_detail]  */
    private String voucherStateName;

    /** 是否手工修改单据编码 0否 1是  */
    private Boolean isModifiedCode;

    /** 现金流量分配状态 0未分配 1已分配  */
    private Boolean cashFlowedState;

    /** 是否差异 0否 1是  */
    private Boolean isDifference;

    /** 是否期初  */
    private Boolean isPeriodBegin;

    /** 时间戳  */
    private String ts;

    /** 分录 */
    private List<DocEntry> entrys;

    /** 附件列表 */
    // private List<VoucherEnclosureDto> enclosures;

    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    public Doc() {
        entrys = new ArrayList<DocEntry>();
        // enclosures = new ArrayList<>();
    }

    /** orgId  */
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /** 凭证ID  */
    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    /** 凭证类别  */
    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    /** 年  */
    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    /** 期间  */
    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    /** 凭证编号  */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /** 借方原币合计  */
    public Double getOrigAmountDrSum() {
        return origAmountDrSum;
    }

    public void setOrigAmountDrSum(Double origAmountDrSum) {
        this.origAmountDrSum = origAmountDrSum;
    }

    /** 贷方原币合计  */
    public Double getOrigAmountCrSum() {
        return origAmountCrSum;
    }

    public void setOrigAmountCrSum(Double origAmountCrSum) {
        this.origAmountCrSum = origAmountCrSum;
    }

    /** 借方本币合计  */
    public Double getAmountDrSum() {
        return amountDrSum;
    }

    public void setAmountDrSum(Double amountDrSum) {
        this.amountDrSum = amountDrSum;
    }

    /** 贷方本币合计  */
    public Double getAmountCrSum() {
        return amountCrSum;
    }

    public void setAmountCrSum(Double amountCrSum) {
        this.amountCrSum = amountCrSum;
    }

    /** 单据日期  */
    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    /** 制单人ID  */
    public Long getMakerId() {
        return makerId;
    }

    public void setMakerId(Long makerId) {
        this.makerId = makerId;
    }

    /** 制单人  */
    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    /** 创建日期  */
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 审核人ID  */
    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    /** 审核人  */
    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /** 审核日期  */
    public Date getAuditedDate() {
        return auditedDate;
    }

    public void setAuditedDate(Date auditedDate) {
        this.auditedDate = auditedDate;
    }

    /** 单据来源[set_enum_detail]  */
    public Long getDocSourceTypeId() {
        return docSourceTypeId;
    }

    public void setDocSourceTypeId(Long docSourceTypeId) {
        this.docSourceTypeId = docSourceTypeId;
    }

    /** 是否现金流量分配 0否 1是  */
    public Boolean getIsCashFlowed() {
        return isCashFlowed;
    }

    public void setIsCashFlowed(Boolean isCashFlowed) {
        this.isCashFlowed = isCashFlowed;
    }

    /** 是否数量凭证 0否 1是  */
    public Boolean getIsQuantityDoc() {
        return isQuantityDoc;
    }

    public void setIsQuantityDoc(Boolean isQuantityDoc) {
        this.isQuantityDoc = isQuantityDoc;
    }

    /** 是否外币凭证 0否 1是  */
    public Boolean getIsForeignCurrencyDoc() {
        return isForeignCurrencyDoc;
    }

    public void setIsForeignCurrencyDoc(Boolean isForeignCurrencyDoc) {
        this.isForeignCurrencyDoc = isForeignCurrencyDoc;
    }

    /** 凭证业务类型[set_enum_detail]  */
    public Long getDocBusinessType() {
        return docBusinessType;
    }

    public void setDocBusinessType(Long docBusinessType) {
        this.docBusinessType = docBusinessType;
    }

    /** 所附单据个数  */
    public Integer getAttachedVoucherNum() {
        return attachedVoucherNum;
    }

    public void setAttachedVoucherNum(Integer attachedVoucherNum) {
        this.attachedVoucherNum = attachedVoucherNum;
    }

    /** 单据状态[set_enum_detail]  */
    public Long getVoucherState() {
        return voucherState;
    }

    public void setVoucherState(Long voucherState) {
        this.voucherState = voucherState;
    }

    /** 单据状态Name[set_enum_detail]  */
    public String getVoucherStateName() {
        return voucherStateName;
    }

    public void setVoucherStateName(String voucherStateName) {
        this.voucherStateName = voucherStateName;
    }

    /** 是否手工修改单据编码 0否 1是  */
    public Boolean getIsModifiedCode() {
        return isModifiedCode;
    }

    public void setIsModifiedCode(Boolean isModifiedCode) {
        this.isModifiedCode = isModifiedCode;
    }

    /** 现金流量分配状态 0未分配 1已分配  */
    public Boolean getCashFlowedState() {
        return cashFlowedState;
    }

    public void setCashFlowedState(Boolean cashFlowedState) {
        this.cashFlowedState = cashFlowedState;
    }

    /** 是否差异 0否 1是  */
    public Boolean getIsDifference() {
        return isDifference;
    }

    public void setIsDifference(Boolean isDifference) {
        this.isDifference = isDifference;
    }

    /** 时间戳  */
    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    /** 分录 */
    public List<DocEntry> getEntrys() {
        return entrys;
    }

    public void setEntrys(List<DocEntry> entrys) {
        this.entrys = entrys;
    }

    // public static List<FiDocDto> trunToFiDocDtoListFromJournal(List<FiJournalDto>
    // journalList) {
    // List<FiDocDto> docList = new ArrayList<>();
    // if (journalList == null || journalList.isEmpty())
    // return docList;
    // Long docId = 0L;
    // FiDocDto fiDocDto = new FiDocDto();
    // List<FiDocEntryDto> entrys = new ArrayList<>();
    // boolean needNew = true;
    // for (FiJournalDto fiJournalDto : journalList) {
    // if (docId.equals(0L)) {
    // docId = fiJournalDto.getDocId();
    // }
    // if (!docId.equals(fiJournalDto.getDocId())) {
    // fiDocDto.setEntrys(entrys);
    // docList.add(fiDocDto);
    // docId = fiJournalDto.getDocId();
    // needNew = true;
    // }
    //
    // if (needNew) {
    // needNew = false;
    // fiDocDto = new FiDocDto();
    // entrys = new ArrayList<>();
    // fiDocDto.setOrgId(fiJournalDto.getOrgId());
    // fiDocDto.setDocType(fiJournalDto.getDocType());
    // fiDocDto.setDocId(fiJournalDto.getDocId());
    // fiDocDto.setCurrentYear(fiJournalDto.getCurrentYear());
    // fiDocDto.setCurrentPeriod(fiJournalDto.getCurrentPeriod());
    // fiDocDto.setCode(fiJournalDto.getCode());
    // fiDocDto.setOrigAmountDrSum(DoubleUtil.formatDoubleScale2(fiJournalDto.getOrigAmountDrSum(),
    // false));
    // fiDocDto.setOrigAmountCrSum(DoubleUtil.formatDoubleScale2(fiJournalDto.getOrigAmountCrSum(),
    // false));
    // fiDocDto.setAmountDrSum(DoubleUtil.formatDoubleScale2(fiJournalDto.getAmountDrSum(),
    // false));
    // fiDocDto.setAmountCrSum(DoubleUtil.formatDoubleScale2(fiJournalDto.getAmountCrSum(),
    // false));
    // fiDocDto.setVoucherDate(new
    // SimpleDateFormat("yyyy-MM-dd").format(fiJournalDto.getVoucherDate()));
    // fiDocDto.setMakerId(fiJournalDto.getMakerId());
    // fiDocDto.setMaker(fiJournalDto.getMaker());
    // fiDocDto.setCreateTime(fiJournalDto.getCreateTime());
    // fiDocDto.setAuditorId(fiJournalDto.getAuditorId());
    // fiDocDto.setAuditor(fiJournalDto.getAuditor());
    // fiDocDto.setAuditedDate(fiJournalDto.getAuditedDate());
    // fiDocDto.setDocSourceTypeId(fiJournalDto.getDocSourceTypeId());
    // fiDocDto.setSourceVoucherId(fiJournalDto.getSourceVoucherId());
    // fiDocDto.setSourceVoucherCode(fiJournalDto.getSourceVoucherCode());
    // fiDocDto.setIsCashFlowed(fiJournalDto.getIsCashFlowed());
    // fiDocDto.setIsQuantityDoc(fiJournalDto.getIsQuantityDoc());
    // fiDocDto.setIsForeignCurrencyDoc(fiJournalDto.getIsForeignCurrencyDoc());
    // fiDocDto.setDocBusinessType(fiJournalDto.getDocBusinessType());
    // fiDocDto.setAttachedVoucherNum(fiJournalDto.getAttachedVoucherNum());
    // fiDocDto.setVoucherState(fiJournalDto.getVoucherState());
    // fiDocDto.setVoucherStateName(fiJournalDto.getVoucherStateName());
    // fiDocDto.setIsModifiedCode(fiJournalDto.getIsModifiedCode());
    // fiDocDto.setCashFlowedState(fiJournalDto.getCashFlowedState());
    // fiDocDto.setIsDifference(fiJournalDto.getIsDifference());
    // fiDocDto.setTs(new SimpleDateFormat("yyyy-MM-dd
    // HH:mm:ss").format(fiJournalDto.getTs()));
    // }
    // FiDocEntryDto fiDocEntryDto = new FiDocEntryDto();
    // fiDocEntryDto.setId(fiJournalDto.getId());
    // fiDocEntryDto.setSummary(fiJournalDto.getSummary());
    // fiDocEntryDto.setExchangeRate(DoubleUtil.formatDouble(fiJournalDto.getExchangeRate(),
    // false, 6));
    // fiDocEntryDto.setAuxiliaryCode(fiJournalDto.getAuxiliaryCode());
    // fiDocEntryDto.setAuxiliaryItems(fiJournalDto.getAuxiliaryItems());
    // fiDocEntryDto.setOrigAmountDr(DoubleUtil.formatDoubleScale2(fiJournalDto.getOrigAmountDr(),
    // false));
    // fiDocEntryDto.setOrigAmountCr(DoubleUtil.formatDoubleScale2(fiJournalDto.getOrigAmountCr(),
    // false));
    // fiDocEntryDto.setAmountDr(DoubleUtil.formatDoubleScale2(fiJournalDto.getAmountDr(),
    // false));
    // fiDocEntryDto.setAmountCr(DoubleUtil.formatDoubleScale2(fiJournalDto.getAmountCr(),
    // false));
    // if (fiJournalDto.getQuantityDr() != null) {
    // fiDocEntryDto.setQuantity(DoubleUtil.formatDoubleScale2(fiJournalDto.getQuantityDr(),
    // false));
    // } else {
    // fiDocEntryDto.setQuantity(DoubleUtil.formatDoubleScale2(fiJournalDto.getQuantityCr(),
    // false));
    // }
    // fiDocEntryDto.setPrice(fiJournalDto.getPrice());
    // fiDocEntryDto.setUnitId(fiJournalDto.getUnitId());
    // fiDocEntryDto.setSequenceNumber(fiJournalDto.getSequenceNumber());
    // fiDocEntryDto.setCurrencyId(fiJournalDto.getCurrencyId());
    // fiDocEntryDto.setAccountId(fiJournalDto.getAccountId());
    // fiDocEntryDto.setDepartmentId(fiJournalDto.getDepartmentId());
    // fiDocEntryDto.setPersonId(fiJournalDto.getPersonId());
    // fiDocEntryDto.setCustomerId(fiJournalDto.getCustomerId());
    // fiDocEntryDto.setSupplierId(fiJournalDto.getSupplierId());
    // fiDocEntryDto.setInventoryId(fiJournalDto.getInventoryId());
    // fiDocEntryDto.setProjectId(fiJournalDto.getProjectId());
    // fiDocEntryDto.setBankAccountId(fiJournalDto.getBankAccountId());
    // fiDocEntryDto.setBillCode(fiJournalDto.getBillCode());
    // fiDocEntryDto.setBillTypeId(fiJournalDto.getBillTypeId());
    // fiDocEntryDto.setSourceBusinessTypeId(fiJournalDto.getSourceBusinessTypeId());
    // fiDocEntryDto.setTaxRate(fiJournalDto.getTaxRate());
    // fiDocEntryDto.setSourceFlag(fiJournalDto.getSourceFlag());
    // fiDocEntryDto.setRowStatus(fiJournalDto.getRowStatus());
    // fiDocEntryDto.setCurrencyName(fiJournalDto.getCurrencyName());
    // fiDocEntryDto.setCurrencyCode(fiJournalDto.getCurrencyCode());
    // fiDocEntryDto.setAccountCode(fiJournalDto.getAccountCode());
    // fiDocEntryDto.setAccountName(fiJournalDto.getAccountName());
    // fiDocEntryDto.setAccountGradeName(fiJournalDto.getAccountGradeName());
    // fiDocEntryDto.setDepartmentName(fiJournalDto.getDepartmentName());
    // fiDocEntryDto.setPersonName(fiJournalDto.getPersonName());
    // fiDocEntryDto.setCustomerName(fiJournalDto.getCustomerName());
    // fiDocEntryDto.setSupplierName(fiJournalDto.getSupplierName());
    // fiDocEntryDto.setInventoryName(fiJournalDto.getInventoryName());
    // fiDocEntryDto.setProjectName(fiJournalDto.getProjectName());
    // fiDocEntryDto.setBankAccountName(fiJournalDto.getBankAccountName());
    // fiDocEntryDto.setUnitName(fiJournalDto.getUnitName());
    // fiDocEntryDto.setTs(new SimpleDateFormat("yyyy-MM-dd
    // HH:mm:ss").format(fiJournalDto.getTs()));
    // fiDocEntryDto.setInPutTaxDeductId(fiJournalDto.getInPutTaxDeductId());
    //
    // entrys.add(fiDocEntryDto);
    // }
    // fiDocDto.setEntrys(entrys);
    // docList.add(fiDocDto);
    // return docList;
    // }

    public Boolean getIsPeriodBegin() {
        return isPeriodBegin;
    }

    public void setIsPeriodBegin(Boolean isPeriodBegin) {
        this.isPeriodBegin = isPeriodBegin;
    }

    public Long getSourceVoucherId() {
        return sourceVoucherId;
    }

    public void setSourceVoucherId(Long sourceVoucherId) {
        this.sourceVoucherId = sourceVoucherId;
    }

    public String getSourceVoucherCode() {
        return sourceVoucherCode;
    }

    public void setSourceVoucherCode(String sourceVoucherCode) {
        this.sourceVoucherCode = sourceVoucherCode;
    }

    // public List<VoucherEnclosureDto> getEnclosures() {
    // return enclosures;
    // }
    //
    // public void setEnclosures(List<VoucherEnclosureDto> enclosures) {
    // this.enclosures = enclosures;
    // }

    public Boolean getIsInsert() {
        return isInsert == null ? false : isInsert;
    }

    public void setIsInsert(Boolean isInsert) {
        this.isInsert = isInsert;
    }

    /** 来源单据明细ID */
    public Long getSourceVoucherDetailId() {
        return sourceVoucherDetailId;
    }

    /** 来源单据明细ID */
    public void setSourceVoucherDetailId(Long sourceVoucherDetailId) {
        this.sourceVoucherDetailId = sourceVoucherDetailId;
    }

}
