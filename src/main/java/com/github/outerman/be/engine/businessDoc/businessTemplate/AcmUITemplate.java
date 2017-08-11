package com.github.outerman.be.engine.businessDoc.businessTemplate;

import com.github.outerman.be.api.vo.SetColumnsSpecialVo;
import com.github.outerman.be.engine.businessDoc.dataProvider.ITemplateProvider;
import com.github.outerman.be.engine.businessDoc.validator.IValidatable;
import com.github.outerman.be.api.constant.AcmConst;
import com.github.outerman.be.api.dto.AcmUITemplateDto;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;

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
    public void init(Long orgId, Long businessCode, ITemplateProvider templateProvider) {
        uiTemplateDto = new AcmUITemplateDto();

        uiTemplateDto.getTacticsMap().putAll(templateProvider.getTacticsByCode(orgId, businessCode));
        uiTemplateDto.getSpecialMap().putAll(templateProvider.getSpecialByCode(orgId, businessCode));

        uiTemplateDto.setOrgId(orgId);
        uiTemplateDto.setBusinessCode(businessCode);
    }

    @Override
    public String validate() {
        // 行业为 key，业务类型元数据信息
        Map<Long, List<SetColumnsTacticsDto>> tacticsMap = uiTemplateDto.getTacticsMap();
        if (tacticsMap.isEmpty()) {
            return "业务类型 " + uiTemplateDto.getBusinessCode() + " 元数据配置信息（tactics）为空；";
        }
        Map<Long, List<SetColumnsSpecialVo>> specialMap = uiTemplateDto.getSpecialMap();

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(validateTatics(tacticsMap));
        errorMessage.append(validateSpecial(tacticsMap, specialMap));

        return errorMessage.toString();
    }

    /**
     * 校验元数据对应的显示规则
     * @param tacticsMap
     * @return
     */
    private String validateTatics(Map<Long, List<SetColumnsTacticsDto>> tacticsMap) {
        // 校验票据类型根据纳税人身份的显示规则，至少在一种纳税人身份下显示
        StringBuilder errorMessage = new StringBuilder();

        // 验证每个行业
        String businessCode = "业务类型 " + uiTemplateDto.getBusinessCode();
        for (Entry<Long, List<SetColumnsTacticsDto>> entry : tacticsMap.entrySet()) {
            Long industry = entry.getKey();
            List<SetColumnsTacticsDto> tacticsList = entry.getValue();
            String industryStr = "，行业 " + industry;

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

            for (Entry<Long, List<SetColumnsTacticsDto>> invoiceEntry : tacticsInvoiceMap.entrySet()) {
                Long invoiceId = invoiceEntry.getKey();
                String invoice = "，票据类型 " + invoiceId;
                List<SetColumnsTacticsDto> tacticsInvoiceList = invoiceEntry.getValue();
                SetColumnsTacticsDto vatTaxpayer41 = null;
                SetColumnsTacticsDto vatTaxpayer42 = null;
                for (SetColumnsTacticsDto tactics : tacticsInvoiceList) {
                    Long columnId = tactics.getColumnsId();
                    if (columnId.equals(AcmConst.VAT_TAX_PAYER_41_COLUMN_ID)) {
                        vatTaxpayer41 = tactics;
                    } else if (columnId.equals(AcmConst.VAT_TAX_PAYER_42_COLUMN_ID)) {
                        vatTaxpayer42 = tactics;
                    }
                }
                boolean vatTaxpayer41Visible = false;
                if (vatTaxpayer41 == null) {
                    errorMessage.append(businessCode + industryStr + invoice + " 缺少一般纳税人的元数据配置信息（tactics）；");
                } else { // 结算方式的特殊输入规则不区分票据类型
                    Integer flag = vatTaxpayer41.getFlag();
                    if (flag != null && flag > 0) {
                        vatTaxpayer41Visible = true;
                    }
                }
                boolean vatTaxpayer42Visible = false;
                if (vatTaxpayer42 == null) {
                    errorMessage.append(businessCode + industryStr + invoice + " 缺少小规模纳税人的元数据配置信息（tactics）；");
                } else { // 税率的特殊输入规则区分票据类型
                    Integer flag = vatTaxpayer42.getFlag();
                    if (flag != null && flag > 0) {
                        vatTaxpayer42Visible = true;
                    }
                }
                if (!vatTaxpayer41Visible && !vatTaxpayer42Visible) {
                    errorMessage.append(businessCode + industryStr + invoice + " 元数据配置信息中，一般纳税人、小规模纳税人至少选择一个；");
                }
            }
        }

        return errorMessage.toString();
    }

    /**
     * 校验元数据对应的特殊输入规则
     * @param tacticsMap
     * @param specialMap
     * @return
     */
    private String validateSpecial(Map<Long, List<SetColumnsTacticsDto>> tacticsMap, Map<Long, List<SetColumnsSpecialVo>> specialMap) {
        // 校验银行账号（结算方式）、税率（征收率）显示（Flag = 1 | 2）时，special 中要有对应的数据
        StringBuilder errorMessage = new StringBuilder();

        // 验证所有行业
        String businessCode = "业务类型 " + uiTemplateDto.getBusinessCode();
        for (Entry<Long, List<SetColumnsTacticsDto>> entry : tacticsMap.entrySet()) {
            Long industry = entry.getKey();
            List<SetColumnsTacticsDto> tacticsList = entry.getValue();
            List<SetColumnsSpecialVo> specialList = specialMap.get(entry.getKey());
            String industryStr = "，行业 " + industry;

            // 不同票据类型元数据配置不同，分别验证
            Map<Long, List<SetColumnsTacticsDto>> tacticsInvoiceMap = new HashMap<>();
            for (SetColumnsTacticsDto tactics : tacticsList) {
                Long invoiceId = tactics.getInvoiceId();
                Long columnId = tactics.getColumnsId();
                if (invoiceId == null || columnId == null) {
                    continue;
                }
                if (!columnId.equals(AcmConst.BANK_ACCOUNT_COLUMN_ID) && !columnId.equals(AcmConst.TAX_RATE_COLUMN_ID)) { // 银行账号、税率
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

            for (Entry<Long, List<SetColumnsTacticsDto>> invoiceEntry : tacticsInvoiceMap.entrySet()) {
                Long invoiceId = invoiceEntry.getKey();
                String invoice = "，票据类型 " + invoiceId;
                List<SetColumnsTacticsDto> tacticsInvoiceList = invoiceEntry.getValue();
                SetColumnsTacticsDto tacticsBankAccount = null;
                SetColumnsTacticsDto tacticsTaxRate = null;
                for (SetColumnsTacticsDto tactics : tacticsInvoiceList) {
                    Long columnId = tactics.getColumnsId();
                    if (columnId.equals(AcmConst.BANK_ACCOUNT_COLUMN_ID)) {
                        tacticsBankAccount = tactics;
                    } else if (columnId.equals(AcmConst.TAX_RATE_COLUMN_ID)) {
                        tacticsTaxRate = tactics;
                    }
                }
                if (tacticsBankAccount == null) {
                    errorMessage.append(businessCode + industryStr + invoice + " 缺少银行账号（结算方式）的元数据配置信息（tactics）；");
                } else { // 结算方式的特殊输入规则不区分票据类型，银行账号在 special 表中 column id 为 12，在 tactics 中为 14
                    Integer flag = tacticsBankAccount.getFlag();
                    if (flag != null && flag > 0 && !specialHasColumn(specialList, AcmConst.SETTLE_STYLE_COLUMN_ID, null)) {
                        errorMessage.append(businessCode + industryStr + invoice + " 缺少银行账号（结算方式）的元数据配置信息（special）；");
                    }
                }
                if (tacticsTaxRate == null) {
                    errorMessage.append(businessCode + invoice + " 缺少税率的元数据配置信息（tactics）；");
                } else { // 税率的特殊输入规则区分票据类型，纳税人身份
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
                        for (Entry<Integer, List<SetColumnsSpecialVo>> vatTaxpayerEntry : vatTaxpayerMap.entrySet()) {
                            Integer vatTaxpayer = vatTaxpayerEntry.getKey();
                            String vatTaxpayerStr = "，纳税人身份 " + vatTaxpayer;
                            List<SetColumnsSpecialVo> vatTaxpayerList = vatTaxpayerEntry.getValue();
                            if (!specialHasColumn(vatTaxpayerList, AcmConst.TAX_RATE_COLUMN_ID, invoiceId)) {
                                errorMessage.append(businessCode + industryStr + invoice + vatTaxpayerStr + " 缺少税率的元数据配置信息（special）；");
                            }
                        }
                    }
                }
            }
        }

        return errorMessage.toString();
    }

    private boolean specialHasColumn(List<SetColumnsSpecialVo> specialList, Long columnId, Long invoiceId) {
        if (columnId == null) {
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
