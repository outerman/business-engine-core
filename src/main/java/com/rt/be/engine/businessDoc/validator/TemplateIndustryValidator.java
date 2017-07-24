package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.dto.AcmDocAccountTemplateDto;
import com.rt.be.api.dto.AcmPaymentTemplateDto;
import com.rt.be.api.dto.AcmUITemplateDto;
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
        //TODO:支持的行业是否一致
        return null;
    }
}
