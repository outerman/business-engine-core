package com.rt.be.engine.businessDoc.validator;

import com.rt.be.api.dto.AcmDocAccountTemplateDto;
import com.rt.be.api.dto.AcmPaymentTemplateDto;
import com.rt.be.api.dto.AcmUITemplateDto;
import com.rt.be.api.vo.AcmSortReceipt;
import com.rt.be.api.vo.FiDocDto;
import com.rt.be.api.vo.SetOrg;
import com.rt.be.engine.businessDoc.businessTemplate.BusinessTemplate;

/**
 * Created by shenxy on 7/7/17.
 *
 * 实现该接口,表示可验证
 */
public interface ITemplateValidatable {
    String validate(AcmDocAccountTemplateDto docAccountTemplateDto, AcmPaymentTemplateDto paymentTemplateDto, AcmUITemplateDto uiTemplateDto);
}
