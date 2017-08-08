package com.github.outerman.be.api.dto;

import com.github.outerman.be.api.vo.SetColumnsSpecialVo;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenxy on 7/7/17.
 * 业务类型--界面元数据模板
 */
@Component
public class AcmUITemplateDto {

    private Map<Long, List<SetColumnsTacticsDto>> tacticsMap = new HashMap<>();  //该businessCode所有可能的模板, key为行业
    private Map<Long, List<SetColumnsSpecialVo>> specialMap = new HashMap<>();
    private Long orgId;
    private Long businessCode;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(Long businessCode) {
        this.businessCode = businessCode;
    }

    public Map<Long, List<SetColumnsTacticsDto>> getTacticsMap() {
        return tacticsMap;
    }

    public void setTacticsMap(Map<Long, List<SetColumnsTacticsDto>> tacticsMap) {
        this.tacticsMap = tacticsMap;
    }

    public Map<Long, List<SetColumnsSpecialVo>> getSpecialMap() {
        return specialMap;
    }

    public void setSpecialMap(Map<Long, List<SetColumnsSpecialVo>> specialMap) {
        this.specialMap = specialMap;
    }
}
