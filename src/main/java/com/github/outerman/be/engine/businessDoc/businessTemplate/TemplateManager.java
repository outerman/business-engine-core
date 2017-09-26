package com.github.outerman.be.engine.businessDoc.businessTemplate;

import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.util.SpringContextHelper;
import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.vo.SetOrg;
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

    public BusinessTemplate fetchBusinessTemplate(SetOrg org, Long businessCode, ITemplateProvider provider) {
        String key = getKey(org.getId(), businessCode);
        BusinessTemplate ret = businessTemplateMap.get(key);
        if (ret != null) {
            return ret;
        }
        ret = (BusinessTemplate) contextHelper.getBean(AcmConst.BUSINESS_TEMPLATE);
        ret.init(org, businessCode, provider);
        businessTemplateMap.put(key, ret);
        return ret;
    }

    private String getKey(Long orgId, Long businessCode) {
        return orgId.toString() + businessCode.toString();
    }

}
