package com.github.outerman.be.engine.businessDoc.businessTemplate;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.api.vo.AcmSortReceiptDetail;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.businessDoc.validator.IValidatable;
import com.github.outerman.be.engine.util.CommonUtil;
import com.github.outerman.be.engine.util.StringUtil;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的业务类 相关模板
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AcmDocAccountTemplate implements IValidatable {

    private AcmDocAccountTemplateDto docTemplateDto;

    private ITemplateProvider templateProvider;

    // 初始化方法, orgId可能为0; 如不为0, 则初始化公共模板(orgId=0)以及个性化模板
    public void init(SetOrg org, String businessCode, ITemplateProvider templateProvider) {
        this.templateProvider = templateProvider;
        docTemplateDto = new AcmDocAccountTemplateDto();
        // 该业务所有行业和准则的模板
        List<DocAccountTemplateItem> all = templateProvider.getBusinessTemplateByCode(org.getId(), Long.parseLong(businessCode));
        all.forEach(template -> {
            String key = getKey(template.getIndustry(), template.getAccountingStandardsId());
            if (docTemplateDto.getAllPossibleTemplate().get(key) == null) {
                docTemplateDto.getAllPossibleTemplate().put(key, new ArrayList<>());
            }

            docTemplateDto.getAllPossibleTemplate().get(key).add(template);
        });

        for (DocAccountTemplateItem acmBusinessDocTemplate : all) {
            if (!docTemplateDto.getCodeList().contains(acmBusinessDocTemplate.getAccountCode())) {
                docTemplateDto.getCodeList().add(acmBusinessDocTemplate.getAccountCode());
            }
        }

        docTemplateDto.setOrg(org);
        docTemplateDto.setBusinessCode(businessCode);
    }

    /**
     * 根据流水账收支明细获取匹配的凭证模板记录
     * @param org 组织信息
     * @param detail 流水账收支明细
     * @return 凭证模板记录
     */
    public List<DocAccountTemplateItem> getDocTemplate(SetOrg org, AcmSortReceiptDetail detail) {
        List<DocAccountTemplateItem> resultList = new ArrayList<>();
        if (detail == null) {
            return resultList;
        }
        String businessCode = docTemplateDto.getBusinessCode();
        if (!businessCode.equals(detail.getBusinessCode().toString())) {
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
        if (docTemplateListWithFlag.size() == 1) { // 凭证模板只有一条数据且没有影响因素直接返回
            DocAccountTemplateItem docTemplate = docTemplateListWithFlag.get(0);
            if (docTemplate.getInfluence() == null) {
                resultList.add(docTemplate);
                return resultList;
            }
        }

        DocAccountTemplateItem defaultDocTemplate = null; // 默认匹配规则，影响因素值为 0 的记录
        for (DocAccountTemplateItem docTemplate : docTemplateListWithFlag) {
            String influence = docTemplate.getInfluence();
            if (StringUtil.isEmpty(influence)) { // 凭证模板只有一条数据且没有影响因素时已经处理，其他情况都应该是有影响因素的
                continue;
            }

            Long departmentAttr = docTemplate.getDepartmentAttr();
            Long personAttr = docTemplate.getPersonAttr();
            Long extendAttr = docTemplate.getExtendAttr();
            Long detailDepartmentAttr = detail.getDepartmentProperty();
            Long detailPersonAttr = detail.getEmployeeAttribute();
            if ("departmentAttr".equals(influence)) { // 部门属性
                if (detailDepartmentAttr != null && detailDepartmentAttr.equals(departmentAttr)) {
                    resultList.add(docTemplate);
                } else if (departmentAttr == 0) { // 部门属性影响因素默认规则
                    defaultDocTemplate = docTemplate;
                }
            } else if ("departmentAttr,personAttr".equals(influence)) { // 部门属性，人员属性
                if (detailDepartmentAttr != null && detailDepartmentAttr.equals(departmentAttr)) {
                    if (detailDepartmentAttr.equals(AcmConst.DEPTPROPERTY_002)) { // 生产部门，匹配人员属性
                        if (detailPersonAttr != null && detailPersonAttr.equals(personAttr)) {
                            resultList.add(docTemplate);
                            continue;
                        }
                    } else { // 非生产部门，当作只有部门影响因素处理
                        resultList.add(docTemplate);
                        continue;
                    }
                }
                if (personAttr == 0) { // 部门属性，人员属性影响因素默认规则
                    defaultDocTemplate = docTemplate;
                }
            } else if ("vatTaxpayer".equals(influence) || "vatTaxpayer,qualification".equals(influence) || "vatTaxpayer,taxType".equals(influence)) {
                // 纳税人性质，纳税人性质、认证，纳税人性质、计税方式
                Long vatTaxpayer = docTemplateDto.getOrg().getVatTaxpayer();
                if (!vatTaxpayer.equals(docTemplate.getVatTaxpayer())) {
                    continue;
                }
                if ("vatTaxpayer".equals(influence)) {
                    resultList.add(docTemplate);
                } else if ("vatTaxpayer,qualification".equals(influence)) {
                    Boolean detailQualification = (detail.getIsQualification() == null || detail.getIsQualification() == 0) ? false : true;
                    Boolean qualification = docTemplate.getQualification();
                    if (detailQualification.equals(qualification)) {
                        resultList.add(docTemplate);
                    }
                } else if ("vatTaxpayer,taxType".equals(influence)) {
                    Boolean isGeneral = docTemplate.getTaxType();
                    if (isGeneral == null) {
                        continue;
                    }
                    Long taxRateId = detail.getTaxRateId();
                    if (CommonUtil.isSimple(taxRateId, templateProvider) && !isGeneral) {
                        resultList.add(docTemplate);
                    } else if (CommonUtil.isGeneral(taxRateId, templateProvider) && isGeneral) {
                        resultList.add(docTemplate);
                    }
                }
            } else if ("punishmentAttr".equals(influence)) { // 罚款性质
                Long penaltyType = detail.getPenaltyType();
                if (penaltyType != null && penaltyType.equals(extendAttr)) {
                    resultList.add(docTemplate);
                } else if (extendAttr == 0) {
                    defaultDocTemplate = docTemplate;
                }
            } else if ("borrowAttr".equals(influence)) { // 借款期限
                Long loanTerm = detail.getLoanTerm();
                if (loanTerm != null && loanTerm.equals(extendAttr)) {
                    resultList.add(docTemplate);
                } else if (extendAttr == 0) {
                    defaultDocTemplate = docTemplate;
                }
            } else if ("assetAttr".equals(influence)) { // 资产属性
                Long assetAttr = detail.getAssetAttr();
                if (assetAttr != null && assetAttr.equals(extendAttr)) {
                    resultList.add(docTemplate);
                } else if (extendAttr == 0) {
                    defaultDocTemplate = docTemplate;
                }
            } else if ("accountInAttr".equals(influence)) { // 账户属性流入
                Long inBankAccountTypeId = detail.getInBankAccountTypeId();
                if (inBankAccountTypeId != null && inBankAccountTypeId.equals(extendAttr)) {
                    resultList.add(docTemplate);
                } else if (extendAttr == 0) {
                    defaultDocTemplate = docTemplate;
                }
            } else if ("accountOutAttr".equals(influence)) { // 账户属性流出
                Long bankAccountTypeId = detail.getBankAccountTypeId();
                if (bankAccountTypeId != null && bankAccountTypeId.equals(extendAttr)) {
                    resultList.add(docTemplate);
                } else if (extendAttr == 0) {
                    defaultDocTemplate = docTemplate;
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
        String key = getKey(org.getIndustry(), org.getAccountingStandards().intValue());
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
        // 计税方式、认证影响因素对应凭证模板需要两条
        // 影响因素取值有数据时，影响因素类型必须要有值
        StringBuilder errorMessage = new StringBuilder();

        String businessCode = "业务类型 " + docTemplateDto.getBusinessCode();
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = docTemplateDto.getAllPossibleTemplate();
        if (docTemplateMap.isEmpty()) {
            errorMessage.append(businessCode + " 缺少凭证模板数据；");
            return errorMessage.toString();
        }
        for (Entry<String, List<DocAccountTemplateItem>> entry : docTemplateMap.entrySet()) {
            String key = entry.getKey();
            Long industry = getIndustry(key);
            Integer accountingStandard = getAccountingStandard(key);
            String industryStr = "， 行业 " + industry + "，会计准则 " + accountingStandard;

            List<DocAccountTemplateItem> docTemplateList = entry.getValue();
            Map<String, Integer> influenceCountMap = new HashMap<>();
            for (DocAccountTemplateItem docTemplate : docTemplateList) {
                String influence = docTemplate.getInfluence();
                if (StringUtil.isEmpty(influence) && hasInfluenceValue(docTemplate)) {
                    errorMessage.append(businessCode + industryStr + " 凭证模板影响因素缺少数据；");
                    continue;
                }
                String fundSource = docTemplate.getFundSource();
                if (StringUtil.isEmpty(fundSource)) {
                    errorMessage.append(businessCode + industryStr + "金额来源不能为空；");
                }
                // TODO 校验金额表达式正确性
                String countKey = influence + "_" + docTemplate.getFlag();
                Integer count = influenceCountMap.get(countKey);
                if (count == null) {
                    count = 0;
                }
                if ("vatTaxpayer,taxType".equals(influence)) {
                    count++;
                    influenceCountMap.put(countKey, count);
                } else if ("vatTaxpayer,qualification".equals(influence)) {
                    count++;
                    influenceCountMap.put(countKey, count);
                }
            }
            for (Entry<String, Integer> countEntry : influenceCountMap.entrySet()) {
                Integer count = countEntry.getValue();
                if (count != 2) {
                    errorMessage.append(businessCode + industryStr + " 凭证模板数据对应影响因素（计税方式、认证）必须有两条记录；");
                }
            }
        }
        // TODO 相同分类标识下，相同影响因素只能有一条凭证模板数据
        return errorMessage.toString();
    }

    private boolean hasInfluenceValue(DocAccountTemplateItem docTemplate) {
        if (docTemplate == null) {
            return false;
        }
        return docTemplate.getVatTaxpayer() != null || docTemplate.getDepartmentAttr() != null || docTemplate.getPersonAttr() != null
                || (docTemplate.getExtendAttr() != null && docTemplate.getExtendAttr() != 9999999999L) || docTemplate.getTaxType() != null || docTemplate.getQualification() != null;
    }

    public static String getKey(Long industry, Integer standard) {
        return industry + "_" + standard;
    }

    public static Long getIndustry(String key) {
        String[] keyArray = key.split("_");
        if (keyArray.length == 2) {
            return Long.parseLong(keyArray[0]);
        }
        return null;
    }

    public static Integer getAccountingStandard(String key) {
        String[] keyArray = key.split("_");
        if (keyArray.length == 2) {
            return Integer.parseInt(keyArray[1]);
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
