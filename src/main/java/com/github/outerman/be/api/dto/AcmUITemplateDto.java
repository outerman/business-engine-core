package com.github.outerman.be.api.dto;

import com.github.outerman.be.api.vo.SetColumnsSpecialVo;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private List<BusinessAssetDto> businessAssetList = new ArrayList<>();
    private List<BusinessAssetTypeDto> businessAssetTypeList = new ArrayList<>();

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

    /**
     * 获取businessAssetList
     * @return businessAssetList
     */
    public List<BusinessAssetDto> getBusinessAssetList() {
        return businessAssetList;
    }

    /**
     * 设置businessAssetList
     * @param businessAssetList businessAssetList
     */
    public void setBusinessAssetList(List<BusinessAssetDto> businessAssetList) {
        this.businessAssetList = businessAssetList;
    }

    /**
     * 获取businessAssetTypeList
     * @return businessAssetTypeList
     */
    public List<BusinessAssetTypeDto> getBusinessAssetTypeList() {
        return businessAssetTypeList;
    }

    /**
     * 设置businessAssetTypeList
     * @param businessAssetTypeList businessAssetTypeList
     */
    public void setBusinessAssetTypeList(List<BusinessAssetTypeDto> businessAssetTypeList) {
        this.businessAssetTypeList = businessAssetTypeList;
    }

}
