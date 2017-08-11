package com.github.outerman.be.engine.businessDoc.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.AcmSortReceiptDetail;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.FiDocDto;
import com.github.outerman.be.api.vo.FiDocEntryDto;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AmountGetter;
import com.github.outerman.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import com.github.outerman.be.engine.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shenxy on 19/7/17.
 *
 * 验证凭证分录, 非结算的"A"\"B"类的分录
 */
@Component
public class DocEntryValidator implements IBusinessDocValidatable {

    @Autowired
    public DocEntryValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(SetOrg setOrg, BusinessTemplate businessTemplate, AcmSortReceipt acmSortReceipt, FiDocDto doc) {
        // 根据凭证分录（分类标识、科目、金额）、流水账（影响因素）反查对应的凭证模板数据
        String businessCode = "业务类型 " + businessTemplate.getBusinessCode();
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = businessTemplate.getDocAccountTemplate().getDocTemplateMap(setOrg);
        if (docTemplateMap.isEmpty()) {
            return businessCode + " 没有找到凭证模板数据；";
        }

        // 遍历检查, 带有"A"\"B"的来源标记的分录
        return doc.getEntrys()
                .stream()
                .filter(entry -> entry.getSourceFlag() != null)
                .map(entry -> validateEntry(docTemplateMap, entry, setOrg, businessTemplate, acmSortReceipt, doc))
                .filter(str -> !StringUtil.isEmpty(str))
                .collect(Collectors.joining(";"));
    }

