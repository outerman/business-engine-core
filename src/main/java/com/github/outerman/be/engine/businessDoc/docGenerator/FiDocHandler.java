package com.github.outerman.be.engine.businessDoc.docGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.outerman.be.api.constant.BusinessCode;
import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.AcmSortReceiptDetail;
import com.github.outerman.be.api.vo.AcmSortReceiptSettlestyle;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.FiAccount;
import com.github.outerman.be.api.vo.FiDocDto;
import com.github.outerman.be.api.vo.FiDocEntryDto;
import com.github.outerman.be.api.vo.PaymentTemplateItem;
import com.github.outerman.be.api.vo.SetCurrency;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AmountGetter;
import com.github.outerman.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import com.github.outerman.be.engine.util.BusinessUtil;
import com.github.outerman.be.engine.util.DoubleUtil;
import com.github.outerman.be.engine.util.StringUtil;

public class FiDocHandler {

    private SetOrg org;

    private SetCurrency currency;

    private AcmSortReceipt voucher;

    private Map<String, BusinessTemplate> templateMap;

    private Map<String, FiAccount> accountMap;

    private FiDocDto doc;

    /** 借方主科目分录 */
    private List<FiDocEntryDto> debitMainList = new ArrayList<>();
    /** 借方税科目分录 */
    private List<FiDocEntryDto> debitTaxList = new ArrayList<>();
    /** 贷方主科目分录 */
    private List<FiDocEntryDto> creditMainList = new ArrayList<>();
    /** 贷方税科目分录 */
    private List<FiDocEntryDto> creditTaxList = new ArrayList<>();

    private Map<String, FiDocEntryDto> entryMap = new HashMap<>();;

    public FiDocHandler(SetOrg org, SetCurrency currency, AcmSortReceipt receipt) {
        this.org = org;
        this.currency = currency;
        this.voucher = receipt;
        this.doc = getDefaultDoc(receipt);
    }

    public FiDocDto getDoc() {
        List<FiDocEntryDto> entrys = new ArrayList<>();
        entrys.addAll(debitMainList);
        entrys.addAll(debitTaxList);
        entrys.addAll(creditMainList);
        entrys.addAll(creditTaxList);
        // 正负混录的业务明细，分录合并之后分录金额可能为 0 ，需要去掉金额为 0 的分录
        List<FiDocEntryDto> zeroAmountList = new ArrayList<>();
        for (FiDocEntryDto docEntry : entrys) {
            if (DoubleUtil.isNullOrZero(docEntry.getAmountDr()) && DoubleUtil.isNullOrZero(docEntry.getAmountCr())) {
                zeroAmountList.add(docEntry);
            }
        }
        if (!zeroAmountList.isEmpty()) {
            entrys.removeAll(zeroAmountList);
        }
        doc.setEntrys(entrys);
        return doc;
    }

    /**
     * 根据单据信息获取默认的凭证 dto
     * @param receipt 单据信息
     */
    private FiDocDto getDefaultDoc(AcmSortReceipt receipt) {
        FiDocDto doc = new FiDocDto();
        doc.setSourceVoucherId(receipt.getSourceVoucherId());
        doc.setSourceVoucherCode(receipt.getSourceVoucherCode());
        doc.setDocSourceTypeId(receipt.getSourceVoucherTypeId());
        doc.setAttachedVoucherNum(receipt.getAppendNum());
        String dateStr = StringUtil.format(receipt.getInAccountDate());
        doc.setVoucherDate(dateStr);
        // 如果曾经生成过,保留凭证号
        if (!StringUtil.isEmpty(receipt.getDocCodeBak())) {
            doc.setCode(receipt.getDocCodeBak());
        }
        doc.setDocId(receipt.getDocId());
        return doc;
    }

