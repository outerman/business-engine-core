package com.github.outerman.be.engine.businessDoc.businessTemplate;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.constant.CommonConst;
import com.github.outerman.be.api.dto.BusinessTemplateDto;
import com.github.outerman.be.api.dto.TemplateValidateResultDto;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.businessDoc.validator.IValidatable;
import com.github.outerman.be.engine.businessDoc.validator.ValidatorManager;
import com.github.outerman.be.engine.util.StringUtil;

/**
 * Created by shenxy on 7/7/17.
 * 业务类型--主模板
 */
@Component(CommonConst.BUSINESS_TEMPLATE)
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

    private ITemplateProvider templateProvider;

    /**
     * 初始化方法，按照企业、业务类型编码，获取凭证模板、结算模板、元数据模板数据
     * <p>企业 id 为 0 时获取系统预置数据
     * @param org 企业信息
     * @param businessCode 业务类型编码
     * @param templateProvider
     */
    public void init(SetOrg org, String businessCode, ITemplateProvider templateProvider) {
        this.templateProvider = templateProvider;
        docAccountTemplate.init(org, businessCode, templateProvider);
        paymentTemplate.init(org, businessCode, templateProvider);
        uiTemplate.init(org, businessCode, templateProvider);

        businessTemplateDto = new BusinessTemplateDto();
        businessTemplateDto.setOrg(org);
        businessTemplateDto.setBusinessCode(businessCode);
        businessTemplateDto.setDocAccountTemplate(docAccountTemplate.getDocTemplateDto());
        businessTemplateDto.setPaymentTemplate(paymentTemplate.getPaymentTemplateDto());
        businessTemplateDto.setUiTemplate(uiTemplate.getUiTemplateDto());
    }

    @Override
    public String validate() {
        TemplateValidateResultDto result = new TemplateValidateResultDto();

        result.setDocTemplateMessage(docAccountTemplate.validate());
        result.setPayDocTemplateMessage(paymentTemplate.validate());
        result.setUiTemplateMessage(uiTemplate.validate());
        if (!result.getResult()) {
            return result.getErrorMessage();
        }

        String errorMessage = validatorManager.getTemplateValidators()
                .parallelStream()
                .map(validator -> validator.validate(docAccountTemplate.getDocTemplateDto(),
                        paymentTemplate.getPaymentTemplateDto(),
                        uiTemplate.getUiTemplateDto()))
                .filter(message -> !StringUtil.isEmpty(message))
                .collect(Collectors.joining("\n"));
        return errorMessage;
    }

    public String getBusinessCode() {
        return businessTemplateDto.getBusinessCode();
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

    /**
     * 获取templateProvider
     * @return templateProvider
     */
    public ITemplateProvider getTemplateProvider() {
        return templateProvider;
    }

}
