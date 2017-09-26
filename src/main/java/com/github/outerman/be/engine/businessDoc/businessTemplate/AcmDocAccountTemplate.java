package com.github.outerman.be.engine.businessDoc.businessTemplate;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.constant.BusinessEngineException;
import com.github.outerman.be.api.dto.AcmDocAccountTemplateDto;
import com.github.outerman.be.api.vo.AcmSortReceiptDetail;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.SetOrg;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.businessDoc.validator.IValidatable;
import com.github.outerman.be.engine.util.CommonUtil;
import com.github.outerman.be.engine.util.StringUtil;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的业务类 相关模板
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AcmDocAccountTemplate implements IValidatable {

    private AcmDocAccountTemplateDto docTemplateDto;

    private ITemplateProvider templateProvider;

    // 初始化方法, orgId可能为0; 如不为0, 则初始化公共模板(orgId=0)以及个性化模板
    public void init(SetOrg org, Long businessCode, ITemplateProvider templateProvider) {
        this.templateProvider = templateProvider;
        docTemplateDto = new AcmDocAccountTemplateDto();
        // 该业务所有行业和准则的模板
        List<DocAccountTemplateItem> all = templateProvider.getBusinessTemplateByCode(org.getId(), businessCode);
        all.forEach(template -> {
            String key = getKey(template.getIndustry(), template.getAccountingStandardsId());
            if (docTemplateDto.getAllPossibleTemplate().get(key) == null) {
                docTemplateDto.getAllPossibleTemplate().put(key, new ArrayList<>());
            }

            docTemplateDto.getAllPossibleTemplate().get(key).add(template);
        });

        for (DocAccountTemplateItem acmBusinessDocTemplate : all) {
            if (!docTemplateDto.getCodeList().contains(acmBusinessDocTemplate.getAccountCode())) {
                docTemplateDto.getCodeList().add(acmBusinessDocTemplate.getAccountCode());
            }
        }

        docTemplateDto.setOrg(org);
        docTemplateDto.setBusinessCode(businessCode);
    }

    // 获取具体模板,orgId不能为0
    public List<DocAccountTemplateItem> getTemplate(SetOrg org, AcmSortReceiptDetail acmSortReceiptDetail) {
        // 获取当前"行业 + 准则"的模板
        List<DocAccountTemplateItem> result = new ArrayList<>();
        Long businessCode = docTemplateDto.getBusinessCode();
        if (!businessCode.equals(acmSortReceiptDetail.getBusinessCode())) {
            return result;
        }
        List<DocAccountTemplateItem> templatesForOrg = docTemplateDto.getAllPossibleTemplate().get(getKey(org.getIndustry(), org.getAccountingStandards().intValue()));
        List<List<DocAccountTemplateItem>> fiBillDocTemplateListABCDEFG = getBusniess(templatesForOrg);
        if (fiBillDocTemplateListABCDEFG.isEmpty()) {
            return result;
        }
        return getBusinessTemplate(fiBillDocTemplateListABCDEFG, acmSortReceiptDetail, org.getVatTaxpayer());
    }

    /**
     * 获取当前业务对应组织的所有凭证模板信息
     * @param org 组织信息
     * @return 凭证模板信息，以 flag（A,B,C...）为 key 的 map
     */
    public Map<String, List<DocAccountTemplateItem>> getDocTemplateMap(SetOrg org) {
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = new HashMap<>();
        String key = getKey(org.getIndustry(), org.getAccountingStandards().intValue());
        List<DocAccountTemplateItem> docTemplateList = docTemplateDto.getAllPossibleTemplate().get(key);
        for (DocAccountTemplateItem docTemplate : docTemplateList) {
            String flag = docTemplate.getFlag();
            List<DocAccountTemplateItem> docTemplateWithFlagList;
            if (docTemplateMap.containsKey(flag)) {
                docTemplateWithFlagList = docTemplateMap.get(flag);
            } else {
                docTemplateWithFlagList = new ArrayList<>();
                docTemplateMap.put(flag, docTemplateWithFlagList);
            }
            docTemplateWithFlagList.add(docTemplate);
        }
        return docTemplateMap;
    }

    @Override
    public String validate() {
        // 计税方式、认证影响因素对应凭证模板需要两条
        // 影响因素取值有数据时，影响因素类型必须要有值
        StringBuilder errorMessage = new StringBuilder();

        String businessCode = "业务类型 " + docTemplateDto.getBusinessCode();
        Map<String, List<DocAccountTemplateItem>> docTemplateMap = docTemplateDto.getAllPossibleTemplate();
        if (docTemplateMap.isEmpty()) {
            errorMessage.append(businessCode + " 缺少凭证模板数据；");
            return errorMessage.toString();
        }
        for (Entry<String, List<DocAccountTemplateItem>> entry : docTemplateMap.entrySet()) {
            String key = entry.getKey();
            Long industry = getIndustry(key);
            Integer accountingStandard = getAccountingStandard(key);
            String industryStr = "， 行业 " + industry + "，会计准则 " + accountingStandard;

            List<DocAccountTemplateItem> docTemplateList = entry.getValue();
            Map<String, Integer> influenceCountMap = new HashMap<>();
            for (DocAccountTemplateItem docTemplate : docTemplateList) {
                String influence = docTemplate.getInfluence();
                if (StringUtil.isEmpty(influence) && hasInfluenceValue(docTemplate)) {
                    errorMessage.append(businessCode + industryStr + " 凭证模板影响因素缺少数据；");
                    continue;
                }
                String fundSource = docTemplate.getFundSource();
                if (StringUtil.isEmpty(fundSource)) {
                    errorMessage.append(businessCode + industryStr + "金额来源不能为空；");
                }
                // TODO 校验金额表达式正确性
                String countKey = influence + "_" + docTemplate.getFlag();
                Integer count = influenceCountMap.get(countKey);
                if (count == null) {
                    count = 0;
                }
                if ("vatTaxpayer,taxType".equals(influence)) {
                    count++;
                    influenceCountMap.put(countKey, count);
                } else if ("vatTaxpayer,qualification".equals(influence)) {
                    count++;
                    influenceCountMap.put(countKey, count);
                }
            }
            for (Entry<String, Integer> countEntry : influenceCountMap.entrySet()) {
                Integer count = countEntry.getValue();
                if (count != 2) {
                    errorMessage.append(businessCode + industryStr + " 凭证模板数据对应影响因素（计税方式、认证）必须有两条记录；");
                }
            }
        }
        // TODO 相同分类标识下，相同影响因素只能有一条凭证模板数据
        return errorMessage.toString();
    }

    private boolean hasInfluenceValue(DocAccountTemplateItem docTemplate) {
        if (docTemplate == null) {
            return false;
        }
        return docTemplate.getVatTaxpayer() != null || docTemplate.getDepartmentAttr() != null || docTemplate.getPersonAttr() != null
                || (docTemplate.getExtendAttr() != null && docTemplate.getExtendAttr() != 9999999999L) || docTemplate.getTaxType() != null || docTemplate.getQualification() != null;
    }

    public static String getKey(Long industry, Integer standard) {
        return industry + "_" + standard;
    }

    public static Long getIndustry(String key) {
        String[] keyArray = key.split("_");
        if (keyArray.length == 2) {
            return Long.parseLong(keyArray[0]);
        }
        return null;
    }

    public static Integer getAccountingStandard(String key) {
        String[] keyArray = key.split("_");
        if (keyArray.length == 2) {
            return Integer.parseInt(keyArray[1]);
        }
        return null;
    }


    // TODO: 原有方法,有待优化

    private List<DocAccountTemplateItem> getBusinessTemplate(List<List<DocAccountTemplateItem>> fiBillDocTemplateListABCDEFG, AcmSortReceiptDetail acmSortReceiptDetail, Long vatTaxpayer) {
        List<DocAccountTemplateItem> fiBillDocTemplateList = new ArrayList<>();
        if (acmSortReceiptDetail == null) {
            return fiBillDocTemplateList;
        }

        // 2.挑选出取值类型A一条B一条：
        DocAccountTemplateItem fiBillDocTemplateDefault = null;
        for (int i = 0; i < fiBillDocTemplateListABCDEFG.size(); i++) {
            List<DocAccountTemplateItem> list = fiBillDocTemplateListABCDEFG.get(i);
            if (list != null && !list.isEmpty()) {
                if (list.size() == 1) {// 如果有一条直接取
                    DocAccountTemplateItem acmBusinessDocTemplate = list.get(0);
                    if (acmBusinessDocTemplate.getInfluence() == null) {
                        fiBillDocTemplateList.add(acmBusinessDocTemplate);
                        continue;
                    }
                }

                for (DocAccountTemplateItem fiBillDocTemplate : list) {
                    if (fiBillDocTemplate == null) {// 判空如果是空返回
                        continue;
                    }
                    // 部门 人员 拓展存在默认，预先处理
                    if (fiBillDocTemplate.getDepartmentAttr() != null || fiBillDocTemplate.getPersonAttr() != null || fiBillDocTemplate.getExtendAttr() != null) {// 查看影响因素内是否有默认值，如果有默认值增加

                        if (fiBillDocTemplate.getDepartmentAttr() != null) {// 有部门影响因素
                            if (fiBillDocTemplate.getDepartmentAttr() == 0) {// 模板等于默认，添加部门默认
                                fiBillDocTemplateDefault = fiBillDocTemplate;
                            }

                            if (acmSortReceiptDetail.getDepartmentProperty() != null) {
                                if (acmSortReceiptDetail.getDepartmentProperty().equals(AcmConst.DEPTPROPERTY_002)) {// 生产部
                                    if (fiBillDocTemplate.getPersonAttr() != null) {
                                        if (fiBillDocTemplate.getPersonAttr() == 0) {
                                            fiBillDocTemplateDefault = fiBillDocTemplate;
                                        }
                                    }
                                }
                            }

                        }

                        if (fiBillDocTemplateDefault == null) {
                            if (fiBillDocTemplate.getExtendAttr() != null) {
                                if (fiBillDocTemplate.getExtendAttr() == 0) {
                                    fiBillDocTemplateDefault = fiBillDocTemplate;
                                }
                            }
                        }
                    }
                    if (fiBillDocTemplate.getInfluence() != null) {

                        switch (fiBillDocTemplate.getInfluence()) {
                        case "departmentAttr": {
                            if (acmSortReceiptDetail.getDepartmentProperty() != null) {// 部门属性 是否为空 取默认
                                if (acmSortReceiptDetail.getDepartmentProperty().equals(fiBillDocTemplate.getDepartmentAttr())) {
                                    fiBillDocTemplateList.add(fiBillDocTemplate);
                                }
                            }
                        }
                            break;
                        case "departmentAttr,personAttr": {
                            if (acmSortReceiptDetail.getDepartmentProperty() != null) {// 部门属性 是否为空 取默认
                                if (acmSortReceiptDetail.getDepartmentProperty().equals(fiBillDocTemplate.getDepartmentAttr())) {
                                    if (acmSortReceiptDetail.getDepartmentProperty().equals(AcmConst.DEPTPROPERTY_002)) {// 如果是生产部门才取人员属性
                                        if (acmSortReceiptDetail.getEmployeeAttribute() != null) {
                                            if (acmSortReceiptDetail.getEmployeeAttribute().equals(fiBillDocTemplate.getPersonAttr())) {// 不为空 取属性值相等的
                                                fiBillDocTemplateList.add(fiBillDocTemplate);
                                            }
                                        }
                                    } else {// 如果不是生产部门，按照部门属性取值，不考虑人员属性
                                        if (acmSortReceiptDetail.getDepartmentProperty().equals(fiBillDocTemplate.getDepartmentAttr())) {
                                            fiBillDocTemplateList.add(fiBillDocTemplate);
                                        }
                                    }
                                }
                            }
                        }
                            break;
                        case "vatTaxpayer":
                        case "vatTaxpayer,qualification":
                        case "vatTaxpayer,taxType": {

                            if (fiBillDocTemplate.getVatTaxpayer().equals(vatTaxpayer)) {// 直接取纳税人相等值
                                if ("vatTaxpayer".equals(fiBillDocTemplate.getInfluence())) {
                                    fiBillDocTemplateList.add(fiBillDocTemplate);
                                } else if ("vatTaxpayer,qualification".equals(fiBillDocTemplate.getInfluence())) {
                                    if (acmSortReceiptDetail.getIsQualification() == null || acmSortReceiptDetail.getIsQualification() == 0) {
                                        if (fiBillDocTemplate.getQualification() == false) {
                                            fiBillDocTemplateList.add(fiBillDocTemplate);
                                        }
                                    }
                                    if (acmSortReceiptDetail.getIsQualification() != null && acmSortReceiptDetail.getIsQualification() == 1) {
                                        if (fiBillDocTemplate.getQualification() == true) {
                                            fiBillDocTemplateList.add(fiBillDocTemplate);
                                        }
                                    }

                                } else if ("vatTaxpayer,taxType".equals(fiBillDocTemplate.getInfluence())) {
                                    Long taxRateId = acmSortReceiptDetail.getTaxRateId();
                                    if (CommonUtil.isSimple(taxRateId, templateProvider)) {
                                        if (fiBillDocTemplate.getTaxType() == false) {
                                            fiBillDocTemplateList.add(fiBillDocTemplate);
                                        }
                                    } else if (CommonUtil.isGeneral(taxRateId, templateProvider)) {
                                        if (fiBillDocTemplate.getTaxType() == true) {
                                            fiBillDocTemplateList.add(fiBillDocTemplate);
                                        }
                                    } else if (CommonUtil.isSpecial(taxRateId, templateProvider)) {
                                        continue;
                                    } else {
                                        throw new BusinessEngineException("", "获取计税方式：不能匹配税率" + fiBillDocTemplate.getTaxType());
                                    }
                                }
                            }
                        }
                            break;
                        case "punishmentAttr": {// 罚款性质
                            if (acmSortReceiptDetail.getPenaltyType() != null) {//
                                if (acmSortReceiptDetail.getPenaltyType().equals(fiBillDocTemplate.getExtendAttr())) {
                                    fiBillDocTemplateList.add(fiBillDocTemplate);
                                }
                            }
                        }
                            break;
                        case "borrowAttr": {// 借款期限
                            if (acmSortReceiptDetail.getLoanTerm() != null) {//

                                if (acmSortReceiptDetail.getLoanTerm().equals(fiBillDocTemplate.getExtendAttr())) {
                                    fiBillDocTemplateList.add(fiBillDocTemplate);
                                }
                            }
                        }
                            break;
                        case "assetAttr": {// 资产属性
                            if (acmSortReceiptDetail.getAssetAttr() != null) {//
                                if (acmSortReceiptDetail.getAssetAttr().equals(fiBillDocTemplate.getExtendAttr())) {
                                    fiBillDocTemplateList.add(fiBillDocTemplate);
                                }
                            }
                        }
                            break;
                        // case "intangibleAssetAttr":{//无形资产属性
                        // if(acmSortReceiptDetail.getAssetType()!= null){//
                        // if(acmSortReceiptDetail.getAssetType().equals(fiBillDocTemplate.getExtendAttr())){
                        // fiBillDocTemplateList.add(fiBillDocTemplate);
                        // }
                        // }
                        // }
                        // break;
                        case "accountInAttr": {// 账户属性
                            if (acmSortReceiptDetail.getInBankAccountTypeId() != null) {// 借是流入 贷是流出
                                if (acmSortReceiptDetail.getInBankAccountTypeId().equals(fiBillDocTemplate.getExtendAttr())) {
                                    fiBillDocTemplateList.add(fiBillDocTemplate);
                                }
                            }
                        }
                            break;
                        case "accountOutAttr": {// 账户属性
                            if (acmSortReceiptDetail.getBankAccountTypeId() != null) {// 借是流入 贷是流出
                                if (acmSortReceiptDetail.getBankAccountTypeId().equals(fiBillDocTemplate.getExtendAttr())) {
                                    fiBillDocTemplateList.add(fiBillDocTemplate);
                                }
                            }
                        }
                            break;

                        default:
                            break;
                        }
                    }
                }
                if (fiBillDocTemplateDefault != null) {
                    if (fiBillDocTemplateList.isEmpty()) {
                        fiBillDocTemplateList.add(fiBillDocTemplateDefault);
                    }
                }
            }
        }

        return fiBillDocTemplateList;
    }

    private List<List<DocAccountTemplateItem>> getBusniess(List<DocAccountTemplateItem> busniessTemp) {
        List<List<DocAccountTemplateItem>> result = new ArrayList<>();
        if (busniessTemp == null || busniessTemp.isEmpty()) {
            return result;
        }

        List<DocAccountTemplateItem> listA = new ArrayList<>();
        List<DocAccountTemplateItem> listB = new ArrayList<>();
        List<DocAccountTemplateItem> listC = new ArrayList<>();
        List<DocAccountTemplateItem> listD = new ArrayList<>();
        List<DocAccountTemplateItem> listE = new ArrayList<>();
        List<DocAccountTemplateItem> listF = new ArrayList<>();
        List<DocAccountTemplateItem> listG = new ArrayList<>();
        List<DocAccountTemplateItem> listH = new ArrayList<>();

        for (int i = 0; i < busniessTemp.size(); i++) {
            DocAccountTemplateItem acmBusinessDocTemplate = busniessTemp.get(i);
            switch (acmBusinessDocTemplate.getFlag()) {
            case "A":
                listA.add(acmBusinessDocTemplate);
                break;
            case "B":
                listB.add(acmBusinessDocTemplate);
                break;
            case "C":
                listC.add(acmBusinessDocTemplate);
                break;
            case "D":
                listD.add(acmBusinessDocTemplate);
                break;
            case "E":
                listE.add(acmBusinessDocTemplate);
                break;
            case "F":
                listF.add(acmBusinessDocTemplate);
                break;
            case "G":
                listG.add(acmBusinessDocTemplate);
                break;
            case "H":
                listH.add(acmBusinessDocTemplate);
                break;
            default:
                break;
            }
        }
        if (!listA.isEmpty()) {
            result.add(listA);
        }
        if (!listB.isEmpty()) {
            result.add(listB);
        }
        if (!listC.isEmpty()) {
            result.add(listC);
        }
        if (!listD.isEmpty()) {
            result.add(listD);
        }
        if (!listE.isEmpty()) {
            result.add(listE);
        }
        if (!listF.isEmpty()) {
            result.add(listF);
        }
        if (!listG.isEmpty()) {
            result.add(listG);
        }
        if (!listH.isEmpty()) {
            result.add(listH);
        }

        return result;
    }

    public AcmDocAccountTemplateDto getDocTemplateDto() {
        return docTemplateDto;
    }

    public void setDocTemplateDto(AcmDocAccountTemplateDto docTemplateDto) {
        this.docTemplateDto = docTemplateDto;
    }
}
