package com.github.outerman.be.template;

import java.util.HashMap;
import java.util.Map;

import com.github.outerman.be.model.Org;

/**
 * Created by shenxy on 19/7/17.
 *
 * 管理模板
 * <p>模板数据会动态修改，在数据提供 {@code ITemplateProvider} 方进行缓存的处理
 * <p>模板管理只按照企业、业务类型编码，缓存 BusinessTemplate 对象
 */
public final class TemplateManager {

    private static TemplateManager instance;

    public static TemplateManager getInstance() {
        if (instance == null) {
            synchronized (TemplateManager.class) {
                if (instance == null) {
                    instance = new TemplateManager();
                }
            }
        }
        return instance;
    }

    Map<String, BusinessTemplate> businessTemplateMap = new HashMap<>();

    public BusinessTemplate fetch(Org org, String businessCode, ITemplateProvider provider) {
        BusinessTemplate result;
        String key = getKey(org.getId(), businessCode);
        if (businessTemplateMap.containsKey(key)) {
            result = businessTemplateMap.get(key);
        } else {
            result = new BusinessTemplate();
        }
        result.init(org, businessCode, provider);
        return result;
    }

    private String getKey(Long orgId, String businessCode) {
        return orgId.toString() + "_" + businessCode;
    }

    private TemplateManager() {
        // avoid instantiate
    }
}
