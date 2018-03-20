package com.github.outerman.be.businessDoc.dataProvider;

import java.util.List;

import com.github.outerman.be.model.AcmSortReceipt;
import com.github.outerman.be.model.FiDocDto;

/**
 * Created by shenxy on 18/7/17.
 *
 * 在验证时,提供凭证数据
 */
public interface IFiDocProvider {

    List<FiDocDto> getFiDocList(Long orgId, String businessCode);

    AcmSortReceipt getReceiptById(Long id, Long voucherId);

}
