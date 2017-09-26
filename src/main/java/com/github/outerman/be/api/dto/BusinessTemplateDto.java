package com.github.outerman.be.api.dto;

import com.github.outerman.be.api.vo.SetOrg;

/**
 * Created by shenxy on 7/7/17.
 * 业务类型--主模板
 */
public class BusinessTemplateDto {

    /** 组织信息 */
    private SetOrg org;

    private AcmDocAccountTemplateDto docAccountTemplate;
    private AcmPaymentTemplateDto paymentTemplate;
    private AcmUITemplateDto uiTemplate;
    private Long businessCode;

    /**
     * 获取组织信息
     * @return 组织信息
     */
    public SetOrg getOrg() {
        return org;
    }

    /**
     * 设置组织信息
     * @param org 组织信息
     */
    public void setOrg(SetOrg org) {
        this.org = org;
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
