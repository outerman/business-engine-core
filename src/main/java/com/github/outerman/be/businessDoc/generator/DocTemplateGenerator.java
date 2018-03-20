package com.github.outerman.be.businessDoc.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.outerman.be.FiDocGenetateResultDto;
import com.github.outerman.be.FiDocGenetateResultDto.ReceiptResult;
import com.github.outerman.be.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.businessDoc.template.BusinessTemplate;
import com.github.outerman.be.businessDoc.template.TemplateManager;
import com.github.outerman.be.contant.ErrorCode;
import com.github.outerman.be.model.AcmSortReceipt;
import com.github.outerman.be.model.AcmSortReceiptDetail;
import com.github.outerman.be.model.AcmSortReceiptSettlestyle;
import com.github.outerman.be.model.DocAccountTemplateItem;
import com.github.outerman.be.model.FiAccount;
import com.github.outerman.be.model.FiDocDto;
import com.github.outerman.be.model.PaymentTemplateItem;
import com.github.outerman.be.model.SetCurrency;
import com.github.outerman.be.model.SetOrg;
import com.github.outerman.be.util.StringUtil;

/**
 * Created by shenxy on 16/12/28. 生成凭证的工具类
 */
public class DocTemplateGenerator {

    private TemplateManager templateManager = TemplateManager.getInstance();

    private static DocTemplateGenerator instance;

    public static DocTemplateGenerator getInstance() {
        if (instance == null) {
            synchronized (DocTemplateGenerator.class) {
                if (instance == null) {
                    instance = new DocTemplateGenerator();
                }
            }
        }
        return instance;
    }

    public FiDocGenetateResultDto sortConvertVoucher(SetOrg org, List<AcmSortReceipt> voucherList, ITemplateProvider templateProvider) {
        if (voucherList == null || voucherList.isEmpty()) {
            throw ErrorCode.EXCEPTION_VOUCHER_EMPATY;
        }
        if (org == null || org.getId() == null || org.getId() == 0) {
            throw ErrorCode.EXCEPTION_ORG_EMPATY;
        }

        FiDocGenetateResultDto resultDto = new FiDocGenetateResultDto();
        validateVoucher(voucherList, resultDto);
        if (resultDto.getFailedReceipt().size() == voucherList.size()) {
            return resultDto;
        }

        Map<String, BusinessTemplate> templateMap = new HashMap<>();
        Set<String> accountCodeSet = new HashSet<>();
        List<AcmSortReceiptDetail> detailList = new ArrayList<>();
        for (AcmSortReceipt voucher : voucherList) {
            if (!voucher.getValid()) {
                continue;
            }
            detailList.addAll(voucher.getAcmSortReceiptDetailList());
            for (AcmSortReceiptDetail detail : voucher.getAcmSortReceiptDetailList()) {
                String businessCode = detail.getBusinessCode();
                if (templateMap.containsKey(businessCode)) {
                    continue;
                }
                BusinessTemplate businessTemplate = templateManager.fetchBusinessTemplate(org, businessCode, templateProvider);
                templateMap.put(businessCode, businessTemplate);
                accountCodeSet.addAll(businessTemplate.getDocAccountTemplate().getAccountCodeList());
                accountCodeSet.addAll(businessTemplate.getPaymentTemplate().getAccountCodeList());
            }
        }
        Map<String, FiAccount> accountMap = templateProvider.getAccountCode(org.getId(), new ArrayList<String>(accountCodeSet), detailList);

        SetCurrency currency = templateProvider.getBaseCurrency(org.getId());
        List<FiDocDto> docList = new ArrayList<>();
        for (AcmSortReceipt voucher : voucherList) {
            if (!voucher.getValid()) {
                continue;
            }
            FiDocHandler docHandler = new FiDocHandler(org, currency, voucher);
            docHandler.setTemplateMap(templateMap);
            docHandler.setAccountMap(accountMap);
            boolean result = convertVoucher(docHandler, resultDto);
            if (!result) {
                continue;
            }

            FiDocDto fiDocDto = docHandler.getDoc();
            docList.add(fiDocDto);
        }

        docList.forEach(docDto -> {
            if (docDto.getDocId() != null) {
                resultDto.getToUpdateFiDocList().add(docDto);
            } else {
                resultDto.getToInsertFiDocList().add(docDto);
            }
        });
        return resultDto;
    }

