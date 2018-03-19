package com.github.outerman.be.engine.businessDoc.businessTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.constant.CommonConst;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
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

    /**
     * 初始化方法，按照企业、业务编码，获取凭证结算模板数据
     * <p>企业 id 为 0 时获取系统预置数据
     * @param org 企业信息
     * @param businessCode 业务编码
     * @param templateProvider
     */
    public void init(SetOrg org, String businessCode, ITemplateProvider templateProvider) {
        paymentTemplateDto = new AcmPaymentTemplateDto();
        paymentTemplateDto.setOrg(org);
        paymentTemplateDto.setBusinessCode(businessCode);

        List<PaymentTemplateItem> payTemp = templateProvider.getPayTemplate(org.getId(), businessCode);
        Map<String, PaymentTemplateItem> payMap = new HashMap<>();
        for (PaymentTemplateItem acmPayDocTemplate : payTemp) {
            payMap.put(acmPayDocTemplate.getPaymentsType().toString() + acmPayDocTemplate.getAccountType(), acmPayDocTemplate);
            if (!paymentTemplateDto.getCodeList().contains(acmPayDocTemplate.getSubjectDefault())) {
                paymentTemplateDto.getCodeList().add(acmPayDocTemplate.getSubjectDefault());
            }
        }
        paymentTemplateDto.getPayMap().putAll(payMap);
    }

    public List<String> getAccountCodeList() {
        List<String> accountCodeList = new ArrayList<>();
        if (paymentTemplateDto != null && paymentTemplateDto.getCodeList() != null) {
            accountCodeList.addAll(paymentTemplateDto.getCodeList());
        }
        return accountCodeList;
    }

    public PaymentTemplateItem getTemplate(AcmSortReceiptSettlestyle settle) {
        if (paymentTemplateDto.getPayMap() == null) {
            return null;
        }

        Long paymentType = getPaymentsType(settle);
        return paymentTemplateDto.getPayMap().get(paymentType + "" + settle.getBankAccountAttr());
    }

    @Override
    public String validate() {
        // do nothing for now
        return "";
    }

    public Long getPaymentsType(AcmSortReceiptSettlestyle settle) {
        Long paymentType;
        Integer payType = settle.getPayType(); // 0 收入；1 支出
        if (payType != null && payType == 0) {
            paymentType = CommonConst.PAYMENTSTYPE_10;
        } else {
            paymentType = CommonConst.PAYMENTSTYPE_20;
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
