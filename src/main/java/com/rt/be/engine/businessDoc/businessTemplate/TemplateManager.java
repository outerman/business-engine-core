package com.rt.be.engine.businessDoc.businessTemplate;

import com.rt.be.api.constant.AcmConst;
import com.rt.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.rt.be.engine.util.SpringContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenxy on 19/7/17.
 *
 * 管理模板, 主要处理缓存,避免重复初始化,浪费性能
 */
@Component
public class TemplateManager {
    Map<String, BusinessTemplate> businessTemplateMap = new HashMap<>();

    @Autowired
    private SpringContextHelper contextHelper;

    public BusinessTemplate fetchBusinessTemplate(Long orgId, Long businessCode, ITemplateProvider provider) {
        BusinessTemplate ret = businessTemplateMap.get(getKey(orgId, businessCode));

        if (ret != null) {
            return ret;
        }

        ret = (BusinessTemplate) contextHelper.getBean(AcmConst.BUSINESS_TEMPLATE);
        ret.init(orgId, businessCode, provider);

        businessTemplateMap.put(getKey(orgId, businessCode), ret);

        return ret;
    }

    private String getKey(Long orgId, Long businessCode) {
        return orgId.toString() + businessCode.toString();
    }
}
