package com.github.outerman.be.engine.businessDoc.dataProvider;

import com.github.outerman.be.api.vo.*;
import com.github.outerman.be.api.dto.BusinessAssetDto;
import com.github.outerman.be.api.dto.BusinessAssetTypeDto;
import com.github.outerman.be.api.dto.FiDocGenetateResultDto.ReceiptResult;

import java.util.List;
import java.util.Map;

/**
 * Created by shenxy on 18/7/17.
 *
 * 模板数据的提供者接口
 */
public interface ITemplateProvider {

    List<DocAccountTemplateItem> getBusinessTemplateByCode(Long orgId, Long businessCode);

    List<PaymentTemplateItem> getPayTemplate(Long orgId, Long businessCode);

    Map<Long, List<SetColumnsTacticsDto>> getTacticsByCode(Long orgId, Long businessCode);

    Map<Long, List<SetColumnsSpecialVo>> getSpecialByCode(Long orgId, Long businessCode);

    List<BusinessAssetDto> getInventoryProperty(String businessCode);

    List<BusinessAssetTypeDto> getAssetType(String businessCode);

    List<SetTaxRateDto> getTaxRateList(Long orgId);

    SetCurrency getBaseCurrency(Long orgId);

    Map<String, FiAccount> getAccountCode(Long orgId, List<String> codeList, List<AcmSortReceiptDetail> detailList);

//    DocAccountTemplateItem requestAdvice(DocAccountTemplateItem acmBusinessDoc, Map<String, FiAccount> codeMap, Long bankAccountId, Long investorId, List<ReceiptResult> fiDocReturnFailList, Long sortId);

    PaymentTemplateItem requestAdvice(PaymentTemplateItem acmPayDoc, Map<String, FiAccount> codeMap, AcmSortReceiptSettlestyle sett);

    DocAccountTemplateItem requestAdvice(DocAccountTemplateItem acmBusinessDoc, Map<String, FiAccount> codeMap, AcmSortReceiptDetail acmSortReceiptDetail, List<ReceiptResult> fiDocReturnFailList);
}
