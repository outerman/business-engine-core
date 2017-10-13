package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
import com.github.outerman.be.api.dto.AcmUITemplateDto;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;
import com.github.outerman.be.engine.businessDoc.businessTemplate.AcmDocAccountTemplate;
import com.github.outerman.be.engine.util.CommonUtil;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 19/7/17.
 *
 * 验证模板, 各模板是否同时结算或不结算
 */
@Component
public class TemplateSettleValidator implements ITemplateValidatable {

    @Autowired
    public TemplateSettleValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(AcmDocAccountTemplateDto docAccountTemplateDto, AcmPaymentTemplateDto paymentTemplateDto, AcmUITemplateDto uiTemplateDto) {
        // 元数据模板银行账号（结算方式）显示（现在显示即必填）时，凭证模板对方科目来源必须存在结算方式
        if (docAccountTemplateDto.getBusinessCode().toString().startsWith("40")) {
            // 存取现金/内部账户互转 都是本表自平
            return "";
        }

        String errorMessage = "业务类型 " + docAccountTemplateDto.getBusinessCode() + " 元数据模板银行账户（结算方式）显示时，凭证模板需要存在对应科目来源为结算方式的数据：";
        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = docAccountTemplateDto.getAllPossibleTemplate();

        // 验证每个行业
        StringBuilder industryMessage = new StringBuilder();
        for (Entry<Long, List<SetColumnsTacticsDto>> entry : tacticsMap.entrySet()) {
            boolean accountVisible = false;
            for (SetColumnsTacticsDto tactics : entry.getValue()) {
                Long columnId = tactics.getColumnsId();
                Integer flag = tactics.getFlag();
                if (columnId.equals(AcmConst.BANK_ACCOUNT_COLUMN_ID) && flag != null && flag > 0) {
                    accountVisible = true;
                    break;
                }
            }
            if (!accountVisible) {
                continue;
            }

            // 凭证模板区分会计准则，每个会计准则都需要验证
            Long industry = entry.getKey();
            String industryStr = CommonUtil.getIndustryName(industry) + "行业";
            for (Long accountingStandard : AcmConst.ACCOUNTING_STANDARD_ID_LIST) {
                String key = AcmDocAccountTemplate.getKey(industry, accountingStandard.intValue());
                if (!docTemplateMap.containsKey(key)) {
                    continue;
                }

                boolean docTemplateHasSettle = false;
                List<DocAccountTemplateItem> docTemplateList = docTemplateMap.get(key);
                for (DocAccountTemplateItem docTemplate : docTemplateList) {
                    Boolean isSettlement = docTemplate.getIsSettlement();
                    if (isSettlement != null && isSettlement) {
                        docTemplateHasSettle = true;
                        break;
                    }
                }
                if (!docTemplateHasSettle) {
                    industryMessage.append(CommonUtil.getAccountingStandardName(accountingStandard.intValue()) + industryStr + "；");
                }
            }
        }
        if (industryMessage.length() == 0) {
            return "";
        }
        return errorMessage + industryMessage.toString();
    }

}
