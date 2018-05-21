package com.github.outerman.be.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.github.outerman.be.model.BusinessVoucherDetail;
import com.github.outerman.be.model.DocTemplate;
import com.github.outerman.be.model.Org;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的业务类 相关模板
 */
public class BusinessDocTemplate {

    public static final String INDUSTRY_ID = "industryId";

    public static final String ACCOUNTING_STANDARDS_ID = "accountingStandardsId";

    public static final String VAT_TAXPAYER_ID = "vatTaxpayerId";

    /** 企业信息 */
    private Org org;

    /** 业务编码 */
    private String businessCode;

    /** 业务凭证模板信息 */
    private Map<String, List<DocTemplate>> docTemplateMap = new HashMap<>();

    /** 业务凭证模板中使用到的科目编码信息 */
    private List<String> accountCodeList = new ArrayList<>();

    /**
     * 初始化方法，按照企业、业务编码，获取业务凭证模板数据
     * <p>企业 id 为 0 时获取系统预置数据
     * @param org 企业信息
     * @param businessCode 业务编码
     * @param templateProvider
     */
    public void init(Org org, String businessCode, ITemplateProvider templateProvider) {
        this.org = org;
        this.businessCode = businessCode;

        List<DocTemplate> all = templateProvider.getDocTemplate(org.getId(), businessCode);
        all.forEach(template -> {
            String key = getKey(org);
            if (docTemplateMap.get(key) == null) {
                docTemplateMap.put(key, new ArrayList<>());
            }
            docTemplateMap.get(key).add(template);
            if (!accountCodeList.contains(template.getAccountCode())) {
                accountCodeList.add(template.getAccountCode());
            }
        });

    }

    /**
     * 根据流水账收支明细获取匹配的凭证模板记录
     * @param org 组织信息
     * @param detail 流水账收支明细
     * @return 凭证模板记录
     */
    public List<DocTemplate> getDocTemplate(Org org, BusinessVoucherDetail detail) {
        List<DocTemplate> resultList = new ArrayList<>();
        if (!businessCode.equals(detail.getBusinessCode())) {
            return resultList;
        }

        Map<String, List<DocTemplate>> docTemplateMap = getDocTemplateMap(org);
        if (docTemplateMap.isEmpty()) {
            return resultList;
        }

        // 凭证模板按照分录标识获取匹配记录
        for (List<DocTemplate> docTemplateListWithFlag : docTemplateMap.values()) {
            resultList.addAll(getDocTemplate(docTemplateListWithFlag, detail));
        }
        return resultList;
    }

    private List<DocTemplate> getDocTemplate(List<DocTemplate> docTemplateListWithFlag,
            BusinessVoucherDetail detail) {
        List<DocTemplate> resultList = new ArrayList<>();
        Map<String, String> detailInfluenceMap = detail.getInfluenceMap();
        DocTemplate defaultDocTemplate = null; // 影响因素默认匹配模板，影响因素值为默认的记录
        for (DocTemplate docTemplate : docTemplateListWithFlag) {
            Map<String, String> influenceMap = docTemplate.getInfluenceMap();
            if (influenceMap == null || influenceMap.isEmpty()) { // 没有影响因素
                resultList.add(docTemplate);
                continue;
            }

            boolean match = false;
            for (Entry<String, String> entry : influenceMap.entrySet()) {
                match = false;
                String value = entry.getValue();
                if (value.equals("默认")) { // 影响因素取值为默认，设置为默认匹配模板
                    defaultDocTemplate = docTemplate;
                    continue;
                }
                String influence = entry.getKey();
                if (detailInfluenceMap != null && detailInfluenceMap.containsKey(influence)) {
                    String detailValue = detailInfluenceMap.get(influence);
                    if (value.equals(detailValue)) {
                        if (docTemplate != defaultDocTemplate) {
                            match = true;
                        } else { // 默认匹配模板单独处理，不算匹配到
                            continue;
                        }
                    }
                }
                if (!match) { // 存在影响因素不匹配模板时不再继续匹配
                    if (defaultDocTemplate == docTemplate) { // 不匹配时，如果之前设置为了默认匹配模板，清空
                        defaultDocTemplate = null;
                    }
                    break;
                }
            }
            if (match) {
                resultList.add(docTemplate);
            }
        }
        if (resultList.isEmpty() && defaultDocTemplate != null) {
            resultList.add(defaultDocTemplate);
        }
        return resultList;
    }

    /**
     * 获取当前业务对应组织的所有凭证模板信息
     * @param org 组织信息
     * @return 凭证模板信息，以 flag（A,B,C...）为 key 的 map
     */
    public Map<String, List<DocTemplate>> getDocTemplateMap(Org org) {
        Map<String, List<DocTemplate>> resultMap = new TreeMap<>();
        if (!org.getId().equals(this.org.getId())) {
            return resultMap;
        }
        List<DocTemplate> docTemplateList = new ArrayList<>();
        String key = getKey(org);
        if (docTemplateMap.containsKey(key)) {
            docTemplateList = docTemplateMap.get(key);
        }
        for (DocTemplate docTemplate : docTemplateList) {
            String flag = docTemplate.getFlag();
            List<DocTemplate> docTemplateWithFlagList;
            if (resultMap.containsKey(flag)) {
                docTemplateWithFlagList = resultMap.get(flag);
            } else {
                docTemplateWithFlagList = new ArrayList<>();
                resultMap.put(flag, docTemplateWithFlagList);
            }
            docTemplateWithFlagList.add(docTemplate);
        }
        return resultMap;
    }

    public static String getKey(Org org) {
        StringBuilder key = new StringBuilder();
        key.append(INDUSTRY_ID).append(":").append(org.getIndustry()).append(";");
        key.append(ACCOUNTING_STANDARDS_ID).append(":").append(org.getAccountingStandards()).append(";");
        key.append(VAT_TAXPAYER_ID).append(":").append(org.getVatTaxpayer());
        return key.toString();
    }

    public static Long getValue(String key, String keyName) {
        String[] keyArray = key.split(";");
        for (String keyString : keyArray) {
            String[] keyValue = keyString.split(":");
            String _keyName = keyValue[0];
            if (keyName.equals(_keyName)) {
                return Long.parseLong(keyValue[1]);
            }
        }
        return null;
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
     * 获取业务凭证模板信息
     * @return 业务凭证模板信息
     */
    public Map<String, List<DocTemplate>> getDocTemplateMap() {
        return docTemplateMap;
    }

    /**
     * 设置业务凭证模板信息
     * @param docTemplateMap 业务凭证模板信息
     */
    public void setDocTemplateMap(Map<String, List<DocTemplate>> docTemplateMap) {
        this.docTemplateMap = docTemplateMap;
    }

    /**
     * 获取业务凭证模板中使用到的科目编码信息
     * @return 业务凭证模板中使用到的科目编码信息
     */
    public List<String> getAccountCodeList() {
        return accountCodeList;
    }

    /**
     * 设置业务凭证模板中使用到的科目编码信息
     * @param codeList 业务凭证模板中使用到的科目编码信息
     */
    public void setAccountCodeList(List<String> accountCodeList) {
        this.accountCodeList = accountCodeList;
    }

}
