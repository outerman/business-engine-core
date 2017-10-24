package com.github.outerman.be.engine.businessDoc.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

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

    private static List<String> fieldNameList = new ArrayList<>();

    static {
        fieldNameList.add("amount");
        fieldNameList.add("tax");
        fieldNameList.add("taxInclusiveAmount");
        // fieldNameList.add("privilegeTaxAmount"); // 减免税额
        fieldNameList.add("deductibleInputTax");
        fieldNameList.add("ext0");
        fieldNameList.add("ext1");
        fieldNameList.add("ext2");
        fieldNameList.add("ext3");
        fieldNameList.add("ext4");
        fieldNameList.add("ext5");
        fieldNameList.add("ext6");
        fieldNameList.add("ext7");
        fieldNameList.add("ext8");
        fieldNameList.add("ext9");
    }

    @Autowired
    public TemplateFundsourceValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(AcmDocAccountTemplateDto docAccountTemplateDto, AcmPaymentTemplateDto paymentTemplateDto, AcmUITemplateDto uiTemplateDto) {
        // 凭证模板金额来源表达式使用的字段需要元数据模板对应的字段显示
        StringBuilder message = new StringBuilder();
        // 验证每个行业
        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();
        for (Entry<String, List<DocAccountTemplateItem>> entry : docAccountTemplateDto.getAllPossibleTemplate().entrySet()) {
            StringBuilder errorMessage = new StringBuilder();

            String key = entry.getKey();
            Long industry = AcmDocAccountTemplate.getIndustry(key);
            List<SetColumnsTacticsDto> tacticsList = tacticsMap.get(industry);
            for (DocAccountTemplateItem docTemplate : entry.getValue()) {
                String fundsource = docTemplate.getFundSource();
                if (StringUtil.isEmpty(fundsource)) {
                    continue;
                }
                List<Long> columnIdList = getColumnId(fundsource);
                List<String> columnNameList = new ArrayList<>();
                for (Long columnId : columnIdList) {
                    Integer flag = getFlag(columnId, tacticsList);
                    if (flag == null || flag == 0) {
                        columnNameList.add(CommonUtil.getColumnName(columnId));
                    }
                }
                if (!columnNameList.isEmpty()) {
                    errorMessage.append("分录" + docTemplate.getFlag() + "金额来源需要元数据模板对应的字段" + String.join("、", columnNameList) + "显示；");
                }
            }

            if (errorMessage.length() != 0) {
                Integer accountingStandard = AcmDocAccountTemplate.getAccountingStandard(key);
                String industryStr = CommonUtil.getAccountingStandardName(accountingStandard) + CommonUtil.getIndustryName(industry) + "行业，";
                message.append(industryStr + errorMessage.toString());
            }
        }
        if (message.length() == 0) {
            return "";
        }
        return "业务类型 " + docAccountTemplateDto.getBusinessCode() + " 凭证模板金额来源校验失败：" + message.toString();
    }

    private List<Long> getColumnId(String fundsource) {
        List<Long> idList = new ArrayList<>();
        for (String fieldName : fieldNameList) {
            if (!fundsource.contains(fieldName)) {
                continue;
            }
            if (fieldName.equals("tax")) {
                String regex = ".*[^A-Za-z]+tax[^A-Za-z]+.*";
                if (!fundsource.equals("tax") && !Pattern.matches(regex, fundsource)) {
                    continue;
                }
            }
            Long id = CommonUtil.getColumnIdByFieldName(fieldName);
            if (id != null) {
                idList.add(id);
            }
        }
        return idList;
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
