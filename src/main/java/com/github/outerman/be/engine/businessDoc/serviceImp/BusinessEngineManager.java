package com.github.outerman.be.engine.businessDoc.serviceImp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shenxy on 10/8/17.
 * 项目入口
 */
public class BusinessEngineManager {

    private static class SingletonHolder{
        private static final BusinessEngineManager INSTANCE = new BusinessEngineManager();
    }

    public static BusinessEngineManager getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private BusinessGenerateService businessGenerateService;
    private TemplateMaintainService templateMaintainService;
    private TemplateValidateService templateValidateService;

    public BusinessEngineManager() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/be-spring-context.xml");
        businessGenerateService = context.getBean(BusinessGenerateService.class);
        templateMaintainService = context.getBean(TemplateMaintainService.class);
        templateValidateService = context.getBean(TemplateValidateService.class);
    }

    public BusinessGenerateService getBusinessGenerateService() {
        return businessGenerateService;
    }

    public TemplateMaintainService getTemplateMaintainService() {
        return templateMaintainService;
    }

    public TemplateValidateService getTemplateValidateService() {
        return templateValidateService;
    }
}
