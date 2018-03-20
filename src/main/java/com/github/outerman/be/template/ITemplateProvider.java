package com.github.outerman.be.template;

import java.util.List;
import java.util.Map;

import com.github.outerman.be.model.AcmSortReceiptDetail;
import com.github.outerman.be.model.DocAccountTemplateItem;
import com.github.outerman.be.model.FiAccount;
import com.github.outerman.be.model.PaymentTemplateItem;
import com.github.outerman.be.model.SetCurrency;

/**
 * Created by shenxy on 18/7/17.
 *
 * 模板数据的提供者接口
 */
public interface ITemplateProvider {

    List<DocAccountTemplateItem> getBusinessTemplateByCode(Long orgId, String businessCode);

    List<PaymentTemplateItem> getPayTemplate(Long orgId, String businessCode);

    SetCurrency getBaseCurrency(Long orgId);

    Map<String, FiAccount> getAccountCode(Long orgId, List<String> codeList, List<AcmSortReceiptDetail> detailList);

}
