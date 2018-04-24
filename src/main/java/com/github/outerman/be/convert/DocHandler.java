package com.github.outerman.be.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.outerman.be.contant.CommonConst;
import com.github.outerman.be.model.Account;
import com.github.outerman.be.model.BusinessVoucher;
import com.github.outerman.be.model.BusinessVoucherDetail;
import com.github.outerman.be.model.BusinessVoucherSettle;
import com.github.outerman.be.model.Doc;
import com.github.outerman.be.model.DocEntry;
import com.github.outerman.be.model.DocTemplate;
import com.github.outerman.be.model.Org;
import com.github.outerman.be.model.SettleTemplate;
import com.github.outerman.be.template.BusinessTemplate;
import com.github.outerman.be.util.AmountGetter;
import com.github.outerman.be.util.DoubleUtil;
import com.github.outerman.be.util.StringUtil;

public class DocHandler {

    private Org org;

    private BusinessVoucher voucher;

    private Map<String, BusinessTemplate> templateMap;

    private Map<String, Account> accountMap;

    private Doc doc;

    /** 借方主科目分录 */
    private List<DocEntry> debitMainList = new ArrayList<>();
    /** 借方税科目分录 */
    private List<DocEntry> debitTaxList = new ArrayList<>();
    /** 贷方主科目分录 */
    private List<DocEntry> creditMainList = new ArrayList<>();
    /** 贷方税科目分录 */
    private List<DocEntry> creditTaxList = new ArrayList<>();

    private Map<String, DocEntry> entryMap = new HashMap<>();;

    public DocHandler(Org org, BusinessVoucher voucher) {
        this.org = org;
        this.voucher = voucher;
        this.doc = getDefaultDoc(voucher);
    }

    /**
     * 根据单据信息获取默认的凭证 dto
     * @param voucher 单据信息
     */
    private Doc getDefaultDoc(BusinessVoucher voucher) {
        Doc doc = new Doc();
        doc.setSourceVoucherId(voucher.getSourceVoucherId());
        doc.setSourceVoucherCode(voucher.getSourceVoucherCode());
        doc.setSourceVoucherTypeId(voucher.getSourceVoucherTypeId());
        doc.setAttachedVoucherNum(voucher.getAppendNum());
        doc.setVoucherDate(voucher.getVoucherDate());
        doc.setDocId(voucher.getDocId());
        doc.setCode(voucher.getDocCode());
        return doc;
    }

