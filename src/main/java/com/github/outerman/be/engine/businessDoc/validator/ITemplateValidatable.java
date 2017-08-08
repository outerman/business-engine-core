package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.api.dto.AcmPaymentTemplateDto;
import com.github.outerman.be.api.dto.AcmUITemplateDto;

/**
 * Created by shenxy on 7/7/17.
 *
 * 实现该接口,表示可验证
 */
public interface ITemplateValidatable {
    String validate(AcmDocAccountTemplateDto docAccountTemplateDto, AcmPaymentTemplateDto paymentTemplateDto, AcmUITemplateDto uiTemplateDto);
}
