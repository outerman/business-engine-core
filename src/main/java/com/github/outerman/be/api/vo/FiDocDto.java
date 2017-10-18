package com.github.outerman.be.api.vo;

import com.github.outerman.be.engine.util.ArithUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FiDocDto implements Serializable {

	/**
	 * 
	 */
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
	private List<FiDocEntryDto> entrys;

	/** 附件列表 */
//	private List<VoucherEnclosureDto> enclosures;

	private List<String> codeList1 = new ArrayList<String>();// 借主科目
	private List<String> codeList2 = new ArrayList<String>();// 借税科目
	private List<String> codeList3 = new ArrayList<String>();// 贷主科目
	private List<String> codeList4 = new ArrayList<String>();// 贷税科目

	private List<String> codeList5 = new ArrayList<String>();// 本表自平科目
    private List<Long> businessCodeList5 = new ArrayList<>();// businessCode的列表,与codeList5对应

	private List<FiDocEntryDto> entryList1 = new ArrayList<>();
	private List<FiDocEntryDto> entryList2 = new ArrayList<>();
	private List<FiDocEntryDto> entryList3 = new ArrayList<>();
	private List<FiDocEntryDto> entryList4 = new ArrayList<>();

	private List<FiDocEntryDto> entryList5 = new ArrayList<>();

	public Integer getPurpose() {
		return purpose;
	}

	public void setPurpose(Integer purpose) {
		this.purpose = purpose;
	}

	public FiDocDto() {
		entrys = new ArrayList<FiDocEntryDto>();
//		enclosures = new ArrayList<>();
	}

	public void addEntryJie(String accountCode, Double amount, Map<String, Object> map, Boolean isSettlement, Long businessCode) {
		addEntry(accountCode, amount, false, map, isSettlement, businessCode);
	}

	public void addEntryDai(String accountCode, Double amount, Map<String, Object> map, Boolean isSettlement, Long businessCode) {
		addEntry(accountCode, amount, true, map, isSettlement, businessCode);
	}

	/**
     * @param account_merge 科目
     * @param amount 金额
     * @param isJieDai false 借 true 贷
     * @param businessCode
     *
	 */

	private void addEntry(String account_merge, Double amount, boolean isJieDai, Map<String, Object> map,
                          Boolean isSettlement, Long businessCode) {
		FiDocEntryDto fiDocEntry = new FiDocEntryDto();

		Double commodifyNum = null;
		if (map != null && !map.isEmpty()) {
			if (map.get("commodifyNum") != null) {
				commodifyNum = (Double) map.get("commodifyNum");
			}
		}
		if (isJieDai) {// 贷
			// 合并
			if (isSettlement) {
				if (!"2221".equals(account_merge.substring(0, 4))) {// 主科目
					if (codeList3.contains(account_merge)) {
						merge(codeList3, account_merge, amount, commodifyNum, this.getEntrys(), isJieDai, false,
								isSettlement);
						return;
					}
				} else {// 辅助科目 税
					if (codeList4.contains(account_merge)) {
						merge(codeList4, account_merge, amount, commodifyNum, this.getEntrys(), isJieDai, true,
								isSettlement);
						return;
					}
				}
			} else {
				if (codeList5.contains(account_merge)) {
					merge(codeList5, account_merge, amount, commodifyNum, this.getEntrys(), isJieDai, false,
							isSettlement);
					return;
				}
			}

		} else {// 借
				// 合并
			if (isSettlement) {
				if (!"2221".equals(account_merge.substring(0, 4))) {// 主科目
					if (codeList1.contains(account_merge)) {
						merge(codeList1, account_merge, amount, commodifyNum, this.getEntrys(), isJieDai, false,
								isSettlement);
						return;
					}
				} else {// 辅助科目 税
					if (codeList2.contains(account_merge)) {
						merge(codeList2, account_merge, amount, commodifyNum, this.getEntrys(), isJieDai, true,
								isSettlement);
						return;
					}
				}
			} else {
				if (codeList5.contains(account_merge)) {
					merge(codeList5, account_merge, amount, commodifyNum, this.getEntrys(), isJieDai, true,
							isSettlement);
					return;
				}
			}
		}

		// false 借 true 贷
		if (isJieDai) {
			fiDocEntry.setAmountCr(amount);// 贷
			fiDocEntry.setOrigAmountCr(amount);// 贷
		} else {
			fiDocEntry.setAmountDr(amount);// 借
			fiDocEntry.setOrigAmountDr(amount);// 借
		}
		if (map != null && !map.isEmpty()) {
			if (map.get("memo") != null) {// 摘要
				fiDocEntry.setSummary(map.get("memo").toString());
			}
			if (map.get("accountId") != null) {// 科目id
				fiDocEntry.setAccountId((Long) map.get("accountId"));
			}
			if (map.get("accountCode") != null) {// 科目code
				fiDocEntry.setAccountCode(map.get("accountCode").toString());
			}

			if (map.get("department") != null) {// 部门
				fiDocEntry.setDepartmentId((Long) map.get("department"));
			}
			if (map.get("employee") != null) {// 员工
				fiDocEntry.setPersonId((Long) map.get("employee"));
			}
			if (map.get("consumer") != null) {// 客户
				fiDocEntry.setCustomerId((Long) map.get("consumer"));
			}
			if (map.get("vendor") != null) {// 供应商
				fiDocEntry.setSupplierId((Long) map.get("vendor"));
			}
			if (map.get("inventory") != null) {// 存货
				fiDocEntry.setInventoryId((Long) map.get("inventory"));
			}
			if (map.get("project") != null) {// 项目
				fiDocEntry.setProjectId((Long) map.get("project"));
			}
			if (map.get("commodifyNum") != null) {// 数量
				fiDocEntry.setQuantity((Double) map.get("commodifyNum"));
			}
			if (map.get("price") != null) {// 单价
				fiDocEntry.setPrice((Double) map.get("price"));
			}
			if (map.get("bank") != null) {// 银行卡号
				fiDocEntry.setBankAccountId((Long) map.get("bank"));
			}
			if (map.get("notesNum") != null) {// 票据号
				fiDocEntry.setBillCode((String) map.get("notesNum"));
			}
			if (map.get("invoiceType") != null) {// 票据类型
				fiDocEntry.setBillTypeId((Long) map.get("invoiceType"));
			}
			if (map.get("businessType") != null) {// 业务类型
				fiDocEntry.setSourceBusinessTypeId((Long) map.get("businessType"));
			}
			if (map.get("taxRate") != null) {// 税率
				fiDocEntry.setTaxRate((Double) map.get("taxRate"));
			}
			if (map.get("currency") != null) {// 本位币
				fiDocEntry.setCurrencyId((Long) map.get("currency"));// 币种
				fiDocEntry.setExchangeRate(1.0);// 汇率
			}
			if (map.get("flag") != null) {// 凭证ABCD标识
				fiDocEntry.setSourceFlag((String) map.get("flag"));
			}
			if (map.get("drawbackPolicy") != null) {// 即征即退
                fiDocEntry.setLevyAndRetreatId((Long) map.get("drawbackPolicy"));
            }
		}
		if (isSettlement) {
			if (isJieDai) {
				if (!"2221".equals(account_merge.substring(0, 4))) {// 主科目
					sort(entrys, entryList3, codeList3, fiDocEntry, account_merge);
				} else {// 辅助科目 税
					sort(entrys, entryList4, codeList4, fiDocEntry, account_merge);
				}
			} else {
				if (!"2221".equals(account_merge.substring(0, 4))) {// 主科目
					sort(entrys, entryList1, codeList1, fiDocEntry, account_merge);
				} else {// 辅助科目 税
					sort(entrys, entryList2, codeList2, fiDocEntry, account_merge);
				}
			}
		} else {
			//找到合适的位置插入(目前基本顺序是)
			int pos = getInsertPosForList5(fiDocEntry, businessCode);
			entryList5.add(pos, fiDocEntry);
			codeList5.add(pos, account_merge);
            businessCodeList5.add(pos, businessCode);

			entrys.clear();
			entrys.addAll(entryList5);
			entrys.addAll(entryList1);
			entrys.addAll(entryList2);
			entrys.addAll(entryList3);
			entrys.addAll(entryList4);
		}
	}

	// 根据待插入分录的借贷方向,找到合适的插入位置: 同一个业务 + 同一个方向
	private int getInsertPosForList5(FiDocEntryDto fiDocEntry, Long businessCode) {
		if (entryList5.size() == 0) {
			return 0;
		}

		if (fiDocEntry == null || businessCode == null) {
			return entryList5.size();
		}

		boolean isDebit = fiDocEntry.getOrigAmountDr() != null && fiDocEntry.getOrigAmountDr() > 0; //是否借方分录
		int i;
		for (i = entryList5.size(); i > 0; i--) {
			FiDocEntryDto dto = entryList5.get(i - 1); //取上一个分录
            //同时满足以下条件, 允许上移:
            // 1)新插入金额在借方,上一条金额在贷方
            // 2)上下两条是同一个业务: businessCode一致 或 分录摘要一致
            if (isDebit && dto.getOrigAmountCr() != null && dto.getOrigAmountCr() > 0 &&
                    (businessCodeList5.get(i - 1).equals(businessCode) || dto.getSummary().equals(fiDocEntry.getSummary()))) {
                continue;
            }

            return i;
		}

		return entryList5.size();
	}

	private void sort(List<FiDocEntryDto> list, List<FiDocEntryDto> entryList12, List<String> codeList12,
			FiDocEntryDto entry, String account) {
		String[] split = account.split("_");
		String acc = split[0];// 获取科目code
		if (codeList12.isEmpty()) {// 如果codeList是空，直接赋值
			codeList12.add(account);
			entryList12.add(entry);
		} else {
			for (int j = 0; j < codeList12.size(); j++) {
				String b = codeList12.get(j);
				String[] split2 = b.split("_");
				// 给code补位0000 00 00 00 00 总共12位
				long accLong = Long.parseLong(buwei(acc));
				long codeAcc = Long.parseLong(buwei(split2[0]));
				if (accLong >= codeAcc) {
					if (j == codeList12.size() - 1) {
						codeList12.add(account);
						entryList12.add(entry);
						break;
					}
				} else {
					if (j == 0) {
						codeList12.add(0, account);
						entryList12.add(0, entry);
						break;
					} else {
						codeList12.add(j, account);
						entryList12.add(j, entry);
						break;
					}
				}
			}
		}
		list.clear();
		list.addAll(entryList5);
		list.addAll(entryList1);
		list.addAll(entryList2);
		list.addAll(entryList3);
		list.addAll(entryList4);
	}

	private String buwei(String acc) {
		int length = acc.length();
		StringBuilder sb = new StringBuilder();
		sb.append(acc);
		switch (length) {
		case 4:
			sb.append("00000000");
			break;
		case 6:
			sb.append("000000");
			break;
		case 8:
			sb.append("0000");
			break;
		case 10:
			sb.append("00");
			break;

		default:
			break;
		}
		return sb.toString();
	}

	/**
	 * @param codeList
	 * @param Account
	 * @param amount
	 * @param list
	 * @param jiedai
	 * @param shui
	 * 合并
	 */
	private void merge(List<String> codeList, String Account, Double amount, Double commodifyNum,
			List<FiDocEntryDto> list, boolean jiedai, boolean shui, boolean isSettlement) {
		if (codeList.contains(Account)) {
			for (int i = 0; i < codeList.size(); i++) {
				String code = codeList.get(i);

				if (code.equals(Account)) {
					if (jiedai) {// 贷 Cr
						FiDocEntryDto mapAmountCr;
						if (isSettlement) {
							if (shui) {
								mapAmountCr = list.get(
										codeList5.size() + codeList1.size() + codeList2.size() + codeList3.size() + i);
							} else {
								mapAmountCr = list.get(codeList5.size() + codeList1.size() + codeList2.size() + i);
							}

						} else {
							mapAmountCr = list.get(i);
						}
						// 科目金额相加
						Double AmountDrOld = ArithUtil.add(mapAmountCr.getAmountCr(), amount);
						mapAmountCr.setAmountCr(AmountDrOld);
						// 数量相加
						if (commodifyNum != null && mapAmountCr.getQuantity() != null) {
							mapAmountCr.setQuantity(ArithUtil.add(mapAmountCr.getQuantity(), commodifyNum));
						}

					} else {// 借Dr
						FiDocEntryDto mapAmountDr;
						if (isSettlement) {
							if (shui) {
								mapAmountDr = list.get(codeList5.size() + codeList1.size() + i);
							} else {
								mapAmountDr = list.get(codeList5.size() + i);
							}
						} else {
							mapAmountDr = list.get(i);
						}
						// 科目金额相加
						Double AmountDrOld = ArithUtil.add(mapAmountDr.getAmountDr(), amount);
						mapAmountDr.setAmountDr(AmountDrOld);

						// 数量相加
						if (commodifyNum != null && mapAmountDr.getQuantity() != null) {
							mapAmountDr.setQuantity(ArithUtil.add(mapAmountDr.getQuantity(), commodifyNum));
						}
					}

					break;
				}
			}
		}
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
	public List<FiDocEntryDto> getEntrys() {
		return entrys;
	}

	public void setEntrys(List<FiDocEntryDto> entrys) {
		this.entrys = entrys;
	}

//	public static List<FiDocDto> trunToFiDocDtoListFromJournal(List<FiJournalDto> journalList) {
//		List<FiDocDto> docList = new ArrayList<>();
//		if (journalList == null || journalList.isEmpty())
//			return docList;
//		Long docId = 0L;
//		FiDocDto fiDocDto = new FiDocDto();
//		List<FiDocEntryDto> entrys = new ArrayList<>();
//		boolean needNew = true;
//		for (FiJournalDto fiJournalDto : journalList) {
//			if (docId.equals(0L)) {
//				docId = fiJournalDto.getDocId();
//			}
//			if (!docId.equals(fiJournalDto.getDocId())) {
//				fiDocDto.setEntrys(entrys);
//				docList.add(fiDocDto);
//				docId = fiJournalDto.getDocId();
//				needNew = true;
//			}
//
//			if (needNew) {
//				needNew = false;
//				fiDocDto = new FiDocDto();
//				entrys = new ArrayList<>();
//				fiDocDto.setOrgId(fiJournalDto.getOrgId());
//				fiDocDto.setDocType(fiJournalDto.getDocType());
//				fiDocDto.setDocId(fiJournalDto.getDocId());
//				fiDocDto.setCurrentYear(fiJournalDto.getCurrentYear());
//				fiDocDto.setCurrentPeriod(fiJournalDto.getCurrentPeriod());
//				fiDocDto.setCode(fiJournalDto.getCode());
//				fiDocDto.setOrigAmountDrSum(DoubleUtil.formatDoubleScale2(fiJournalDto.getOrigAmountDrSum(), false));
//				fiDocDto.setOrigAmountCrSum(DoubleUtil.formatDoubleScale2(fiJournalDto.getOrigAmountCrSum(), false));
//				fiDocDto.setAmountDrSum(DoubleUtil.formatDoubleScale2(fiJournalDto.getAmountDrSum(), false));
//				fiDocDto.setAmountCrSum(DoubleUtil.formatDoubleScale2(fiJournalDto.getAmountCrSum(), false));
//				fiDocDto.setVoucherDate(new SimpleDateFormat("yyyy-MM-dd").format(fiJournalDto.getVoucherDate()));
//				fiDocDto.setMakerId(fiJournalDto.getMakerId());
//				fiDocDto.setMaker(fiJournalDto.getMaker());
//				fiDocDto.setCreateTime(fiJournalDto.getCreateTime());
//				fiDocDto.setAuditorId(fiJournalDto.getAuditorId());
//				fiDocDto.setAuditor(fiJournalDto.getAuditor());
//				fiDocDto.setAuditedDate(fiJournalDto.getAuditedDate());
//				fiDocDto.setDocSourceTypeId(fiJournalDto.getDocSourceTypeId());
//				fiDocDto.setSourceVoucherId(fiJournalDto.getSourceVoucherId());
//				fiDocDto.setSourceVoucherCode(fiJournalDto.getSourceVoucherCode());
//				fiDocDto.setIsCashFlowed(fiJournalDto.getIsCashFlowed());
//				fiDocDto.setIsQuantityDoc(fiJournalDto.getIsQuantityDoc());
//				fiDocDto.setIsForeignCurrencyDoc(fiJournalDto.getIsForeignCurrencyDoc());
//				fiDocDto.setDocBusinessType(fiJournalDto.getDocBusinessType());
//				fiDocDto.setAttachedVoucherNum(fiJournalDto.getAttachedVoucherNum());
//				fiDocDto.setVoucherState(fiJournalDto.getVoucherState());
//				fiDocDto.setVoucherStateName(fiJournalDto.getVoucherStateName());
//				fiDocDto.setIsModifiedCode(fiJournalDto.getIsModifiedCode());
//				fiDocDto.setCashFlowedState(fiJournalDto.getCashFlowedState());
//				fiDocDto.setIsDifference(fiJournalDto.getIsDifference());
//				fiDocDto.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fiJournalDto.getTs()));
//			}
//			FiDocEntryDto fiDocEntryDto = new FiDocEntryDto();
//			fiDocEntryDto.setId(fiJournalDto.getId());
//			fiDocEntryDto.setSummary(fiJournalDto.getSummary());
//			fiDocEntryDto.setExchangeRate(DoubleUtil.formatDouble(fiJournalDto.getExchangeRate(), false, 6));
//			fiDocEntryDto.setAuxiliaryCode(fiJournalDto.getAuxiliaryCode());
//			fiDocEntryDto.setAuxiliaryItems(fiJournalDto.getAuxiliaryItems());
//			fiDocEntryDto.setOrigAmountDr(DoubleUtil.formatDoubleScale2(fiJournalDto.getOrigAmountDr(), false));
//			fiDocEntryDto.setOrigAmountCr(DoubleUtil.formatDoubleScale2(fiJournalDto.getOrigAmountCr(), false));
//			fiDocEntryDto.setAmountDr(DoubleUtil.formatDoubleScale2(fiJournalDto.getAmountDr(), false));
//			fiDocEntryDto.setAmountCr(DoubleUtil.formatDoubleScale2(fiJournalDto.getAmountCr(), false));
//			if (fiJournalDto.getQuantityDr() != null) {
//				fiDocEntryDto.setQuantity(DoubleUtil.formatDoubleScale2(fiJournalDto.getQuantityDr(), false));
//			} else {
//				fiDocEntryDto.setQuantity(DoubleUtil.formatDoubleScale2(fiJournalDto.getQuantityCr(), false));
//			}
//			fiDocEntryDto.setPrice(fiJournalDto.getPrice());
//			fiDocEntryDto.setUnitId(fiJournalDto.getUnitId());
//			fiDocEntryDto.setSequenceNumber(fiJournalDto.getSequenceNumber());
//			fiDocEntryDto.setCurrencyId(fiJournalDto.getCurrencyId());
//			fiDocEntryDto.setAccountId(fiJournalDto.getAccountId());
//			fiDocEntryDto.setDepartmentId(fiJournalDto.getDepartmentId());
//			fiDocEntryDto.setPersonId(fiJournalDto.getPersonId());
//			fiDocEntryDto.setCustomerId(fiJournalDto.getCustomerId());
//			fiDocEntryDto.setSupplierId(fiJournalDto.getSupplierId());
//			fiDocEntryDto.setInventoryId(fiJournalDto.getInventoryId());
//			fiDocEntryDto.setProjectId(fiJournalDto.getProjectId());
//			fiDocEntryDto.setBankAccountId(fiJournalDto.getBankAccountId());
//			fiDocEntryDto.setBillCode(fiJournalDto.getBillCode());
//			fiDocEntryDto.setBillTypeId(fiJournalDto.getBillTypeId());
//			fiDocEntryDto.setSourceBusinessTypeId(fiJournalDto.getSourceBusinessTypeId());
//			fiDocEntryDto.setTaxRate(fiJournalDto.getTaxRate());
//			fiDocEntryDto.setSourceFlag(fiJournalDto.getSourceFlag());
//			fiDocEntryDto.setRowStatus(fiJournalDto.getRowStatus());
//			fiDocEntryDto.setCurrencyName(fiJournalDto.getCurrencyName());
//			fiDocEntryDto.setCurrencyCode(fiJournalDto.getCurrencyCode());
//			fiDocEntryDto.setAccountCode(fiJournalDto.getAccountCode());
//			fiDocEntryDto.setAccountName(fiJournalDto.getAccountName());
//			fiDocEntryDto.setAccountGradeName(fiJournalDto.getAccountGradeName());
//			fiDocEntryDto.setDepartmentName(fiJournalDto.getDepartmentName());
//			fiDocEntryDto.setPersonName(fiJournalDto.getPersonName());
//			fiDocEntryDto.setCustomerName(fiJournalDto.getCustomerName());
//			fiDocEntryDto.setSupplierName(fiJournalDto.getSupplierName());
//			fiDocEntryDto.setInventoryName(fiJournalDto.getInventoryName());
//			fiDocEntryDto.setProjectName(fiJournalDto.getProjectName());
//			fiDocEntryDto.setBankAccountName(fiJournalDto.getBankAccountName());
//			fiDocEntryDto.setUnitName(fiJournalDto.getUnitName());
//			fiDocEntryDto.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fiJournalDto.getTs()));
//			fiDocEntryDto.setInPutTaxDeductId(fiJournalDto.getInPutTaxDeductId());
//
//			entrys.add(fiDocEntryDto);
//		}
//		fiDocDto.setEntrys(entrys);
//		docList.add(fiDocDto);
//		return docList;
//	}

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

//	public List<VoucherEnclosureDto> getEnclosures() {
//		return enclosures;
//	}
//
//	public void setEnclosures(List<VoucherEnclosureDto> enclosures) {
//		this.enclosures = enclosures;
//	}

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
