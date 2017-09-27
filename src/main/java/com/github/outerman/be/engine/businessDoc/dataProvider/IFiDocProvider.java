package com.github.outerman.be.engine.businessDoc.dataProvider;

import com.github.outerman.be.api.vo.AcmSortReceipt;
import com.github.outerman.be.api.vo.FiDocDto;

import java.util.List;

/**
 * Created by shenxy on 18/7/17.
 *
 * 在验证时,提供凭证数据
 */
public interface IFiDocProvider {

    List<FiDocDto> getFiDocList(Long orgId, String businessCode);

    AcmSortReceipt getReceiptById(Long id, Long voucherId);

}
