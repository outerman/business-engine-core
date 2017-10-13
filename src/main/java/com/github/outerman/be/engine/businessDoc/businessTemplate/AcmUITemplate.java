package com.github.outerman.be.engine.businessDoc.businessTemplate;

import com.github.outerman.be.api.vo.SetColumnsSpecialVo;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.businessDoc.validator.IValidatable;
import com.github.outerman.be.engine.util.CommonUtil;
import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.dto.AcmUITemplateDto;
import com.github.outerman.be.api.dto.BusinessAssetDto;
import com.github.outerman.be.api.dto.BusinessAssetTypeDto;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;
import com.github.outerman.be.api.vo.SetOrg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by shenxy on 7/7/17.
 * 业务类型--界面元数据模板
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AcmUITemplate implements IValidatable {

    private AcmUITemplateDto uiTemplateDto;

    // 初始化方法, orgId可能为0; 如不为0, 则初始化公共模板(orgId=0)以及个性化模板
    public void init(SetOrg org, String businessCode, ITemplateProvider templateProvider) {
        uiTemplateDto = new AcmUITemplateDto();

        uiTemplateDto.getTacticsMap().putAll(templateProvider.getTacticsByCode(org.getId(), businessCode));
        uiTemplateDto.getSpecialMap().putAll(templateProvider.getSpecialByCode(org.getId(), businessCode));
        uiTemplateDto.setBusinessAssetList(templateProvider.getInventoryProperty(businessCode.toString()));
        uiTemplateDto.setBusinessAssetTypeList(templateProvider.getAssetType(businessCode.toString()));

        uiTemplateDto.setOrg(org);
        uiTemplateDto.setBusinessCode(businessCode);
    }

    @Override
    public String validate() {
        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();
        String errorMessage = "业务类型 " + uiTemplateDto.getBusinessCode() + " 元数据模板数据校验失败：";
        if (tacticsMap.isEmpty()) {
            return errorMessage + "缺少元数据配置信息；";
        }

        StringBuilder message = new StringBuilder();
        message.append(validateAsset());
        message.append(validateTatics());
        message.append(validateSpecial());
        if (message.length() == 0) {
            return "";
        }
        return errorMessage + message.toString();
    }

    /**
     * 校验元数据对应的显示规则
     * @return
     */
    private String validateTatics() {
        // 票据类型根据纳税人身份的显示规则，至少在一种纳税人身份下显示
        StringBuilder message = new StringBuilder();
        // 验证每个行业
        for (Entry<Long, List<SetColumnsTacticsDto>> entry : uiTemplateDto.getTacticsMap().entrySet()) {
            List<SetColumnsTacticsDto> tacticsList = entry.getValue();

            // 不同票据类型元数据配置不同，分别验证，纳税人身份分别对应两条 tactics 记录
            Map<Long, List<SetColumnsTacticsDto>> tacticsInvoiceMap = new HashMap<>();
            for (SetColumnsTacticsDto tactics : tacticsList) {
                Long invoiceId = tactics.getInvoiceId();
                Long columnId = tactics.getColumnsId();
                if (invoiceId == null || columnId == null) {
                    continue;
                }
                if (!columnId.equals(AcmConst.VAT_TAX_PAYER_41_COLUMN_ID) && !columnId.equals(AcmConst.VAT_TAX_PAYER_42_COLUMN_ID)) {
                    continue;
                }
                List<SetColumnsTacticsDto> tacticsInvoiceList;
                if (tacticsInvoiceMap.containsKey(invoiceId)) {
                    tacticsInvoiceList = tacticsInvoiceMap.get(invoiceId);
                } else {
                    tacticsInvoiceList = new ArrayList<>();
                    tacticsInvoiceMap.put(invoiceId, tacticsInvoiceList);
                }
                tacticsInvoiceList.add(tactics);
            }

            List<String> invoiceNameList = new ArrayList<>();
            for (Entry<Long, List<SetColumnsTacticsDto>> invoiceEntry : tacticsInvoiceMap.entrySet()) {
                SetColumnsTacticsDto vatTaxpayer41 = null;
                SetColumnsTacticsDto vatTaxpayer42 = null;
                for (SetColumnsTacticsDto tactics : invoiceEntry.getValue()) {
                    Long columnId = tactics.getColumnsId();
                    if (columnId.equals(AcmConst.VAT_TAX_PAYER_41_COLUMN_ID)) {
                        vatTaxpayer41 = tactics;
                    } else if (columnId.equals(AcmConst.VAT_TAX_PAYER_42_COLUMN_ID)) {
                        vatTaxpayer42 = tactics;
                    }
                }
                boolean vatTaxpayer41Visible = false;
                if (vatTaxpayer41 != null) {
                    Integer flag = vatTaxpayer41.getFlag();
                    if (flag != null && flag > 0) {
                        vatTaxpayer41Visible = true;
                    }
                }
                boolean vatTaxpayer42Visible = false;
                if (vatTaxpayer42 != null) {
                    Integer flag = vatTaxpayer42.getFlag();
                    if (flag != null && flag > 0) {
                        vatTaxpayer42Visible = true;
                    }
                }
                if (!vatTaxpayer41Visible && !vatTaxpayer42Visible) {
                    String invoiceName = CommonUtil.getInvoiceName(invoiceEntry.getKey());
                    invoiceNameList.add(invoiceName);
                }
            }
            if (!invoiceNameList.isEmpty()) {
                String industryStr = CommonUtil.getIndustryName(entry.getKey()) + "行业，";
                message.append(industryStr + String.join("、", invoiceNameList) + "纳税人身份至少选择一个；");
            }
        }
        return message.toString();
    }

    /**
     * 校验元数据对应的特殊输入规则
     * @return
     */
    private String validateSpecial() {
        Map<Long, List<SetColumnsSpecialVo>> specialMap = uiTemplateDto.getSpecialMap();
        // 校验银行账号（结算方式）、税率（征收率）显示（Flag = 1 | 2）时，特殊输入规则中要有对应的数据
        StringBuilder message = new StringBuilder();
        // 验证所有行业
        for (Entry<Long, List<SetColumnsTacticsDto>> entry : uiTemplateDto.getTacticsMap().entrySet()) {
            List<SetColumnsSpecialVo> specialList = specialMap.get(entry.getKey());

            // 不同票据类型元数据配置不同，分别验证
            Map<Long, List<SetColumnsTacticsDto>> tacticsInvoiceMap = new HashMap<>();
            for (SetColumnsTacticsDto tactics : entry.getValue()) {
                Long invoiceId = tactics.getInvoiceId();
                Long columnId = tactics.getColumnsId();
                if (invoiceId == null || columnId == null) {
                    continue;
                }
                if (!columnId.equals(AcmConst.BANK_ACCOUNT_COLUMN_ID) && !columnId.equals(AcmConst.TAX_RATE_COLUMN_ID)
                        && !columnId.equals(AcmConst.VAT_TAX_PAYER_41_COLUMN_ID) && !columnId.equals(AcmConst.VAT_TAX_PAYER_42_COLUMN_ID)) {
                    // 银行账号、税率、一般纳税人、小规模纳税人
                    continue;
                }
                List<SetColumnsTacticsDto> tacticsInvoiceList;
                if (tacticsInvoiceMap.containsKey(invoiceId)) {
                    tacticsInvoiceList = tacticsInvoiceMap.get(invoiceId);
                } else {
                    tacticsInvoiceList = new ArrayList<>();
                    tacticsInvoiceMap.put(invoiceId, tacticsInvoiceList);
                }
                tacticsInvoiceList.add(tactics);
            }

            List<String> accountInvoiceNameList = new ArrayList<>();
            List<String> taxRateInvoiceNameList = new ArrayList<>();
            for (Entry<Long, List<SetColumnsTacticsDto>> invoiceEntry : tacticsInvoiceMap.entrySet()) {
                String invoiceName = CommonUtil.getInvoiceName(invoiceEntry.getKey());
                SetColumnsTacticsDto tacticsBankAccount = null;
                SetColumnsTacticsDto tacticsTaxRate = null;
                Boolean isGeneralTaxPayer = false;
                Boolean isSmallScaleTaxPayer = false;
                for (SetColumnsTacticsDto tactics : invoiceEntry.getValue()) {
                    Long columnId = tactics.getColumnsId();
                    if (columnId.equals(AcmConst.BANK_ACCOUNT_COLUMN_ID)) {
                        tacticsBankAccount = tactics;
                    } else if (columnId.equals(AcmConst.TAX_RATE_COLUMN_ID)) {
                        tacticsTaxRate = tactics;
                    } else if (columnId.equals(AcmConst.VAT_TAX_PAYER_41_COLUMN_ID)) {
                        Integer flag = tactics.getFlag();
                        isGeneralTaxPayer = (flag != null && flag > 0);
                    } else if (columnId.equals(AcmConst.VAT_TAX_PAYER_42_COLUMN_ID)) {
                        Integer flag = tactics.getFlag();
                        isSmallScaleTaxPayer = (flag != null && flag > 0);
                    }
                }
                if (tacticsBankAccount != null) {
                    // 结算方式的特殊输入规则不区分票据类型，银行账号在 special 表中 column id 为 12，在 tactics 中为 14
                    Integer flag = tacticsBankAccount.getFlag();
                    if (flag != null && flag > 0 && !specialHasColumn(specialList, AcmConst.SETTLE_STYLE_COLUMN_ID, null)) {
                        accountInvoiceNameList.add(invoiceName);
                    }
                }
                if (tacticsTaxRate != null) {
                    Integer flag = tacticsTaxRate.getFlag();
                    if (flag != null && flag > 0) {
                        Map<Integer, List<SetColumnsSpecialVo>> vatTaxpayerMap = new HashMap<>();
                        for (SetColumnsSpecialVo special : specialList) {
                            Long columnId = special.getColumnsId();
                            if (!AcmConst.TAX_RATE_COLUMN_ID.equals(columnId)) {
                                continue;
                            }
                            Integer vatTaxpayer = special.getVatTaxpayer();
                            List<SetColumnsSpecialVo> vatTaxpayerList;
                            if (vatTaxpayerMap.containsKey(vatTaxpayer)) {
                                vatTaxpayerList = vatTaxpayerMap.get(vatTaxpayer);
                            } else {
                                vatTaxpayerList = new ArrayList<>();
                                vatTaxpayerMap.put(vatTaxpayer, vatTaxpayerList);
                            }
                            vatTaxpayerList.add(special);
                        }
                        if (isGeneralTaxPayer) {
                            Integer vatTaxpayer = 41;
                            List<SetColumnsSpecialVo> vatTaxpayerList = vatTaxpayerMap.get(vatTaxpayer);
                            if (!specialHasColumn(vatTaxpayerList, AcmConst.TAX_RATE_COLUMN_ID, invoiceEntry.getKey())) {
                                taxRateInvoiceNameList.add(invoiceName + CommonUtil.getVatTaxPayerName(vatTaxpayer));
                            }
                        }
                        if (isSmallScaleTaxPayer) {
                            Integer vatTaxpayer = 42;
                            List<SetColumnsSpecialVo> vatTaxpayerList = vatTaxpayerMap.get(vatTaxpayer);
                            if (!specialHasColumn(vatTaxpayerList, AcmConst.TAX_RATE_COLUMN_ID, invoiceEntry.getKey())) {
                                taxRateInvoiceNameList.add(invoiceName + CommonUtil.getVatTaxPayerName(vatTaxpayer));
                            }
                        }
                    }
                }
            }
            String industryStr = CommonUtil.getIndustryName(entry.getKey()) + "行业，";
            if (!accountInvoiceNameList.isEmpty()) {
                message.append(industryStr + String.join("、", accountInvoiceNameList) + "银行账号（结算方式）显示，可选账户类型未设置；");
            }
            if (!taxRateInvoiceNameList.isEmpty()) {
                message.append(industryStr + String.join("、", taxRateInvoiceNameList) + "税率显示，可选税率未设置；");
            }
        }
        return message.toString();
    }

    /**
     * 验证存货属性对照关系、资产类别对照关系
     * @return
     */
    private String validateAsset() {
        // 业务类型存货、资产类别显示时，对应存货属性、资产类别（存货属性细分）必须要有对应数据
        StringBuilder message = new StringBuilder();
        boolean inventoryVisible = false;
        boolean assetTypeVisible = false;
        for (Entry<Long, List<SetColumnsTacticsDto>> entry : uiTemplateDto.getTacticsMap().entrySet()) {
            for (SetColumnsTacticsDto tactics : entry.getValue()) {
                Long invoiceId = tactics.getInvoiceId();
                Long columnId = tactics.getColumnsId();
                if (invoiceId == null || columnId == null) {
                    continue;
                }
                Integer flag = tactics.getFlag();
                if (columnId.equals(AcmConst.INVENTORY_COLUMN_ID) || columnId.equals(AcmConst.ASSET_COLUMN_ID)) {
                    if (flag == 1 || flag == 2) {
                        inventoryVisible = true;
                    }
                } else if (columnId.equals(AcmConst.ASSET_TYPE_COLUMN_ID)) {
                    if (flag == 1 || flag == 2) {
                        assetTypeVisible = true;
                    }
                }
                if (inventoryVisible && assetTypeVisible) {
                    break;
                }
            }
            if (inventoryVisible && assetTypeVisible) {
                break;
            }
        }
        if (inventoryVisible) {
            List<BusinessAssetDto> businessAssetList = uiTemplateDto.getBusinessAssetList();
            if (businessAssetList == null || businessAssetList.isEmpty()) {
                message.append("商品或服务名称显示，业务类型可选存货属性未设置；");
            }
        }
        if (assetTypeVisible) {
            List<BusinessAssetTypeDto> businessAssetTypeList = uiTemplateDto.getBusinessAssetTypeList();
            if (businessAssetTypeList == null || businessAssetTypeList.isEmpty()) {
                message.append("资产类别显示，业务类型缺少对应的资产类别属性；");
            }
        }
        return message.toString();
    }

    private boolean specialHasColumn(List<SetColumnsSpecialVo> specialList, Long columnId, Long invoiceId) {
        if (columnId == null || specialList == null) {
            return false;
        }
        for (SetColumnsSpecialVo special : specialList) {
            Long columnsId = special.getColumnsId();
            Long optionId = special.getOptionId();
            if (columnId.equals(columnsId) && (invoiceId == null || optionId.equals(invoiceId))) {
                return true;
            }
        }
        return false;
    }

    public AcmUITemplateDto getUiTemplateDto() {
        return uiTemplateDto;
    }

    public void setUiTemplateDto(AcmUITemplateDto uiTemplateDto) {
        this.uiTemplateDto = uiTemplateDto;
    }
}
