package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.dto.AcmDocAccountTemplateDto;
import com.rt.be.api.dto.AcmPaymentTemplateDto;
import com.rt.be.api.dto.AcmUITemplateDto;
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
        //TODO:  影响因素字段的必填是否一致（凭证模板没有“默认”则为必填）
        return null;
    }
}
