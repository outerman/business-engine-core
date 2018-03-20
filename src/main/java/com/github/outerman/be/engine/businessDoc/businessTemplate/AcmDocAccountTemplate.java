package com.github.outerman.be.engine.businessDoc.businessTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.api.vo.AcmSortReceiptDetail;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.util.StringUtil;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的业务类 相关模板
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AcmDocAccountTemplate {

    public static final String INDUSTRY_ID = "industryId";

    public static final String ACCOUNTING_STANDARDS_ID = "accountingStandardsId";

    public static final String VAT_TAXPAYER_ID = "vatTaxpayerId";

    private AcmDocAccountTemplateDto docTemplateDto;

    /**
     * 初始化方法，按照企业、业务编码，获取业务凭证模板数据
     * <p>企业 id 为 0 时获取系统预置数据
     * @param org 企业信息
     * @param businessCode 业务编码
     * @param templateProvider
     */
    public void init(SetOrg org, String businessCode, ITemplateProvider templateProvider) {
        docTemplateDto = new AcmDocAccountTemplateDto();
        docTemplateDto.setOrg(org);
        docTemplateDto.setBusinessCode(businessCode);

        List<DocAccountTemplateItem> all = templateProvider.getBusinessTemplateByCode(org.getId(), businessCode);
        all.forEach(template -> {
            String key = getKey(org);
            if (docTemplateDto.getDocTemplateMap().get(key) == null) {
                docTemplateDto.getDocTemplateMap().put(key, new ArrayList<>());
            }
            docTemplateDto.getDocTemplateMap().get(key).add(template);
            if (!docTemplateDto.getCodeList().contains(template.getAccountCode())) {
                docTemplateDto.getCodeList().add(template.getAccountCode());
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
        String businessCode = docTemplateDto.getBusinessCode();
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
        if (!org.getId().equals(docTemplateDto.getOrg().getId())) {
            return resultMap;
        }
        List<DocAccountTemplateItem> docTemplateList = new ArrayList<>();
        String key = getKey(org);
        Map<String, List<DocAccountTemplateItem>> map = docTemplateDto.getDocTemplateMap();
        if (map.containsKey(key)) {
            docTemplateList = map.get(key);
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

    public AcmDocAccountTemplateDto getDocTemplateDto() {
        return docTemplateDto;
    }

    public void setDocTemplateDto(AcmDocAccountTemplateDto docTemplateDto) {
        this.docTemplateDto = docTemplateDto;
    }

}
