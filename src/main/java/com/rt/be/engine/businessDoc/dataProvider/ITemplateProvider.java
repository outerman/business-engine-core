package com.rt.be.engine.businessDoc.dataProvider;

import com.rt.be.api.vo.*;

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

    List<SetTaxRateDto> getTaxRateList(Long orgId);

    SetCurrency getBaseCurrency(Long orgId);
}
