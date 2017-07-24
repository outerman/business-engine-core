package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.vo.*;
import com.rt.be.engine.businessDoc.businessTemplate.AmountGetter;
import com.rt.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import com.rt.be.engine.businessDoc.serviceImp.TemplateValidateService;
import com.rt.be.engine.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

        //凭证验证的各个因素: 企业\单据\模板\凭证
        //2, 根据传入的模板, 验证分录数
        //该函数给出的list,已经是每个分类一条了 //TODO: 此处的模板选择逻辑, 和生成凭证的相同, 作为验证, 应该考虑另一套逻辑
        List<DocAccountTemplateItem> docAccountTemplateList = businessTemplate.getDocAccountTemplate().getTemplate(
                setOrg.getId(), setOrg.getAccountingStandards().intValue(), setOrg.getIndustry(), setOrg.getVatTaxpayer(), acmSortReceipt.getAcmSortReceiptDetailList().get(0));

        Map<String, DocAccountTemplateItem> templateMap = new HashMap<>();
        for (DocAccountTemplateItem template : docAccountTemplateList) {
            templateMap.put(template.getFlag(), template);
        }

        //3, 遍历检查, 带有"A"\"B"的来源标记的分录
        return doc.getEntrys()
                .stream()
                .filter(entry -> entry.getSourceFlag() != null)
                .map(entry -> validateEntry(acmSortReceipt, templateMap, entry))
                .filter(str -> !StringUtil.isEmpty(str))
                .collect(Collectors.joining(";"));
    }

    private String validateEntry(AcmSortReceipt acmSortReceipt, Map<String, DocAccountTemplateItem> templateMap, FiDocEntryDto entry) {
        //3.1)根据分类标记找到相应分录的模板:
        DocAccountTemplateItem template = templateMap.get(entry.getSourceFlag());
        if (template == null) {
            return "没有找到模板";
        }
        templateMap.remove(entry.getSourceFlag()); //检查过了就移走

        //3.2)科目是否吻合
        if (!entry.getAccountCode().equals(template.getAccountCode())) {
            return "分录的会计科目和模板不一致";
        }
        //3.3)根据模板上的金额取数规则,与业务单据比较
        //TODO: 此处的模板选择逻辑, 和生成凭证的相同, 作为验证, 应该考虑另一套逻辑
        AcmSortReceiptDetail detail = acmSortReceipt.getAcmSortReceiptDetailList().get(0); //TODO: 验证假设前提是,每个流水账只有一个明细
        Double amountFromReceipt = AmountGetter.getAmount(detail, template);
        Double amountFromDoc = entry.getAmountDr() != null ?  entry.getAmountDr() : -entry.getAmountCr();
        if (Math.abs(amountFromReceipt) != Math.abs(amountFromDoc)) {
            return "分录的金额与业务单据不一致";
        }

        //3.2)根据模板上影响因素,与业务单据\企业属性比较
        //3.4)根据模板上的行业,与企业属性比较
        return "";
    }
}
