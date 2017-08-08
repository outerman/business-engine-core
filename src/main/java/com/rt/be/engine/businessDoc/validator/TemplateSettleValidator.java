package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.constant.AcmConst;
import com.rt.be.api.dto.AcmDocAccountTemplateDto;
import com.rt.be.api.dto.AcmPaymentTemplateDto;
import com.rt.be.api.dto.AcmUITemplateDto;
import com.rt.be.api.vo.DocAccountTemplateItem;
import com.rt.be.api.vo.SetColumnsTacticsDto;
import com.rt.be.engine.businessDoc.businessTemplate.AcmDocAccountTemplate;

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
        // TODO 现在判断凭证模板是否有对应的结算信息是根据对方科目来源是结算方式，isSettlement=1，是否应该根据结算凭证模板来判断
        StringBuilder errorMessage = new StringBuilder();

        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = docAccountTemplateDto.getAllPossibleTemplate();

        // 验证每个行业
        String businessCode = "业务类型 " + docAccountTemplateDto.getBusinessCode();
        Long industry;
        List<SetColumnsTacticsDto> tacticsList;
        List<DocAccountTemplateItem> docTemplateList;
        for (Entry<Long, List<SetColumnsTacticsDto>> entry : tacticsMap.entrySet()) {
            industry = entry.getKey();
            tacticsList = entry.getValue();
            boolean bankAccountVisible = false;
            for (SetColumnsTacticsDto tactics : tacticsList) {
                Long columnId = tactics.getColumnsId();
                Integer flag = tactics.getFlag();
                if (columnId.equals(AcmConst.BANK_ACCOUNT_COLUMN_ID) && flag != null && flag > 0) {
                    bankAccountVisible = true;
                    break;
                }
            }
            if (!bankAccountVisible) {
                continue;
            }

            // 凭证模板区分会计准则，每个会计准则都需要验证
            String industryStr = "，行业 " + industry;
            for (Long accountingStandard : AcmConst.ACCOUNTING_STANDARD_ID_LIST) {
                String key = AcmDocAccountTemplate.getKey(industry, accountingStandard.intValue());
                String accountingStandardStr = "，会计准则 " + accountingStandard;
                if (!docTemplateMap.containsKey(key)) {
                    errorMessage.append(businessCode + industryStr + accountingStandardStr + "，缺少凭证模板数据；");
                    continue;
                }

                boolean docTemplateHasSettle = false;
                docTemplateList = docTemplateMap.get(key);
                for (DocAccountTemplateItem docTemplate : docTemplateList) {
                    Boolean isSettlement = docTemplate.getIsSettlement();
                    if (isSettlement != null && isSettlement) {
                        docTemplateHasSettle = true;
                        break;
                    }
                }
                if (!docTemplateHasSettle) {
                    errorMessage.append(businessCode + industryStr + accountingStandardStr + "，凭证模板缺少结算数据；");
                }
            }
        }

        return errorMessage.toString();
    }

}
