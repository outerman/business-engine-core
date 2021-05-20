package com.github.outerman.be.template;

import com.github.outerman.be.model.Org;

/**
 * Created by shenxy on 7/7/17.
 * 业务类型--主模板
 */
public class BusinessTemplate {

    /** 企业信息 */
    private Org org;

    /** 业务编码 */
    private String businessCode;

    private BusinessDocTemplate businessDocTemplate = new BusinessDocTemplate();

    private BusinessSettleTemplate paymentTemplate = new BusinessSettleTemplate();

    /**
     * 初始化方法，按照企业、业务类型编码，获取凭证模板、结算模板
     * <p>企业 id 为 0 时获取系统预置数据
     * @param org 企业信息
     * @param businessCode 业务类型编码
     * @param templateProvider 模板提供者
     */
    public void init(Org org, String businessCode, ITemplateProvider templateProvider) {
        this.org = org;
        this.businessCode = businessCode;
        businessDocTemplate.init(org, businessCode, templateProvider);
        paymentTemplate.init(org, businessCode, templateProvider);
    }

    /**
     * 获取企业信息
     * @return 企业信息
     */
    public Org getOrg() {
        return org;
    }

    /**
     * 设置企业信息
     * @param org 企业信息
     */
    public void setOrg(Org org) {
        this.org = org;
    }

    /**
     * 获取业务编码
     * @return 业务编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置业务编码
     * @param businessCode 业务编码
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public BusinessDocTemplate getDocAccountTemplate() {
        return businessDocTemplate;
    }

    public void setDocAccountTemplate(BusinessDocTemplate docAccountTemplate) {
        this.businessDocTemplate = docAccountTemplate;
    }

    public BusinessSettleTemplate getPaymentTemplate() {
        return paymentTemplate;
    }

    public void setPaymentTemplate(BusinessSettleTemplate paymentTemplate) {
        this.paymentTemplate = paymentTemplate;
    }

}