    public Doc getDoc() {
        List<DocEntry> entrys = new ArrayList<>();
        entrys.addAll(debitMainList);
        entrys.addAll(debitTaxList);
        entrys.addAll(creditMainList);
        entrys.addAll(creditTaxList);
        // 正负混录的业务明细，分录合并之后分录金额可能为 0 ，需要去掉金额为 0 的分录
        List<DocEntry> zeroAmountList = new ArrayList<>();
        for (DocEntry docEntry : entrys) {
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

    public void addEntry(DocTemplate docTemplate, BusinessVoucherDetail detail) {
        boolean needMerge = true;
        InnerFiDocEntryDto innerEntry = getDocEntryDto(docTemplate, detail);
        if (innerEntry == null) {
            return;
        }

        String key = innerEntry.getKey();
        DocEntry entry = innerEntry.getFiDocEntryDto();
        if (needMerge && entryMap.containsKey(key)) {
            // 按照分录合并规则需要合并
            Double quantity = entry.getQuantity();
            DocEntry existEntry = entryMap.get(key);
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

    private InnerFiDocEntryDto getDocEntryDto(DocTemplate docTemplate, BusinessVoucherDetail detail) {
        Double amount = AmountGetter.getAmount(detail, docTemplate.getAmountSource(), detail.getAmountMap());
        if (DoubleUtil.isNullOrZero(amount)) {
            return null;
        }

        Account account = docTemplate.getAccount();
        StringBuilder key = new StringBuilder();
        DocEntry entry = new DocEntry();
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
        if (detail.getBusinessPropertyId() != null && detail.getBusinessPropertyId() == CommonConst.BUSINESSPROPERTY_income || isDeduction) {
            entry.setPrice(detail.getPrice());
        } else {
            entry.setPrice(detail.getTaxInclusivePrice());
        }
        // 0 借 1 贷，流水账不区分币种，本币原币金额一样
        Integer direction = docTemplate.getBalanceDirection();
        key.append("_direction").append(direction);
        if (direction != null && direction.equals(1)) {
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
            if (account.getIsAuxAccBankAccount() != null && account.getIsAuxAccBankAccount()) {
                key.append("_bankAccountId").append(detail.getBankAccountId());
                entry.setBankAccountId(detail.getBankAccountId());
            }
            if (account.getIsMultiCalc() != null && account.getIsMultiCalc()) { // 多币种
                entry.setCurrencyId(org.getBaseCurrencyId());
                key.append("_currencyId").append(org.getBaseCurrencyId());
            }
            // 即征即退，影响合并
            if (account.getIsAuxAccLevyAndRetreat() != null && account.getIsAuxAccLevyAndRetreat()) {
                entry.setLevyAndRetreatId(detail.getDrawbackPolicy());
                key.append("_levyAndRetreatId").append(detail.getDrawbackPolicy());
            }
        }

        InnerFiDocEntryDto result = new InnerFiDocEntryDto();
        result.setFiDocEntryDto(entry);
        result.setKey(key.toString());
        return result;
    }

    private void addEntry(DocEntry entry) {
        boolean isDebit = !DoubleUtil.isNullOrZero(entry.getAmountDr());
        String accountCode = entry.getAccountCode();
        List<DocEntry> entryList;
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

        entryList.add(entry);
    }

    public void addEntry(SettleTemplate payDocTemplate, BusinessVoucherSettle settle) {
        DocEntry entry = getDocEntryDto(payDocTemplate, settle);
        if (entry == null) {
            return;
        }
        addEntry(entry);
    }

    private DocEntry getDocEntryDto(SettleTemplate payDocTemplate, BusinessVoucherSettle settle) {
        Double amount = AmountGetter.getAmount(settle, payDocTemplate.getAmountSource(), settle.getAmountMap());
        if (DoubleUtil.isNullOrZero(amount)) {
            return null;
        }

        Account account = payDocTemplate.getAccount();
        DocEntry entry = new DocEntry();
        if (account.getIsAuxAccCalc() != null && account.getIsAuxAccCalc()) {
            if (account.getIsAuxAccPerson() != null && account.getIsAuxAccPerson()) { // 人员
                entry.setPersonId(settle.getPersonId());
            }
            if (account.getIsAuxAccCustomer() != null && account.getIsAuxAccCustomer()) { // 客户
                entry.setCustomerId(settle.getCustomerId());
            }
            if (account.getIsAuxAccSupplier() != null && account.getIsAuxAccSupplier()) { // 供应商
                entry.setSupplierId(settle.getSupplierId());
            }
            if (account.getIsAuxAccBankAccount() != null && account.getIsAuxAccBankAccount()) { // 银行账号
                entry.setBankAccountId(settle.getBankAccountId());
            }
            if (account.getIsMultiCalc() != null && account.getIsMultiCalc()) { // 多币种
                entry.setCurrencyId(org.getBaseCurrencyId());
            }
        }

        String summary = getSettleSummary(settle, payDocTemplate);
        entry.setSummary(summary);
        entry.setAccountId(account.getId());
        entry.setAccountCode(account.getCode());

        // 0 借 1 贷，流水账不区分币种，本币原币金额一样
        Integer direction = payDocTemplate.getBalanceDirection();
        if (direction == null) {
            direction = 0;
        }
        boolean isCr = direction != null && direction.equals(1);
        // 处理分录借贷方向、正负金额是否需要颠倒
        if (payDocTemplate.getReversal() && amount < 0) {
            isCr = !isCr;
            amount*= -1;
        }
        if (isCr) {
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
    private String getSettleSummary(BusinessVoucherSettle settle, SettleTemplate payDocTemplate) {
        String summary = settle.getRemark();
        if (!StringUtil.isEmpty(summary)) {
            return summary;
        }
        if (voucher.getSettles().size() != 1) {
            summary = payDocTemplate.getSummary();
        } else {
            summary = voucher.getDetails().get(0).getMemo();
            if (StringUtil.isEmpty(summary)) {
                summary = payDocTemplate.getSummary();
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
    public Account getAccount(DocTemplate docTemplate, BusinessVoucherDetail detail) {
        Account account;
        String accountCode = docTemplate.getAccountCode();
        String key = accountCode + "_" + detail.getBankAccountId();
        if (accountMap.containsKey(key)) {
            account = accountMap.get(key);
        } else {
            account = accountMap.get(accountCode);
        }
        if (account != null) {
            key = account.getCode() + "_" + detail.getBusinessCode();
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
    public Account getAccount(SettleTemplate payDocTemplate, BusinessVoucherSettle settle) {
        Account account;
        String accountCode = payDocTemplate.getAccountCode();
        String key = accountCode + "_" + settle.getBankAccountId();
        if (accountMap.containsKey(key)) {
            account = accountMap.get(key);
        } else {
            account = accountMap.get(accountCode);
        }
        return account;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public BusinessVoucher getVoucher() {
        return voucher;
    }

    public void setVoucher(BusinessVoucher receipt) {
        this.voucher = receipt;
    }

    public Map<String, BusinessTemplate> getTemplateMap() {
        return templateMap;
    }

    public void setTemplateMap(Map<String, BusinessTemplate> templateMap) {
        this.templateMap = templateMap;
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

    class InnerFiDocEntryDto {

        private DocEntry fiDocEntryDto;

        private String key;

        public DocEntry getFiDocEntryDto() {
            return fiDocEntryDto;
        }

        public void setFiDocEntryDto(DocEntry fiDocEntryDto) {
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
