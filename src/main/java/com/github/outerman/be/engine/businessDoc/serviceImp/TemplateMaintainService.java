package com.github.outerman.be.engine.businessDoc.serviceImp;

import com.github.outerman.be.api.dto.BusinessTemplateDto;
import com.github.outerman.be.api.ift.ITemplateMaintainService;
import org.springframework.stereotype.Service;

/**
 * Created by shenxy on 7/7/17.
 * 业务模板,管理服务
 *
 * TODO: 需要设计dto时,再移动到interface工程
 */
@Service
public class TemplateMaintainService implements ITemplateMaintainService {

    @Override
    public BusinessTemplateDto create(BusinessTemplateDto templateDto) {
        //TODO: 创建时,需要区分orgId = 0(系统管理员维护系统模板) 或 orgId != 0 (用户维护个性化模板)
        return null;
    }

    @Override
    public boolean delete(Long orgId, Long businessCode) {
        //TODO: 创建时,需要区分orgId = 0(系统管理员维护系统模板) 或 orgId != 0 (用户维护个性化模板)
        return false;
    }

    @Override
    public BusinessTemplateDto update(BusinessTemplateDto templateDto) {
        //TODO: 创建时,需要区分orgId = 0(系统管理员维护系统模板) 或 orgId != 0 (用户维护个性化模板)
        return null;
    }

    @Override
    public BusinessTemplateDto query(Long orgId, Long businessCode) {
        //TODO: 创建时,需要区分orgId = 0(系统管理员维护系统模板) 或 orgId != 0 (用户维护个性化模板)
        return null;
    }
}
