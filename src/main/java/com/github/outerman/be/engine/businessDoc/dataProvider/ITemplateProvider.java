package com.github.outerman.be.engine.businessDoc.dataProvider;

import java.util.List;
import java.util.Map;

import com.github.outerman.be.api.dto.BusinessAssetDto;
import com.github.outerman.be.api.dto.BusinessAssetTypeDto;
import com.github.outerman.be.api.vo.AcmSortReceiptDetail;
import com.github.outerman.be.api.vo.DocAccountTemplateItem;
import com.github.outerman.be.api.vo.FiAccount;
import com.github.outerman.be.api.vo.PaymentTemplateItem;
import com.github.outerman.be.api.vo.SetColumnsSpecialVo;
import com.github.outerman.be.api.vo.SetColumnsTacticsDto;
import com.github.outerman.be.api.vo.SetCurrency;

/**
 * Created by shenxy on 18/7/17.
 *
 * 模板数据的提供者接口
 */
public interface ITemplateProvider {

    List<DocAccountTemplateItem> getBusinessTemplateByCode(Long orgId, String businessCode);

    List<PaymentTemplateItem> getPayTemplate(Long orgId, String businessCode);

    Map<Long, List<SetColumnsTacticsDto>> getTacticsByCode(Long orgId, String businessCode);

    Map<Long, List<SetColumnsSpecialVo>> getSpecialByCode(Long orgId, String businessCode);

    List<BusinessAssetDto> getInventoryProperty(String businessCode);

    List<BusinessAssetTypeDto> getAssetType(String businessCode);

    SetCurrency getBaseCurrency(Long orgId);

    Map<String, FiAccount> getAccountCode(Long orgId, List<String> codeList, List<AcmSortReceiptDetail> detailList);

}
