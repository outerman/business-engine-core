package com.github.outerman.be.engine.businessDoc.docGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.constant.AcmConst;
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

        for (AcmSortReceipt receipt : receiptList) {
            FiDocHandler docHandler = new FiDocHandler(org, currency, receipt);
            ReceiptResult result = convertReceipt(docHandler);
            if (result != null) {
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


    private ReceiptResult convertReceipt(FiDocHandler docHandler) {
        AcmSortReceipt receipt = docHandler.getReceipt();
        ReceiptResult fail = checkReceipt(receipt);
        if (fail != null) {
            return fail;
        }

        SetOrg org = docHandler.getOrg();
        List<AcmSortReceiptDetail> detailList = reorderReceiptDetailList(receipt.getAcmSortReceiptDetailList());
        for (AcmSortReceiptDetail detail : detailList) {
            if (detail == null) {
                continue;
            }

            String businessCode = detail.getBusinessCode().toString();
            BusinessTemplate businessTemplate = templateManager.fetchBusinessTemplate(org, businessCode, templateProvider);
            List<DocAccountTemplateItem> docTemplateList = businessTemplate.getDocAccountTemplate().getDocTemplate(org, detail);
            if (docTemplateList.isEmpty()) {
                fail = new ReceiptResult();
                fail.setReceipt(receipt);
                fail.setMsg(String.format("业务类型 %s 凭证模板数据没有找到", businessCode.toString()));
                return fail;
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
                    return fiDocReturnFailList.get(0);
                }

                docHandler.addEntry(docTemplate, detail);
            }
        }

        FiDocDto fiDocDto = docHandler.getFiDocDto();
        if (fiDocDto.getEntrys().isEmpty()) {
            // 所有流水账明细处理之后，凭证分录为空：获取到的金额字段都是 0
            fail = new ReceiptResult();
            fail.setReceipt(receipt);
            fail.setMsg("凭证分录为空");
            return fail;
        }

        List<AcmSortReceiptSettlestyle> settleList = receipt.getAcmSortReceiptSettlestyleList();
        if (settleList == null || settleList.isEmpty()) {
            return null;
        }

        // TODO: 结算方式暂时没有和businessCode挂钩, 所以取任何一个都会返回所有
        BusinessTemplate businessTemplate = templateManager.fetchBusinessTemplate(org, detailList.get(0).getBusinessCode().toString(), templateProvider);
        // TODO 科目编码处理
        List<String> accountCodeList = businessTemplate.getPaymentTemplate().getAccountCodeList();
        Map<String, FiAccount> codeMap = templateProvider.getAccountCode(org.getId(), accountCodeList, detailList);
        // 结算方式转换分录
        for (AcmSortReceiptSettlestyle settle : settleList) {
            if (settle == null) {
                continue;
            }

            PaymentTemplateItem payDocTemplate = businessTemplate.getPaymentTemplate().getTemplate(settle);
            if (payDocTemplate == null) {
                fail = new ReceiptResult();
                fail.setReceipt(receipt);
                fail.setMsg(ErrorCode.ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_MSG);
                return fail;
            }
            // requestAdvice 中没有获取到结算模板数据时抛出了异常
            payDocTemplate = templateProvider.requestAdvice(payDocTemplate, codeMap, settle);

            docHandler.addEntry(payDocTemplate, settle);
        }
        return null;
    }

    private ReceiptResult checkReceipt(AcmSortReceipt receipt) {
        String errorMessage = null;
        if (receipt == null) {
            errorMessage = ErrorCode.ENGINE_DOC_GENERATE_RECEIPT_EMPTY;
        } else {
            List<AcmSortReceiptDetail> detailList = receipt.getAcmSortReceiptDetailList();
            if (detailList == null || detailList.isEmpty()) {
                errorMessage = ErrorCode.ENGINE_DOC_GENETARE_EMPTY_DETAIL_ERROR_MSG;
            }
            Long paymenysType = receipt.getPaymentsType();
            if (paymenysType != null && paymenysType == AcmConst.PAYMENTSTYPE_60) {
                // 请会计处理分类业务类型的流水账不生成凭证，直接审核
                errorMessage = ErrorCode.ENGINE_DOC_GENETARE_UNRESOVE_ERROR_MSG;
            }
        }
        if (StringUtil.isEmpty(errorMessage)) {
            return null;
        }
        ReceiptResult fail = new ReceiptResult();
        fail.setReceipt(receipt);
        fail.setMsg(errorMessage);
        return fail;
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

}