    private String validateEntry(Map<String, List<DocAccountTemplateItem>> docTemplateMap, FiDocEntryDto entry, SetOrg setOrg, BusinessTemplate businessTemplate, AcmSortReceipt acmSortReceipt, FiDocDto doc) {
        String flag = entry.getSourceFlag();
        String message = "流水账 " + acmSortReceipt.getCode() + " 收支明细生成的凭证 " + doc.getCode() + " 标识" + flag + "分录";

        // 3.1) 根据凭证分录分类标记，查找凭证模板数据
        if (!docTemplateMap.containsKey(flag)) {
            return message + entry.getAccountName() + "没有找到凭证模板数据；";
        }
        List<DocAccountTemplateItem> docTemplateList = docTemplateMap.get(flag);
        docTemplateMap.remove(flag); // 检查过了就移走

        // 3.2) 根据凭证分录科目和金额，查找凭证模板数据
        // TODO 验证假设前提是,每个流水账只有一个明细
        AcmSortReceiptDetail detail = acmSortReceipt.getAcmSortReceiptDetailList().get(0);
        String accountCode = entry.getAccountCode();
        Double amountFromDoc = entry.getAmountDr() != null ? entry.getAmountDr() : -entry.getAmountCr();
        List<DocAccountTemplateItem> docTemplateWithAccountList = new ArrayList<>();
        for (DocAccountTemplateItem docTemplate : docTemplateList) {
            if (!accountCode.equals(docTemplate.getAccountCode())) {
                continue;
            }
            Double amountFromReceipt = AmountGetter.getAmount(detail, docTemplate);
            if (Math.abs(amountFromReceipt) != Math.abs(amountFromDoc)) {
                continue;
            }
            docTemplateWithAccountList.add(docTemplate);
        }
        if (docTemplateWithAccountList.isEmpty()) {
            return message + entry.getAccountName() + "会计科目、金额和凭证模板不一致；";
        }

        // 3.3) 根据业务单据数据和凭证模板影响因素以及取值，查找凭证模板数据
        List<DocAccountTemplateItem> resultList = new ArrayList<>();
        for (DocAccountTemplateItem docTemplate : docTemplateWithAccountList) {
            String influence = docTemplate.getInfluence();
            if (StringUtil.isEmpty(influence)) {
                resultList.add(docTemplate);
                continue;
            }
            if (influence.contains("vatTaxpayer")) {
                if (!setOrg.getVatTaxpayer().equals(docTemplate.getVatTaxpayer())) {
                    continue;
                }
                if ("vatTaxpayer".equals(influence)) {
                    resultList.add(docTemplate);
                } else if ("vatTaxpayer,taxType".equals(influence)) {
                    Boolean taxType = docTemplate.getTaxType();
                    if (taxType == null) {
                        taxType = false;
                    }
                    Long taxRateId = detail.getTaxRateId();
                    if (taxType && businessTemplate.getDocAccountTemplate().isGeneral(taxRateId)) {
                        resultList.add(docTemplate);
                    } else if (!taxType && businessTemplate.getDocAccountTemplate().isSimple(taxRateId)) {
                        resultList.add(docTemplate);
                    }
                } else if ("vatTaxpayer,qualification".equals(influence)) {
                    Boolean qualification = docTemplate.getQualification();
                    if (qualification != null && qualification && detail.getIsQualification() != null && detail.getIsQualification() == 1) {
                        resultList.add(docTemplate);
                    }
                }
            } else if ("departmentAttr".equals(influence)) {
                Long departmentAttr = docTemplate.getDepartmentAttr();
                Long detailDepartmentAttr = detail.getDepartmentProperty();
                if (detailDepartmentAttr == null) {
                    detailDepartmentAttr = 0L;
                }
                if (departmentAttr != null && departmentAttr.equals(detailDepartmentAttr)) {
                    resultList.add(docTemplate);
                }
            } else if ("departmentAttr,personAttr".equals(influence)) {
                Long departmentAttr = docTemplate.getDepartmentAttr();
                Long personAttr = docTemplate.getPersonAttr();
                if (departmentAttr != null && departmentAttr.equals(detail.getDepartmentProperty()) && personAttr != null && personAttr.equals(detail.getEmployeeAttribute())) {
                    resultList.add(docTemplate);
                }
            } else if ("assetAttr".equals(influence)) {
                Long extendAttr = docTemplate.getExtendAttr();
                Long assetAttr = detail.getAssetAttr();
                if (assetAttr == null) {
                    assetAttr = 0L;
                }
                if (extendAttr != null && extendAttr.equals(assetAttr)) {
                    resultList.add(docTemplate);
                }
            } else if ("punishmentAttr".equals(influence)) {
                Long extendAttr = docTemplate.getExtendAttr();
                Long penaltyType = detail.getPenaltyType();
                if (penaltyType == null) {
                    penaltyType = 0L;
                }
                if (extendAttr != null && extendAttr.equals(penaltyType)) {
                    resultList.add(docTemplate);
                }
            } else if ("borrowAttr".equals(influence)) {
                Long extendAttr = docTemplate.getExtendAttr();
                Long loanTerm = detail.getLoanTerm();
                if (loanTerm == null) {
                    loanTerm = 0L;
                }
                if (extendAttr != null && extendAttr.equals(loanTerm)) {
                    resultList.add(docTemplate);
                }
            } else if ("accountInAttr".equals(influence)) {
                Long extendAttr = docTemplate.getExtendAttr();
                Long inBankAccountTypeId = detail.getInBankAccountTypeId();
                if (inBankAccountTypeId == null) {
                    inBankAccountTypeId = 0L;
                }
                if (extendAttr != null && extendAttr.equals(inBankAccountTypeId)) {
                    resultList.add(docTemplate);
                }
            } else if ("accountOutAttr ".equals(influence)) {
                Long extendAttr = docTemplate.getExtendAttr();
                Long bankAccountTypeId = detail.getBankAccountTypeId();
                if (bankAccountTypeId == null) {
                    bankAccountTypeId = 0L;
                }
                if (extendAttr != null && extendAttr.equals(bankAccountTypeId)) {
                    resultList.add(docTemplate);
                }
            }
        }
        if (resultList.size() != 1) {
            return message + "没有找到或者找到多个一致的凭证模板数据；";
        }
        return "";
    }
}
