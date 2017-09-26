package com.github.outerman.be.engine.businessDoc.serviceImp;

import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.FiDocDto;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.IFiDocProvider;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.api.dto.BusinessTemplateDto;
import com.github.outerman.be.api.ift.ITemplateValidateService;
import com.github.outerman.be.engine.businessDoc.businessTemplate.BusinessTemplate;
import com.github.outerman.be.engine.businessDoc.businessTemplate.TemplateManager;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITestGenerateProvider;
import com.github.outerman.be.engine.businessDoc.validator.ValidatorManager;
import com.github.outerman.be.engine.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shenxy on 12/7/17.
 *
 * 模板验证, 验证单个业务编码的业务
 * 既可以提供businessCode,验证数据库里的模板;
 * 也可以直接验证提供BusinessTemplateDto —— 编辑\新建等场景
 */
@Service
public class TemplateValidateService implements ITemplateValidateService {

    @Autowired
    private TemplateManager templateManager;

    private BusinessTemplate businessTemplate;

    private IFiDocProvider fiDocProvider;

    private ITestGenerateProvider testGenerateProvider;

    @Autowired
    private ValidatorManager validatorManager;
//    Map<ValidateType, LinkedHashSet<IBusinessValidatable>> validatorMap = new HashMap<>();
//
//    public enum ValidateType {
//        fiDoc,      //整单校验
//        fiEntry     //分录校验
//    }
//
//    public boolean addValidator(ValidateType type, IBusinessValidatable validatable) {
//        if (validatorMap.get(type) == null) {
//            validatorMap.put(type, new LinkedHashSet<>());
//        }
//        validatorMap.get(type).add(validatable);
//    }

//    LinkedHashSet<IBusinessDocValidatable> validatorSet = new LinkedHashSet<>();
//    public boolean addValidator(IBusinessDocValidatable validatable) {
//        validatorSet.add(validatable);
//        return true;
//    }

    //TODO: 全面验证,需要各个属性的企业覆盖
    @Override
    public String validateTemplateByMockDoc(SetOrg setOrg, Long businessCode, ITemplateProvider templateProvider, IFiDocProvider fiDocProvider, ITestGenerateProvider testGenerateProvider) {
        businessTemplate = templateManager.fetchBusinessTemplate(setOrg, businessCode.toString(), templateProvider);
        this.fiDocProvider = fiDocProvider;
        this.testGenerateProvider = testGenerateProvider;

        //return validateTemplate(setOrg, businessCode, businessTemplate);
        return validateTemplate(businessTemplate);
    }

    @Override
    public String validateTemplateByMockDoc(SetOrg setOrg, BusinessTemplateDto businessTemplateDto) {
        //验证用参数传入的模板, 用传入参数初始化
        businessTemplate.setBusinessTemplateDto(businessTemplateDto);

        return validateTemplate(setOrg, businessTemplateDto.getBusinessCode(), businessTemplate);
    }

    private String validateTemplate(BusinessTemplate businessTemplate) {
        return businessTemplate.validate();
    }

    private String validateTemplate(SetOrg setOrg, String businessCode, BusinessTemplate businessTemplate) {
        //1, 先检查模板
        String errorMessage = businessTemplate.validate();
        if (!StringUtil.isEmpty(errorMessage)) {
            return errorMessage;
        }
        //1.5, TODO:清空现有流水账?

        // TODO 现在新增以及审核校验只处理了当前组织
        // 2, 后模拟生成凭证
        try {
            // a）构建dto的模拟数据,遍历各个影响因素
            // TODO: 后续在工程内生成测试用例, 去掉testGenerateProvider
            String errMsg = testGenerateProvider.constructSortReceiptByCode(setOrg.getId(), Long.parseLong(businessCode));
            if (!StringUtil.isEmpty(errMsg)) {
                return errMsg;
            }
            // b）审核单据生成凭证
            errMsg = testGenerateProvider.approveSortReceiptByCode(setOrg.getId(), Long.parseLong(businessCode));
            if (!StringUtil.isEmpty(errMsg)) {
                return errMsg;
            }
            // c）根据模板验证凭证科目和金额是否正确
            errMsg = validateDocByCode(setOrg,  businessTemplate);
            if (!StringUtil.isEmpty(errMsg)) {
                return errMsg;
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "";
    }

    //根据业务编码, 校验关联凭证
    private String validateDocByCode(SetOrg setOrg, BusinessTemplate businessTemplate) {
        List<FiDocDto> allDocs = fiDocProvider.getFiDocList(setOrg.getId(), Long.parseLong(businessTemplate.getBusinessCode()));
        return allDocs
                .stream()
                .parallel() //多线程
                .map(doc -> {
                    if (doc.getSourceVoucherId() == null) {
                        return "";
                    }

                    //校验凭证, 由于做验证,不能使用生成时候的逻辑; 因此从凭证分录倒查
                    //1,获取关联的单据类型.
                    AcmSortReceipt acmSortReceipt = fiDocProvider.getReceiptById(setOrg.getId(), doc.getSourceVoucherId());//acmOwnSortService.querySortReceiptById(setOrg.getId(), doc.getSourceVoucherId());
                    if (acmSortReceipt.getAcmSortReceiptDetailList() == null || acmSortReceipt.getAcmSortReceiptDetailList().isEmpty()) {
                        return "业务单据缺少明细";
                    }
                    if (!businessTemplate.getBusinessCode().equals(acmSortReceipt.getAcmSortReceiptDetailList().get(0).getBusinessCode())) {
                        return "";
                    }

                    return validatorManager.getBusinessDocValidators()
                            .parallelStream()
                            .map(validator -> validator.validate(setOrg, businessTemplate, acmSortReceipt, doc))
                            .filter(str -> !StringUtil.isEmpty(str))
                            .collect(Collectors.joining(";"));

                })
                .filter(str -> !StringUtil.isEmpty(str))
                .collect(Collectors.joining("\n"));
    }
}
