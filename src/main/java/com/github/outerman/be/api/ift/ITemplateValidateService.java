package com.github.outerman.be.api.ift;

import com.github.outerman.be.engine.businessDoc.dataProvider.IFiDocProvider;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.api.dto.BusinessTemplateDto;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITestGenerateProvider;

/**
 * Created by shenxy on 12/7/17.
 *
 * 模板验证服务
 */
public interface ITemplateValidateService {
    // 用"模拟生成凭证"的方式, 验证"数据库里的"模板.
    String validateTemplateByMockDoc(SetOrg setOrg, Long businessCode, ITemplateProvider templateProvider, IFiDocProvider fiDocProvider, ITestGenerateProvider testGenerateProvider);

    // 用"模拟生成凭证"的方式, 验证传入的模板(待新增)
    String validateTemplateByMockDoc(SetOrg setOrg, BusinessTemplateDto businessTemplateDto);
}
