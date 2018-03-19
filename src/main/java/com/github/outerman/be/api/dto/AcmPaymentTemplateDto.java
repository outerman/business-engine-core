package com.github.outerman.be.api.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.outerman.be.api.vo.PaymentTemplateItem;
import com.github.outerman.be.api.vo.SetOrg;

/**
 * Created by shenxy on 16/12/28.
 * 业务单据生成凭证的结算凭证模板信息
 */
public class AcmPaymentTemplateDto {

    /** 企业信息 */
    private SetOrg org;

    /** 业务编码 */
    private String businessCode;

    /** 结算凭证模板信息 */
    private Map<String, PaymentTemplateItem> settleTemplateMap = new HashMap<>();

    /** 结算凭证模板中使用到的科目编码信息 */
    private List<String> codeList = new ArrayList<>();

    /**
     * 获取企业信息
     * @return 企业信息
     */
    public SetOrg getOrg() {
        return org;
    }

    /**
     * 设置企业信息
     * @param org 企业信息
     */
    public void setOrg(SetOrg org) {
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
    public Map<String, PaymentTemplateItem> getSettleTemplateMap() {
        return settleTemplateMap;
    }

    /**
     * 设置结算凭证模板信息
     * @param settleTemplateMap 结算凭证模板信息
     */
    public void setSettleTemplateMap(Map<String, PaymentTemplateItem> settleTemplateMap) {
        this.settleTemplateMap = settleTemplateMap;
    }

    /**
     * 获取结算凭证模板中使用到的科目编码信息
     * @return 结算凭证模板中使用到的科目编码信息
     */
    public List<String> getCodeList() {
        return codeList;
    }

    /**
     * 设置结算凭证模板中使用到的科目编码信息
     * @param codeList 结算凭证模板中使用到的科目编码信息
     */
    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

}
