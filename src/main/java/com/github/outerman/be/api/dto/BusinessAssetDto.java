package com.github.outerman.be.api.dto;

import java.io.Serializable;

/**
 * @author gaoxue
 *
 */
public class BusinessAssetDto implements Serializable {

    private static final long serialVersionUID = 7002702965896335445L;

    /** id */
    private Long id;

    /** 组织 id */
    private Long orgId;
    
    /** 业务类型编码 */
    private String businessCode;

    /** 存货属性名称 */
    private String inventoryPropertyName;

    /** 资产类别id */
    private String assetTypeId;

    /**
     * 获取id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取orgId
     * @return orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置orgId
     * @param orgId orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取businessCode
     * @return businessCode
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置businessCode
     * @param businessCode businessCode
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * 获取inventoryPropertyName
     * @return inventoryPropertyName
     */
    public String getInventoryPropertyName() {
        return inventoryPropertyName;
    }

    /**
     * 设置inventoryPropertyName
     * @param inventoryPropertyName inventoryPropertyName
     */
    public void setInventoryPropertyName(String inventoryPropertyName) {
        this.inventoryPropertyName = inventoryPropertyName;
    }

    /**
     * 获取assetTypeId
     * @return assetTypeId
     */
    public String getAssetTypeId() {
        return assetTypeId;
    }

    /**
     * 设置assetTypeId
     * @param assetTypeId assetTypeId
     */
    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }


}
