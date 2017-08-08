package com.github.outerman.be.engine.businessDoc.dataProvider;

/**
 * Created by shenxy on 18/7/17.
 */
public interface ITestGenerateProvider {
    String constructSortReceiptByCode(Long orgId, Long businessCode);

    String approveSortReceiptByCode(Long orgId, Long businessCode);
}
