package com.rt.be.engine.businessDoc.businessTemplate;

import com.rt.be.api.dto.AcmUITemplateDto;
import com.rt.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.rt.be.engine.businessDoc.validator.IValidatable;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 7/7/17.
 * 业务类型--界面元数据模板
 */
@Component
public class AcmUITemplate implements IValidatable {

    private AcmUITemplateDto uiTemplateDto;

    //初始化方法, orgId可能为0; 如不为0, 则初始化公共模板(orgId=0)以及个性化模板
    public void init(Long orgId, Long businessCode, ITemplateProvider templateProvider) {
        uiTemplateDto = new AcmUITemplateDto();

        uiTemplateDto.getTacticsMap().putAll(templateProvider.getTacticsByCode(orgId, businessCode));
        uiTemplateDto.getSpecialMap().putAll(templateProvider.getSpecialByCode(orgId, businessCode));

        uiTemplateDto.setOrgId(orgId);
        uiTemplateDto.setBusinessCode(businessCode);
    }

    @Override
    public String validate() {
        //TODO
        return "";
    }

    public AcmUITemplateDto getUiTemplateDto() {
        return uiTemplateDto;
    }

    public void setUiTemplateDto(AcmUITemplateDto uiTemplateDto) {
        this.uiTemplateDto = uiTemplateDto;
    }
}
