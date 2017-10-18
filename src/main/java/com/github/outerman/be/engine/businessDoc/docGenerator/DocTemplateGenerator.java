package com.github.outerman.be.engine.businessDoc.docGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.constant.BusinessTypeUtil;
import com.github.outerman.be.api.constant.ErrorCode;
import com.github.outerman.be.api.dto.FiDocGenetateResultDto;
import com.github.outerman.be.api.dto.FiDocGenetateResultDto.ReceiptResult;
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
import com.github.outerman.be.engine.businessDoc.BusinessUtil;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AmountGetter;
import com.github.outerman.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import com.github.outerman.be.engine.businessDoc.businessTemplate.TemplateManager;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.util.DoubleUtil;
import com.github.outerman.be.engine.util.StringUtil;

/**
 * Created by shenxy on 16/12/28. 生成凭证的工具类
 */
@Component
public class DocTemplateGenerator {

    @Autowired
    private TemplateManager templateManager;

    public FiDocGenetateResultDto sortConvertVoucher(SetOrg org, List<AcmSortReceipt> receiptList,
            ITemplateProvider templateProvider) {
        if (receiptList == null || receiptList.isEmpty()) {
            throw ErrorCode.EXCEPTION_RECEIPT_EMPATY;
        }
        if (org == null || org.getId() == null || org.getId() == 0) {
            throw ErrorCode.EXCEPTION_ORG_EMPATY;
        }

        List<FiDocDto> fiDocList = new ArrayList<>();
        SetCurrency currency = templateProvider.getBaseCurrency(org.getId());
        FiDocGenetateResultDto resultDto = new FiDocGenetateResultDto();

        // 进项税额转出相关分录模板
        Map<String, DocAccountTemplateItem> templateMap = new HashMap<>();
        for (AcmSortReceipt acmSortReceipt : receiptList) {
            if (!checkReceipt(acmSortReceipt, resultDto)) {
                continue;
            }

            FiDocDto fiDocDto = getDefaultDoc(acmSortReceipt);
            List<AcmSortReceiptDetail> detailList = reorderReceiptDetailList(
                    acmSortReceipt.getAcmSortReceiptDetailList());
            boolean hasError = false;
            for (int i = 0; i < detailList.size(); i++) {
                AcmSortReceiptDetail detail = detailList.get(i);
                if (detail == null) {
                    continue;
                }

                Long businessCode = detail.getBusinessCode();
                BusinessTemplate businessTemplate = templateManager.fetchBusinessTemplate(org, businessCode.toString(), templateProvider);
                List<DocAccountTemplateItem> docTemplateList = businessTemplate.getDocAccountTemplate().getDocTemplate(org, detail);
                if (docTemplateList.isEmpty()) {
                    FiDocGenetateResultDto.ReceiptResult fail = new FiDocGenetateResultDto.ReceiptResult();
                    fail.setReceipt(acmSortReceipt);
                    fail.setMsg(String.format("业务类型 %s 凭证模板数据没有找到", businessCode.toString()));
                    resultDto.getFailedReceipt().add(fail);
                    hasError = true;
                    break;
                }

                // TODO 科目编码处理，考虑缓存，考虑外部一次调用获取全部数据
                List<String> accountCodeList = new ArrayList<>();
                for (DocAccountTemplateItem docTemplate : docTemplateList) {
                    accountCodeList.add(docTemplate.getAccountCode());
                }
                Map<String, FiAccount> codeMap = templateProvider.getAccountCode(org.getId(), accountCodeList, detailList);
                for (DocAccountTemplateItem docTemplate : docTemplateList) {
                    List<ReceiptResult> fiDocReturnFailList = new ArrayList<>();
                    docTemplate = templateProvider.requestAdvice(docTemplate, codeMap, detail, fiDocReturnFailList);
                    if (!fiDocReturnFailList.isEmpty()) {
                        resultDto.getFailedReceipt().addAll(fiDocReturnFailList);
                        hasError = true;
                        break;
                    }

                    String summary = docTemplate.getSummary();
                    if ("进项税额转出".equals(summary)) {
                        templateMap.put(docTemplate.getAccountCode(), docTemplate);
                    }

                    Double amount = AmountGetter.getAmount(detail, docTemplate);
                    if (DoubleUtil.isNullOrZero(amount)) {
                        continue;
                    }

                    StringBuilder fuz = new StringBuilder();
                    // 建立辅助核算
                    Map<String, Object> auxInfo = new HashMap<>();
                    setAuxInfo(org.getVatTaxpayer(), currency, acmSortReceipt, detail, docTemplate, fuz, auxInfo);

                    Boolean isSort = true;
                    // 查看是否需要特殊排序: 所有"本表"平的业务,都特殊排序
                    // "工资-发放-实发工资"特殊处理, 虽然是"结算方式",但是和其他"本表"的业务,一起单独排序
                    if (docTemplate.getIsSettlement() != null && !docTemplate.getIsSettlement()
                            || BusinessTypeUtil.SPECIAL_ORDER.contains(docTemplate.getBusinessCode())) {
                        isSort = false;
                    }

                    String accountCode = fuz.insert(0, docTemplate.getAccountCode()).toString();
                    // "账户内"互转的三项业务, 固定不做合并
                    if (BusinessTypeUtil.NOT_MERGE_BUSINESS.contains(docTemplate.getBusinessCode())) {
                        accountCode += "_ran" + i;
                        businessCode = null;
                    }

                    if (!docTemplate.getDirection()) {// 借
                        fiDocDto.addEntryJie(accountCode, amount, (auxInfo.isEmpty() ? null : auxInfo), isSort, businessCode);
                    } else {// 贷
                        fiDocDto.addEntryDai(accountCode, amount, (auxInfo.isEmpty() ? null : auxInfo), isSort, businessCode);
                    }
                }
                if (hasError) {
                    break;
                }
            }
            if (hasError) { // 转换过程有错误
                continue;
            }
            if (fiDocDto.getEntrys().isEmpty()) {
                // 所有流水账明细处理之后，凭证分录为空：获取到的金额字段都是 0
                FiDocGenetateResultDto.ReceiptResult fail = new FiDocGenetateResultDto.ReceiptResult();
                fail.setReceipt(acmSortReceipt);
                fail.setMsg("凭证分录为空");
                resultDto.getFailedReceipt().add(fail);
                continue;
            }

            List<AcmSortReceiptSettlestyle> settleList = acmSortReceipt.getAcmSortReceiptSettlestyleList();
            if (settleList == null || settleList.isEmpty()) {
                if (acmSortReceipt.getDocId() != null) {
                    fiDocDto.setDocId(acmSortReceipt.getDocId());
                }
                fiDocList.add(fiDocDto);
                continue;
            }

            // TODO: 结算方式暂时没有和businessCode挂钩, 所以取任何一个都会返回所有
            BusinessTemplate businessTemplate = templateManager.fetchBusinessTemplate(org,
                    detailList.get(0).getBusinessCode().toString(), templateProvider);
            // TODO 科目编码处理
            List<String> accountCodeList = businessTemplate.getPaymentTemplate().getAccountCodeList();
            Map<String, FiAccount> codeMap = templateProvider.getAccountCode(org.getId(), accountCodeList, detailList);
            // 查询结算方式生成凭证
            for (int i = 0; i < settleList.size(); i++) {
                AcmSortReceiptSettlestyle settle = settleList.get(i);
                if (settle == null) {
                    continue;
                }

                PaymentTemplateItem payDocTemplate = businessTemplate.getPaymentTemplate().getTemplate(settle);
                if (payDocTemplate == null) {
                    FiDocGenetateResultDto.ReceiptResult fail = new FiDocGenetateResultDto.ReceiptResult();
                    fail.setReceipt(acmSortReceipt);
                    fail.setMsg(ErrorCode.ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_MSG);
                    resultDto.getFailedReceipt().add(fail);
                    hasError = true;
                    break;
                }

                payDocTemplate = templateProvider.requestAdvice(payDocTemplate, codeMap, settle);
                if (payDocTemplate == null) {
                    // getPayTemplateByAdvice 中已经处理错误信息
                    hasError = true;
                    break;
                }

                Double amount = 0D;
                if (AmountGetter.AMOUNT_TAXINCLUSIVEAMOUNT.equals(payDocTemplate.getFundSource())) {
                    amount = settle.getTaxInclusiveAmount();
                }
                if (DoubleUtil.isNullOrZero(amount)) {
                    continue;
                }

                String memo = getSettleSummary(settle, acmSortReceipt, payDocTemplate);
                Boolean direction = payDocTemplate.getDirection();
                // 处理分录借贷方向、正负金额是否需要颠倒
                if (payDocTemplate.getReversal() && amount < 0) {
                    direction = !direction;
                    amount*= -1;
                }
                // 建立辅助核算
                Map<String, Object> auxInfo = new HashMap<>();
                StringBuilder fuz = new StringBuilder();
                setSettlementAuxInfo(currency, settle, payDocTemplate, auxInfo, fuz, memo);

                // 取科目
                if (!direction) {// 借 07.3.31 增加 _ran 结算科目不合并
                    fiDocDto.addEntryJie(fuz.insert(0, payDocTemplate.getSubjectDefault() + "_ran" + i).toString(), amount, auxInfo, true, -1L);
                } else {// 贷
                    fiDocDto.addEntryDai(fuz.insert(0, payDocTemplate.getSubjectDefault() + "_ran" + i).toString(), amount, auxInfo, true, -1L);
                }
            }
            if (hasError) { // 转换过程有错误
                continue;
            }

            // 正负混录的流水账收支明细，分录合并之后分录金额可能为 0 ，需要去掉金额为 0 的分录
            // 进项税额转出分录提出来放在最后，按科目进行合并
            List<FiDocEntryDto> zeroAmountList = new ArrayList<>();
            List<FiDocEntryDto> list = new ArrayList<>();
            Map<String, FiDocEntryDto> map = new LinkedHashMap<>();
            for (FiDocEntryDto docEntry : fiDocDto.getEntrys()) {
                if (DoubleUtil.isNullOrZero(docEntry.getAmountDr())
                        && DoubleUtil.isNullOrZero(docEntry.getAmountCr())) {
                    zeroAmountList.add(docEntry);
                } else {
                    String summary = docEntry.getSummary();
                    if ("进项税额转出".equals(summary)) {
                        FiDocEntryDto entry;
                        String accountCode = docEntry.getAccountCode();
                        DocAccountTemplateItem template;
                        if (templateMap.containsKey(accountCode)) {
                            template = templateMap.get(accountCode);
                        } else {
                            // 正常不应该有这种情况
                            template = new DocAccountTemplateItem();
                        }
                        StringBuilder entryKey = new StringBuilder(accountCode);
                        if (template.getIsAuxAccCalc() != null && template.getIsAuxAccCalc()) {
                            if (template.getIsAuxAccBankAccount() != null && template.getIsAuxAccBankAccount()) {
                                entryKey.append("_" + docEntry.getAccountId());
                            }
                            if (template.getIsAuxAccCustomer() != null && template.getIsAuxAccCustomer()) {
                                entryKey.append("_" + docEntry.getCustomerId());
                            }
                            if (template.getIsAuxAccDepartment() != null && template.getIsAuxAccDepartment()) {
                                entryKey.append("_" + docEntry.getDepartmentId());
                            }
                            // if (template.getIsAuxAccInputTax() != null &&
                            // template.getIsAuxAccInputTax()) {
                            // entryKey.append("_" + docEntry.getInputTaxId());
                            // }
                            if (template.getIsAuxAccInventory() != null && template.getIsAuxAccInventory()) {
                                entryKey.append("_" + docEntry.getInventoryId());
                            }
                            if (template.getIsAuxAccLevyAndRetreat() != null && template.getIsAuxAccLevyAndRetreat()) {
                                entryKey.append("_" + docEntry.getLevyAndRetreatId());
                            }
                            if (template.getIsAuxAccPerson() != null && template.getIsAuxAccPerson()) {
                                entryKey.append("_" + docEntry.getPersonId());
                            }
                            if (template.getIsAuxAccProject() != null && template.getIsAuxAccProject()) {
                                entryKey.append("_" + docEntry.getProjectId());
                            }
                            if (template.getIsAuxAccSupplier() != null && template.getIsAuxAccSupplier()) {
                                entryKey.append("_" + docEntry.getSupplierId());
                            }
                        }
                        String entryKeyStr = entryKey.toString();
                        if (map.containsKey(entryKeyStr)) {
                            entry = map.get(entryKeyStr);
                            entry.setAmountCr(DoubleUtil.add(entry.getAmountCr(), docEntry.getAmountCr()));
                            entry.setAmountDr(DoubleUtil.add(entry.getAmountDr(), docEntry.getAmountDr()));
                            entry.setOrigAmountCr(DoubleUtil.add(entry.getOrigAmountCr(), docEntry.getOrigAmountCr()));
                            entry.setOrigAmountDr(DoubleUtil.add(entry.getOrigAmountDr(), docEntry.getOrigAmountDr()));
                        } else {
                            entry = docEntry;
                            map.put(entryKeyStr, entry);
                        }
                        list.add(docEntry);
                    }
                }
            }
            if (!zeroAmountList.isEmpty()) {
                fiDocDto.getEntrys().removeAll(zeroAmountList);
            }
            if (!list.isEmpty()) {
                fiDocDto.getEntrys().removeAll(list);
                fiDocDto.getEntrys().addAll(map.values());
            }
            if (acmSortReceipt.getDocId() != null) {
                fiDocDto.setDocId(acmSortReceipt.getDocId());
            }
            fiDocList.add(fiDocDto);
        }

        fiDocList.forEach(docDto -> {
            if (docDto.getDocId() != null) {
                resultDto.getToUpdateFiDocList().add(docDto);
            } else {
                resultDto.getToInsertFiDocList().add(docDto);
            }
        });
        return resultDto;
    }

