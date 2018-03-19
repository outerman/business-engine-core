package com.github.outerman.be.engine.businessDoc.businessTemplate;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.github.outerman.be.engine.businessDoc.validator.IValidatable;
import com.github.outerman.be.engine.util.CommonUtil;
import com.github.outerman.be.engine.util.StringUtil;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的业务类 相关模板
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AcmDocAccountTemplate implements IValidatable {

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
            if (docTemplateDto.getAllPossibleTemplate().get(key) == null) {
                docTemplateDto.getAllPossibleTemplate().put(key, new ArrayList<>());
            }
            docTemplateDto.getAllPossibleTemplate().get(key).add(template);
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

    private List<DocAccountTemplateItem> getDocTemplate(List<DocAccountTemplateItem> docTemplateListWithFlag, AcmSortReceiptDetail detail) {
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
        Map<String, List<DocAccountTemplateItem>> map = docTemplateDto.getAllPossibleTemplate();
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

    @Override
    public String validate() {
        // 影响因素取值有数据时，影响因素必须要有值；影响因素有值时，对应的取值必须有数据；
        // 同一分组纳税人、计税方式，纳税人、认证影响因素需要两条记录
        String errorMessage = "业务类型 " + docTemplateDto.getBusinessCode() + " 凭证模板数据校验失败：";
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = docTemplateDto.getAllPossibleTemplate();
        if (docTemplateMap.isEmpty()) {
            return errorMessage + "缺少凭证模板数据；";
        }

        StringBuilder message = new StringBuilder();
        for (Entry<String, List<DocAccountTemplateItem>> entry : docTemplateMap.entrySet()) {
            StringBuilder industryMessage = new StringBuilder();
            Map<String, Integer> influenceCountMap = new HashMap<>();
            for (DocAccountTemplateItem docTemplate : entry.getValue()) {
                String fundSource = docTemplate.getFundSource();
                if (StringUtil.isEmpty(fundSource)) {
                    industryMessage.append("分录" + docTemplate.getFlag() + "金额来源不能为空；");
                    continue;
                }
                if (!AmountGetter.validateExpression(fundSource)) {
                    industryMessage.append("分录" + docTemplate.getFlag() + "金额来源" + AmountGetter.fundsource2Chinese(fundSource) + "格式不正确；");
                    continue;
                }
                String influence = docTemplate.getInfluence();
                if ("vatTaxpayer,taxType".equals(influence)) { //  || "vatTaxpayer,qualification".equals(influence) 认证影响因素两条的校验去掉
                    String countKey = influence + "_" + docTemplate.getFlag();
                    Integer count = 1;
                    if (influenceCountMap.containsKey(countKey)) {
                        count += influenceCountMap.get(countKey);
                    }
                    influenceCountMap.put(countKey, count);
                }
            }
            for (Entry<String, Integer> countEntry : influenceCountMap.entrySet()) {
                if (countEntry.getValue() != 2) {
                    String[] key = countEntry.getKey().split("_");
                    String influence = key[0];
                    String flag = key[1];
                    industryMessage.append("分录" + flag + "影响因素" + CommonUtil.getInfluenceName(influence) + "必须有两条记录；");
                }
            }
            if (industryMessage.length() != 0) {
                String key = entry.getKey();
                Long industry = getValue(key, INDUSTRY_ID);
                Long accountingStandard = getValue(key, ACCOUNTING_STANDARDS_ID);
                String industryErrorMessage = CommonUtil.getAccountingStandardName(accountingStandard) + CommonUtil.getIndustryName(industry) + "行业，";
                message.append(industryErrorMessage + industryMessage.toString());
            }
        }
        if (message.length() == 0) {
            return "";
        }
        return errorMessage + message.toString();
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
