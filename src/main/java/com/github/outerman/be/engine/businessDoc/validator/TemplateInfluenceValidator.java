package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AcmDocAccountTemplate;
import com.github.outerman.be.engine.util.StringUtil;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
import com.github.outerman.be.api.dto.AcmUITemplateDto;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 19/7/17.
 *
 * 验证模板, 影响因素字段的必填是否一致（凭证模板没有“默认”则为必填）
 */
@Component
public class TemplateInfluenceValidator implements ITemplateValidatable {

    @Autowired
    public TemplateInfluenceValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(AcmDocAccountTemplateDto docAccountTemplateDto, AcmPaymentTemplateDto paymentTemplateDto, AcmUITemplateDto uiTemplateDto) {
        // 影响因素字段的必填是否一致（凭证模板没有“默认”则为必填）
        // 凭证模板影响因素有对应的字段并且没有默认记录，则元数据必填
        StringBuilder errorMessage = new StringBuilder();

        // 行业_会计准则为 key，业务类型凭证模板信息
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = docAccountTemplateDto.getAllPossibleTemplate();
        // 行业为 key，业务类型元数据信息
        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();

        // 验证每个行业
        String businessCode = "业务类型 " + docAccountTemplateDto.getBusinessCode();
        for (Entry<String, List<DocAccountTemplateItem>> entry : docTemplateMap.entrySet()) {
            String key = entry.getKey();
            Long industry = AcmDocAccountTemplate.getIndustry(key);
            Integer accountingStandard = AcmDocAccountTemplate.getAccountingStandard(key);
            List<DocAccountTemplateItem> docTemplateList = entry.getValue();
            Map<String, Boolean> influenceMap = new HashMap<>();
            for (DocAccountTemplateItem docTemplate : docTemplateList) {
                String influence = docTemplate.getInfluence();
                if (StringUtil.isEmpty(influence)) {
                    continue;
                }
                Boolean hasDefault = false;
                if (influenceMap.containsKey(influence)) {
                    hasDefault = influenceMap.get(influence);
                    if (hasDefault) {
                        continue;
                    }
                }
                if ("departmentAttr".equals(influence)) {
                    Long departmentAttr = docTemplate.getDepartmentAttr();
                    if (departmentAttr != null && departmentAttr == 0) {
                        hasDefault = true;
                    }
                } else if ("departmentAttr,personAttr".equals(influence)) {
                    Long departmentAttr = docTemplate.getDepartmentAttr();
                    Long personAttr = docTemplate.getPersonAttr();
                    if (departmentAttr != null && departmentAttr == 200000000000071L && personAttr != null && personAttr == 0) {
                        hasDefault = true;
                    }
                } else if ("vatTaxpayer".equals(influence) || "vatTaxpayer,taxType".equals(influence) || "vatTaxpayer,qualification".equals(influence)) {
                    // 纳税人身份不处理，计税方式在 TemplateTaxRateValidator 中处理，是否认证不处理
                    continue;
                } else {
                    Long extendAttr = docTemplate.getExtendAttr();
                    if (extendAttr != null && extendAttr == 0) {
                        hasDefault = true;
                    }
                }
                influenceMap.put(influence, hasDefault);
            }
            if (influenceMap.isEmpty()) {
                continue;
            }

            String industryStr = "，行业 " + industry + "，会计准则 " + accountingStandard;
            List<SetColumnsTacticsDto> tacticsList = tacticsMap.get(industry);
            for (Entry<String, Boolean> influenceEntry : influenceMap.entrySet()) {
                String influence = influenceEntry.getKey();
                Boolean hasDefault = influenceEntry.getValue();
                if (!hasDefault) {
                    Long columnId = getColumnId(influence);
                    if (columnId == null) {
                        errorMessage.append(influence + " 未知影响因素；");
                        continue;
                    }
                    Integer flag = getFlag(columnId, tacticsList);
                    if (flag == null || flag < 2) {
                        errorMessage.append(businessCode + industryStr + "，字段 " + columnId + " 元数据不必填，凭证模板有对应影响因素且没有默认记录");
                    }
                }
            }
        }
        return errorMessage.toString();
    }

    private Long getColumnId(String influence) {
        Long columnId = null;
        if ("assetAttr".equals(influence)) {
            columnId = 8L;
        } else if ("departmentAttr".equals(influence)) {
            columnId = 4L;
        } else if ("departmentAttr,personAttr".equals(influence)) {
            columnId = 5L;
        } else if ("punishmentAttr".equals(influence)) {
            columnId = 23L;
        } else if ("accountInAttr".equals(influence)) {
            columnId = 25L;
        } else if ("accountOutAttr".equals(influence)) {
            columnId = 14L;
        } else if ("borrowAttr".equals(influence)) {
            columnId = 24L;
        }
        return columnId;
    }

    private Integer getFlag(Long columnId, List<SetColumnsTacticsDto> tacticsList) {
        Integer flag = null;
        for (SetColumnsTacticsDto tactics : tacticsList) {
            Long columnsId = tactics.getColumnsId();
            if (columnId.equals(columnsId)) {
                flag = tactics.getFlag();
                break;
            }
        }
        return flag;
    }

}