    private boolean checkReceipt(AcmSortReceipt receipt, FiDocGenetateResultDto resultDto) {
        if (receipt == null) {
            FiDocGenetateResultDto.ReceiptResult fail = new FiDocGenetateResultDto.ReceiptResult();
            fail.setReceipt(receipt);
            fail.setMsg(ErrorCode.ENGINE_DOC_GENERATE_RECEIPT_EMPTY);
            resultDto.getFailedReceipt().add(fail);
            return false;
        }
        List<AcmSortReceiptDetail> detailList = receipt.getAcmSortReceiptDetailList();
        if (detailList == null || detailList.isEmpty()) {
            FiDocGenetateResultDto.ReceiptResult fail = new FiDocGenetateResultDto.ReceiptResult();
            fail.setReceipt(receipt);
            fail.setMsg(ErrorCode.ENGINE_DOC_GENETARE_EMPTY_DETAIL_ERROR_MSG);
            resultDto.getFailedReceipt().add(fail);
            return false;
        }
        Long paymenysType = receipt.getPaymentsType();
        if (paymenysType != null && paymenysType == AcmConst.PAYMENTSTYPE_60) {
            // 请会计处理分类业务类型的流水账不生成凭证，直接审核
            FiDocGenetateResultDto.ReceiptResult receiptResult = new FiDocGenetateResultDto.ReceiptResult();
            receiptResult.setReceipt(receipt);
            receiptResult.setMsg(ErrorCode.ENGINE_DOC_GENETARE_UNRESOVE_ERROR_MSG);
            resultDto.getUnResolvedReceipt().add(receiptResult);
            return false;
        }
        return true;
    }

