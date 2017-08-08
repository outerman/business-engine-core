package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AcmDocAccountTemplate;
import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
import com.github.outerman.be.api.dto.AcmUITemplateDto;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 19/7/17.
 *
 * 验证模板, 支持的行业是否一致
 */
@Component
public class TemplateIndustryValidator implements ITemplateValidatable {
    @Autowired
    public TemplateIndustryValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(AcmDocAccountTemplateDto docAccountTemplateDto, AcmPaymentTemplateDto paymentTemplateDto, AcmUITemplateDto uiTemplateDto) {
        StringBuilder errorMessage = new StringBuilder();

        // 行业_会计准则为 key，业务类型凭证模板信息
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = docAccountTemplateDto.getAllPossibleTemplate();
        // 行业为 key，业务类型元数据信息
        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();

        // 对应行业业务类型元数据存在，必须要有对应的两个会计准则（2007、2013）凭证模板信息
        String businessCode = "业务类型 " + docAccountTemplateDto.getBusinessCode();
        for (Entry<Long, List<SetColumnsTacticsDto>> tacticsEntry : tacticsMap.entrySet()) {
            Long industry = tacticsEntry.getKey();

            // 凭证模板区分会计准则，每个会计准则都需要验证
            String industryStr = "，行业 " + industry;
            for (Long accountingStandard : AcmConst.ACCOUNTING_STANDARD_ID_LIST) {
                String accountingStandardStr = "，会计准则 " + accountingStandard;
                String key = AcmDocAccountTemplate.getKey(industry, accountingStandard.intValue());
                if (!docTemplateMap.containsKey(key)) {
                    errorMessage.append(businessCode + industryStr + accountingStandardStr + " 缺少凭证模板数据；");
                }
            }
        }
        return errorMessage.toString();
    }
}
