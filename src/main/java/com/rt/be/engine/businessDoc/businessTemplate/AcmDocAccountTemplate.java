package com.rt.be.engine.businessDoc.businessTemplate;

import com.rt.be.api.constant.AcmConst;
import com.rt.be.api.constant.BusinessEngineException;
import com.rt.be.api.dto.AcmDocAccountTemplateDto;
import com.rt.be.api.vo.AcmSortReceiptDetail;
import com.rt.be.api.vo.DocAccountTemplateItem;
import com.rt.be.api.vo.SetTaxRateDto;
import com.rt.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.rt.be.engine.businessDoc.validator.IValidatable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by shenxy on 16/12/28.
 * 流水账生成凭证的业务类 相关模板
 */
@Component
public class AcmDocAccountTemplate implements IValidatable {

    private AcmDocAccountTemplateDto docTemplateDto;

//    ISetTaxRateService setTaxRateService;
    private ITemplateProvider templateProvider;

    //初始化方法, orgId可能为0; 如不为0, 则初始化公共模板(orgId=0)以及个性化模板
    public void init(Long orgId, Long businessCode, ITemplateProvider templateProvider) {
        this.templateProvider = templateProvider;
        docTemplateDto = new AcmDocAccountTemplateDto();
        //该业务所有行业和准则的模板
        List<DocAccountTemplateItem> all = templateProvider.getBusinessTemplateByCode(orgId, businessCode);
        all.forEach(template -> {
            String key = getKey(template.getIndustry(), template.getAccountingStandardsId());
            if (docTemplateDto.getAllPossibleTemplate().get(key) == null) {
                docTemplateDto.getAllPossibleTemplate().put(key, new ArrayList<>());
            }

            docTemplateDto.getAllPossibleTemplate().get(key).add(template);
        });

        for (DocAccountTemplateItem acmBusinessDocTemplate : all) {
            if(!docTemplateDto.getCodeList().contains(acmBusinessDocTemplate.getAccountCode())){
                docTemplateDto.getCodeList().add(acmBusinessDocTemplate.getAccountCode());
            }
        }

        docTemplateDto.setOrgId(orgId);
        docTemplateDto.setBusinessCode(businessCode);
    }

    //获取具体模板,orgId不能为0
    public List<DocAccountTemplateItem> getTemplate(Long orgId, Integer accountingStandardsId, Long industry, Long vatTaxpayer, AcmSortReceiptDetail acmSortReceiptDetail) {
        //获取当前"行业 + 准则"的模板
        List<DocAccountTemplateItem> templatesForOrg = docTemplateDto.getAllPossibleTemplate().get(getKey(industry, accountingStandardsId));
        //TODO: 原有逻辑, 可优化, 因为此处只有一个业务
        Map<String,List<List<DocAccountTemplateItem>>> busMap = getBusniess(null, null, null, templatesForOrg);
        List<List<DocAccountTemplateItem>> fiBillDocTemplateListABCDEFG = busMap.get(acmSortReceiptDetail.getBusinessType().toString());//(docTemplateDto.getBusinessCode().toString());
        if (fiBillDocTemplateListABCDEFG == null || fiBillDocTemplateListABCDEFG.isEmpty()) {
            //返回空 消息体增加空
            return null;
        }
        return getBusinessTemplate(fiBillDocTemplateListABCDEFG, acmSortReceiptDetail, vatTaxpayer);
    }

    @Override
    public String validate() {
        //TODO
        return "";
    }

    private String getKey(Long industry, Integer standard) {
        return industry + "_" + standard;
    }

