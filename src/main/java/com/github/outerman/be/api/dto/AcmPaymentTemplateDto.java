package com.github.outerman.be.api.dto;

import com.github.outerman.be.api.vo.PaymentTemplateItem;
import com.github.outerman.be.api.vo.SetOrg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的结算类 相关模板
 */
public class AcmPaymentTemplateDto {

    /** 组织信息 */
    private SetOrg org;

    /** 业务类型编码 */
    private String businessCode;

    private Map<String, PaymentTemplateItem> payMap = new HashMap<>();  //可能会用到的凭证模板
    private List<String> codeList = new ArrayList<>();      //可能用到的科目

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

    /**
     * 获取业务类型编码
     * @return 业务类型编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置业务类型编码
     * @param businessCode 业务类型编码
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Map<String, PaymentTemplateItem> getPayMap() {
        return payMap;
    }

    public void setPayMap(Map<String, PaymentTemplateItem> payMap) {
        this.payMap = payMap;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }
}
