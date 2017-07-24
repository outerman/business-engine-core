package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.vo.*;
import com.rt.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import com.rt.be.engine.businessDoc.serviceImp.TemplateValidateService;
import com.rt.be.engine.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        //遍历检查分录
        return doc.getEntrys()
                .stream()
                .filter(entry -> entry.getSourceFlag() == null)
                .map(entry -> validateEntry(businessTemplate, acmSortReceipt, entry))
                .filter(str -> !StringUtil.isEmpty(str))
                .collect(Collectors.joining(";"));
    }

    private String validateEntry(BusinessTemplate businessTemplate, AcmSortReceipt acmSortReceipt, FiDocEntryDto entry) {
        AcmSortReceiptSettlestyle settlestyle = acmSortReceipt.getAcmSortReceiptSettlestyleList().get(0); // TODO: 假设前提, 模拟数据里只有一个结算方式
        //3.5)没有分类的分录(结算情况),与结算情况比较
        //TODO: 此处的模板选择逻辑, 和生成凭证的相同, 作为验证, 应该考虑另一套逻辑
        PaymentTemplateItem payDocTemplate = businessTemplate.getPaymentTemplate().getTemplate(acmSortReceipt, settlestyle);
        //模板上的可能不是末级, 凭证上是下级
        if (!entry.getAccountCode().startsWith(payDocTemplate.getSubjectDefault())) {
            return "结算方式的分录的会计科目和模板不一致";
        }

        //3.6)结算金额检查, 由于有对调借贷方的情况, 此处暂时仅校验数值,不校验方向
        Double amount = entry.getAmountDr() == null ? entry.getAmountCr() : entry.getAmountDr();
        if (!settlestyle.getTaxInclusiveAmount().equals(Math.abs(amount))) {
            return "结算方式的金额与业务单据不一致";
        }

        return  "";
    }
}
