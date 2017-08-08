package com.rt.be.engine.businessDoc.businessTemplate;

import com.rt.be.api.constant.AcmConst;
import com.rt.be.api.constant.BusinessEngineException;
import com.rt.be.api.dto.BusinessTemplateDto;
import com.rt.be.api.constant.ErrorCode;
import com.rt.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.rt.be.engine.businessDoc.validator.IValidatable;
import com.rt.be.engine.businessDoc.validator.ValidatorManager;
import com.rt.be.engine.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created by shenxy on 7/7/17.
 * 业务类型--主模板
 */
@Component(AcmConst.BUSINESS_TEMPLATE)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BusinessTemplate implements IValidatable {
    @Autowired
    private AcmDocAccountTemplate docAccountTemplate;
    @Autowired
    private AcmPaymentTemplate paymentTemplate;
    @Autowired
    private AcmUITemplate uiTemplate;
    @Autowired
    private ValidatorManager validatorManager;

    private BusinessTemplateDto businessTemplateDto;

    //初始化方法, orgId可能为0; 如不为0, 则初始化公共模板(orgId=0)以及个性化模板
    public void init(Long orgId, Long businessCode, ITemplateProvider templateProvider) {
        docAccountTemplate.init(orgId, businessCode, templateProvider);
        paymentTemplate.init(orgId, businessCode, templateProvider);
        uiTemplate.init(orgId, businessCode, templateProvider);

        businessTemplateDto = new BusinessTemplateDto();
        businessTemplateDto.setOrgId(orgId);
        businessTemplateDto.setBusinessCode(businessCode);
        businessTemplateDto.setDocAccountTemplate(docAccountTemplate.getDocTemplateDto());
        businessTemplateDto.setPaymentTemplate(paymentTemplate.getPaymentTemplateDto());
        businessTemplateDto.setUiTemplate(uiTemplate.getUiTemplateDto());
    }

    @Override
    public String validate() {
        //先分别校验
        String docErrorMsg = docAccountTemplate.validate();
        String paymentErrorMsg = paymentTemplate.validate();
        String uiErrorMsg = uiTemplate.validate();
        if (!StringUtil.isEmpty(docErrorMsg)|| !StringUtil.isEmpty(paymentErrorMsg)|| !StringUtil.isEmpty(uiErrorMsg)) {
            throw new BusinessEngineException(ErrorCode.ENGINE_VALIDATE_ERROR_CODE, ErrorCode.ENGINE_VALIDATE_ERROR_MESSAGE + ":\n" + docErrorMsg + "\n" + paymentErrorMsg + "\n" + uiErrorMsg + "\n");
        }

        //再组合校验, 例如:
//        a）各模板是否同时结算或不结算
//        b）影响因素字段的必填是否一致（凭证模板没有“默认”则为必填）
//        c）支持的行业是否一致
        String errorMessage = validatorManager.getTemplateValidators()
                .parallelStream()
                .map(validator -> validator.validate(docAccountTemplate.getDocTemplateDto(),
                        paymentTemplate.getPaymentTemplateDto(),
                        uiTemplate.getUiTemplateDto()))
                .filter(message -> !StringUtil.isEmpty(message))
                .collect(Collectors.joining(";"));
        return errorMessage;
    }

    public Long getBusinessCode() {
        return businessTemplateDto.getBusinessCode();
    }

    public Long getOrgId() {
        return businessTemplateDto.getOrgId();
    }

    public BusinessTemplateDto getBusinessTemplateDto() {
        return businessTemplateDto;
    }

    public void setBusinessTemplateDto(BusinessTemplateDto businessTemplateDto) {
        this.businessTemplateDto = businessTemplateDto;
    }

    public AcmDocAccountTemplate getDocAccountTemplate() {
        return docAccountTemplate;
    }

    public void setDocAccountTemplate(AcmDocAccountTemplate docAccountTemplate) {
        this.docAccountTemplate = docAccountTemplate;
    }

    public AcmPaymentTemplate getPaymentTemplate() {
        return paymentTemplate;
    }

    public void setPaymentTemplate(AcmPaymentTemplate paymentTemplate) {
        this.paymentTemplate = paymentTemplate;
    }

    public AcmUITemplate getUiTemplate() {
        return uiTemplate;
    }

    public void setUiTemplate(AcmUITemplate uiTemplate) {
        this.uiTemplate = uiTemplate;
    }
}
