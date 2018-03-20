package com.github.outerman.be.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.outerman.be.model.BusinessVoucherSettle;
import com.github.outerman.be.model.SettleTemplate;
import com.github.outerman.be.model.Org;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的结算类 相关模板
 */
public class BusinessSettleTemplate {

    /** 企业信息 */
    private Org org;

    /** 业务编码 */
    private String businessCode;

    /** 结算凭证模板信息 */
    private Map<String, SettleTemplate> settleTemplateMap = new HashMap<>();

    /** 结算凭证模板中使用到的科目编码信息 */
    private List<String> accountCodeList = new ArrayList<>();

    /**
     * 初始化方法，按照企业、业务编码，获取凭证结算模板数据
     * <p>企业 id 为 0 时获取系统预置数据
     * @param org 企业信息
     * @param businessCode 业务编码
     * @param templateProvider
     */
    public void init(Org org, String businessCode, ITemplateProvider templateProvider) {
        this.org = org;
        this.businessCode = businessCode;

        List<SettleTemplate> payTemp = templateProvider.getPayTemplate(org.getId(), businessCode);
        settleTemplateMap = new HashMap<>();
        for (SettleTemplate acmPayDocTemplate : payTemp) {
            settleTemplateMap.put(acmPayDocTemplate.getPaymentsType().toString() + acmPayDocTemplate.getAccountType(),
                    acmPayDocTemplate);
            if (!accountCodeList.contains(acmPayDocTemplate.getSubjectDefault())) {
                accountCodeList.add(acmPayDocTemplate.getSubjectDefault());
            }
        }
    }

    public SettleTemplate getTemplate(BusinessVoucherSettle settle) {
        return settleTemplateMap.get(settle.getBusinessPropertyId() + "" + settle.getBankAccountAttr());
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

    /**
     * 获取结算凭证模板信息
     * @return 结算凭证模板信息
     */
    public Map<String, SettleTemplate> getSettleTemplateMap() {
        return settleTemplateMap;
    }

    /**
     * 设置结算凭证模板信息
     * @param settleTemplateMap 结算凭证模板信息
     */
    public void setSettleTemplateMap(Map<String, SettleTemplate> settleTemplateMap) {
        this.settleTemplateMap = settleTemplateMap;
    }

    /**
     * 获取结算凭证模板中使用到的科目编码信息
     * @return 结算凭证模板中使用到的科目编码信息
     */
    public List<String> getAccountCodeList() {
        return accountCodeList;
    }

    /**
     * 设置结算凭证模板中使用到的科目编码信息
     * @param codeList 结算凭证模板中使用到的科目编码信息
     */
    public void setAccountCodeList(List<String> accountCodeList) {
        this.accountCodeList = accountCodeList;
    }

}
