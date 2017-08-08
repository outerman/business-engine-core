package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.dto.AcmDocAccountTemplateDto;
import com.rt.be.api.dto.AcmPaymentTemplateDto;
import com.rt.be.api.dto.AcmUITemplateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 19/7/17.
 *
 * 验证模板, 税率必填的情况下, 是否有明确可选税率范围
 */
@Component
public class TemplateTaxRateValidator implements ITemplateValidatable {

    @Autowired
    public TemplateTaxRateValidator(ValidatorManager validatorManager) {
        validatorManager.addValidator(this);
    }

    @Override
    public String validate(AcmDocAccountTemplateDto docAccountTemplateDto, AcmPaymentTemplateDto paymentTemplateDto, AcmUITemplateDto uiTemplateDto) {
        // 税率必填的情况下, 是否有明确可选税率范围
        // 税率档案的存在，在 excel 元数据导入的时候直接验证税率的正确性
        return null;
    }
}
