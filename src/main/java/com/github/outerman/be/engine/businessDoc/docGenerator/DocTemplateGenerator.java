package com.github.outerman.be.engine.businessDoc.docGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.constant.CommonConst;
import com.github.outerman.be.api.constant.ErrorCode;
import com.github.outerman.be.api.dto.FiDocGenetateResultDto;
import com.github.outerman.be.api.dto.FiDocGenetateResultDto.ReceiptResult;
import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.AcmSortReceiptDetail;
import com.github.outerman.be.api.vo.AcmSortReceiptSettlestyle;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.FiAccount;
import com.github.outerman.be.api.vo.FiDocDto;
import com.github.outerman.be.api.vo.PaymentTemplateItem;
import com.github.outerman.be.api.vo.SetCurrency;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import com.github.outerman.be.engine.businessDoc.businessTemplate.TemplateManager;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.util.StringUtil;

/**
 * Created by shenxy on 16/12/28. 生成凭证的工具类
 */
@Component
public class DocTemplateGenerator {

    @Autowired
    private TemplateManager templateManager;

    private ITemplateProvider templateProvider;

    public FiDocGenetateResultDto sortConvertVoucher(SetOrg org, List<AcmSortReceipt> receiptList, ITemplateProvider templateProvider) {
        this.templateProvider = templateProvider;
        if (receiptList == null || receiptList.isEmpty()) {
            throw ErrorCode.EXCEPTION_RECEIPT_EMPATY;
        }
        if (org == null || org.getId() == null || org.getId() == 0) {
            throw ErrorCode.EXCEPTION_ORG_EMPATY;
        }

        List<FiDocDto> fiDocList = new ArrayList<>();
        SetCurrency currency = templateProvider.getBaseCurrency(org.getId());
        FiDocGenetateResultDto resultDto = new FiDocGenetateResultDto();

        Map<String, BusinessTemplate> templateMap = new HashMap<>();
        Set<String> accountCodeSet = new HashSet<>();
        List<AcmSortReceiptDetail> detailList = new ArrayList<>();
        for (AcmSortReceipt receipt : receiptList) {
            detailList.addAll(receipt.getAcmSortReceiptDetailList());
            for (AcmSortReceiptDetail detail : receipt.getAcmSortReceiptDetailList()) {
                String businessCode = detail.getBusinessCode().toString();
                if (templateMap.containsKey(businessCode)) {
                    continue;
                }
                BusinessTemplate businessTemplate = templateManager.fetchBusinessTemplate(org, businessCode, templateProvider);
                templateMap.put(businessCode, businessTemplate);
                accountCodeSet.addAll(businessTemplate.getDocAccountTemplate().getDocTemplateDto().getCodeList());
                accountCodeSet.addAll(businessTemplate.getPaymentTemplate().getAccountCodeList());
            }
        }

        Map<String, FiAccount> accountMap = templateProvider.getAccountCode(org.getId(), new ArrayList<String>(accountCodeSet), detailList);
        for (AcmSortReceipt receipt : receiptList) {
            FiDocHandler docHandler = new FiDocHandler(org, currency, receipt);
            docHandler.setTemplateMap(templateMap);
            docHandler.setAccountMap(accountMap);
            boolean result = convertReceipt(docHandler, resultDto);
            if (!result) {
                continue;
            }

            FiDocDto fiDocDto = docHandler.getFiDocDto();
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

    private boolean convertReceipt(FiDocHandler docHandler, FiDocGenetateResultDto resultDto) {
        AcmSortReceipt receipt = docHandler.getReceipt();
        if (!validateReceipt(receipt, resultDto)) {
            return false;
        }

        SetOrg org = docHandler.getOrg();
        List<AcmSortReceiptDetail> detailList = reorderReceiptDetailList(receipt.getAcmSortReceiptDetailList());
        Map<String, BusinessTemplate> templateMap = docHandler.getTemplateMap();
        Map<String, FiAccount> accountMap = docHandler.getAccountMap();
        BusinessTemplate businessTemplate = null;
        for (AcmSortReceiptDetail detail : detailList) {
            String businessCode = detail.getBusinessCode().toString();
            if (!templateMap.containsKey(businessCode)) {
                continue;
            }
            businessTemplate = templateMap.get(businessCode);
            List<DocAccountTemplateItem> docTemplateList = businessTemplate.getDocAccountTemplate().getDocTemplate(org, detail);
            if (docTemplateList.isEmpty()) {
                resultDto.addFailed(receipt, String.format("业务类型 %s 凭证模板数据没有找到", businessCode.toString()));
                return false;
            }
            for (DocAccountTemplateItem docTemplate : docTemplateList) {
                List<ReceiptResult> failList = new ArrayList<>();
                docTemplate = templateProvider.requestAdvice(docTemplate, accountMap, detail, failList);
                if (!failList.isEmpty()) {
                    ReceiptResult fail = failList.get(0);
                    fail.setReceipt(receipt);
                    resultDto.addFailed(fail);
                    return false;
                }

                docHandler.addEntry(docTemplate, detail);
            }
        }

        FiDocDto fiDocDto = docHandler.getFiDocDto();
        if (fiDocDto.getEntrys().isEmpty()) {
            // 所有流水账明细处理之后，凭证分录为空：获取到的金额字段都是 0
            resultDto.addFailed(receipt, "凭证分录为空");
            return false;
        }

        List<AcmSortReceiptSettlestyle> settleList = receipt.getAcmSortReceiptSettlestyleList();
        if (settleList == null || settleList.isEmpty()) {
            return true;
        }

        // 结算方式转换分录
        for (AcmSortReceiptSettlestyle settle : settleList) {
            if (settle == null) {
                continue;
            }

            PaymentTemplateItem payDocTemplate = businessTemplate.getPaymentTemplate().getTemplate(settle);
            if (payDocTemplate == null) {
                resultDto.addFailed(receipt, ErrorCode.ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_MSG);
                return false;
            }
            // requestAdvice 中没有获取到结算模板数据时抛出了异常
            payDocTemplate = templateProvider.requestAdvice(payDocTemplate, accountMap, settle);

            docHandler.addEntry(payDocTemplate, settle);
        }
        return true;
    }

    private boolean validateReceipt(AcmSortReceipt receipt, FiDocGenetateResultDto resultDto) {
        List<ReceiptResult> failList = resultDto.getFailedReceipt();
        String errorMessage = null;
        if (receipt == null) {
            errorMessage = ErrorCode.ENGINE_DOC_GENERATE_RECEIPT_EMPTY;
        } else {
            List<AcmSortReceiptDetail> detailList = receipt.getAcmSortReceiptDetailList();
            if (detailList == null || detailList.isEmpty()) {
                errorMessage = ErrorCode.ENGINE_DOC_GENETARE_EMPTY_DETAIL_ERROR_MSG;
            }
            Long paymenysType = receipt.getPaymentsType();
            if (paymenysType != null && paymenysType == CommonConst.PAYMENTSTYPE_60) {
                // 请会计处理分类业务类型的流水账不生成凭证，直接审核
                errorMessage = ErrorCode.ENGINE_DOC_GENETARE_UNRESOVE_ERROR_MSG;
                failList = resultDto.getUnResolvedReceipt();
            }
        }
        if (!StringUtil.isEmpty(errorMessage)) {
            ReceiptResult fail = new ReceiptResult();
            fail.setReceipt(receipt);
            fail.setMsg(errorMessage);
            failList.add(fail);
            return false;
        }
        return true;
    }

    /**
     * 收支明细将后续出现的相同业务放在第一次出现位置之后
     * @param detailList
     * @return 重新排序后的收支明细
     */
    private List<AcmSortReceiptDetail> reorderReceiptDetailList(List<AcmSortReceiptDetail> detailList) {
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
        for (List<AcmSortReceiptDetail> acmSortReceiptDetails : retMap.values()) {
            ret.addAll(acmSortReceiptDetails);
        }
        return ret;
    }

}
