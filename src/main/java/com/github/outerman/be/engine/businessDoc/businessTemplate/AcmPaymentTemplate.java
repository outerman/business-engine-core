package com.github.outerman.be.engine.businessDoc.businessTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.AcmSortReceiptSettlestyle;
import com.github.outerman.be.api.vo.PaymentTemplateItem;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.businessDoc.validator.IValidatable;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的结算类 相关模板
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AcmPaymentTemplate implements IValidatable {

    private AcmPaymentTemplateDto paymentTemplateDto;

    // 初始化方法, orgId可能为0; 如不为0, 则初始化公共模板(orgId=0)以及个性化模板
    public void init(SetOrg org, String businessCode, ITemplateProvider templateProvider) {
        paymentTemplateDto = new AcmPaymentTemplateDto();
        List<PaymentTemplateItem> payTemp = templateProvider.getPayTemplate(org.getId(), Long.parseLong(businessCode));
        paymentTemplateDto.getPayMap().putAll(getPay(payTemp));

        for (PaymentTemplateItem acmPayDocTemplate : payTemp) {
            if (!paymentTemplateDto.getCodeList().contains(acmPayDocTemplate.getSubjectDefault())) {
                paymentTemplateDto.getCodeList().add(acmPayDocTemplate.getSubjectDefault());
            }
        }

        paymentTemplateDto.setOrg(org);
        paymentTemplateDto.setBusinessCode(businessCode);
    }

    public List<String> getAccountCodeList() {
        List<String> accountCodeList = new ArrayList<>();
        if (paymentTemplateDto != null && paymentTemplateDto.getCodeList() != null) {
            accountCodeList.addAll(paymentTemplateDto.getCodeList());
        }
        return accountCodeList;
    }

    public PaymentTemplateItem getTemplate(AcmSortReceipt acmSortReceipt, AcmSortReceiptSettlestyle sett) {
        if (paymentTemplateDto.getPayMap() == null) {
            return null;
        }

        Long paymentType = getPaymentsType(sett);
        // sett.getBankAccountAttr() 与 acmPayDocTemplate.getAccountType() 是同一个值账户属性
        return paymentTemplateDto.getPayMap().get(paymentType + "" + sett.getBankAccountAttr());
    }

    private Map<String, PaymentTemplateItem> getPay(List<PaymentTemplateItem> payTemp) {
        Map<String, PaymentTemplateItem> payMap = new HashMap<>();

        for (PaymentTemplateItem acmPayDocTemplate : payTemp) {
            payMap.put(acmPayDocTemplate.getPaymentsType().toString() + acmPayDocTemplate.getAccountType(), acmPayDocTemplate);
        }

        return payMap;
    }

    @Override
    public String validate() {
        // do nothing for now
        return "";
    }

    public Long getPaymentsType(AcmSortReceiptSettlestyle settle) {
        Long paymentType;
        Integer payType = settle.getPayType(); // 0 收入；1 支出
        if (payType != null && payType == 1) {
            paymentType = AcmConst.PAYMENTSTYPE_20;
        } else {
            paymentType = AcmConst.PAYMENTSTYPE_10;
        }
        return paymentType;
    }

    public AcmPaymentTemplateDto getPaymentTemplateDto() {
        return paymentTemplateDto;
    }

    public void setPaymentTemplateDto(AcmPaymentTemplateDto paymentTemplateDto) {
        this.paymentTemplateDto = paymentTemplateDto;
    }
}
