package com.github.outerman.be.engine.businessDoc.validator;

import com.github.outerman.be.api.dto.BusinessTemplateDto;

/**
 * Created by shenxy on 7/7/17.
 *
 * 实现该接口,表示可验证
 */
public interface ITemplateValidatable {
    String validate(BusinessTemplateDto businessTemplate);
}
