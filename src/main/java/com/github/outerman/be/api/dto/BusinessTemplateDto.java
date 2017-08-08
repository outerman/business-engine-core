package com.github.outerman.be.api.dto;

/**
 * Created by shenxy on 7/7/17.
 * 业务类型--主模板
 */
public class BusinessTemplateDto {

    private AcmDocAccountTemplateDto docAccountTemplate;
    private AcmPaymentTemplateDto paymentTemplate;
    private AcmUITemplateDto uiTemplate;
    private Long orgId;
    private Long businessCode;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(Long businessCode) {
        this.businessCode = businessCode;
    }

    public AcmDocAccountTemplateDto getDocAccountTemplate() {
        return docAccountTemplate;
    }

    public void setDocAccountTemplate(AcmDocAccountTemplateDto docAccountTemplate) {
        this.docAccountTemplate = docAccountTemplate;
    }

    public AcmPaymentTemplateDto getPaymentTemplate() {
        return paymentTemplate;
    }

    public void setPaymentTemplate(AcmPaymentTemplateDto paymentTemplate) {
        this.paymentTemplate = paymentTemplate;
    }

    public AcmUITemplateDto getUiTemplate() {
        return uiTemplate;
    }

    public void setUiTemplate(AcmUITemplateDto uiTemplate) {
        this.uiTemplate = uiTemplate;
    }
}
