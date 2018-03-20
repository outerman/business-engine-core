package com.github.outerman.be.businessDoc.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.github.outerman.be.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.model.AcmSortReceiptDetail;
import com.github.outerman.be.model.DocAccountTemplateItem;
import com.github.outerman.be.model.SetOrg;
import com.github.outerman.be.util.StringUtil;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的业务类 相关模板
 */
public class BusinessDocTemplate {

    public static final String INDUSTRY_ID = "industryId";

    public static final String ACCOUNTING_STANDARDS_ID = "accountingStandardsId";

    public static final String VAT_TAXPAYER_ID = "vatTaxpayerId";

    /** 企业信息 */
    private SetOrg org;

    /** 业务编码 */
    private String businessCode;

    /** 业务凭证模板信息 */
    private Map<String, List<DocAccountTemplateItem>> docTemplateMap = new HashMap<>();

    /** 业务凭证模板中使用到的科目编码信息 */
    private List<String> accountCodeList = new ArrayList<>();

    /**
     * 初始化方法，按照企业、业务编码，获取业务凭证模板数据
     * <p>企业 id 为 0 时获取系统预置数据
     * @param org 企业信息
     * @param businessCode 业务编码
     * @param templateProvider
     */
    public void init(SetOrg org, String businessCode, ITemplateProvider templateProvider) {
        this.org = org;
        this.businessCode = businessCode;

        List<DocAccountTemplateItem> all = templateProvider.getBusinessTemplateByCode(org.getId(), businessCode);
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
    public List<DocAccountTemplateItem> getDocTemplate(SetOrg org, AcmSortReceiptDetail detail) {
        List<DocAccountTemplateItem> resultList = new ArrayList<>();
        if (!businessCode.equals(detail.getBusinessCode())) {
            return resultList;
        }

        Map<String, List<DocAccountTemplateItem>> docTemplateMap = getDocTemplateMap(org);
        if (docTemplateMap.isEmpty()) {
            return resultList;
        }

        // 凭证模板按照分录标识获取匹配记录
        for (List<DocAccountTemplateItem> docTemplateListWithFlag : docTemplateMap.values()) {
            resultList.addAll(getDocTemplate(docTemplateListWithFlag, detail));
        }
        return resultList;
    }

    private List<DocAccountTemplateItem> getDocTemplate(List<DocAccountTemplateItem> docTemplateListWithFlag,
            AcmSortReceiptDetail detail) {
        List<DocAccountTemplateItem> resultList = new ArrayList<>();
        Map<String, String> detailInfluenceMap = detail.getInfluenceMap();
        DocAccountTemplateItem defaultDocTemplate = null; // 影响因素默认匹配规则，影响因素值为 0 的记录
        for (DocAccountTemplateItem docTemplate : docTemplateListWithFlag) {
            String influence = docTemplate.getInfluence();
            if (StringUtil.isEmpty(influence)) { // 没有影响因素
                resultList.add(docTemplate);
                continue;
            }

            Map<String, String> influenceMap = docTemplate.getInfluenceMap();
            if (influenceMap != null) {
                boolean match = true;
                for (Entry<String, String> entry : influenceMap.entrySet()) {
                    influence = entry.getKey();
                    String value = entry.getValue();
                    if (detailInfluenceMap == null || !detailInfluenceMap.containsKey(influence)) {
                        match = false;
                        if (value.equals("默认")) {
                            defaultDocTemplate = docTemplate;
                        } else {
                            break;
                        }
                    } else {
                        String detailValue = detailInfluenceMap.get(influence);
                        if (!value.equals(detailValue)) {
                            match = false;
                            break;
                        }
                    }
                }
                if (match) {
                    resultList.add(docTemplate);
                }
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
    public Map<String, List<DocAccountTemplateItem>> getDocTemplateMap(SetOrg org) {
        Map<String, List<DocAccountTemplateItem>> resultMap = new TreeMap<>();
        if (!org.getId().equals(this.org.getId())) {
            return resultMap;
        }
        List<DocAccountTemplateItem> docTemplateList = new ArrayList<>();
        String key = getKey(org);
        if (docTemplateMap.containsKey(key)) {
            docTemplateList = docTemplateMap.get(key);
        }
        for (DocAccountTemplateItem docTemplate : docTemplateList) {
            String flag = docTemplate.getFlag();
            List<DocAccountTemplateItem> docTemplateWithFlagList;
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

    public static String getKey(SetOrg org) {
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
     * 获取业务凭证模板信息
     * @return 业务凭证模板信息
     */
    public Map<String, List<DocAccountTemplateItem>> getDocTemplateMap() {
        return docTemplateMap;
    }

    /**
     * 设置业务凭证模板信息
     * @param docTemplateMap 业务凭证模板信息
     */
    public void setDocTemplateMap(Map<String, List<DocAccountTemplateItem>> docTemplateMap) {
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