    /**
     * 根据流水账信息获取默认的凭证 dto
     * 
     * @param receipt
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
        return doc;
    }

    /**
     * 将后续出现的相同业务放在第一次出现位置之后
     * 
     * @param detailList
     * @return
     */
    private List<AcmSortReceiptDetail> reorderReceiptDetailList(List<AcmSortReceiptDetail> detailList) {
        LinkedHashMap<Long, List<AcmSortReceiptDetail>> retMap = new LinkedHashMap<>();
        for (AcmSortReceiptDetail detail : detailList) {
            Long businessType = detail.getBusinessType();
            if (retMap.containsKey(businessType)) {
                retMap.get(businessType).add(detail);
            } else {
                List<AcmSortReceiptDetail> list = new ArrayList<>();
                list.add(detail);
                retMap.put(businessType, list);
            }
        }

        List<AcmSortReceiptDetail> ret = new ArrayList<>();
        for (List<AcmSortReceiptDetail> acmSortReceiptDetails : retMap.values()) {
            ret.addAll(acmSortReceiptDetails);
        }
        return ret;
    }

    private void setAuxInfo(Long vatTaxpayer, SetCurrency currency, AcmSortReceipt acmSortReceipt,
            AcmSortReceiptDetail detail, DocAccountTemplateItem docTemplate, StringBuilder fuz,
            Map<String, Object> auxInfo) {
        if (docTemplate.getIsAuxAccCalc() != null && docTemplate.getIsAuxAccCalc()) {
            if (docTemplate.getIsAuxAccDepartment() != null && docTemplate.getIsAuxAccDepartment()) {// 部门
                if (detail.getDepartment() != null) {
                    auxInfo.put("department", detail.getDepartment());
                    fuz.append("_bm").append(detail.getDepartment());
                }
            }
            if (docTemplate.getIsAuxAccPerson() != null && docTemplate.getIsAuxAccPerson()) {// 人员
                if (detail.getEmployee() != null) {
                    auxInfo.put("employee", detail.getEmployee());
                    fuz.append("_yy").append(detail.getEmployee());
                }
            }
            if (docTemplate.getIsAuxAccCustomer()) {// 客户
                if (detail.getConsumer() != null) {
                    auxInfo.put("consumer", detail.getConsumer());
                    fuz.append("_kh").append(detail.getConsumer());
                }
            }
            if (docTemplate.getIsAuxAccSupplier()) {// 供应商
                if (detail.getVendor() != null) {
                    auxInfo.put("vendor", detail.getVendor());
                    fuz.append("_gys").append(detail.getVendor());
                }
            }
            if (docTemplate.getIsAuxAccInventory()) {// 存货
                if (detail.getInventory() != null) {
                    auxInfo.put("inventory", detail.getInventory());
                    fuz.append("_ch").append(detail.getInventory());
                }
                if (detail.getAssetId() != null) {
                    auxInfo.put("inventory", detail.getAssetId());
                    fuz.append("_ch").append(detail.getAssetId());
                }
            }
            if (docTemplate.getIsAuxAccProject()) {// 项目
                if (detail.getProject() != null) {
                    auxInfo.put("project", detail.getProject());
                    fuz.append("_xm").append(detail.getProject());
                }
            }
            if (docTemplate.getIsQuantityCalc()) {// 数量
                if (detail.getCommodifyNum() != null) {
                    auxInfo.put("commodifyNum", detail.getCommodifyNum());
                    fuz.append("_sl").append(detail.getCommodifyNum());
                }
            }
            // if(fiBillDocTemplate.getIsAuxAccBankAccount()){//银行账号
            // auxInfo.put("bank",
            // acmSortReceiptDetail.getBankAccountId());
            // fuz.append(acmSortReceiptDetail.getBankAccountId());
            // }

            // 银行账号
            if ("402000".equals(docTemplate.getBusinessCode().toString()) && "A".equals(docTemplate.getFlag())) {
                fuz.append("_yhzh").append(detail.getInBankAccountId());
                if (detail.getInBankAccountId() != null) {
                    auxInfo.put("bank", detail.getInBankAccountId());
                }
            } else {
                fuz.append("_yhzh").append(detail.getBankAccountId());
                if (detail.getBankAccountId() != null) {
                    auxInfo.put("bank", detail.getBankAccountId());
                }
            }

        }
        if (acmSortReceipt.getSourceVoucherTypeId() != null
                && !BusinessTypeUtil.GONGZI_VOUCHERTYPE_LIST.contains(acmSortReceipt.getSourceVoucherTypeId())) {
            // 科目+辅助核算+业务类型+部门属性+人员属性+借款期限+账户属性流入+账户属性流出+纳税人+资产类别+罚款性质
            if (docTemplate.getInfluence() != null) {// 是否有影响因素

                switch (docTemplate.getInfluence()) {
                case "departmentAttr": {
                    // 部门属性
                    if (detail.getDepartmentProperty() != null) {
                        fuz.append("_bmsx").append(detail.getDepartmentProperty());
                    }
                }
                    break;
                case "departmentAttr,personAttr": {
                    // 部门属性
                    if (detail.getDepartmentProperty() != null) {
                        fuz.append("_bmsx").append(detail.getDepartmentProperty());
                    }
                    // 人员属性
                    if (detail.getEmployeeAttribute() != null) {
                        fuz.append("_rysx").append(detail.getEmployeeAttribute());
                    }
                }
                    break;
                case "vatTaxpayer": {
                    // 纳税人性质
                    if (vatTaxpayer != null) {
                        fuz.append("_nsr").append(vatTaxpayer);
                    }
                }
                    break;
                case "punishmentAttr": {// 罚款性质
                    if (detail.getPenaltyType() != null) {
                        fuz.append("_fkxz").append(detail.getPenaltyType());
                    }
                }
                    break;
                case "borrowAttr": {// 借款期限
                    // 借款期限
                    if (detail.getLoanTerm() != null) {
                        fuz.append("_jkqx").append(detail.getLoanTerm());
                    }
                }
                    break;
                case "commodityAttr": {// 商品或服务名称
                }
                    break;
                case "assetAttr": {// 资产类别
                    if (detail.getAssetAttr() != null) {
                        fuz.append("_zclb").append(detail.getAssetAttr());
                    }
                }
                    break;
                case "accountInAttr": {// 账户属性
                    // 账户流入
                    if (detail.getInBankAccountId() != null) {
                        fuz.append("_zhlr").append(detail.getInBankAccountId());
                    }
                }
                    break;
                case "accountOutAttr": {// 账户属性
                    // 账户流入
                    if (detail.getBankAccountId() != null) {
                        fuz.append("_zhlc").append(detail.getBankAccountId());
                    }
                }
                    break;

                default:
                    break;
                }

            }

        }

        // 业务类型+ 712 zbs 不管需不需要业务类型都给凭证传递过去
        fuz.append("_ywlx").append(detail.getBusinessType());
        if (detail.getBusinessType() != null) {
            auxInfo.put("businessType", detail.getBusinessType());
        }

        // 单价
        if (BusinessUtil.paymentDirection(detail.getBusinessCode()) == 1
                || (detail.getIsDeduction() != null && detail.getIsDeduction() == 1)) {// 是看是否抵扣然后传递不同的单价
            if (detail.getPrice() != null) {
                auxInfo.put("price", detail.getPrice());
            }
        } else {
            if (detail.getTaxInclusivePrice() != null) {
                auxInfo.put("price", detail.getTaxInclusivePrice());
            }
        }

        // 摘要
        String summary;
        if (!StringUtil.isEmpty(docTemplate.getSummary())) {
            summary = docTemplate.getSummary();
        } else {
            summary = detail.getMemo();
        }
        fuz.append("_zy").append(summary);
        if (summary != null) {
            auxInfo.put("memo", summary);
        }

        // 科目id
        if (docTemplate.getAccountId() != null) {
            auxInfo.put("accountId", docTemplate.getAccountId());
        }
        // 科目code
        if (docTemplate.getAccountCode() != null) {
            auxInfo.put("accountCode", docTemplate.getAccountCode());
        }
        // 科目flag
        if (docTemplate.getFlag() != null) {
            auxInfo.put("flag", docTemplate.getFlag());
        }
        if (docTemplate.getIsMultiCalc()) {// 多币种
            if (currency.getId() != null) {
                auxInfo.put("currency", currency.getId());
                fuz.append("_dbz").append(currency.getId());
            }
        }
        // 即征即退，影响合并
        if (docTemplate.getIsAuxAccLevyAndRetreat() != null && docTemplate.getIsAuxAccLevyAndRetreat()) {
            if (detail.getDrawbackPolicy() != null) {
                auxInfo.put("drawbackPolicy", detail.getDrawbackPolicy());
                fuz.append("_drawbackPolicy").append(detail.getDrawbackPolicy());
            }
        }
    }

