package com.rt.be.engine.businessDoc.businessTemplate;

import com.rt.be.api.constant.AcmConst;
import com.rt.be.api.dto.AcmPaymentTemplateDto;
import com.rt.be.api.vo.AcmSortReceipt;
import com.rt.be.api.vo.AcmSortReceiptSettlestyle;
import com.rt.be.api.vo.PaymentTemplateItem;
import com.rt.be.engine.businessDoc.BusinessUtil;
import com.rt.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.rt.be.engine.businessDoc.validator.IValidatable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的结算类 相关模板
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AcmPaymentTemplate implements IValidatable {

    private AcmPaymentTemplateDto paymentTemplateDto;

    //初始化方法, orgId可能为0; 如不为0, 则初始化公共模板(orgId=0)以及个性化模板
    public void init(Long orgId, Long businessCode, ITemplateProvider templateProvider) {
        paymentTemplateDto = new AcmPaymentTemplateDto();
        List<PaymentTemplateItem> payTemp = templateProvider.getPayTemplate(orgId, businessCode);
        paymentTemplateDto.getPayMap().putAll(getPay(payTemp));

        for (PaymentTemplateItem acmPayDocTemplate : payTemp) {
			if (!paymentTemplateDto.getCodeList().contains(acmPayDocTemplate.getSubjectDefault())) {
                paymentTemplateDto.getCodeList().add(acmPayDocTemplate.getSubjectDefault());
			}
		}

        paymentTemplateDto.setOrgId(orgId);
        paymentTemplateDto.setBusinessCode(businessCode);
    }

    public List<String> getAccountCodeList() {
        List<String> accountCodeList = new ArrayList<>();
        if (paymentTemplateDto != null && paymentTemplateDto.getCodeList() != null) {
            accountCodeList.addAll(paymentTemplateDto.getCodeList());
        }
        return accountCodeList;
    }

    private final String PAYTYPE_501 = "501";
    private final String PAYTYPE_502 = "502";
    public PaymentTemplateItem getTemplate(AcmSortReceipt acmSortReceipt, AcmSortReceiptSettlestyle sett) {
        if (paymentTemplateDto.getPayMap() == null) {
            return null;
        }

        Long paymentType = acmSortReceipt.getPaymentsType();//BusinessUtil.getPaymentTypeFromBusiness(acmSortReceiptDetail.getBusinessCode().toString());

        //sett.getBankAccountAttr() 与 acmPayDocTemplate.getAccountType() 是同一个值 -- 账户属性
        if (paymentType.equals(AcmConst.PAYMENTSTYPE_50)) {  //收付款
            if (isDebit(acmSortReceipt)) {
                return paymentTemplateDto.getPayMap().get(paymentType + PAYTYPE_501 + sett.getBankAccountAttr());
            } else {
                return paymentTemplateDto.getPayMap().get(paymentType + PAYTYPE_502 + sett.getBankAccountAttr());
            }
        } else {
            return paymentTemplateDto.getPayMap().get(paymentType + "" + sett.getBankAccountAttr());
        }
    }

    //TODO: 后续在AcmSortReceiptSettlestyle上增加收支方向比较合适,此处计算太费性能, 因为这个方法在好几重for循环里
    //判断该业务是否是收款: 使用明细的合计值来判断, 收方合计大于0, 则返回true
    private boolean isDebit(AcmSortReceipt acmSortReceipt) {
        return acmSortReceipt.getAcmSortReceiptDetailList()
                .parallelStream()
                .map(detail -> {
                    int payDirection = BusinessUtil.paymentDirection(detail.getBusinessCode());
                    if (payDirection == AcmConst.PAYMENT_DIRECTION_IN) {
                        return detail.getTaxInclusiveAmount();
                    }
                    else if (payDirection == AcmConst.PAYMENT_DIRECTION_OUT){
                        return -detail.getTaxInclusiveAmount();
                    }
                    else {
                        return 0D;
                    }
                })
                .reduce((amount1, amount2)-> amount1 + amount2)
                .get() > 0;
    }

    private Map<String, PaymentTemplateItem> getPay(List<PaymentTemplateItem> payTemp) {
        Map<String, PaymentTemplateItem> payMap = new HashMap<>();

        List<String> payList = new ArrayList<>();
        for (PaymentTemplateItem acmPayDocTemplate : payTemp) {
            // 支付类型 + 结算方式  10000 + 98
            if(payList.contains(acmPayDocTemplate.getId().toString())){
                continue;
            }else{
                payList.add(acmPayDocTemplate.getId().toString());
                payMap.put(acmPayDocTemplate.getPaymentsType().toString()+acmPayDocTemplate.getAccountType(), acmPayDocTemplate);
            }
        }

        return payMap;
    }

    @Override
    public String validate() {
        //TODO
        return "";
    }

    public AcmPaymentTemplateDto getPaymentTemplateDto() {
        return paymentTemplateDto;
    }

    public void setPaymentTemplateDto(AcmPaymentTemplateDto paymentTemplateDto) {
        this.paymentTemplateDto = paymentTemplateDto;
    }
}