    public void addEntry(DocAccountTemplateItem docTemplate, AcmSortReceiptDetail detail) {
        boolean needMerge = true;
        InnerFiDocEntryDto innerEntry = getDocEntryDto(docTemplate, detail);
        if (innerEntry == null) {
            return;
        }

        String key = innerEntry.getKey();
        FiDocEntryDto entry = innerEntry.getFiDocEntryDto();
        if (needMerge && entryMap.containsKey(key)) {
            // 按照分录合并规则需要合并
            Double quantity = entry.getQuantity();
            FiDocEntryDto existEntry = entryMap.get(key);
            if (quantity != null || existEntry.getQuantity() != null) {
                existEntry.setQuantity(DoubleUtil.add(quantity, existEntry.getQuantity()));
            }
            existEntry.setAmountCr(DoubleUtil.add(existEntry.getAmountCr(), entry.getAmountCr()));
            existEntry.setOrigAmountCr(DoubleUtil.add(existEntry.getOrigAmountCr(), entry.getOrigAmountCr()));
            existEntry.setAmountDr(DoubleUtil.add(existEntry.getAmountDr(), entry.getAmountDr()));
            existEntry.setOrigAmountDr(DoubleUtil.add(existEntry.getOrigAmountDr(), entry.getOrigAmountDr()));
            Double amount = entry.getAmountCr();
            if (DoubleUtil.isNullOrZero(amount)) {
                amount = entry.getAmountDr();
            }
            existEntry.setPrice(DoubleUtil.div(amount, existEntry.getQuantity()));
        } else {
            addEntry(entry);
            entryMap.put(key, entry);
        }
    }

    private InnerFiDocEntryDto getDocEntryDto(DocAccountTemplateItem docTemplate, AcmSortReceiptDetail detail) {
        Double amount = AmountGetter.getAmount(detail, docTemplate.getFundSource());
        if (DoubleUtil.isNullOrZero(amount)) {
            return null;
        }

        FiAccount account = docTemplate.getAccount();
        StringBuilder key = new StringBuilder();
        FiDocEntryDto entry = new FiDocEntryDto();
        String summary;
        if (!StringUtil.isEmpty(docTemplate.getSummary())) {
            summary = docTemplate.getSummary();
        } else {
            summary = detail.getMemo();
        }
        key.append("_summary").append(summary);
        entry.setSummary(summary);

        entry.setAccountId(account.getId());
        entry.setAccountCode(account.getCode());
        key.append("_accountCode").append(account.getCode());
        entry.setSourceFlag(docTemplate.getFlag());

        key.append("_businessType").append(detail.getBusinessType());
        entry.setSourceBusinessTypeId(detail.getBusinessType());
        // 单价 看是否抵扣然后传递不同的单价
        Boolean isDeduction = detail.getIsDeduction() != null && detail.getIsDeduction() == 1;
        if (BusinessUtil.paymentDirection(detail.getBusinessCode()) == 1 || isDeduction) {
            entry.setPrice(detail.getPrice());
        } else {
            entry.setPrice(detail.getTaxInclusivePrice());
        }
        // 0 借 1 贷，流水账不区分币种，本币原币金额一样
        Boolean direction = docTemplate.getDirection();
        key.append("_direction").append(direction);
        if (direction) {
            entry.setAmountCr(amount);
            entry.setOrigAmountCr(amount);
        } else {
            entry.setAmountDr(amount);
            entry.setOrigAmountDr(amount);
        }

        if (account.getIsAuxAccCalc() != null && account.getIsAuxAccCalc()) {
            if (account.getIsAuxAccDepartment() != null && account.getIsAuxAccDepartment()) { // 部门
                entry.setDepartmentId(detail.getDepartment());
                key.append("_departmentId").append(detail.getDepartment());
            }
            if (account.getIsAuxAccPerson() != null && account.getIsAuxAccPerson()) { // 人员
                entry.setPersonId(detail.getEmployee());
                key.append("_personId").append(detail.getEmployee());
            }
            if (account.getIsAuxAccCustomer() != null && account.getIsAuxAccCustomer()) { // 客户
                entry.setCustomerId(detail.getConsumer());
                key.append("_customerId").append(detail.getConsumer());
            }
            if (account.getIsAuxAccSupplier() != null && account.getIsAuxAccSupplier()) { // 供应商
                entry.setSupplierId(detail.getVendor());
                key.append("_supplierId").append(detail.getVendor());
            }
            if (account.getIsAuxAccInventory() != null && account.getIsAuxAccInventory()) { // 存货
                if (detail.getAssetId() != null) {
                    entry.setInventoryId(detail.getAssetId());
                    key.append("_inventoryId").append(detail.getAssetId());
                } else {
                    entry.setInventoryId(detail.getInventory());
                    key.append("_inventoryId").append(detail.getInventory());
                }
            }
            if (account.getIsAuxAccProject() != null && account.getIsAuxAccProject()) { // 项目
                entry.setProjectId(detail.getProject());
                key.append("_projectId").append(detail.getProject());
            }
            if(account.getIsQuantityCalc() != null && account.getIsQuantityCalc()){ // 数量辅助核算时，值传给凭证，不作为分组的依据
                entry.setQuantity(detail.getCommodifyNum());
            }
            // 银行账号
            if ("402000".equals(docTemplate.getBusinessCode().toString()) && "A".equals(docTemplate.getFlag())) {
                key.append("_bankAccountId").append(detail.getInBankAccountId());
                entry.setBankAccountId(detail.getInBankAccountId());
            } else {
                key.append("_bankAccountId").append(detail.getBankAccountId());
                entry.setBankAccountId(detail.getBankAccountId());
            }
            if (account.getIsMultiCalc() != null && account.getIsMultiCalc()) { // 多币种
                entry.setCurrencyId(currency.getId());
                key.append("_currencyId").append(currency.getId());
            }
            // 即征即退，影响合并
            if (account.getIsAuxAccLevyAndRetreat() != null && account.getIsAuxAccLevyAndRetreat()) {
                entry.setLevyAndRetreatId(detail.getDrawbackPolicy());
                key.append("_levyAndRetreatId").append(detail.getDrawbackPolicy());
            }
        }

        Map<String, String> influenceMap = docTemplate.getInfluenceMap();
        if (influenceMap != null) {
            for (Entry<String, String> influence : influenceMap.entrySet()) {
                key.append("_" + influence.getKey()).append(influence.getValue());
            }
        }

        InnerFiDocEntryDto result = new InnerFiDocEntryDto();
        result.setFiDocEntryDto(entry);
        result.setKey(key.toString());
        return result;
    }

