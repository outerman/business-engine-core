package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.vo.*;
import com.github.outerman.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shenxy on 19/7/17.
 *
 * 验证凭证, 业务类型, 分录数等
 */
@Component
public class DocSizeValidator implements IBusinessDocValidatable {

    @Autowired
    public DocSizeValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(SetOrg setOrg, BusinessTemplate businessTemplate, AcmSortReceipt acmSortReceipt, FiDocDto doc) {
        AcmSortReceiptDetail detail = acmSortReceipt.getAcmSortReceiptDetailList().get(0);  //TODO: 验证假设前提是,每个流水账只有一个明细
        Long fiDocBusinessCode = detail.getBusinessCode();
        if (!businessTemplate.getBusinessCode().equals(fiDocBusinessCode)) {
            return "";
        }

        //凭证验证的各个因素: 企业\单据\模板\凭证
        //2, 根据传入的模板, 验证分录数
        //该函数给出的list,已经是每个分类一条了 //TODO: 此处的模板选择逻辑, 和生成凭证的相同, 作为验证, 应该考虑另一套逻辑
        List<DocAccountTemplateItem> docAccountTemplateList = businessTemplate.getDocAccountTemplate().getTemplate(
                setOrg.getId(), setOrg.getAccountingStandards().intValue(), setOrg.getIndustry(), setOrg.getVatTaxpayer(), acmSortReceipt.getAcmSortReceiptDetailList().get(0));

        //是否含有需要结算的项
        boolean isSettle = docAccountTemplateList.stream().anyMatch(DocAccountTemplateItem::getIsSettlement);
        if (doc.getEntrys().size() != (docAccountTemplateList.size() + (isSettle ? 1: 0))) {
            return "凭证分录数与模板配置不一致";
        }

        return "";
    }
}
