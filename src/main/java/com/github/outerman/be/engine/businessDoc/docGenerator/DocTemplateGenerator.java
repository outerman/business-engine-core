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

/**
 * Created by shenxy on 16/12/28. 生成凭证的工具类
 */
@Component
public class DocTemplateGenerator {

    @Autowired
    private TemplateManager templateManager;

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
                accountCodeSet.addAll(businessTemplate.getDocAccountTemplate().getDocTemplateDto().getCodeList());
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

            FiDocDto fiDocDto = docHandler.getFiDocDto();
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
                voucher.setValid(false);
                ReceiptResult fail = new ReceiptResult();
                fail.setReceipt(voucher);
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
        }
    }

    private boolean convertVoucher(FiDocHandler docHandler, FiDocGenetateResultDto resultDto) {
        AcmSortReceipt voucher = docHandler.getReceipt();
        List<AcmSortReceiptDetail> detailList = reorderDetailList(voucher.getAcmSortReceiptDetailList());
        Map<String, BusinessTemplate> templateMap = docHandler.getTemplateMap();
        BusinessTemplate businessTemplate = null;
        for (AcmSortReceiptDetail detail : detailList) {
            String businessCode = detail.getBusinessCode();
            businessTemplate = templateMap.get(businessCode);
            List<DocAccountTemplateItem> docTemplateList = businessTemplate.getDocAccountTemplate().getDocTemplate(docHandler.getOrg(), detail);
            if (docTemplateList.isEmpty()) {
                resultDto.addFailed(voucher, String.format("业务类型 %s 凭证模板数据没有找到", businessCode));
                return false;
            }
            for (DocAccountTemplateItem docTemplate : docTemplateList) {
                FiAccount account = docHandler.getAccount(docTemplate, detail);
                if (account == null) {
                    resultDto.addFailed(voucher, docTemplate.getAccountCode() + "科目没有查询到，请联系管理员！");
                    return false;
                }
                docTemplate.setAccount(account);

                docHandler.addEntry(docTemplate, detail);
            }
        }

        FiDocDto fiDocDto = docHandler.getFiDocDto();
        if (fiDocDto.getEntrys().isEmpty()) {
            // 所有流水账明细处理之后，凭证分录为空：获取到的金额字段都是 0
            resultDto.addFailed(voucher, "凭证分录为空");
            return false;
        }

        List<AcmSortReceiptSettlestyle> settleList = voucher.getAcmSortReceiptSettlestyleList();
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
                resultDto.addFailed(voucher, ErrorCode.ENGINE_DOC_GENETARE_EMPTY_PAY_ERROR_MSG);
                return false;
            }
            FiAccount account = docHandler.getAccount(payDocTemplate, settle);
            if (account == null) {
                resultDto.addFailed(voucher, payDocTemplate.getSubjectDefault() + "科目没有查询到，请联系管理员！");
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
