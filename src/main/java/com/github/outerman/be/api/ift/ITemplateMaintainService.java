package com.github.outerman.be.api.ift;

import com.github.outerman.be.api.dto.BusinessTemplateDto;

/**
 * Created by shenxy on 10/7/17.
 *
 * 业务模板管理服务
 */
public interface ITemplateMaintainService {
    BusinessTemplateDto create(BusinessTemplateDto templateDto);

    boolean delete(Long orgId, String businessCode);

    BusinessTemplateDto update(BusinessTemplateDto templateDto);

    BusinessTemplateDto query(Long orgId, String businessCode);
}
