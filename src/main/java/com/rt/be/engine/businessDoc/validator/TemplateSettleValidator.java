package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.dto.AcmDocAccountTemplateDto;
import com.rt.be.api.dto.AcmPaymentTemplateDto;
import com.rt.be.api.dto.AcmUITemplateDto;
import com.rt.be.api.vo.AcmSortReceipt;
import com.rt.be.api.vo.FiDocDto;
import com.rt.be.api.vo.SetOrg;
import com.rt.be.engine.businessDoc.businessTemplate.BusinessTemplate;
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
        //TODO:  各模板是否同时结算或不结算
        return null;
    }
}
