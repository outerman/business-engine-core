package com.github.outerman.be.engine.businessDoc.dataProvider;

/**
 * Created by shenxy on 18/7/17.
 */
public interface ITestGenerateProvider {

    String constructSortReceiptByCode(Long orgId, String businessCode);

    String approveSortReceiptByCode(Long orgId, String businessCode);

}
