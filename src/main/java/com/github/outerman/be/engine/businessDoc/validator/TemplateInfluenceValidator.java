package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.constant.CommonConst;
import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AcmDocAccountTemplate;
import com.github.outerman.be.engine.util.CommonUtil;
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
        // 凭证模板影响因素需要元数据模板对应的字段显示
        // 凭证模板影响因素没有默认记录，则元数据模板对应的字段必填
        // 存货属性影响因素，存货必填且没有默认时，和业务类型存货属性对照表校验
        StringBuilder message = new StringBuilder();
        // 验证每个行业
        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();
        for (Entry<String, List<DocAccountTemplateItem>> entry : docAccountTemplateDto.getAllPossibleTemplate().entrySet()) {
            Map<String, Long> influenceColumnIdMap = new HashMap<>();
            Map<String, List<Long>> influenceValueMap = new HashMap<>();
            for (DocAccountTemplateItem docTemplate : entry.getValue()) {
                String influence = docTemplate.getInfluence();
                if (StringUtil.isEmpty(influence)) {
                    continue;
                }
                Long columnId = CommonUtil.getColumnId(influence);
                if (columnId != null) {
                    influenceColumnIdMap.put(influence, columnId);
                }
                if ("vatTaxpayer".equals(influence) || "vatTaxpayer,taxType".equals(influence) || "vatTaxpayer,qualification".equals(influence)
                        || "formula".equals(influence)) {
                    // 纳税人身份不处理，计税方式在 TemplateTaxRateValidator 中处理，是否认证不处理；TODO 表达式暂不处理
                    continue;
                }
                List<Long> attrList;
                if (influenceValueMap.containsKey(influence)) {
                    attrList = influenceValueMap.get(influence);
                } else {
                    attrList = new ArrayList<>();
                    influenceValueMap.put(influence, attrList);
                }
//                Map<String, String> influenceMap = docTemplate.getInfluenceMap();
//                for (Entry<String, String> item : influenceMap.entrySet()) {
//                    attrList.add(Long.parseLong(item.getValue()));
//                }
            }

            StringBuilder errorMessage = new StringBuilder();
            String key = entry.getKey();
            Long industry = AcmDocAccountTemplate.getValue(key, AcmDocAccountTemplate.INDUSTRY_ID);
            List<SetColumnsTacticsDto> tacticsList = tacticsMap.get(industry);
            for (Entry<String, Long> columnIdEntry : influenceColumnIdMap.entrySet()) {
                Long columnId = columnIdEntry.getValue();
                if (columnId == null || columnId.equals(CommonConst.QUALIFICATION_COLUMN_ID)) {
                    continue;
                }
                Integer flag = getFlag(columnId, tacticsList);
                if (flag == null || flag == 0) {
                    String influenceName = CommonUtil.getInfluenceName(columnIdEntry.getKey());
                    errorMessage.append("影响因素" + influenceName + "需要元数据模板对应的字段" + CommonUtil.getColumnNameById(columnId) + "显示；");
                }
            }

            if (influenceValueMap.isEmpty()) {
                continue;
            }
            List<BusinessAssetDto> businessAssetList = uiTemplateDto.getBusinessAssetList();
            Map<String, BusinessAssetDto> businessAssetMap = new HashMap<>();
            for (BusinessAssetDto businessAsset : businessAssetList) {
                businessAssetMap.put(businessAsset.getInventoryPropertyName(), businessAsset);
            }
            for (Entry<String, List<Long>> influenceEntry : influenceValueMap.entrySet()) {
                List<Long> attrList = influenceEntry.getValue();
                if (attrList.contains(0L)) {
                    continue;
                }
                String influence = influenceEntry.getKey();
                Long columnId = CommonUtil.getColumnId(influence);
                if (columnId == null) {
                    errorMessage.append(influence + " 未知影响因素；");
                    continue;
                }
                Integer flag = getFlag(columnId, tacticsList);
                if (flag == null || flag < 2) {
                    errorMessage.append(CommonUtil.getInfluenceName(influence) + "没有默认记录，并且元数据模板对应的字段" + CommonUtil.getColumnNameById(columnId) + "不必填；");
                } else {
                    if (columnId.equals(CommonConst.INVENTORY_COLUMN_ID)) {
                        for (Long attrId : attrList) {
                            String propertyName = CommonUtil.getInventoryPropertyName(attrId);
                            if (!businessAssetMap.containsKey(propertyName)) {
                                errorMessage.append("存货属性" + propertyName + "在业务类型存货属性对照表中不存在；");
                            }
                        }
                    }
                }
            }
            if (errorMessage.length() != 0) {
                Long accountingStandard = AcmDocAccountTemplate.getValue(key, AcmDocAccountTemplate.ACCOUNTING_STANDARDS_ID);
                String industryStr = CommonUtil.getAccountingStandardName(accountingStandard) + CommonUtil.getIndustryName(industry) + "行业，";
                message.append(industryStr + errorMessage.toString());
            }
        }
        if (message.length() == 0) {
            return "";
        }
        return "业务类型 " + docAccountTemplateDto.getBusinessCode() + " 凭证模板影响因素校验失败：" + message.toString();
    }

    private Integer getFlag(Long columnId, List<SetColumnsTacticsDto> tacticsList) {
        Integer flag = null;
        if (tacticsList == null) {
            return flag;
        }
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
