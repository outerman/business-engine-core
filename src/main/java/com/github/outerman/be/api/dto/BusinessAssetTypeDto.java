package com.github.outerman.be.api.dto;

import java.io.Serializable;

/**
 * @author gaoxue
 *
 */
public class BusinessAssetTypeDto implements Serializable {

    private static final long serialVersionUID = -7552918714874290453L;

    /** id */
    private Long id;

    /** 组织 id */
    private Long orgId;

    /** 业务类型编码 */
    private String businessCode;

    /** 资产属性名称 */
    private String assetPropertyName;

    /** 资产类别明细id */
    private Long assetTypeDetailId;

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
     * 获取组织 id
     * @return 组织 id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置组织 id
     * @param orgId 组织 id
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取业务类型编码
     * @return 业务类型编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置业务类型编码
     * @param businessCode 业务类型编码
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * 获取资产属性名称
     * @return 资产属性名称
     */
    public String getAssetPropertyName() {
        return assetPropertyName;
    }

    /**
     * 设置资产属性名称
     * @param assetPropertyName 资产属性名称
     */
    public void setAssetPropertyName(String assetPropertyName) {
        this.assetPropertyName = assetPropertyName;
    }

    /**
     * 获取资产类别明细id
     * @return 资产类别明细id
     */
    public Long getAssetTypeDetailId() {
        return assetTypeDetailId;
    }

    /**
     * 设置资产类别明细id
     * @param assetTypeDetailId 资产类别明细id
     */
    public void setAssetTypeDetailId(Long assetTypeDetailId) {
        this.assetTypeDetailId = assetTypeDetailId;
    }

}
