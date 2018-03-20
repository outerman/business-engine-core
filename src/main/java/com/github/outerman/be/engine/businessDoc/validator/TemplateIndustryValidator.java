package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AcmDocAccountTemplate;
import com.github.outerman.be.engine.util.CommonUtil;
import com.github.outerman.be.api.constant.CommonConst;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
import com.github.outerman.be.api.dto.BusinessTemplateDto;
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
    public String validate(BusinessTemplateDto businessTemplate) {
        StringBuilder errorMessage = new StringBuilder();

//        // 行业_会计准则为 key，业务类型凭证模板信息
//        Map<String, List<DocAccountTemplateItem>> docTemplateMap = docAccountTemplateDto.getAllPossibleTemplate();
//        // 行业为 key，业务类型元数据信息
//        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();
//
//        // 元数据模板数据存在，凭证模板必须有对应行业的两个会计准则（2007、2013）的数据
//        String businessCode = "业务类型" + docAccountTemplateDto.getBusinessCode();
//        for (Entry<Long, List<SetColumnsTacticsDto>> tacticsEntry : tacticsMap.entrySet()) {
//            Long industry = tacticsEntry.getKey();
//
//            // 凭证模板区分会计准则，每个会计准则都需要验证
//            String industryStr = CommonUtil.getIndustryName(industry) + "行业";
//            for (Long accountingStandard : CommonConst.ACCOUNTING_STANDARD_ID_LIST) {
//                String key = AcmDocAccountTemplate.getKey(industry, accountingStandard.intValue());
//                if (!docTemplateMap.containsKey(key)) {
//                    String accountingStandardStr = CommonUtil.getAccountingStandardName(accountingStandard.intValue());
//                    errorMessage.append(businessCode + industryStr + "元数据模板有数据，" + accountingStandardStr + "缺少凭证模板数据；");
//                }
//            }
//        }
//        // 凭证模板数据存在，元数据模板必须有对应行业的数据
//        for (String key : docTemplateMap.keySet()) {
//            Long industry = AcmDocAccountTemplate.getIndustry(key);
//            if (!tacticsMap.containsKey(industry)) {
//                String industryStr = CommonUtil.getIndustryName(industry) + "行业";
//                errorMessage.append(businessCode + industryStr + "凭证模板有数据，缺少元数据模板数据；");
//            }
//        }
        return errorMessage.toString();
    }
}
