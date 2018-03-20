package com.github.outerman.be.template;

import java.util.List;
import java.util.Map;

import com.github.outerman.be.model.BusinessVoucherDetail;
import com.github.outerman.be.model.DocTemplate;
import com.github.outerman.be.model.Account;
import com.github.outerman.be.model.SettleTemplate;

/**
 * Created by shenxy on 18/7/17.
 *
 * 模板数据的提供者接口
 */
public interface ITemplateProvider {

    List<DocTemplate> getBusinessTemplateByCode(Long orgId, String businessCode);

    List<SettleTemplate> getPayTemplate(Long orgId, String businessCode);

    Map<String, Account> getAccountCode(Long orgId, List<String> codeList, List<BusinessVoucherDetail> detailList);

}