    /**
     * 校验单据信息，并将校验失败信息写入 {@code resultDto}，同时单据 {@code valid} 字段赋值 {@code false}
     * <p>单据 {@code valid} 字段赋值 {@code false}，用于之后判断单据信息的有效性，保留原始的 {@code voucherList} 内容以及顺序主要用于异常信息中的第%s条单据
     * @param voucherList
     * @param resultDto
     */
    private void validateVoucher(List<AcmSortReceipt> voucherList, FiDocGenetateResultDto resultDto) {
        List<ReceiptResult> failList = resultDto.getFailedReceipt();
        for (int index = 0, length = voucherList.size(); index < length; index++) {
            AcmSortReceipt voucher = voucherList.get(index);
            if (voucher == null) {
                voucher = new AcmSortReceipt();
                voucherList.set(index, voucher);
                voucher.setValid(false);
                ReceiptResult fail = new ReceiptResult();
                fail.setMsg(String.format(ErrorCode.VOUCHER_EMPTY, Integer.toString(index + 1)));
                failList.add(fail);
                continue;
            }
            List<AcmSortReceiptDetail> detailList = voucher.getAcmSortReceiptDetailList();
            if (detailList == null || detailList.isEmpty()) {
                voucher.setValid(false);
                ReceiptResult fail = new ReceiptResult();
                fail.setReceipt(voucher);
                fail.setMsg(String.format(ErrorCode.VOUCHER_DETAIL_EMPTY, Integer.toString(index + 1)));
                failList.add(fail);
                continue;
            }
            for (int detailIndex = 0, detailLength = detailList.size(); detailIndex < detailLength; detailIndex++) {
                AcmSortReceiptDetail detail = detailList.get(detailIndex);
                if (detail == null) {
                    voucher.setValid(false);
                    ReceiptResult fail = new ReceiptResult();
                    fail.setReceipt(voucher);
                    fail.setMsg(String.format(ErrorCode.VOUCHER_DETAIL_NULL, Integer.toString(index + 1),
                            Integer.toString(detailIndex + 1)));
                    failList.add(fail);
                    break;
                }
                String businessCode = detail.getBusinessCode();
                if (StringUtil.isEmpty(businessCode)) {
                    voucher.setValid(false);
                    ReceiptResult fail = new ReceiptResult();
                    fail.setReceipt(voucher);
                    fail.setMsg(String.format(ErrorCode.BUSINESS_CODE_EMPTY, Integer.toString(index + 1),
                            Integer.toString(detailIndex + 1)));
                    failList.add(fail);
                    break;
                }
            }
        }
    }

    private boolean convertVoucher(FiDocHandler docHandler, FiDocGenetateResultDto resultDto) {
        AcmSortReceipt voucher = docHandler.getVoucher();
        List<AcmSortReceiptDetail> detailList = reorderDetailList(voucher.getAcmSortReceiptDetailList());
        BusinessTemplate businessTemplate = null;
        Map<String, BusinessTemplate> templateMap = docHandler.getTemplateMap();
        // 业务明细转换凭证分录
        for (AcmSortReceiptDetail detail : detailList) {
            String businessCode = detail.getBusinessCode();
            businessTemplate = templateMap.get(businessCode);
            List<DocAccountTemplateItem> docTemplateList = businessTemplate.getDocAccountTemplate()
                    .getDocTemplate(docHandler.getOrg(), detail);
            if (docTemplateList.isEmpty()) {
                resultDto.addFailed(voucher, String.format(ErrorCode.DOC_TEMPLATE_EMPTY, businessCode));
                return false;
            }
            for (DocAccountTemplateItem docTemplate : docTemplateList) {
                FiAccount account = docHandler.getAccount(docTemplate, detail);
                if (account == null) {
                    resultDto.addFailed(voucher,
                            String.format(ErrorCode.ACCOUNT_CODE_INVALID, docTemplate.getAccountCode()));
                    return false;
                }
                docTemplate.setAccount(account);

                docHandler.addEntry(docTemplate, detail);
            }
        }

        FiDocDto fiDocDto = docHandler.getDoc();
        if (fiDocDto.getEntrys().isEmpty()) {
            // 所有明细处理之后，凭证分录为空：获取到的金额字段都是 0
            resultDto.addFailed(voucher, ErrorCode.ENTRY_EMPTY);
            return false;
        }

        List<AcmSortReceiptSettlestyle> settleList = voucher.getAcmSortReceiptSettlestyleList();
        if (settleList == null || settleList.isEmpty()) {
            return true;
        }

        // 结算明细转换凭证分录
        for (AcmSortReceiptSettlestyle settle : settleList) {
            if (settle == null) {
                continue;
            }

            PaymentTemplateItem payDocTemplate = businessTemplate.getPaymentTemplate().getTemplate(settle);
            if (payDocTemplate == null) {
                resultDto.addFailed(voucher, ErrorCode.SETTLE_TEMPLATE_EMPTY);
                return false;
            }
            FiAccount account = docHandler.getAccount(payDocTemplate, settle);
            if (account == null) {
                resultDto.addFailed(voucher,
                        String.format(ErrorCode.ACCOUNT_CODE_INVALID, payDocTemplate.getSubjectDefault()));
                return false;
            }
            payDocTemplate.setAccount(account);

            docHandler.addEntry(payDocTemplate, settle);
        }
        return true;
    }

    /**
     * 收支明细将后续出现的相同业务放在第一次出现位置之后
     * @param detailList
     * @return 重新排序后的收支明细
     */
    private List<AcmSortReceiptDetail> reorderDetailList(List<AcmSortReceiptDetail> detailList) {
        LinkedHashMap<Long, List<AcmSortReceiptDetail>> retMap = new LinkedHashMap<>();
        for (AcmSortReceiptDetail detail : detailList) {
            if (detail == null) {
                continue;
            }
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
        for (List<AcmSortReceiptDetail> details : retMap.values()) {
            ret.addAll(details);
        }
        return ret;
    }

}