    private void addEntry(FiDocEntryDto entry) {
        boolean isDebit = !DoubleUtil.isNullOrZero(entry.getAmountDr());
        String accountCode = entry.getAccountCode();
        List<FiDocEntryDto> entryList;
        if (isDebit) {
            if (!accountCode.startsWith("2221")) {
                entryList = debitMainList;
            } else {
                entryList = debitTaxList;
            }
        } else {
            if (!accountCode.startsWith("2221")) {
                entryList = creditMainList;
            } else {
                entryList = creditTaxList;
            }
        }

        if (entryList.isEmpty()) {
            entryList.add(entry);
            return;
        }

        int index = entryList.size();
        for (FiDocEntryDto item : entryList) {
            String code = item.getAccountCode();
            if (accountCode.compareTo(code) < 0) {
                index = entryList.indexOf(item);
                break;
            }
        }
        entryList.add(index, entry);
    }

    public void addEntry(PaymentTemplateItem payDocTemplate, AcmSortReceiptSettlestyle settle) {
        FiDocEntryDto entry = getDocEntryDto(payDocTemplate, settle);
        if (entry == null) {
            return;
        }
        addEntry(entry);
    }

    private FiDocEntryDto getDocEntryDto(PaymentTemplateItem payDocTemplate, AcmSortReceiptSettlestyle settle) {
        Double amount = AmountGetter.getAmount(settle, payDocTemplate.getFundSource());
        if (DoubleUtil.isNullOrZero(amount)) {
            return null;
        }

        FiAccount account = payDocTemplate.getAccount();
        FiDocEntryDto entry = new FiDocEntryDto();
        if (account.getIsAuxAccCalc() != null && account.getIsAuxAccCalc()) {
            if (account.getIsAuxAccPerson() != null && account.getIsAuxAccPerson()) { // 人员
                entry.setPersonId(settle.getEmployee());
            }
            if (account.getIsAuxAccCustomer() != null && account.getIsAuxAccCustomer()) { // 客户
                entry.setCustomerId(settle.getConsumer());
            }
            if (account.getIsAuxAccSupplier() != null && account.getIsAuxAccSupplier()) { // 供应商
                entry.setSupplierId(settle.getVendor());
            }
            if (account.getIsAuxAccBankAccount() != null && account.getIsAuxAccBankAccount()) { // 银行账号
                entry.setBankAccountId(settle.getBankAccountId());
            }
            if (account.getIsMultiCalc() != null && account.getIsMultiCalc()) { // 多币种
                entry.setCurrencyId(currency.getId());
            }
        }

        String summary = getSettleSummary(settle, payDocTemplate);
        entry.setSummary(summary);
        entry.setAccountId(account.getId());
        entry.setAccountCode(account.getCode());

        // 0 借 1 贷，流水账不区分币种，本币原币金额一样
        Boolean direction = payDocTemplate.getDirection();
        // 处理分录借贷方向、正负金额是否需要颠倒
        if (payDocTemplate.getReversal() && amount < 0) {
            direction = !direction;
            amount*= -1;
        }
        if (direction) {
            entry.setAmountCr(amount);
            entry.setOrigAmountCr(amount);
        } else {
            entry.setAmountDr(amount);
            entry.setOrigAmountDr(amount);
        }

        return entry;
    }

