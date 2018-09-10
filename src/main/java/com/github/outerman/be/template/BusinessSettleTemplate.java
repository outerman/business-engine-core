package com.github.outerman.be.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.outerman.be.model.Account;
import com.github.outerman.be.model.BusinessVoucherSettle;
import com.github.outerman.be.model.SettleTemplate;
import com.github.outerman.be.util.StringUtil;
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

    /** 结算凭证模板中使用到的科目信息 */
    private List<Account> accounts = new ArrayList<>();

    /**
     * 初始化方法，按照企业、业务编码，获取凭证结算模板数据
     * <p>企业 id 为 0 时获取系统预置数据
     * @param org 企业信息
     * @param businessCode 业务编码
     * @param provider
     */
    public void init(Org org, String businessCode, ITemplateProvider provider) {
        this.org = org;
        this.businessCode = businessCode;

        Set<String> accountSet = new HashSet<>();
        List<SettleTemplate> templateList = provider.getSettleTemplate(org.getId(), businessCode);
        settleTemplateMap = new HashMap<>();
        for (SettleTemplate template : templateList) {
            String key = "" + template.getBusinessPropertyId() + template.getBankAccountTypeId();
            settleTemplateMap.put(key, template);
            String accountKey = template.getAccountCode();
            if (StringUtil.isEmpty(accountKey)) {
                accountKey = template.getAccountId().toString();
            }
            if (!accountSet.contains(accountKey)) {
                Account account = new Account();
                account.setId(template.getAccountId());
                account.setCode(template.getAccountCode());
                accounts.add(account);
            }
        }
    }

    public SettleTemplate getTemplate(BusinessVoucherSettle settle) {
        String key = "" + settle.getBusinessPropertyId() + settle.getBankAccountTypeId();
        return settleTemplateMap.get(key);
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
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * 设置结算凭证模板中使用到的科目编码信息
     * @param codeList 结算凭证模板中使用到的科目编码信息
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

}