    //TODO: 原有方法,有待优化
    List<Long> simple = new ArrayList<>();
    List<Long> general = new ArrayList<>();
    List<Long> special = new ArrayList<>();
    private List<DocAccountTemplateItem> getBusinessTemplate(List<List<DocAccountTemplateItem>> fiBillDocTemplateListABCDEFG, AcmSortReceiptDetail acmSortReceiptDetail, Long vatTaxpayer){
        List<DocAccountTemplateItem> fiBillDocTemplateList = new ArrayList<>();
        if(acmSortReceiptDetail == null){
            return fiBillDocTemplateList;
        }


        // 2.挑选出取值类型A一条B一条：
        DocAccountTemplateItem fiBillDocTemplateDefault = null ;
        for(int i = 0 ; i < fiBillDocTemplateListABCDEFG.size() ; i++){
            List<DocAccountTemplateItem> list = fiBillDocTemplateListABCDEFG.get(i);
            if(list != null && !list.isEmpty()){
                if(list.size() == 1){// 如果有一条直接取
                    DocAccountTemplateItem acmBusinessDocTemplate = list.get(0);
                    if(acmBusinessDocTemplate.getInfluence() == null){
                        fiBillDocTemplateList.add(acmBusinessDocTemplate);
                        continue;
                    }
                }

                for (DocAccountTemplateItem fiBillDocTemplate : list) {
                    if(fiBillDocTemplate == null){//判空如果是空返回
                        continue;
                    }
                    //部门 人员 拓展存在默认，预先处理
                    if(fiBillDocTemplate.getDepartmentAttr() != null
                            || fiBillDocTemplate.getPersonAttr() != null
                            || fiBillDocTemplate.getExtendAttr() != null){//查看影响因素内是否有默认值，如果有默认值增加

                        if(fiBillDocTemplate.getDepartmentAttr() != null){//有部门影响因素
                            if(fiBillDocTemplate.getDepartmentAttr() == 0){//模板等于默认-- 添加部门默认
                                fiBillDocTemplateDefault = fiBillDocTemplate;
                            }

                            if(acmSortReceiptDetail.getDepartmentProperty() != null){
                                if(acmSortReceiptDetail.getDepartmentProperty().equals(AcmConst.DEPTPROPERTY_002)){//生产部
                                    if(fiBillDocTemplate.getPersonAttr() != null){
                                        if(fiBillDocTemplate.getPersonAttr() == 0 ){
                                            fiBillDocTemplateDefault = fiBillDocTemplate;
                                        }
                                    }
                                }
                            }

                        }

                        if(fiBillDocTemplateDefault == null){
                            if(fiBillDocTemplate.getExtendAttr() != null){
                                if(fiBillDocTemplate.getExtendAttr() == 0 ){
                                    fiBillDocTemplateDefault = fiBillDocTemplate;
                                }
                            }
                        }
                    }
                    if(fiBillDocTemplate.getInfluence() != null){

                        switch (fiBillDocTemplate.getInfluence()) {
                            case "departmentAttr":{
                                if(acmSortReceiptDetail.getDepartmentProperty()!= null){//部门属性 是否为空  取默认
                                    if(acmSortReceiptDetail.getDepartmentProperty().equals(fiBillDocTemplate.getDepartmentAttr())){
                                        fiBillDocTemplateList.add(fiBillDocTemplate);
                                    }
                                }
                            }
                            break;
                            case "departmentAttr,personAttr":{
                                if(acmSortReceiptDetail.getDepartmentProperty()!= null){//部门属性 是否为空  取默认
                                    if(acmSortReceiptDetail.getDepartmentProperty().equals(fiBillDocTemplate.getDepartmentAttr())){
                                        if(acmSortReceiptDetail.getDepartmentProperty().equals(AcmConst.DEPTPROPERTY_002)){//如果是生产部门才取人员属性
                                            if(acmSortReceiptDetail.getEmployeeAttribute() != null){
                                                if(acmSortReceiptDetail.getEmployeeAttribute().equals(fiBillDocTemplate.getPersonAttr())){// 不为空 取属性值相等的
                                                    fiBillDocTemplateList.add(fiBillDocTemplate);
                                                }
                                            }
                                        }else{//如果不是生产部门，按照部门属性取值，不考虑人员属性
                                            if(acmSortReceiptDetail.getDepartmentProperty().equals(fiBillDocTemplate.getDepartmentAttr())){
                                                fiBillDocTemplateList.add(fiBillDocTemplate);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                            case "vatTaxpayer":
                            case "vatTaxpayer,qualification":
                            case "vatTaxpayer,taxType":{

                                if(fiBillDocTemplate.getVatTaxpayer().equals(vatTaxpayer)){//直接取纳税人相等值
                                    if("vatTaxpayer".equals(fiBillDocTemplate.getInfluence())){
                                        fiBillDocTemplateList.add(fiBillDocTemplate);
                                    }
                                    else if("vatTaxpayer,qualification".equals(fiBillDocTemplate.getInfluence())){
                                        if(acmSortReceiptDetail.getIsQualification() == null || acmSortReceiptDetail.getIsQualification() == 0){
                                            if(fiBillDocTemplate.getQualification() == false){
                                                fiBillDocTemplateList.add(fiBillDocTemplate);
                                            }
                                        }
                                        if(acmSortReceiptDetail.getIsQualification() != null && acmSortReceiptDetail.getIsQualification() == 1){
                                            if(fiBillDocTemplate.getQualification() == true){
                                                fiBillDocTemplateList.add(fiBillDocTemplate);
                                            }
                                        }



                                    }else if("vatTaxpayer,taxType".equals(fiBillDocTemplate.getInfluence())){

                                        if(general.isEmpty() || simple.isEmpty()){
                                            List<SetTaxRateDto> taxRateList = templateProvider.getTaxRateList(0L);//setTaxRateService.getTaxRateByOrgId(0L);
                                            if(taxRateList != null && !taxRateList.isEmpty()){
                                                for (SetTaxRateDto setTaxRateDto : taxRateList) {
                                                    Long id = setTaxRateDto.getId();
                                                    //1一般 2简易 3其他
                                                    if(setTaxRateDto.getType().equals(1L)){
                                                        general.add(id);
                                                    }
                                                    if(setTaxRateDto.getType().equals(2L)){
                                                        simple.add(id);
                                                    }
                                                    if(setTaxRateDto.getType().equals(3L)){
                                                        special.add(id);
                                                    }
                                                }
                                            }

                                        }

                                        if(simple.contains(acmSortReceiptDetail.getTaxRateId())){
                                            if(fiBillDocTemplate.getTaxType() == false){
                                                fiBillDocTemplateList.add(fiBillDocTemplate);
                                            }
                                        }else if(general.contains(acmSortReceiptDetail.getTaxRateId())){
                                            if(fiBillDocTemplate.getTaxType() == true){
                                                fiBillDocTemplateList.add(fiBillDocTemplate);
                                            }
                                        }else if(special.contains(acmSortReceiptDetail.getTaxRateId())){
                                            continue;
                                        }else{
                                            throw new BusinessEngineException("", "获取计税方式：不能匹配税率"+fiBillDocTemplate.getTaxType());
                                        }
                                    }
                                }
                            }
                            break;
                            case "punishmentAttr":{//罚款性质
                                if(acmSortReceiptDetail.getPenaltyType()!= null){//
                                    if(acmSortReceiptDetail.getPenaltyType().equals(fiBillDocTemplate.getExtendAttr())){
                                        fiBillDocTemplateList.add(fiBillDocTemplate);
                                    }
                                }
                            }
                            break;
                            case "borrowAttr":{//借款期限
                                if(acmSortReceiptDetail.getLoanTerm()!= null){//

                                    if(acmSortReceiptDetail.getLoanTerm().equals(fiBillDocTemplate.getExtendAttr())){
                                        fiBillDocTemplateList.add(fiBillDocTemplate);
                                    }
                                }
                            }
                            break;
                            case "assetAttr":{//资产属性
                                if(acmSortReceiptDetail.getAssetAttr()!= null){//
                                    if(acmSortReceiptDetail.getAssetAttr().equals(fiBillDocTemplate.getExtendAttr())){
                                        fiBillDocTemplateList.add(fiBillDocTemplate);
                                    }
                                }
                            }
                            break;
//                            case "intangibleAssetAttr":{//无形资产属性
//                                if(acmSortReceiptDetail.getAssetType()!= null){//
//                                    if(acmSortReceiptDetail.getAssetType().equals(fiBillDocTemplate.getExtendAttr())){
//                                        fiBillDocTemplateList.add(fiBillDocTemplate);
//                                    }
//                                }
//                            }
//                            break;
                            case "accountInAttr":{//账户属性
                                if(acmSortReceiptDetail.getInBankAccountTypeId()!= null){//借是流入  贷是流出
                                    if(acmSortReceiptDetail.getInBankAccountTypeId().equals(fiBillDocTemplate.getExtendAttr())){
                                        fiBillDocTemplateList.add(fiBillDocTemplate);
                                    }
                                }
                            }
                            break;
                            case "accountOutAttr":{//账户属性
                                if(acmSortReceiptDetail.getBankAccountTypeId()!= null){//借是流入  贷是流出
                                    if(acmSortReceiptDetail.getBankAccountTypeId().equals(fiBillDocTemplate.getExtendAttr())){
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
                if(fiBillDocTemplateDefault != null){
                    if(fiBillDocTemplateList.isEmpty()){
                        fiBillDocTemplateList.add(fiBillDocTemplateDefault);
                    }
                }
            }
        }

        return fiBillDocTemplateList;
    }

    //TODO: 原有方法,有待优化
    private Map<String, List<List<DocAccountTemplateItem>>> getBusniess(Long orgId,
    		Long accountingStandardsId, Long industry,
    		List<DocAccountTemplateItem> busniessTemp
    		) {
        Map<String, List<List<DocAccountTemplateItem>>> busMap = new HashMap<>();
        
        Long indexId = busniessTemp.get(0).getBusinessId();
        List<List<DocAccountTemplateItem>> list = new ArrayList<>();
        List<DocAccountTemplateItem> listA = new ArrayList<>();
        List<DocAccountTemplateItem> listB = new ArrayList<>();
        List<DocAccountTemplateItem> listC = new ArrayList<>();
        List<DocAccountTemplateItem> listD = new ArrayList<>();
        List<DocAccountTemplateItem> listE = new ArrayList<>();
        List<DocAccountTemplateItem> listF = new ArrayList<>();
        List<DocAccountTemplateItem> listG = new ArrayList<>();
        List<DocAccountTemplateItem> listH = new ArrayList<>();

        List<Long> idList = new ArrayList<>();
        for (int i = 0; i < busniessTemp.size(); i++) {
            DocAccountTemplateItem acmBusinessDocTemplate = busniessTemp.get(i);
            if(idList.contains(acmBusinessDocTemplate.getId())){
                continue;
            }else{
                idList.add(acmBusinessDocTemplate.getId());
            }
            if(indexId.equals(acmBusinessDocTemplate.getBusinessId())){
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
                    default :
                        break;
                }
            }else{
                list.add(listA);
                if(!listB.isEmpty()){
                    list.add(listB);
                }
                if(!listC.isEmpty()){
                    list.add(listC);
                }
                if(!listD.isEmpty()){
                    list.add(listD);
                }
                if(!listE.isEmpty()){
                    list.add(listE);
                }
                if(!listF.isEmpty()){
                    list.add(listF);
                }
                if(!listG.isEmpty()){
                    list.add(listG);
                }
                if(!listH.isEmpty()){
                    list.add(listH);
                }
                busMap.put(indexId.toString(),list);
                indexId = acmBusinessDocTemplate.getBusinessId();
                list = new ArrayList<>();
                listA = new ArrayList<>();
                listB = new ArrayList<>();
                listC = new ArrayList<>();
                listD = new ArrayList<>();
                listE = new ArrayList<>();
                listF = new ArrayList<>();
                listG = new ArrayList<>();
                listH = new ArrayList<>();
                if("A".equals(acmBusinessDocTemplate.getFlag())){
                    listA.add(acmBusinessDocTemplate);
                }
                if("B".equals(acmBusinessDocTemplate.getFlag())){
                    listB.add(acmBusinessDocTemplate);
                }
                if("C".equals(acmBusinessDocTemplate.getFlag())){
                    listC.add(acmBusinessDocTemplate);
                }
                if("D".equals(acmBusinessDocTemplate.getFlag())){
                    listD.add(acmBusinessDocTemplate);
                }
                if("E".equals(acmBusinessDocTemplate.getFlag())){
                    listE.add(acmBusinessDocTemplate);
                }
                if("F".equals(acmBusinessDocTemplate.getFlag())){
                    listF.add(acmBusinessDocTemplate);
                }
                if("G".equals(acmBusinessDocTemplate.getFlag())){
                    listG.add(acmBusinessDocTemplate);
                }
                if("H".equals(acmBusinessDocTemplate.getFlag())){
                	listH.add(acmBusinessDocTemplate);
                }
            }
            if(i == busniessTemp.size()-1){
            	if(!listA.isEmpty()){
            		list.add(listA);
            	}
            	if(!listB.isEmpty()){
            		list.add(listB);
            	}
            	if(!listC.isEmpty()){
            		list.add(listC);
            	}
            	if(!listD.isEmpty()){
            		list.add(listD);
            	}
            	if(!listE.isEmpty()){
            		list.add(listE);
            	}
            	if(!listF.isEmpty()){
            		list.add(listF);
            	}
            	if(!listG.isEmpty()){
            		list.add(listG);
            	}
            	if(!listH.isEmpty()){
            		list.add(listH);
            	}
            	busMap.put(indexId.toString(),list);
            }
        }

        return busMap;
    }

    public AcmDocAccountTemplateDto getDocTemplateDto() {
        return docTemplateDto;
    }

    public void setDocTemplateDto(AcmDocAccountTemplateDto docTemplateDto) {
        this.docTemplateDto = docTemplateDto;
    }
}
