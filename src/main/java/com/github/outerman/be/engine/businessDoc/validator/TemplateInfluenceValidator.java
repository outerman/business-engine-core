package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AcmDocAccountTemplate;
import com.github.outerman.be.engine.util.StringUtil;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
import com.github.outerman.be.api.dto.AcmUITemplateDto;
import com.github.outerman.be.api.dto.BusinessAssetDto;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;

import java.util.ArrayList;
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
        // 存货属性影响因素，存货必填且没有默认时，和业务类型存货属性对照表校验
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
            Map<String, List<Long>> influenceMap = new HashMap<>();
            for (DocAccountTemplateItem docTemplate : docTemplateList) {
                String influence = docTemplate.getInfluence();
                if (StringUtil.isEmpty(influence)) {
                    continue;
                }
                if ("vatTaxpayer".equals(influence) || "vatTaxpayer,taxType".equals(influence) || "vatTaxpayer,qualification".equals(influence)
                        || "formula".equals(influence)) {
                    // 纳税人身份不处理，计税方式在 TemplateTaxRateValidator 中处理，是否认证不处理；TODO 表达式暂不处理
                    continue;
                }
                List<Long> attrList;
                if (influenceMap.containsKey(influence)) {
                    attrList = influenceMap.get(influence);
                } else {
                    attrList = new ArrayList<>();
                    influenceMap.put(influence, attrList);
                }
                if ("departmentAttr".equals(influence)) {
                    Long departmentAttr = docTemplate.getDepartmentAttr();
                    attrList.add(departmentAttr);
                } else if ("departmentAttr,personAttr".equals(influence)) {
                    Long personAttr = docTemplate.getPersonAttr();
                    attrList.add(personAttr);
                } else {
                    Long extendAttr = docTemplate.getExtendAttr();
                    attrList.add(extendAttr);
                }
            }
            if (influenceMap.isEmpty()) {
                continue;
            }

            List<BusinessAssetDto> businessAssetList = uiTemplateDto.getBusinessAssetList();
            Map<String, BusinessAssetDto> businessAssetMap = new HashMap<>();
            for (BusinessAssetDto businessAsset : businessAssetList) {
                businessAssetMap.put(businessAsset.getInventoryPropertyName(), businessAsset);
            }
            String industryStr = "，行业 " + industry + "，会计准则 " + accountingStandard;
            List<SetColumnsTacticsDto> tacticsList = tacticsMap.get(industry);
            for (Entry<String, List<Long>> influenceEntry : influenceMap.entrySet()) {
                String influence = influenceEntry.getKey();
                List<Long> attrList = influenceEntry.getValue();
                if (!attrList.contains(0L)) {
                    Long columnId = getColumnId(influence);
                    if (columnId == null) {
                        errorMessage.append(influence + " 未知影响因素；");
                        continue;
                    }
                    Integer flag = getFlag(columnId, tacticsList);
                    if (flag == null || flag < 2) {
                        errorMessage.append(businessCode + industryStr + "，字段 " + columnId + " 元数据不必填，凭证模板有对应影响因素且没有默认记录；");
                    } else {
                        if (columnId.equals(AcmConst.INVENTORY_COLUMN_ID)) {
                            for (Long attrId : attrList) {
                                String propertyName = getInventoryPropertyName(attrId);
                                if (propertyName == null) {
                                    errorMessage.append(businessCode + industryStr + "，存货属性影响因素 " + attrId + " 无法识别" );
                                }
                                if (!businessAssetMap.containsKey(propertyName)) {
                                    errorMessage.append(businessCode + industryStr + "，存货属性影响因素 " + propertyName + " 在业务类型存货属性对照表中不存在" );
                                }
                            }
                        }
                    }
                }
            }
        }
        return errorMessage.toString();
    }

    private Long getColumnId(String influence) {
        Long columnId = null;
        if ("assetAttr".equals(influence)) {
            columnId = AcmConst.ASSET_TYPE_COLUMN_ID;
        } else if ("departmentAttr".equals(influence)) {
            columnId = AcmConst.DEPARTMENT_COLUMN_ID;
        } else if ("departmentAttr,personAttr".equals(influence)) {
            columnId = AcmConst.PERSON_COLUMN_ID;
        } else if ("punishmentAttr".equals(influence)) {
            columnId = AcmConst.PENALTY_TYPE_COLUMN_ID;
        } else if ("accountInAttr".equals(influence)) {
            columnId = AcmConst.BANK_ACCOUNT_TWO_COLUMN_ID;
        } else if ("accountOutAttr".equals(influence)) {
            columnId = AcmConst.BANK_ACCOUNT_COLUMN_ID;
        } else if ("borrowAttr".equals(influence)) {
            columnId = AcmConst.LOAN_TERM_COLUMN_ID;
        } else if ("inventoryAttr".equals(influence)) {
            columnId = AcmConst.INVENTORY_COLUMN_ID;
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

    private String getInventoryPropertyName(Long id) {
        String name = null;
        if (id.equals(1L)) {
            name = "商品";
        } else if (id.equals(2L)) {
            name = "原材料";
        } else if (id.equals(3L)) {
            name = "半成品";
        } else if (id.equals(4L)) {
            name = "周转材料";
        }
        return name;
    }

}
