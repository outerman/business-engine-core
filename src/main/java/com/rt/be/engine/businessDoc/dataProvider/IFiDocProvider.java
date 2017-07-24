package com.rt.be.engine.businessDoc.dataProvider;

import com.rt.be.api.vo.AcmSortReceipt;
import com.rt.be.api.vo.FiDocDto;

import java.util.List;

/**
 * Created by shenxy on 18/7/17.
 *
 * 在验证时,提供凭证数据
 */
public interface IFiDocProvider {

    List<FiDocDto> getFiDocList(Long orgId, Long businessCode);

    AcmSortReceipt getReceiptById(Long id, Long voucherId);

}
