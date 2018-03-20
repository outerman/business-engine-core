package com.github.outerman.be.template;

import java.util.HashMap;
import java.util.Map;

import com.github.outerman.be.model.SetOrg;

/**
 * Created by shenxy on 19/7/17.
 *
 * 管理模板, 主要处理缓存,避免重复初始化,浪费性能
 * <p>模板数据会动态修改，在数据提供 {@code ITemplateProvider} 方进行缓存的处理
 * <p>模板管理只按照企业、业务类型编码，缓存 BusinessTemplate bean 对象
 */
public class TemplateManager {

    Map<String, BusinessTemplate> businessTemplateMap = new HashMap<>();

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

    public BusinessTemplate fetchBusinessTemplate(SetOrg org, String businessCode, ITemplateProvider provider) {
        BusinessTemplate ret;
        String key = getKey(org.getId(), businessCode);
        if (businessTemplateMap.containsKey(key)) {
            ret = businessTemplateMap.get(key);
        } else {
            ret = new BusinessTemplate();
        }
        ret.init(org, businessCode, provider);
        return ret;
    }

    private String getKey(Long orgId, String businessCode) {
        return orgId.toString() + "_" + businessCode;
    }
}
