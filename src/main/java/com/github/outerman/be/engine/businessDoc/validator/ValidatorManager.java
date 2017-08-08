package com.github.outerman.be.engine.businessDoc.validator;

import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

/**
 * Created by shenxy on 19/7/17.
 *
 * 统一管理各个validator
 */
@Component
public class ValidatorManager {
    LinkedHashSet<IBusinessDocValidatable> businessDocValidators = new LinkedHashSet<>();
    LinkedHashSet<ITemplateValidatable> templateValidators = new LinkedHashSet<>();

//    public enum ValidateType {
//        Doc,            //生成的凭证的验证
//        Template        //模板的静态验证
//    }

    public boolean addValidator(IBusinessDocValidatable validatable) {
//        if (businessDocValidators.get(ValidateType.Doc) == null) {
//            businessDocValidators.put(ValidateType.Doc, new LinkedHashSet<>());
//        }
//        businessDocValidators.get(ValidateType.Doc).add(validatable);
        businessDocValidators.add(validatable);
        return true;
    }

    public LinkedHashSet<IBusinessDocValidatable> getBusinessDocValidators() {
        return businessDocValidators;
    }

    public boolean addValidator(ITemplateValidatable validatable) {
        templateValidators.add(validatable);
        return true;
    }

    public LinkedHashSet<ITemplateValidatable> getTemplateValidators() {
        return templateValidators;
    }
}