    /**
     * 获取结算情况明细对应的分录摘要
     * 
     * @param settleDetail
     * @param receipt
     * @param acmPayDocTemplate
     * @return
     */
    private String getSettleSummary(AcmSortReceiptSettlestyle settleDetail, AcmSortReceipt receipt,
            PaymentTemplateItem acmPayDocTemplate) {
        String summary = settleDetail.getMemo();
        if (!StringUtil.isEmpty(summary)) {
            return summary;
        }
        if (receipt.getAcmSortReceiptSettlestyleList().size() != 1) {
            summary = acmPayDocTemplate.getSubjectType();
        } else {
            summary = receipt.getAcmSortReceiptDetailList().get(0).getMemo();
        }
        return summary;
    }

    private void setSettlementAuxInfo(SetCurrency currency, AcmSortReceiptSettlestyle sett,
            PaymentTemplateItem acmPayDocTemplate, Map<String, Object> auxInfo, StringBuilder fuz, String mome) {
        if (acmPayDocTemplate.getIsAuxAccCalc() != null && acmPayDocTemplate.getIsAuxAccCalc()) {
            if (acmPayDocTemplate.getIsAuxAccPerson()) {// 人员
                if (sett.getEmployee() != null && sett.getEmployee() != 0) {
                    auxInfo.put("employee", sett.getEmployee());
                    fuz.append("_yy").append(sett.getEmployee());
                }
            }
            if (acmPayDocTemplate.getIsAuxAccCustomer()) {// 客户
                if (sett.getConsumer() != null) {
                    auxInfo.put("consumer", sett.getConsumer());
                    fuz.append("_kh").append(sett.getConsumer());
                }
            }
            if (acmPayDocTemplate.getIsAuxAccSupplier()) {// 供应商
                if (sett.getVendor() != null) {
                    auxInfo.put("vendor", sett.getVendor());
                    fuz.append("_gys").append(sett.getVendor());

                }
            }
            if (acmPayDocTemplate.getIsAuxAccBankAccount()) {// 银行账号
                if (sett.getBankAccountId() != null) {
                    auxInfo.put("bank", sett.getBankAccountId());
                    fuz.append("_yhzh").append(sett.getBankAccountId());

                }
            }
        }
        if (acmPayDocTemplate.getIsMultiCalc() != null) {// 多币种
            if (currency.getId() != null) {
                auxInfo.put("currency", currency.getId());
                fuz.append("_dbz").append(currency.getId());
            }
        }

        // 摘要
        fuz.append("_zy").append(mome);
        if (!StringUtil.isEmpty(mome)) {
            auxInfo.put("memo", mome);
        } else {
            if (acmPayDocTemplate.getSubjectType() != null) {
                auxInfo.put("memo", acmPayDocTemplate.getSubjectType());
            }
        }
        // 科目id
        if (acmPayDocTemplate.getAccountId() != null) {
            auxInfo.put("accountId", acmPayDocTemplate.getAccountId());
        }
        // 科目code
        if (acmPayDocTemplate.getSubjectDefault() != null) {
            auxInfo.put("accountCode", acmPayDocTemplate.getSubjectDefault());
        }
    }

}