    /**
     * 获取结算情况明细对应的分录摘要
     * @param settle
     * @param voucher
     * @param payDocTemplate
     * @return
     */
    private String getSettleSummary(AcmSortReceiptSettlestyle settle, PaymentTemplateItem payDocTemplate) {
        String summary = settle.getMemo();
        if (!StringUtil.isEmpty(summary)) {
            return summary;
        }
        if (voucher.getAcmSortReceiptSettlestyleList().size() != 1) {
            summary = payDocTemplate.getSubjectType();
        } else {
            summary = voucher.getAcmSortReceiptDetailList().get(0).getMemo();
            if (StringUtil.isEmpty(summary)) {
                summary = payDocTemplate.getSubjectType();
            }
        }
        return summary;
    }

    /**
     * 根据凭证模板和流水账收支明细获取对应的科目信息，没有获取到时返回 {@code null}
     * @param docTemplate 凭证模板信息
     * @param detail 流水账收支明细信息
     * @return 科目信息
     */
    public FiAccount getAccount(DocAccountTemplateItem docTemplate, AcmSortReceiptDetail detail) {
        FiAccount account;
        String accountCode = docTemplate.getAccountCode();
        String businessCode = detail.getBusinessCode();
        if ("1002".equals(accountCode)) {
            Long bankAccountId = detail.getBankAccountId();
            if (businessCode.equals(BusinessCode.BUSINESS_402000) && "accountInAttr".equals(docTemplate.getInfluence())
                    || (businessCode.equals(BusinessCode.BUSINESS_401050) && detail.getInBankAccountId() != null)) {
                bankAccountId = detail.getInBankAccountId();
            }
            account = accountMap.get(accountCode + "_" + bankAccountId);
        } else {
            account = accountMap.get(accountCode);
        }
        if (account != null) {
            String key = account.getCode() + "_" + businessCode;
            if (accountMap.containsKey(key)) {
                account = accountMap.get(key);
            }
        }
        return account;
    }

    /**
     * 根据结算凭证模板和流水账结算明细获取对应的科目信息，没有获取到时返回 {@code null}
     * @param payDocTemplate 结算凭证模板信息
     * @param settle 流水账结算明细信息
     * @param accountMap 科目信息 map
     * @return 科目信息
     */
    public FiAccount getAccount(PaymentTemplateItem payDocTemplate, AcmSortReceiptSettlestyle settle) {
        FiAccount account;
        String accountCode = payDocTemplate.getSubjectDefault();
        if ("1002".equals(accountCode)) {
            account = accountMap.get(accountCode + "_" + settle.getBankAccountId());
        } else {
            account = accountMap.get(accountCode);
        }
        return account;
    }

    public SetOrg getOrg() {
        return org;
    }

    public void setOrg(SetOrg org) {
        this.org = org;
    }

    public SetCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(SetCurrency currency) {
        this.currency = currency;
    }

    public AcmSortReceipt getVoucher() {
        return voucher;
    }

    public void setVoucher(AcmSortReceipt receipt) {
        this.voucher = receipt;
    }

    public Map<String, BusinessTemplate> getTemplateMap() {
        return templateMap;
    }

    public void setTemplateMap(Map<String, BusinessTemplate> templateMap) {
        this.templateMap = templateMap;
    }

    public Map<String, FiAccount> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<String, FiAccount> accountMap) {
        this.accountMap = accountMap;
    }

    class InnerFiDocEntryDto {

        private FiDocEntryDto fiDocEntryDto;

        private String key;

        public FiDocEntryDto getFiDocEntryDto() {
            return fiDocEntryDto;
        }

        public void setFiDocEntryDto(FiDocEntryDto fiDocEntryDto) {
            this.fiDocEntryDto = fiDocEntryDto;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

    }
}
