package com.github.outerman.be.engine.businessDoc.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
import com.github.outerman.be.api.dto.AcmUITemplateDto;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AcmDocAccountTemplate;
import com.github.outerman.be.engine.util.CommonUtil;
import com.github.outerman.be.engine.util.StringUtil;

/**
 * 校验凭证模板金额来源表达式
 * @author gaoxue
 */
@Component
public class TemplateFundsourceValidator implements ITemplateValidatable {

    private static List<String> EXCLUDE_FIELDNAME_LIST = new ArrayList<>();

    static {
        EXCLUDE_FIELDNAME_LIST.add("privilegeTaxAmount"); // 减免税额
        EXCLUDE_FIELDNAME_LIST.add("isDeduction"); // 抵扣
        EXCLUDE_FIELDNAME_LIST.add("isQualification"); // 认证 前端自己控制
    }

    @Autowired
    public TemplateFundsourceValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(AcmDocAccountTemplateDto docAccountTemplateDto, AcmPaymentTemplateDto paymentTemplateDto, AcmUITemplateDto uiTemplateDto) {
        // 凭证模板金额来源表达式使用的字段需要元数据模板对应的字段至少在一种票据类型下显示，根据现有实现只校验金额字段
        StringBuilder message = new StringBuilder();
        // 验证每个行业
        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();
        for (Entry<String, List<DocAccountTemplateItem>> entry : docAccountTemplateDto.getAllPossibleTemplate().entrySet()) {
            StringBuilder errorMessage = new StringBuilder();

            String key = entry.getKey();
            Long industry = AcmDocAccountTemplate.getValue(key, AcmDocAccountTemplate.INDUSTRY_ID);
            List<SetColumnsTacticsDto> tacticsList = tacticsMap.get(industry);
            for (DocAccountTemplateItem docTemplate : entry.getValue()) {
                String fundsource = docTemplate.getFundSource();
                if (StringUtil.isEmpty(fundsource)) {
                    continue;
                }
                List<String> columnNameList = new ArrayList<>();
                //List<String> amountFieldNameList = AmountGetter.getAmountFieldNameList(fundsource);
                List<String> amountFieldNameList = new ArrayList<>();
                amountFieldNameList.add("amount");
                for (String fieldName : amountFieldNameList) {
                    if (EXCLUDE_FIELDNAME_LIST.contains(fieldName)) {
                        continue;
                    }
                    Long columnId = CommonUtil.getColumnIdByFieldName(fieldName);
                    if (columnId == null) {
                        errorMessage.append("分录" + docTemplate.getFlag() + "金额来源无法识别字段" + fieldName + "；");
                        continue;
                    }
                    if (!isVisible(columnId, tacticsList)) {
                        columnNameList.add(CommonUtil.getColumnNameById(columnId));
                    }
                }
                if (!columnNameList.isEmpty()) {
                    errorMessage.append("分录" + docTemplate.getFlag() + "金额来源需要元数据模板对应的字段" + String.join("、", columnNameList) + "显示；");
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
        return "业务类型 " + docAccountTemplateDto.getBusinessCode() + " 凭证模板金额来源校验失败：" + message.toString();
    }

    private boolean isVisible(Long columnId, List<SetColumnsTacticsDto> tacticsList) {
        boolean visible = false;
        if (tacticsList == null) {
            return visible;
        }
        for (SetColumnsTacticsDto tactics : tacticsList) {
            Long columnsId = tactics.getColumnsId();
            if (columnId.equals(columnsId)) {
                Integer flag = tactics.getFlag();
                visible = !(flag == null || flag == 0);
                if (visible) { // 多种票据类型只要有一种票据类型下显示就返回
                    return visible;
                }
            }
        }
        return visible;
    }

}
