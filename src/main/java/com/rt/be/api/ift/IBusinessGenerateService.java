package com.rt.be.api.ift;

import com.rt.be.api.dto.FiDocGenetateResultDto;
import com.rt.be.api.vo.AcmSortReceipt;
import com.rt.be.api.vo.SetOrg;
import com.rt.be.engine.businessDoc.dataProvider.ITemplateProvider;

import java.util.List;

/**
 * Created by shenxy on 21/7/17.
 *
 * 凭证生成服务
 */
public interface IBusinessGenerateService {

    FiDocGenetateResultDto sortConvertVoucher(SetOrg setOrg, Long userId, String userName, List<AcmSortReceipt> receiptList, ITemplateProvider templateProvider);

}
