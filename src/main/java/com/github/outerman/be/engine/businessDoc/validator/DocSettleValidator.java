package com.github.outerman.be.engine.businessDoc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.AcmSortReceiptSettlestyle;
import com.github.outerman.be.api.vo.FiDocDto;
import com.github.outerman.be.api.vo.FiDocEntryDto;
import com.github.outerman.be.api.vo.PaymentTemplateItem;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import com.github.outerman.be.engine.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shenxy on 19/7/17.
 *
 * 验证凭证分录, 结算方式有关的分录
 */
@Component
public class DocSettleValidator implements IBusinessDocValidatable {

    @Autowired
    public DocSettleValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(SetOrg setOrg, BusinessTemplate businessTemplate, AcmSortReceipt acmSortReceipt, FiDocDto doc) {
        // 根据凭证分录（没有分类标识、科目、金额）反查对应的结算凭证模板数据
        return doc.getEntrys()
                .stream()
                .filter(entry -> entry.getSourceFlag() == null)
                .map(entry -> validateEntry(entry, businessTemplate, acmSortReceipt, doc))
                .filter(str -> !StringUtil.isEmpty(str))
                .collect(Collectors.joining(";"));
    }

    private String validateEntry(FiDocEntryDto entry, BusinessTemplate businessTemplate, AcmSortReceipt acmSortReceipt, FiDocDto doc) {
        String message = "流水账 " + acmSortReceipt.getCode() + " 结算情况生成的凭证 " + doc.getCode() + "结算分录";

        // 根据凭证分录科目和金额，查找结算凭证模板数据
        // TODO:假设前提,模拟数据里只有一个结算方式
        AcmSortReceiptSettlestyle settlestyle = acmSortReceipt.getAcmSortReceiptSettlestyleList().get(0);
        String accountCode = entry.getAccountCode();
        Double amount = entry.getAmountDr() == null ? entry.getAmountCr() : entry.getAmountDr();
        Map<String, PaymentTemplateItem> payTemplateMap = businessTemplate.getPaymentTemplate().getPaymentTemplateDto().getSettleTemplateMap();
        List<PaymentTemplateItem> docTemplateWithAccountList = new ArrayList<>();
        for (PaymentTemplateItem payTemplate : payTemplateMap.values()) {
            if (!accountCode.startsWith(payTemplate.getSubjectDefault())) {
                continue;
            }
            if (!settlestyle.getTaxInclusiveAmount().equals(Math.abs(amount))) {
                continue;
            }
            docTemplateWithAccountList.add(payTemplate);
        }
        if (docTemplateWithAccountList.isEmpty()) {
            return message + "会计科目、金额和结算凭证模板不一致；";
        }

        // 根据业务单据结算数据和结算凭证模板 accountType、paymentsType，查找结算凭证模板数据
        List<PaymentTemplateItem> resultList = new ArrayList<>();
        for (PaymentTemplateItem payTemplate : docTemplateWithAccountList) {
            String accountType = payTemplate.getAccountType().toString();
            Long paymentsType = payTemplate.getPaymentsType();
            if (accountType.equals(settlestyle.getBankAccountAttr()) && paymentsType.equals(settlestyle.getBusinessPropertyId())) {
                resultList.add(payTemplate);
            }
        }
        if (resultList.size() != 1) {
            return message + "没有找到或者找到多个一致的结算凭证模板数据；";
        }
        return "";
    }
}
